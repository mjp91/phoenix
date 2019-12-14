<template>
  <login-template title="Login">
    <v-form
        ref="form"
        v-model="valid"
        @keyup.native.enter="valid && login()"
        @submit.prevent="login"
    >
      <v-text-field
          v-model="user.username"
          label="Username"
          :rules="usernameRules"
          prepend-icon="mdi-account"
          required>
      </v-text-field>
      <v-text-field
          v-model="user.password"
          label="Password"
          type="password"
          :rules="passwordRules"
          prepend-icon="mdi-lock"
          required>
      </v-text-field>
    </v-form>

    <template v-slot:actions>
      <v-btn to="/forgotten-password" text x-small>Forgot Password?</v-btn>
      <v-btn color="deep-orange" @click="login">Login</v-btn>
    </template>
  </login-template>
</template>

<script>
  import store from '../store';
  import LoginTemplate from "../components/LoginTemplate";

  export default {
    name: "Login",
    components: {LoginTemplate},
    data: () => {
      return {
        valid: false,
        user: {
          username: null,
          password: null
        },
        usernameRules: [
          v => !!v || 'Username is required'
        ],
        passwordRules: [
          v => !!v || 'Password is required'
        ],
      };
    },
    methods: {
      login() {
        let valid = this.$refs.form.validate();

        if (valid) {
          store.dispatch('login', this.user).catch((error) => {
            if (error.response && error.response.status === 401) {
              store.commit('addAlert', {
                type: "warning",
                message: "Username or Password is incorrect"
              });
            } else {
              console.error(error);
            }
          });
        }
      }
    }
  };
</script>

<style scoped>

</style>
