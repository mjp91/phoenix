import Vue from 'vue';

const state = {
  company: null,
};

const getters = {
  getCompany: (state) => {
    return state.company;
  }
};

const mutations = {
  setCompany: (state, payload) => {
    state.company = payload;
  }
};

const actions = {
  fetchCompany: ({commit}) => {
    Vue.axios.get("/company").then((response) => {
      commit('setCompany', response.data);
    });
  },
  saveCompany: ({commit}, payload) => {
    Vue.axios.post("/company", payload).then((response) => {
      commit('setCompany', response.data);
      commit('addAlert', {
        type: 'success',
        message: 'Company updated'
      });
    });
  }
};

export default {
  state,
  getters,
  mutations,
  actions,
};
