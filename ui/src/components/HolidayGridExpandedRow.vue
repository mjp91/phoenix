<template>
  <div>
    <v-icon small>mdi-calendar-today</v-icon>
    <span class="pl-2">{{this.holidayRange.range}} ({{this.holidayRange.length}})</span>
    <span v-if="holiday.approved === false && holiday.disapprovalReason">
      <strong class="red--text text--lighten-1">Reason:</strong> {{this.holiday.disapprovalReason}}
    </span>
  </div>
</template>

<script>
  export default {
    name: "HolidayGridExpandedRow",
    props: {
      holiday: Object,
      holidayRange: Object,
    }
  };
</script>

<style scoped>

</style>
