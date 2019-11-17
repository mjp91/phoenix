import Vue from 'vue';

const state = {
  absence: null,
  absences: [],
  absenceAuthorisations: []
};

const getters = {
  getAbsence: (state) => {
    return state.absence;
  },
  getAbsences: (state) => {
    return state.absences;
  },
  getAbsenceAuthorisations: (state) => {
    return state.absenceAuthorisations;
  }
};

const mutations = {
  setAbsence: (state, payload) => {
    state.absence = payload;
  },
  setAbsences: (state, payload) => {
    state.absences = payload;
  },
  setAbsenceAuthorisations: (state, payload) => {
    state.absenceAuthorisations = payload;
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
  fetchAbsenceAuthorisations: ({commit}) => {
    Vue.axios.get('/absence/authorisation').then((response) => {
      commit('setAbsenceAuthorisations', response.data);
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
  authoriseAbsence: ({dispatch}, payload) => {
    Vue.axios.patch(`/absence/authorise/${payload.id}`).then(() => {
      dispatch('fetchAbsenceAuthorisations');
    });
  },
  unauthoriseAbsence: ({dispatch}, payload) => {
    Vue.axios.patch(`/absence/unauthorise/${payload.id}`).then(() => {
      dispatch('fetchAbsenceAuthorisations');
    });
  }
};

export default {
  state,
  getters,
  mutations,
  actions
};
