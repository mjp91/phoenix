import Vue from 'vue';

const state = {
  departments: [],
  departmentEmployees: []
};

const getters = {
  getDepartments: (state) => {
    return state.departments;
  },
  getDepartmentEmployees: (state) => {
    return state.departmentEmployees;
  }
};

const mutations = {
  setDepartments: (state, payload) => {
    state.departments = payload;
  },
  setDepartmentEmployees: (state, payload) => {
    state.departmentEmployees = payload;
  }
};

const actions = {
  fetchDepartments: ({commit}) => {
    Vue.axios.get('/department').then((response) => {
      commit('setDepartments', response.data);
    });
  },
  fetchDepartmentEmployees: ({commit}) => {
    Vue.axios.get('/department/employee').then((response) => {
      commit('setDepartmentEmployees', response.data);
    });
  },
  saveDepartment: ({commit}, payload) => {
    return Vue.axios.post('/department', payload).then(() => {
      commit('addAlert', {
        type: 'success',
        message: 'Department created'
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
