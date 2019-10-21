<template>
  <v-row>
    <v-col sm="4">
      <v-subheader>From</v-subheader>
      <v-date-picker
          v-model="startDate"
          color="green"
          @input="validateDates">
      </v-date-picker>
    </v-col>
    <v-col sm="4">
      <v-subheader>To</v-subheader>
      <v-date-picker
          v-model="endDate"
          color="red"
          @input="validateDates">
      </v-date-picker>
    </v-col>
  </v-row>
</template>

<script>
  import moment from "moment";

  export default {
    name: "DateRange",
    data: () => {
      return {
        startDate: null,
        endDate: null
      };
    },
    methods: {
      validateDates() {
        const startDate = this.startDate;
        const endDate = this.endDate;
        if (startDate && endDate) {
          const invalid = moment(startDate).isAfter(moment(endDate));
          if (invalid) {
            this.endDate = startDate;
          }
        }

        this.$emit('rangeSet', {
          startDate: this.startDate,
          endDate: this.endDate
        });
      },
    }
  };
</script>

<style scoped>

</style>
