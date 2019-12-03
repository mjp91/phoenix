import Vue from 'vue';

const state = {
  employee: null,
  bradfordScore: null,
  employees: []
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
  }
};

const mutations = {
  setEmployees: (state, payload) => {
    state.employees = payload;
  },
  setEmployee: (state, payload) => {
    state.employee = payload;
  },
  setBradfordScore: (state, payload) => {
    state.bradfordScore = payload;
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
  saveEmployee: ({commit}, payload) => {
    return Vue.axios.post('/employee', payload).then((response) => {
      commit('setEmployee', response.data);
    });
  }
};

export default {
  state,
  getters,
  mutations,
  actions,
};
