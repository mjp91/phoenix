<template>
  <v-data-table
      :headers="headers"
      :items="this.absenceAuthorisations"
  >
    <template v-slot:item.action="{ item }">
      <v-icon
          class="mr-2"
          @click.stop="authorise(item)"
          color="success"
      >
        mdi-thumb-up
      </v-icon>
      <v-icon
          class="mr-2"
          color="error"
          @click.stop="unauthorise(item)"
      >
        mdi-thumb-down
      </v-icon>
      <v-icon
          class="mr-2"
          @click.stop="cancel(item)"
      >
        mdi-delete
      </v-icon>
    </template>
  </v-data-table>
</template>

<script>
  import {mapActions, mapGetters} from "vuex";
  import store from "../../store";

  export default {
    name: "AbsenceAuthorisation",
    data: () => {
      return {
        headers: [
          {
            text: 'Employee',
            value: 'employee.user.fullName'
          },
          {
            text: 'Year',
            value: 'companyYear.name'
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
            text: 'Actions',
            value: 'action',
            sortable: false
          }
        ]
      };
    },
    computed: {
      ...mapGetters({
        absenceAuthorisations: 'getAbsenceAuthorisations'
      })
    },
    methods: {
      ...mapActions({
        fetchAbsenceAuthorisations: 'fetchAbsenceAuthorisations'
      }),
      authorise(absence) {
        store.dispatch('authoriseAbsence', absence);
      },
      unauthorise(absence) {
        store.dispatch('unauthoriseAbsence', absence);
      },
      cancel(absence) {
        confirm("Are you sure you want to cancel this absence?")
        && store.dispatch('cancelAbsence', absence);
      }
    },
    beforeMount() {
      this.fetchAbsenceAuthorisations();
    }
  };
</script>

<style scoped>

</style>
