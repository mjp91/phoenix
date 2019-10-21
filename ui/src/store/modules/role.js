import Vue from 'vue';

const state = {
  roles: []
};

const getters = {
  getRoles: (state) => {
    return state.roles;
  }
};

const mutations = {
  setRoles: (state, payload) => {
    state.roles = payload;
  }
};

const actions = {
  fetchRoles: ({commit}) => {
    Vue.axios.get('/roles').then((response) => {
      commit('setRoles', response.data);
    });
  }
};

export default {
  state,
  getters,
  mutations,
  actions,
};
