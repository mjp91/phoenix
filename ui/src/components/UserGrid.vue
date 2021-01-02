<template>
  <v-data-table
      :headers="headers"
      :items="users"
      @click:row="edit"
  >
    <template v-if="hasAdmin" v-slot:top>
      <v-toolbar flat>
        <div class="flex-grow-1"></div>
        <v-btn color="success" to="/admin/user/new">New</v-btn>
      </v-toolbar>
    </template>

    <template v-slot:item.admin="{ item }">
      <span v-if="isAdmin(item)">
          <v-icon>mdi-checkbox-marked-outline</v-icon>
      </span>
      <span v-else>
          <v-icon>mdi-checkbox-blank-outline</v-icon>
      </span>
    </template>

    <template v-slot:item.ldap="{ item }">
      <span v-if="item.ldap">
          <v-icon>mdi-checkbox-marked-outline</v-icon>
      </span>
      <span v-else>
          <v-icon>mdi-checkbox-blank-outline</v-icon>
      </span>
    </template>

    <template v-slot:item.totpEnabled="{ item }">
      <span v-if="item.totpEnabled">
          <v-icon>mdi-checkbox-marked-outline</v-icon>
      </span>
      <span v-else>
          <v-icon>mdi-checkbox-blank-outline</v-icon>
      </span>
    </template>

    <template v-slot:item.enabled="{ item }">
      <span v-if="item.enabled">
          <v-icon>mdi-checkbox-marked-outline</v-icon>
      </span>
      <span v-else>
          <v-icon>mdi-checkbox-blank-outline</v-icon>
      </span>
    </template>
  </v-data-table>
</template>

<script>
import hasRole from "../lib/hasRole";
import {Roles} from "../lib/Constants";
import UserMixin from "../mixins/UserMixin";

export default {
    name: "UserGrid",
    data: () => {
      return {
        headers: [
          {
            text: 'Name',
            value: 'fullName'
          },
          {
            text: 'Admin',
            value: 'admin',
            sortable: false
          },
          {
            text: 'LDAP',
            value: 'ldap',
            sortable: false
          },
          {
            text: '2FA',
            value: 'totpEnabled',
            sortable: false
          },
          {
            text: 'Enabled',
            value: 'enabled',
            sortable: false
          }
        ]
      };
    },
    props: {
      users: Array
    },
    methods: {
      isAdmin(user) {
        return hasRole(user, Roles.admin);
      },
      edit(row) {
        this.$router.push({
          name: 'user',
          params: {
            id: row.id
          }
        });
      }
    },
    mixins: [UserMixin],
  };
</script>

<style scoped>

</style>
