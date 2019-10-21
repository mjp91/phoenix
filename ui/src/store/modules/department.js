import Vue from 'vue';

const state = {
  departments: []
};

const getters = {
  getDepartments: (state) = > {
  return state.departments;
}
}
;

const mutations = {
  setDepartments: (state, payload) = > {
  state.departments = payload;
}
}
;

const actions = {
  fetchDepartments: ({commit}) = > {
  Vue.axios.get('/department').then((response) = > {
    commit('setDepartments', response.data
)
;
})
;
},
saveDepartment: ({commit}, payload) =
>
{
  return Vue.axios.post('/department', payload).then(() = > {
    commit('addAlert',
  {
    type: 'success',
      message;
  :
    'Department created';
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
