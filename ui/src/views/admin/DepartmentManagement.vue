<template>
  <v-data-table
      :headers="headers"
      :items="this.departments"
  >
    <template v-slot:top>
      <v-toolbar flat>
        <div class="flex-grow-1"></div>
        <v-btn color="success" to="/admin/department/new">New</v-btn>
      </v-toolbar>
    </template>
  </v-data-table>
</template>

<script>
import {mapActions, mapGetters} from "vuex";

export default {
    name: "DepartmentManagement",
    data: () => {
      return {
        headers: [
          {
            text: 'Description',
            value: 'title'
          }
        ]
      };
    },
    computed: {
      ...mapGetters({
        departments: 'getDepartments'
      })
    },
    methods: {
      ...mapActions({
        fetchDepartments: 'fetchDepartments'
      })
    },
    beforeMount() {
      this.fetchDepartments();
    }
  };
</script>

<style scoped>

</style>
