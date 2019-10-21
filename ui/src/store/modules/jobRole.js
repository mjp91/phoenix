import Vue from 'vue';

const state = {
  jobRoles: []
};

const getters = {
  getJobRoles: (state) => {
    return state.jobRoles;
  }
};

const mutations = {
  setJobRoles: (state, payload) => {
    state.jobRoles = payload;
  }
};

const actions = {
  fetchJobRoles: ({commit}) => {
    Vue.axios.get('/job-role').then((response) => {
      commit('setJobRoles', response.data);
    });
  },
  saveJobRole: ({commit}, payload) => {
    return Vue.axios.post('/job-role', payload).then(() => {
      commit('addAlert', {
        type: 'success',
        message: 'Job role created'
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
