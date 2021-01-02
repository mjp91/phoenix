<template>
  <holiday-grid :data="this.holidays" :cancel-option="true">
    <template v-slot:top>
      <v-toolbar flat>
        <div class="flex-grow-1"></div>
        <v-btn color="success" to="/holiday/new">New</v-btn>
      </v-toolbar>
    </template>
  </holiday-grid>
</template>

<script>
import {mapActions, mapGetters} from 'vuex';
import HolidayGrid from '../../components/HolidayGrid';

export default {
    name: "Holiday",
    computed: {
      ...mapGetters({
        holidays: 'getHolidays'
      }),
    },
    methods: {
      ...mapActions({
        fetchHolidays: 'fetchHolidays'
      })
    },
    beforeMount() {
      this.fetchHolidays();
    },
    components: {
      HolidayGrid
    }
  };
</script>

<style scoped>

</style>
