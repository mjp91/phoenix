<template>
  <v-menu
      v-model="dateMenu"
      :disabled="readOnly"
      :close-on-content-click="false"
      :nudge-right="40"
      transition="scale-transition"
      offset-y
      min-width="290px"
  >
    <template v-slot:activator="{ on }">
      <v-text-field
          :value="dateRange"
          label="Range"
          prepend-icon="mdi-calendar"
          readonly
          v-on="on"
      />
    </template>
    <v-date-picker
        v-model="range"
        color="primary"
        @input="dateInput"
        :readonly="readOnly"
        :range="true"
        :min="min"
        :max="max"
    />
  </v-menu>
</template>

<script>
import moment from "moment";

export default {
    name: "DateRange",
    data: function () {
      return {
        dateMenu: false,
        range: [
          this.startValue,
          this.endValue
        ]
      };
    },
    props: {
      startValue: String,
      endValue: String,
      readOnly: {
        type: Boolean,
        default: false,
      },
      min: String,
      max: String
    },
    computed: {
      dateRange() {
        if (!this.range[0] && !this.range[1]) {
          return null;
        }

        return `${this.range[0] ? this.range[0] : '?'} to ${this.range[1] ? this.range[1] : '?'}`;
      },
    },
    methods: {
      dateInput() {
        this.validateDates();
        this.dateMenu = false;
      },
      validateDates() {
        const startDate = this.range[0];
        const endDate = this.range[1];
        if (startDate && endDate) {
          const invalid = moment(startDate).isAfter(moment(endDate));
          if (invalid) {
            this.range[0] = endDate;
            this.range[1] = startDate;
          }
        }

        this.$emit('rangeSet', {
          startDate: this.range[0],
          endDate: this.range[1]
        });
      },
    }
  };
</script>

<style scoped>

</style>
