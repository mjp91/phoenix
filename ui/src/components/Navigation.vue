<template>
  <div>
    <v-navigation-drawer
        v-model="drawer"
        app
        clipped
        class="grey darken-4"
    >
      <v-list dense>
        <template v-for="item in navigation">
          <v-list-item
              v-if="!item.children && (!item.admin || (item.admin && hasAdmin()))"
              :to="item.path"
              :exact="item.exact"
              :key="item.title">
            <v-list-item-action>
              <v-icon>{{item.icon}}</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>{{item.title}}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <v-list-group
              v-else-if="item.children && (!item.admin || (item.admin && hasAdmin()))"
              :key="item.title"
              :prepend-icon="item.icon"
          >
            <template v-slot:activator>
              <v-list-item-title>{{item.title}}</v-list-item-title>
            </template>
            <template v-for="child in item.children">
              <v-list-item
                  v-if="(!child.admin || (child.admin && hasAdmin()))"
                  :to="child.path"
                  :exact="child.exact"
                  :key="child.title">
                <v-list-item-action>
                  <v-icon>{{child.icon}}</v-icon>
                </v-list-item-action>
                <v-list-item-content>
                  <v-list-item-title>{{child.title}}</v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </template>
          </v-list-group>
        </template>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar app clipped-left class="grey darken-4">
      <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title class="headline text-uppercase">
        <span>Holibyte</span>
      </v-toolbar-title>
      <v-spacer/>
      {{user.fullName}}
      <div class="mr-2"></div>
      <v-btn icon to="/account">
        <v-icon>mdi-account</v-icon>
      </v-btn>
      <v-btn icon @click="logout">
        <v-icon>mdi-exit-to-app</v-icon>
      </v-btn>
    </v-app-bar>
  </div>
</template>

<script>
  import {mapActions, mapGetters} from "vuex";
  import UserMixin from "../mixins/UserMixin";

  export default {
    name: "Navigation",
    data() {
      return {
        drawer: null,
        navigation: [
          {
            title: 'Dashboard',
            path: '/',
            icon: 'mdi-view-dashboard'
          },
          {
            title: 'Employee Search',
            path: '/employee/search',
            icon: 'mdi-account-search'
          },
          {
            title: 'Absence',
            path: '/absence',
            icon: 'mdi-hotel'
          },
          {
            title: 'Holiday',
            path: '/holiday',
            exact: true,
            icon: 'mdi-palm-tree'
          },
          {
            title: 'Requests',
            path: '/holiday/requests',
            icon: 'mdi-inbox-arrow-down'
          },
          {
            title: 'Admin',
            icon: 'mdi-settings',
            admin: true,
            children: [
              {
                title: 'Company',
                path: '/admin/company',
                icon: 'mdi-office-building',
                admin: true,
              },
              {
                title: 'Departments',
                path: '/admin/department',
                icon: 'mdi-file-tree',
                admin: true
              },
              {
                title: 'Holiday Years',
                path: '/admin/holiday-year',
                icon: 'mdi-calendar-blank',
                admin: true
              },
              {
                title: 'Job Roles',
                path: '/admin/job-role',
                icon: 'mdi-account-badge-horizontal',
                admin: true,
              },
              {
                title: 'Users',
                path: '/admin/user',
                icon: 'mdi-account-group',
                admin: true
              }
            ]
          }
        ]
      };
    },
    computed: {
      ...mapGetters({
        user: 'getAuthUser',
      }),
    },
    methods: {
      ...mapActions({
        logout: 'logout'
      }),
    },
    mixins: [UserMixin],
  };
</script>

<style scoped>

</style>
