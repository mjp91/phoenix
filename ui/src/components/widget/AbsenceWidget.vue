<template>
  <v-card>
    <v-card-title primary-title class="layout justify-center">
      <h2 class="display-1">Days Absent</h2>
    </v-card-title>
    <v-row class="pa-3" justify="center">
      <div class="display-4 pa-4">
        {{this.daysAbsent}}
      </div>
    </v-row>
  </v-card>
</template>

<script>
  import {mapActions, mapGetters} from "vuex";

  export default {
    name: "AbsenceWidget",
    computed: {
      ...mapGetters({
        daysAbsent: 'getAbsenceDays'
      })
    },
    methods: {
      ...mapActions({
        fetchAbsenceDays: 'fetchAbsenceDays'
      })
    },
    beforeMount() {
      this.fetchAbsenceDays();
    }
  };
</script>

<style scoped>

</style>
