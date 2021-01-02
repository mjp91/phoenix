import Vue from 'vue';

const state = {
  holidayEntitlement: []
};

const getters = {
  getHolidayEntitlement: (state) => {
    return state.holidayEntitlement;
  }
};

const mutations = {
  setHolidayEntitlement: (state, payload) => {
    state.holidayEntitlement = payload;
  }
};

const actions = {
  fetchHolidayEntitlement: ({commit}, payload) => {
    Vue.axios.get(`/holiday-entitlement/employee/${payload.employeeId}`).then((response) => {
      commit('setHolidayEntitlement', response.data);
    });
  },
  saveHolidayEntitlement: ({commit}, payload) => {
    return Vue.axios.post('/holiday-entitlement', payload).then(() => {
      commit('addAlert', {
        type: 'success',
        message: 'Holiday entitlement saved'
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
