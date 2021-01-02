<template>
  <widget>
    <v-card-title primary-title class="layout justify-center">
      <h2 class="display-1">Today</h2>
    </v-card-title>
    <v-row>
      <v-col cols="12" v-if="todaysHolidays.currentUserHoliday">
        <div>
          <holiday-list :holidays="[todaysHolidays.currentUserHoliday]"/>
        </div>
      </v-col>
      <v-col cols="12" v-if="todaysHolidays.managedEmployeeHolidays.length > 0">
        <holiday-list :holidays="todaysHolidays.managedEmployeeHolidays" title="employee"/>
      </v-col>
      <v-col v-if="!isHolidaysToday" cols="12">
        No holiday today
      </v-col>
    </v-row>
    <v-card-title primary-title class="layout justify-center">
      <h2 class="display-1">Upcoming</h2>
    </v-card-title>
    <v-row>
      <v-col cols="12">
        <div v-if="todaysHolidays.currentUserNextHoliday">
          <div class="pa-2">Your next holiday is <strong>{{getNextHolidayDaysToGo()}}</strong></div>
          <holiday-list :holidays="[todaysHolidays.currentUserNextHoliday]"/>
        </div>
        <div v-else>
          No upcoming holidays
        </div>
      </v-col>
    </v-row>
  </widget>
</template>

<script>
import {mapActions, mapGetters} from 'vuex';
import getHolidayRange from "../../lib/getHolidayRange";
import getDaysToHoliday from "../../lib/getDaysToHoliday";
import HolidayList from "../HolidayList";
import Widget from "./Widget";

export default {
    name: "HolidayTodayWidget",
    computed: {
      ...mapGetters({
        todaysHolidays: 'getTodaysHolidays'
      }),
      isHolidaysToday() {
        return this.todaysHolidays.currentUserHoliday
          && this.todaysHolidays.managedEmployeeHolidays.length === 0;
      }
    },
    methods: {
      ...mapActions({
        fetchTodaysHolidays: 'fetchTodaysHolidays'
      }),
      getNextHolidayRange() {
        const holidayRange = getHolidayRange(this.todaysHolidays.currentUserNextHoliday);
        return holidayRange.range;
      },
      getNextHolidayDaysToGo() {
        return getDaysToHoliday(this.todaysHolidays.currentUserNextHoliday);
      }
    },
    beforeMount() {
      this.fetchTodaysHolidays();
    },
    components: {Widget, HolidayList}
  };
</script>

<style scoped>

</style>
