const state = {
  alerts: []
};

const getters = {
  getAlerts(state) {
    return state.alerts;
  }
};

const mutations = {
  addAlert(state, payload) {
    const notShown = state.alerts.filter((alert) => !alert.shown);
    notShown.push({
      message: "",
      shown: false,
      type: 'info',
      ...payload
    });

    state.alerts = notShown;
  },
  alertsShown(state) {
    state.alerts.map((alert) => alert.shown = true);
  },
};

const actions = {};

export default {
  state,
  getters,
  mutations,
  actions,
};