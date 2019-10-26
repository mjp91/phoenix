import Vue from 'vue';

const state = {
  user: null,
  users: []
};

const getters = {
  getUser: (state) => {
    return state.user;
  },
  getUsers: (state) => {
    return state.users;
  }
};

const mutations = {
  setUser: (state, payload) => {
    state.user = payload;
  },
  setUsers: (state, payload) => {
    state.users = payload;
  }
};

const actions = {
  fetchUser: ({commit}, payload) => {
    Vue.axios.get(`/users/${payload}`).then((response) => {
      commit('setUser', response.data);
    });
  },
  fetchUsers: ({commit}) => {
    Vue.axios.get('/users').then((response) => {
      commit('setUsers', response.data);
    });
  },
  saveUser: ({commit}, payload) => {
    Vue.axios.post('/users', payload).then((response) => {
      commit('setUser', response.data);
    });
  }
};

export default {
  state,
  getters,
  mutations,
  actions,
};
