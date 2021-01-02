<template>
  <holiday-grid
      :data="this.holidays"
      :headers="headers"
  />
</template>

<script>
import HolidayGrid from "../../components/HolidayGrid";
import {mapActions, mapGetters} from "vuex";

export default {
    name: "HolidayAll",
    data: () => {
      return {
        headers: [
          {
            text: 'Employee',
            value: 'employee'
          },
          {
            text: 'Year',
            value: 'companyYear'
          },
          {
            text: 'Requested',
            value: 'createdDate'
          },
        ]
      };
    },
    computed: {
      ...mapGetters({
        holidays: 'getHolidays'
      })
    },
    methods: {
      ...mapActions({
        fetchAllHolidays: 'fetchAllHolidays'
      })
    },
    beforeMount() {
      this.fetchAllHolidays();
    },
    components: {HolidayGrid}
  };
</script>

<style scoped>

</style>
