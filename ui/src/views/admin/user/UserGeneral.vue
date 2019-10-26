<template>
  <v-row>
    <v-col sm="6">
      <v-card flat class="pa-4">
        <v-text-field
            label="Username"
            :value="user.username"
            readonly
        />
        <v-text-field
            label="Email"
            :value="user.email"
            readonly
        />
        <v-select
            v-model="user.roles"
            :items="this.roles"
            chips
            label="Roles"
            multiple
            clearable
            deletable-chips
            :item-text="roleDisplay"
            :item-value="roleValue"
        ></v-select>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
  import {mapActions, mapGetters} from "vuex";

  export default {
    name: "UserGeneral",
    props: {
      user: Object
    },
    computed: {
      ...mapGetters({
        roles: 'getRoles',
      })
    },
    methods: {
      ...mapActions({
        fetchRoles: 'fetchRoles',
      }),
      roleDisplay(role) {
        const name = role.name;

        return name.substr(5, name.length);
      },
      roleValue(role) {
        return role;
      },
    },
    beforeMount() {
      this.fetchRoles();
    }
  };
</script>

<style scoped>

</style>
