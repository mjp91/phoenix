import Vue from 'vue';

const state = {
  employee: null,
  bradfordScore: null,
  employees: [],
  upcomingBirthdays: [],
  upcomingServiceAnniversaries: []
};

const getters = {
  getEmployees: (state) => {
    return state.employees;
  },
  getEmployee: (state) => {
    return state.employee;
  },
  getBradfordScore: (state) => {
    return state.bradfordScore;
  },
  getUpcomingBirthdays: (state) => {
    return state.upcomingBirthdays;
  },
  getUpcomingServiceAnniversaries: (state) => {
    return state.upcomingServiceAnniversaries;
  }
};

const mutations = {
  setEmployees: (state, payload) => {
    state.employees = payload;
  },
  setEmployee: (state, payload) => {
    if (!payload.address) {
      payload.address = {
        line: null,
        city: null,
        province: null,
        postalCode: null,
        county: null
      };
    }

    state.employee = payload;
  },
  setBradfordScore: (state, payload) => {
    state.bradfordScore = payload;
  },
  setUpcomingBirthdays: (state, payload) => {
    state.upcomingBirthdays = payload;
  },
  setUpcomingServiceAnniversaries: (state, payload) => {
    state.upcomingServiceAnniversaries = payload;
  }
};

const actions = {
  fetchEmployee: ({commit}, payload) => {
    Vue.axios.get(`/employee/${payload}`).then((response) => {
      commit('setEmployee', response.data);
    });
  },
  fetchEmployees: ({commit}) => {
    Vue.axios.get("/employee").then((response) => {
      commit('setEmployees', response.data);
    });
  },
  fetchByUserId: ({commit}, payload) => {
    Vue.axios.get(`/employee/user/${payload}`).then((response) => {
      commit('setEmployee', response.data);
    });
  },
  fetchBradfordScore: ({commit}, payload) => {
    Vue.axios.get(`/absence/bradford-score/${payload}`).then((response) => {
      commit('setBradfordScore', response.data);
    });
  },
  fetchUpcomingBirthdays: ({commit}) => {
    Vue.axios.get(`/employee/upcoming-birthdays`).then((response) => {
      commit('setUpcomingBirthdays', response.data);
    });
  },
  fetchUpcomingServiceAnniversaries: ({commit}) => {
    Vue.axios.get(`/employee/upcoming-service-anniversaries`).then((response) => {
      commit('setUpcomingServiceAnniversaries', response.data);
    });
  },
  saveEmployee: ({commit}, payload) => {
    return Vue.axios.post('/employee', payload).then((response) => {
      commit('setEmployee', response.data);
    });
  },
  leaveEmployee: ({commit}, payload) => {
    return Vue.axios.post('/employee/leave', payload);
  }
};

export default {
  state,
  getters,
  mutations,
  actions,
};
