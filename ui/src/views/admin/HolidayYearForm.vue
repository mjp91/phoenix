<template>
  <div>
    <form-header title="New Holiday Year" :cancel="close" :save="save"/>
    <v-form ref="form">
      <v-row>
        <v-col sm="6">
          <v-text-field
              label="Name"
              v-model="holidayYear.name"
          />
        </v-col>
      </v-row>
      <date-range @rangeSet="rangeSet"/>
    </v-form>
  </div>
</template>

<script>
  import store from "../../store";
  import DateRange from "../../components/DateRange";
  import FormHeader from "../../components/FormHeader";

  export default {
    name: "HolidayYearForm",
    components: {FormHeader, DateRange},
    data: () => {
      return {
        holidayYear: {
          name: null,
          yearStart: null,
          yearEnd: null
        }
      };
    },
    methods: {
      save() {
        store.dispatch('saveHolidayYear', this.holidayYear).then(() => {
          this.close();
        });
      },
      rangeSet(range) {
        this.holidayYear.yearStart = range.startDate;
        this.holidayYear.yearEnd = range.endDate;
      },
      close() {
        this.$router.push({
          name: 'holiday-year-management',
        });
      },
    }
  };
</script>

<style scoped>

</style>
