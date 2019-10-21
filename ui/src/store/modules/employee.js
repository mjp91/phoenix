import Vue from 'vue';

const state = {
  employee: null,
  employees: []
};

const getters = {
  getEmployees: (state) => {
    return state.employees;
  },
  getEmployee: (state) => {
    return state.employee;
  }
};

const mutations = {
  setEmployees: (state, payload) => {
    state.employees = payload;
  },
  setEmployee: (state, payload) => {
    state.employee = payload;
  }
};

const actions = {
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
