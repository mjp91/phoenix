import Vue from 'vue';

const state = {
  companyYear: null,
  companyYears: []
};

const getters = {
  getCompanyYear: (state) => {
    return state.companyYear;
  },
  getCompanyYears: (state) => {
    return state.companyYears;
  }
};

const mutations = {
  setCompanyYear: (state, payload) => {
    state.companyYear = payload;
  },
  setCompanyYears: (state, payload) => {
    state.companyYears = payload;
  }
};

const actions = {
  fetchCompanyYears: ({commit}) => {
    Vue.axios.get('/company-year').then(response => {
      commit('setCompanyYears', response.data);
    });
  },
  saveCompanyYear: ({commit}, payload) => {
    return Vue.axios.post('/company-year', payload).then(response => {
      commit('setCompanyYear', response.data);
    });
  }
};

export default {
  state,
  getters,
  mutations,
  actions,
};
