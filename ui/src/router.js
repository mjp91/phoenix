import Vue from 'vue';
import Router from 'vue-router';
import Home from './views/Home.vue';
import store from './store';
import hasRole from "./lib/hasRole";
import {Roles} from "./lib/Constants";

Vue.use(Router);

const isAdminBeforeEnter = (to, from, next) => {
  const user = store.getters.getAuthUser;

  next(hasRole(user, Roles.admin));
};

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/holiday',
      name: 'holiday',
      component: () => import('./views/Holiday.vue')
    },
    {
      path: '/holiday/new',
      name: 'holidayForm',
      component: () => import('./views/HolidayForm.vue')
    },
    {
      path: '/holiday/requests',
      name: 'holidayRequests',
      component: () => import('./views/HolidayRequests.vue')
    },
    {
      path: '/absence',
      name: 'absence',
      component: () => import('./views/Absence')
    },
    {
      path: '/absence/new',
      name: 'absenceForm',
      component: () => import('./views/AbsenceForm')
    },
    {
      path: '/employee/search',
      name: 'employeeSearch',
      component: () => import('./views/EmployeeLookup')
    },
    {
      path: '/admin/company',
      name: 'admin-company',
      component: () => import('./views/admin/CompanyForm'),
      beforeEnter: isAdminBeforeEnter
    },
    {
      path: '/admin/user',
      name: 'user-management',
      component: () => import('./views/admin/UserManagement'),
      beforeEnter: isAdminBeforeEnter
    },
    {
      path: '/admin/user/:id',
      name: 'user',
      component: () => import('./views/admin/user/UserForm'),
      beforeEnter: isAdminBeforeEnter
    },
    {
      path: '/admin/holiday-year',
      name: 'holiday-year-management',
      component: () => import('./views/admin/HolidayYearManagement'),
      beforeEnter: isAdminBeforeEnter
    },
    {
      path: '/admin/holiday-year/new',
      name: 'holidayYear',
      component: () => import('./views/admin/HolidayYearForm'),
      beforeEnter: isAdminBeforeEnter
    },
    {
      path: '/admin/user/:id/holiday-entitlement/new',
      name: 'holidayEntitlement',
      component: () => import('./views/admin/user/UserEmployeeEntitlementForm'),
      beforeEnter: isAdminBeforeEnter
    },
    {
      path: '/admin/job-role',
      name: 'job-role-management',
      component: () => import('./views/admin/JobRoleManagement'),
      beforeEnter: isAdminBeforeEnter
    },
    {
      path: '/admin/job-role/new',
      name: 'job-role',
      component: () => import('./views/admin/JobRoleForm'),
      beforeEnter: isAdminBeforeEnter
    },
    {
      path: '/admin/department',
      name: 'department-management',
      component: () => import('./views/admin/DepartmentManagement'),
      beforeEnter: isAdminBeforeEnter
    },
    {
      path: '/admin/department/new',
      name: 'department',
      component: () => import('./views/admin/DepartmentForm'),
      beforeEnter: isAdminBeforeEnter
    },
    {
      path: '/account',
      name: 'account',
      component: () => import('./views/Account.vue'),
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "about" */ './views/About.vue')
    }
  ]
});
