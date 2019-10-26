<template>
  <v-data-table
      :headers="headers"
      :items="users"
  >
    <template slot="item" slot-scope="props">
      <tr @click="edit(props.item.id)">
        <td>{{props.item.fullName}}</td>
        <td>
        <span v-if="isAdmin(props.item)">
          <v-icon>mdi-checkbox-marked-outline</v-icon>
        </span>
          <span v-else>
          <v-icon>mdi-checkbox-blank-outline</v-icon>
        </span>
        </td>
      </tr>
    </template>
  </v-data-table>
</template>

<script>
  import hasRole from "../lib/hasRole";
  import {Roles} from "../lib/Constants";

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
      edit(id) {
        this.$router.push({
          name: 'user',
          params: {
            id
          }
        });
      }
    }
  };
</script>

<style scoped>

</style>
