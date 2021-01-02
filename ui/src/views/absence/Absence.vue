<template>
  <v-data-table
      :headers="headers"
      :items="this.absences"
      @click:row="onRowClick"
  >
    <template v-slot:top>
      <v-toolbar flat>
        <div class="flex-grow-1"></div>
        <v-btn color="success" to="/absence/new">New</v-btn>
      </v-toolbar>
    </template>

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
    name: "Absence",
    data: () => {
      return {
        headers: [
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
        fetchAbsences: 'fetchAbsences'
      }),
      onRowClick(item) {
        this.$router.push({
          name: 'absenceFormEdit',
          params: {
            id: item.id
          }
        });
      }
    },
    beforeMount() {
      this.fetchAbsences();
    }
  };
</script>

<style scoped>

</style>
