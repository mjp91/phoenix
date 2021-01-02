import Vue from 'vue';

const state = {
  absence: null,
  absences: [],
  absenceAuthorisations: [],
  daysAbsent: 0
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
  },
  getAbsenceDays: (state) => {
    return state.daysAbsent;
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
  },
  setAbsenceDays: (state, payload) => {
    state.daysAbsent = payload;
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
      commit('setAbsences', response.data);
    });
  },
  fetchAllAbsences: ({commit}) => {
    Vue.axios.get('/absence/all').then(response => {
      commit('setAbsences', response.data);
    });
  },
  fetchAbsenceAuthorisations: ({commit}) => {
    Vue.axios.get('/absence/authorisation').then((response) => {
      commit('setAbsenceAuthorisations', response.data);
    });
  },
  fetchAbsenceDays: ({commit}) => {
    Vue.axios.get('/absence/days').then((response) => {
      commit('setAbsenceDays', response.data.daysAbsent);
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
  authoriseAbsence: ({commit, dispatch}, payload) => {
    Vue.axios.patch(`/absence/authorise/${payload.id}`).then(() => {
      commit('addAlert', {
        type: 'success',
        message: 'Absence authorised'
      });
      dispatch('fetchAbsenceAuthorisations');
    });
  },
  unauthoriseAbsence: ({commit, dispatch}, payload) => {
    Vue.axios.patch(`/absence/unauthorise/${payload.absence.id}`, {
      reason: payload.reason
    }).then(() => {
      commit('addAlert', {
        type: 'success',
        message: 'Absence unauthorised'
      });
      dispatch('fetchAbsenceAuthorisations');
    });
  },
  cancelAbsence: ({commit, dispatch}, payload) => {
    Vue.axios.patch(`/absence/cancel/${payload.id}`).then(() => {
      commit('addAlert', {
        type: 'success',
        message: 'Absence cancelled'
      });
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
