<template>
  <widget title="Holiday Used">
    <div class="layout justify-center">
      <v-progress-circular
          :rotate="-90"
          :size="200"
          :width="25"
          :value="this.getHolidayUsed"
          :color="this.getProgressColor"
          :indeterminate="!this.holidayFetched"
      >
        <span id="gauge-text" v-if="this.holidayFetched">
            {{ `${this.holiday.used} / ${this.holiday.total}` }}
        </span>
      </v-progress-circular>
    </div>
  </widget>
</template>

<script>
import {mapActions, mapGetters} from 'vuex';
import Widget from "./Widget";

export default {
    name: "Holiday",
    components: {Widget},
    computed: {
      ...mapGetters({
        holiday: 'getHoliday'
      }),
      getHolidayUsed() {
        let total = this.holiday.total;
        let used = this.holiday.used;

        if (used > total) {
          return 100.0;
        } else if (total === 0.0 || used === 0.0) {
          return 0.0;
        }

        return (used / total) * 100;
      },
      getProgressColor() {
        const holidayUsed = this.getHolidayUsed;
        let color = 'primary';

        if (this.holidayFetched) {
          if (holidayUsed > 75.0) {
            color = 'red';
          } else if (holidayUsed > 50.0) {
            color = 'orange';
          } else if (holidayUsed > 25.0) {
            color = 'yellow';
          } else {
            color = 'green';
          }
        }

        return color;
      },
      holidayFetched() {
        return this.holiday.used != null && this.holiday.total != null;
      }
    },
    methods: {
      ...mapActions({
        fetchHoliday: 'fetchHoliday'
      })
    },
    beforeMount() {
      this.fetchHoliday();
    }
  };
</script>

<style scoped>
  #gauge-text {
    font-size: 2em;
  }
</style>
