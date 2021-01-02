<template>
  <v-row>
    <v-col cols="6">
      <time-picker label="Start" @timeSet="startTimeUpdated" :value="toIso8601(value ? value.start : null)"/>
    </v-col>
    <v-col cols="6">
      <time-picker label="End" @timeSet="endTimeUpdated" :value="toIso8601(value ? value.end : null)"/>
    </v-col>
  </v-row>
</template>

<script>
import TimePicker from "./TimePicker";
import moment from "moment";

export default {
    name: "EmployeeDay",
    components: {TimePicker},
    data: function () {
      return {
        day: this.value || {}
      };
    },
    props: {
      value: {
        type: Object,
        default: () => {
          return {
            start: null,
            end: null
          };
        }
      }
    },
    methods: {
      startTimeUpdated(time) {
        this.day.start = time;
        this.$emit('input', this.day);
      },
      endTimeUpdated(time) {
        this.day.end = time;
        this.$emit('input', this.day);
      },
      toIso8601(time) {
        if (!time) {
          return null;
        }

        return moment(time, 'HH:mm:ss').format("HH:mm");
      }
    }
  };
</script>

<style scoped>

</style>
