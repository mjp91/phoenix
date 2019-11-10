import Vue from 'vue';
import Vuex from 'vuex';
import alert from './store/modules/alert';
import auth from './store/modules/auth';
import holiday from './store/modules/holiday';
import companyYear from './store/modules/companyYear';
import company from "./store/modules/company";
import user from "./store/modules/user";
import employee from "./store/modules/employee";
import role from "./store/modules/role";
import holidayEntitlement from "./store/modules/holidayEntitlement";
import resource from "./store/modules/resource";
import jobRole from "./store/modules/jobRole";
import department from "./store/modules/department";
import absence from "./store/modules/absence";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    alert,
    auth,
    holiday,
    companyYear,
    company,
    user,
    employee,
    role,
    holidayEntitlement,
    resource,
    jobRole,
    department,
    absence
  },
  state: {},
  mutations: {},
  actions: {}
});
