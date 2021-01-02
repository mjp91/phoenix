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
  createUser: ({commit}, payload) => {
    return Vue.axios.put('/users/register', payload).then(() => {
      commit('addAlert', {
        type: 'success',
        message: 'User registered successfully'
      });
    });
  },
  resetPassword: ({commit}, payload) => {
    return Vue.axios.patch('/users/password-reset', payload);
  },
  reset2fa: ({commit}, payload) => {
    return Vue.axios.patch(`/users/2fa-reset/${payload}`);
  },
  register2fa: ({commit}, payload) => {
    return Vue.axios.patch(`/users/2fa-register`, payload);
  },
  forgottenPassword: ({commit}, payload) => {
    return Vue.axios.post(`/users/forgotten-password/${payload}`);
  },
  changePassword: ({commit}, payload) => {
    return Vue.axios.post('/users/change-password', payload);
  },
  saveUser: ({commit}, payload) => {
    return Vue.axios.post('/users', payload).then((response) => {
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
