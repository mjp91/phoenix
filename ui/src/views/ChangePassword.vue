<template>
  <div>
    <form-header title="Change Password" :save="save" :cancel="cancel"/>
    <v-form
        ref="form"
        v-model="valid"
        @keyup.native.enter="valid && save()"
        @submit.prevent="save"
    >
      <v-row>
        <v-col sm="6">
          <v-text-field
              v-model="currentPassword"
              label="Current Password"
              type="password"
              prepend-icon="mdi-lock-clock"
              :rules="currentPasswordRules"
          />
          <v-text-field
              v-model="newPassword"
              label="New Password"
              type="password"
              prepend-icon="mdi-lock"
              :rules="newPasswordRules"
          />
          <v-text-field
              v-model="confirmNewPassword"
              label="Confirm New Password"
              type="password"
              prepend-icon="mdi-lock"
              :rules="confirmNewPasswordRules"
          />
        </v-col>
      </v-row>
    </v-form>
  </div>
</template>

<script>
import FormHeader from "../components/FormHeader";
import store from "../store";

export default {
    name: "ChangePassword",
    data: function () {
      return {
        valid: false,
        currentPassword: null,
        newPassword: null,
        confirmNewPassword: null,
        currentPasswordRules: [
          v => !!v || 'Current password is required'
        ],
        newPasswordRules: [
          v => !!v || 'New password is required'
        ],
        confirmNewPasswordRules: [
          v => !!v || 'Confirm new password is required',
          v => (!!v && v) === this.newPassword || 'Must match new password'
        ]
      };
    },
    methods: {
      save() {
        const valid = this.$refs.form.validate();

        if (valid) {
          store.dispatch('changePassword', {
            currentPassword: this.currentPassword,
            newPassword: this.newPassword
          }).then(() => {
            store.commit('addAlert', {
              type: 'success',
              message: 'Password changed successfully'
            });
          });
        }
      },
      cancel() {
        this.$router.push({
          name: "account"
        });
      }
    },
    components: {FormHeader}
  };
</script>

<style scoped>

</style>
