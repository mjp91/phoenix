<template>
  <widget title="Requests">
    <holiday-list
        v-if="mostRecentHolidayRequests.length > 0"
        :holidays="mostRecentHolidayRequests"
        title="employee"
    />
    <v-card-text v-else>No outstanding requests</v-card-text>
  </widget>
</template>

<script>
import {mapActions, mapGetters} from 'vuex';
import HolidayList from "../HolidayList";
import Widget from "./Widget";

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
    components: {Widget, HolidayList}
  };
</script>

<style scoped>

</style>
