<template>
  <user-grid :users="this.users"/>
</template>

<script>
import UserGrid from "../../components/UserGrid";
import {mapActions, mapGetters} from 'vuex';

export default {
    name: "UserManagement",
    computed: {
      ...mapGetters({
        users: 'getUsers'
      })
    },
    methods: {
      ...mapActions({
        fetchUsers: 'fetchUsers'
      })
    },
    beforeMount() {
      this.fetchUsers();
    },
    components: {UserGrid}
  };
</script>

<style scoped>

</style>
