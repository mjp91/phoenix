<template>
  <v-row>
    <v-col sm="6">
      <v-text-field
          label="Full Name"
          v-model="user.fullName"
          :readonly="this.readOnly"
      />
      <v-text-field
          label="Username"
          v-model="user.username"
          :readonly="this.readOnly"
      />
      <v-text-field
          label="Email"
          v-model="user.email"
          :readonly="this.readOnly"
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
    </v-col>
  </v-row>
</template>

<script>
  import {mapActions, mapGetters} from "vuex";

  export default {
    name: "UserGeneral",
    props: {
      user: Object,
      readOnly: {
        type: Boolean,
        default: true
      }
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
