import Vue from 'vue';
import VueCookies from 'vue-cookies';
import jwtDecode from 'jwt-decode';
import router from '../../router';

Vue.use(VueCookies);

const getUserFromJwt = () => {
  const jwt = Vue.cookies.get('jwt');
  return jwtDecode(jwt).user;
};

const state = {
  loggedIn: Vue.cookies.isKey('jwt'),
  authUser: Vue.cookies.isKey('jwt') ? getUserFromJwt() : null
};

const getters = {
  isLoggedIn(state) {
    return state.loggedIn;
  },
  getAuthUser(state) {
    return state.authUser;
  }
};

const mutations = {
  setLoggedIn(state, payload) {
    state.loggedIn = payload;
  },
  setAuthUser(state, payload) {
    state.authUser = payload;
  }
};

const actions = {
  login({commit}, payload) {
    return Vue.axios.post('/login', payload).then((response) => {
      const token = response.headers.authorization.slice("BEARER ".length);

      window.$cookies.set('jwt', token);
      commit('setLoggedIn', true);
      commit('setAuthUser', response.data);
    });
  },
  clearCredentials({commit}) {
    window.$cookies.remove('jwt');
    commit('setLoggedIn', false);
    commit('setAuthUser', null);
  },
  logout({dispatch}) {
    dispatch('clearCredentials').then(() => {
      router.go();
    });
  }
};

export default {
  state,
  getters,
  mutations,
  actions,
};
