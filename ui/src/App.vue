<template>
  <v-app>
    <template v-if="isLoggedIn">
      <navigation/>
      <v-content>
        <v-container fluid>
          <alert/>
          <router-view/>
        </v-container>
      </v-content>
    </template>
    <v-content v-else>
      <template v-if="$route.name === 'password-reset'">
        <password-reset/>
      </template>
      <template v-else>
        <login/>
      </template>
    </v-content>
  </v-app>
</template>

<script>
  import Alert from './components/Alert';
  import {mapGetters} from 'vuex';
  import Login from "./views/Login";
  import UserMixin from "./mixins/UserMixin";
  import Navigation from "./components/Navigation";
  import PasswordReset from "./views/PasswordReset";

  export default {
    name: 'App',
    computed: {
      ...mapGetters({
        loggedIn: 'isLoggedIn',
      }),
      isLoggedIn() {
        return this.loggedIn || window.$cookies.isKey('jwt');
      },
    },
    mixins: [UserMixin],
    components: {
      PasswordReset,
      Navigation,
      Login,
      Alert
    }
  };
</script>
