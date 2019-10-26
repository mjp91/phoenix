<template>
  <v-select
      :items="this.holidayYears"
      @input="onSelect"
      label="Period"
      item-text="name"
      :item-value="itemValue"
      :rules="holidayYearRules"
      single-line>
  </v-select>
</template>

<script>
  import {mapActions, mapGetters} from "vuex";

  export default {
    name: "HolidayYearSelect",
    data: () => {
      return {
        holidayYearRules: [
          v => !!v || 'Period is required'
        ]
      };
    },
    computed: {
      ...mapGetters({
        holidayYears: 'getHolidayYears'
      }),
    },
    methods: {
      itemValue(holidayYear) {
        return holidayYear;
      },
      onSelect(e) {
        this.$emit('select', e);
      },
      ...mapActions({
        fetchHolidayYears: 'fetchHolidayYears',
      })
    },
    beforeMount() {
      this.fetchHolidayYears();
    }
  };
</script>

<style scoped>

</style>
