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
    </v-form>
    <template v-slot:actions>
      <v-btn color="deep-orange" @click="reset">Reset</v-btn>
    </template>
  </login-template>
</template>

<script>
  import LoginTemplate from "../components/LoginTemplate";
  import store from "../store";

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
          password: null
        },
        confirmPassword: null,
        passwordRules: [
          v => !!v || 'Password is required'
        ],
        confirmPasswordRules: [
          v => !!v || 'Confirmed password is required',
          v => (!!v && v) === this.passwordReset.password || 'Must match password'
        ]
      };
    },
    methods: {
      reset() {
        let valid = this.$refs.form.validate();

        if (valid) {
          const alert = {};
          store.dispatch('resetPassword', this.passwordReset).then(() => {
            alert.type = "success";
            alert.message = "Password reset successful";

            this.$router.push("/");
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
    components: {LoginTemplate}
  };
</script>

<style scoped>

</style>
