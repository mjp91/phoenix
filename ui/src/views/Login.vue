<template>
  <login-template title="Login">
    <v-form
        ref="form"
        v-model="valid"
        @keyup.native.enter="valid && login()"
        @submit.prevent="login"
    >
      <v-text-field
          v-model="loginDetails.username"
          label="Username"
          :rules="usernameRules"
          prepend-icon="mdi-account"
      />
      <v-text-field
          v-model="loginDetails.password"
          label="Password"
          type="password"
          :rules="passwordRules"
          prepend-icon="mdi-lock"
      />
      <template v-if="totpRequired">
        <totp-qr-code v-if="totpRegisterRequired" :totp-url="this.totpRegisterUrl"/>
        <v-text-field
            v-model="loginDetails.totpCode"
            label="Authenticator Code"
            :rules="totpCodeRules"
            prepend-icon="mdi-two-factor-authentication"
        />
      </template>
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
import {ApplicationErrorType} from "../lib/Constants";
import TotpQrCode from "../components/TotpQrCode";

export default {
    name: "Login",
    components: {TotpQrCode, LoginTemplate},
    data: () => {
      return {
        valid: false,
        totpRegisterRequired: false,
        totpRegisterUrl: null,
        totpRequired: false,
        loginDetails: {
          username: null,
          password: null,
          totpCode: null,
        },
        usernameRules: [
          v => !!v || 'Username is required'
        ],
        passwordRules: [
          v => !!v || 'Password is required'
        ],
        totpCodeRules: [
          v => !!v || 'Authenticator code is required'
        ]
      };
    },
    methods: {
      login() {
        let valid = this.$refs.form.validate();

        if (valid) {
          store.dispatch('login', this.loginDetails).catch((error) => {
            const response = error.response;
            if (response && response.status === 401) {
              const type = response.data.type;
              if (type === ApplicationErrorType.TOTP_REQUIRED) {
                this.totpRequired = true;
                store.commit('addAlert', {
                  type: "info",
                  message: "Please enter 2FA code"
                });
              } else if (type === ApplicationErrorType.TOTP_NOT_REGISTERED) {
                this.totpRequired = true;
                this.totpRegisterRequired = true;
                this.register2fa();
                store.commit('addAlert', {
                  type: "info",
                  message: "Please register for 2FA"
                });
              } else {
                let message = response.data.message;

                if (this.totpRequired) {
                  message = "Username, password or 2FA code is incorrect";
                }

                store.commit('addAlert', {
                  type: "warning",
                  message
                });
              }
            } else {
              console.error(error);
            }
          });
        }
      },
      register2fa() {
        store.dispatch('register2fa', this.loginDetails).then((response) => {
          this.totpRegisterUrl = response.data.totpUrl;
        });
      }
    }
  };
</script>

<style scoped>

</style>
