import Vue from 'vue';

const state = {
  holiday: {},
  holidays: [],
  holidayRequests: [],
  mostRecentRequests: [],
  todaysHolidays: {},
};

const getters = {
  getHoliday: (state) => {
    return state.holiday;
  },
  getHolidays: (state) => {
    return state.holidays;
  },
  getHolidayRequests: (state) => {
    return state.holidayRequests;
  },
  getMostRecentHolidayRequests: (state) => {
    return state.mostRecentRequests;
  },
  getTodaysHolidays: (state) => {
    return state.todaysHolidays;
  }
};

const mutations = {
  setHoliday: (state, payload) => {
    state.holiday = payload;
  },
  setHolidays: (state, payload) => {
    state.holidays = payload;
  },
  setHolidayRequests: (state, payload) => {
    state.holidayRequests = payload;
  },
  setMostRecentHolidayRequests: (state, payload) => {
    state.mostRecentRequests = payload;
  },
  setTodaysHolidays: (state, payload) => {
    state.todaysHolidays = payload;
  }
};

const actions = {
  fetchHoliday: ({commit}) => {
    Vue.axios.get('/holiday/current').then(response => {
      commit('setHoliday', response.data);
    });
  },
  fetchHolidays: ({commit}) => {
    Vue.axios.get('/holiday').then(response => {
      commit('setHolidays', response.data);
    });
  },
  fetchAllHolidays: ({commit}) => {
    Vue.axios.get('/holiday/all').then(response => {
      commit('setHolidays', response.data);
    });
  },
  fetchHolidayRequests: ({commit}) => {
    Vue.axios.get('/holiday/requests').then(response => {
      commit('setHolidayRequests', response.data);
    });
  },
  fetchMostRecentHolidayRequests: ({commit}) => {
    Vue.axios.get('/holiday/requests/page?page=0&size=5').then(response => {
      commit('setMostRecentHolidayRequests', response.data);
    });
  },
  fetchTodaysHolidays: ({commit}) => {
    Vue.axios.get('/holiday/today').then(response => {
      commit('setTodaysHolidays', response.data);
    });
  },
  saveHoliday: ({commit}, payload) => {
    if (!payload.endDate) {
      payload.endDate = payload.startDate;
    }

    return Vue.axios.post('/holiday', payload).then(response => {
      commit('setHoliday', response.data);
    });
  },
  cancelHoliday: ({dispatch}, payload) => {
    Vue.axios.patch(`/holiday/cancel/${payload.id}`).then(() => {
      dispatch('fetchHolidays');
    });
  },
  approveHoliday: ({commit, dispatch}, payload) => {
    Vue.axios.patch(`/holiday/approve/${payload.id}`).then(() => {
      commit('addAlert', {
        type: 'success',
        message: 'Holiday approved'
      });
      dispatch('fetchHolidayRequests');
    });
  },
  disapproveHoliday: ({commit, dispatch}, payload) => {
    Vue.axios.patch(`/holiday/disapprove/${payload.holiday.id}`, {
      reason: payload.reason,
    }).then(() => {
      commit('addAlert', {
        type: 'success',
        message: 'Holiday disapproved'
      });
      dispatch('fetchHolidayRequests');
    });
  }
};

export default {
  state,
  getters,
  mutations,
  actions,
};
