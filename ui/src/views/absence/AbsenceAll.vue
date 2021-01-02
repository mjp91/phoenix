<template>
  <v-data-table
      :headers="headers"
      :items="this.absences"
  >
    <template v-slot:item.authorized="{ item }">
      <v-icon v-if="item.authorized === true" color="success">mdi-thumb-up</v-icon>
      <v-icon v-else-if="item.authorized === false" color="error">mdi-thumb-down</v-icon>
      <v-icon v-else color="warning">mdi-clock</v-icon>
    </template>
  </v-data-table>
</template>

<script>
import {mapActions, mapGetters} from "vuex";

export default {
    name: "AbsenceAll",
    data: () => {
      return {
        headers: [
          {
            text: 'Year',
            value: 'companyYear.name'
          },
          {
            text: 'Employee',
            value: 'employee.user.fullName'
          },
          {
            text: 'Start',
            value: 'start'
          },
          {
            text: 'End',
            value: 'end'
          },
          {
            text: 'Authorized',
            value: 'authorized'
          }
        ]
      };
    },
    computed: {
      ...mapGetters({
        absences: 'getAbsences'
      })
    },
    methods: {
      ...mapActions({
        fetchAllAbsences: 'fetchAllAbsences'
      })
    },
    beforeMount() {
      this.fetchAllAbsences();
    }
  };
</script>

<style scoped>

</style>
