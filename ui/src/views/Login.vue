<template>
  <v-container class="fill-height" fluid>
    <v-row justify="center" align="center">
      <v-col cols="12" sm="8" md="4">
        <v-alert type="warning" :value="!!error">{{error}}</v-alert>
        <v-card class="elevation-12">
          <v-toolbar
              color="deep-orange"
              dark
              flat
          >
            <v-toolbar-title>Holibyte</v-toolbar-title>
            <div class="flex-grow-1"></div>
            <v-subheader>Login</v-subheader>
          </v-toolbar>
          <v-card-text>
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
          </v-card-text>
          <v-card-actions>
            <div class="flex-grow-1"></div>
            <v-btn color="deep-orange" @click="login">Login</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
  import store from '../store';

  export default {
    name: "Login",
    data: () => {
      return {
        error: null,
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
              this.error = "Username or Password is incorrect";
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
