<template>
  <login-template title="Forgotten Password">
    <v-form
        ref="form"
        v-model="valid"
        @keyup.native.enter="valid && submit()"
        @submit.prevent="submit"
    >
      <v-text-field
          v-model="username"
          label="Username"
          prepend-icon="mdi-account"
          :rules="usernameRules"
      />
    </v-form>
    <template v-slot:actions>
      <v-btn to="/">Cancel</v-btn>
      <v-btn color="deep-orange" @click="submit">Submit</v-btn>
    </template>
  </login-template>
</template>

<script>
import LoginTemplate from "../components/LoginTemplate";
import store from "../store";

export default {
    name: "ForgottenPassword",
    data: () => {
      return {
        valid: false,
        username: null,
        usernameRules: [
          v => !!v || 'Username is required'
        ],
      };
    },
    methods: {
      submit() {
        const valid = this.$refs.form.validate();

        if (valid) {
          store.dispatch('forgottenPassword', this.username).then(() => {
            return store.commit('addAlert', {
              type: 'success',
              message: 'Password reset request received, please check your inbox'
            });
          }).then(() => {
            this.$router.push("/");
          });
        }
      }
    },
    components: {LoginTemplate},
  };
</script>

<style scoped>

</style>
