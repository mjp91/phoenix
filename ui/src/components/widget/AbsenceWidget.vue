<template>
  <widget title="Days Absent">
    <div class="display-4 layout justify-center">
      {{daysAbsent}}
    </div>
  </widget>
</template>

<script>
import {mapActions, mapGetters} from "vuex";
import Widget from "./Widget";

export default {
    name: "AbsenceWidget",
    components: {Widget},
    computed: {
      ...mapGetters({
        daysAbsent: 'getAbsenceDays'
      })
    },
    methods: {
      ...mapActions({
        fetchAbsenceDays: 'fetchAbsenceDays'
      })
    },
    beforeMount() {
      this.fetchAbsenceDays();
    }
  };
</script>

