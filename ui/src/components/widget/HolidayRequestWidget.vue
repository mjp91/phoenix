<template>
  <v-card>
    <v-card-title primary-title class="layout justify-center">
      <h2 class="display-1">Requests</h2>
    </v-card-title>
    <v-row class="pa-2" v-if="mostRecentHolidayRequests.length > 0">
      <v-col cols="12">
        <holiday-list :holidays="mostRecentHolidayRequests" title="employee"></holiday-list>
      </v-col>
    </v-row>
    <v-card-text v-else>No outstanding requests</v-card-text>
  </v-card>
</template>

<script>
  import {mapActions, mapGetters} from 'vuex';
  import HolidayList from "../HolidayList";

  export default {
    name: "HolidayRequestWidget",
    computed: {
      ...mapGetters({
        mostRecentHolidayRequests: 'getMostRecentHolidayRequests'
      })
    },
    methods: {
      ...mapActions({
        fetchMostRecentHolidayRequests: 'fetchMostRecentHolidayRequests'
      }),
    },
    beforeMount() {
      this.fetchMostRecentHolidayRequests();
    },
    components: {HolidayList}
  };
</script>

<style scoped>

</style>
