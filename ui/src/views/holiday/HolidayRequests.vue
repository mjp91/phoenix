<template>
  <HolidayGrid :data="this.holidayRequests" :headers="this.headers" :approval-options="true"/>
</template>

<script>
import {mapActions, mapGetters} from 'vuex';
import HolidayGrid from "../../components/HolidayGrid";

export default {
    name: "HolidayRequests",
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
          {
            text: 'Actions',
            value: 'action',
            sortable: false,
          }
        ],
      };
    },
    computed: {
      ...mapGetters({
        holidayRequests: 'getHolidayRequests'
      }),
    },
    methods: {
      ...mapActions({
        fetchHolidayRequests: 'fetchHolidayRequests'
      })
    },
    beforeMount() {
      this.fetchHolidayRequests();
    },
    components: {
      HolidayGrid
    }
  };
</script>

<style scoped>

</style>
