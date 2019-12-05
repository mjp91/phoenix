<template>
  <v-card>
    <v-card-title primary-title class="layout justify-center">
      <h2 class="display-1">Holiday Used</h2>
    </v-card-title>
    <v-row class="pa-3" justify="center">
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
    </v-row>
  </v-card>
</template>

<script>
  import {mapActions, mapGetters} from 'vuex';

  export default {
    name: "Holiday",
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
