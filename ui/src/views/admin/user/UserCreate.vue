<template>
  <div>
    <form-header title="New User" :save="createUser" :cancel="close"/>
    <user-general :user="user" :read-only="false"/>
  </div>
</template>

<script>
import UserGeneral from "./UserGeneral";
import FormHeader from "../../../components/FormHeader";
import store from "../../../store";

export default {
    name: "UserCreate",
    data: () => {
      return {
        user: {
          username: null,
          email: null,
          fullName: null,
          roles: []
        }
      };
    },
    methods: {
      close() {
        this.$router.push({
          name: 'user-management',
        });
      },
      createUser() {
        store.dispatch('createUser', this.user).then(() => {
          this.close();
        });
      }
    },
    components: {FormHeader, UserGeneral}
  };
</script>

<style scoped>

</style>
