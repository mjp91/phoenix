<template>
  <v-row v-if="!slim">
    <v-col sm="4">
      <v-subheader>From</v-subheader>
      <v-date-picker
          v-model="startDate"
          color="green"
          @input="validateDates"
          :readonly="readOnly"
      />
    </v-col>
    <v-col sm="4">
      <v-subheader>To</v-subheader>
      <v-date-picker
          v-model="endDate"
          color="red"
          @input="validateDates"
          :readonly="readOnly"
      />
    </v-col>
  </v-row>
  <v-row v-else>
    <v-col sm="6">
      <v-menu
          v-model="startMenu"
          :disabled="readOnly"
          :close-on-content-click="false"
          :nudge-right="40"
          transition="scale-transition"
          offset-y
          min-width="290px"
      >
        <template v-slot:activator="{ on }">
          <v-text-field
              v-model="startDate"
              label="From"
              prepend-icon="mdi-calendar"
              readonly
              v-on="on"
          ></v-text-field>
        </template>
        <v-date-picker
            v-model="startDate"
            color="green"
            @input="startInput"
            :readonly="readOnly"
        />
      </v-menu>
    </v-col>
    <v-col sm="6">
      <v-menu
          v-model="endMenu"
          :disabled="readOnly"
          :close-on-content-click="false"
          :nudge-right="40"
          transition="scale-transition"
          offset-y
          min-width="290px"
      >
        <template v-slot:activator="{ on }">
          <v-text-field
              v-model="endDate"
              label="To"
              prepend-icon="mdi-calendar"
              readonly
              v-on="on"
          ></v-text-field>
        </template>
        <v-date-picker
            v-model="endDate"
            color="red"
            @input="endInput"
            :readonly="readOnly"
        />
      </v-menu>
    </v-col>
  </v-row>
</template>

<script>
  import moment from "moment";

  export default {
    name: "DateRange",
    data: function () {
      return {
        startMenu: false,
        endMenu: false,
        startDate: this.startValue,
        endDate: this.endValue
      };
    },
    props: {
      startValue: String,
      endValue: String,
      slim: {
        type: Boolean,
        default: false
      },
      readOnly: {
        type: Boolean,
        default: false,
      }
    },
    methods: {
      startInput() {
        this.validateDates();
        this.startMenu = false;
      },
      endInput() {
        this.validateDates();
        this.endMenu = false;
      },
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
