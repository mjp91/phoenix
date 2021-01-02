<template>
  <login-template title="Password Reset">
    <v-form
        ref="form"
        v-model="valid"
        @keyup.native.enter="valid && reset()"
        @submit.prevent="reset"
    >
      <v-text-field
          v-model="passwordReset.password"
          label="Password"
          type="password"
          prepend-icon="mdi-lock"
          :rules="passwordRules"
          required
      />
      <v-text-field
          v-model="confirmPassword"
          label="Confirm Password"
          type="password"
          prepend-icon="mdi-lock"
          :rules="confirmPasswordRules"
          required
      />
      <template v-if="totpRequired">
        <totp-qr-code :totp-url="totpUrl"/>
        <v-text-field
            v-model="passwordReset.totpCode"
            label="Authenticator Code"
            :rules="totpCodeRules"
            prepend-icon="mdi-two-factor-authentication"
        />
      </template>
    </v-form>
    <template v-slot:actions>
      <v-btn to="/">Cancel</v-btn>
      <v-btn color="deep-orange" @click="reset">Reset</v-btn>
    </template>
  </login-template>
</template>

<script>
import LoginTemplate from "../components/LoginTemplate";
import store from "../store";
import TotpQrCode from "../components/TotpQrCode";

export default {
    name: "PasswordReset",
    data: function () {
      return {
        valid: false,
        alert: {
          type: 'warning',
          message: null
        },
        passwordReset: {
          token: this.$route.params.token,
          password: null,
          totpCode: null
        },
        confirmPassword: null,
        totpRequired: false,
        totpUrl: null,
        passwordRules: [
          v => !!v || 'Password is required'
        ],
        confirmPasswordRules: [
          v => !!v || 'Confirmed password is required',
          v => (!!v && v) === this.passwordReset.password || 'Must match password'
        ],
        totpCodeRules: [
          v => !!v || 'Authenticator code is required'
        ]
      };
    },
    methods: {
      reset() {
        const valid = this.$refs.form.validate();

        if (valid) {
          const alert = {};
          store.dispatch('resetPassword', this.passwordReset).then((response) => {
            if (!response.data.success) {
              alert.type = "warning";
              alert.message = response.data.message;

              this.totpRequired = response.data.totpRequired;
              this.totpUrl = response.data.totpUrl;
            } else {
              alert.type = "success";
              alert.message = "Password reset successful";

              this.$router.push("/");
            }
          }).catch(error => {
            if (error.response) {
              const status = error.response.status;

              alert.type = "warning";
              if (status === 401) {
                alert.message = "Invalid request";
              } else if (status === 404) {
                alert.message = "Invalid token";
              } else {
                console.error(error);
              }
            } else {
              console.error(error);
            }
          }).finally(() => {
            store.commit('addAlert', alert);
          });
        }
      }
    },
    components: {TotpQrCode, LoginTemplate}
  };
</script>

