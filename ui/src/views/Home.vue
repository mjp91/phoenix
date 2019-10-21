<template>
  <div class="home">
    <v-row justify="space-around">
      <v-col class="mb-4" sm="3">
        <holiday-widget/>
      </v-col>
      <v-col sm="4">
        <holiday-today-widget/>
      </v-col>
      <v-col sm="4">
        <holiday-request-widget/>
      </v-col>
    </v-row>
  </div>
</template>

<script>
  // @ is an alias to /src
  import HolidayWidget from '../components/HolidayWidget.vue';
  import HolidayRequestWidget from '../components/HolidayRequestWidget';
  import HolidayTodayWidget from "../components/HolidayTodayWidget";

  export default {
    name: 'home',
    components: {
      HolidayTodayWidget,
      HolidayWidget,
      HolidayRequestWidget
    }
  };
</script>
