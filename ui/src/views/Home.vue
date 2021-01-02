<template>
  <div class="home">
    <v-row justify="space-around" class="mb-1">
      <v-col sm="4">
        <holiday-widget/>
      </v-col>
      <v-col sm="4">
        <holiday-today-widget/>
      </v-col>
      <v-col sm="4">
        <holiday-request-widget/>
      </v-col>
    </v-row>
    <v-row>
      <v-col sm="4">
        <absence-widget/>
      </v-col>
      <v-col sm="8">
        <anniversary-widget/>
      </v-col>
    </v-row>
  </div>
</template>

<script>
// @ is an alias to /src
import HolidayWidget from '../components/widget/HolidayWidget.vue';
import HolidayRequestWidget from '../components/widget/HolidayRequestWidget';
import HolidayTodayWidget from "../components/widget/HolidayTodayWidget";
import AbsenceWidget from "../components/widget/AbsenceWidget";
import AnniversaryWidget from "../components/widget/AnniversaryWidget";

export default {
    name: 'home',
    components: {
      AnniversaryWidget,
      AbsenceWidget,
      HolidayTodayWidget,
      HolidayWidget,
      HolidayRequestWidget
    }
  };
</script>
