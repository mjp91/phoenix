import Vue from 'vue';
import vuetify from './plugins/vuetify';
import App from './App.vue';
import router from './router';
import store from './store';
import VueAxios from "vue-axios";
import axios from 'axios';
import VueCookies from 'vue-cookies';
import getBaseUrl from "./lib/getBaseUrl";

Vue.config.productionTip = false;

axios.defaults.baseURL = getBaseUrl();
axios.interceptors.request.use((config) => {
  const auth = window.$cookies.get('jwt');

  if (auth) {
    config.headers.Authorization = `Bearer ${auth}`;
  }
  return config;

});
axios.interceptors.response.use((response) => {
  return response;
}, (error) => {
  const status = error.response.status;
  if (typeof error.response === "undefined" || status === 401) {
    store.dispatch('clearCredentials');
  } else {
    let message = `[${status}]: `;
    if (error.response.data) {
      message += error.response.data.message;
    } else {
      message += error.message;
    }

    let type = 'error';
    if (status >= 400 && status <= 499) {
      type = 'warning';
    }

    store.commit('addAlert', {message, type});
  }

  return Promise.reject(error);
});

Vue.use(VueCookies);
Vue.use(VueAxios, axios);

new Vue({
  vuetify,
  router,
  store,
  render: h => h(App)
}).$mount('#app');
