import Vue from 'vue';

const state = {};

const getters = {};

const mutations = {};

const actions = {
  uploadResource: ({commit}, payload) => {
    const formData = new FormData();
    formData.append('file', payload);

    return Vue.axios.post('/resource', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    }).then((response) => {
      return response.data;
    });
  }
};

export default {
  state,
  getters,
  mutations,
  actions,
};
