import Vue from 'vue';

const state = {
  absence: null,
  absences: []
};

const getters = {
  getAbsence: (state) => {
    return state.absence;
  },
  getAbsences: (state) => {
    return state.absences;
  }
};

const mutations = {
  setAbsence: (state, payload) => {
    state.absence = payload;
  },
  setAbsences: (state, payload) => {
    state.absences = payload;
  }
};

const actions = {
  fetchAbsence: ({commit}, payload) => {
    Vue.axios.get(`/absence/${payload}`).then((response) => {
      commit('setAbsence', response.data);
    });
  },
  fetchAbsences: ({commit}) => {
    Vue.axios.get('/absence').then((response) => {
      commit('setAbsences', response.data.content);
    });
  },
  createAbsence: ({commit}, payload) => {
    return Vue.axios.put('/absence', payload).then((response) => {
      commit('setAbsence', response.data);
    });
  },
  saveAbsence: ({commit}, payload) => {
    return Vue.axios.patch(`/absence/${payload.id}`, payload.absenceUpdate).then((response) => {
      commit('setAbsence', response.data);
    });
  },
};

export default {
  state,
  getters,
  mutations,
  actions
};
