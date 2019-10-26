import Vue from 'vue';

const state = {
  holidayYear: null,
  holidayYears: []
};

const getters = {
  getHolidayYear: (state) => {
    return state.holidayYear;
  },
  getHolidayYears: (state) => {
    return state.holidayYears;
  }
};

const mutations = {
  setHolidayYear: (state, payload) => {
    state.holidayYear = payload;
  },
  setHolidayYears: (state, payload) => {
    state.holidayYears = payload;
  }
};

const actions = {
  fetchHolidayYears: ({commit}) => {
    Vue.axios.get('/holiday-year').then(response => {
      commit('setHolidayYears', response.data);
    });
  },
  saveHolidayYear: ({commit}, payload) => {
    return Vue.axios.post('/holiday-year', payload).then(response => {
      commit('setHolidayYear', response.data);
    });
  }
};

export default {
  state,
  getters,
  mutations,
  actions,
};
