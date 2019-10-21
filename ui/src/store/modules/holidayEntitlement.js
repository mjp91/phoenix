import Vue from 'vue';

const state = {};

const getters = {};

const mutations = {};

const actions = {
  saveHolidayEntitlement: ({commit}, payload) = > {
  return Vue.axios.post('/holiday-entitlement', payload).then(() = > {
    commit('addAlert',
{
  type: 'success',
    message;
:
  'Holiday entitlement saved';
}
)
;
})
;
}
}
;

export default {
  state,
  getters,
  mutations,
  actions,
};
