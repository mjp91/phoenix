<template>
  <v-menu
      ref="menu"
      v-model="open"
      :close-on-content-click="false"
      :nudge-right="40"
      :return-value.sync="time"
      transition="scale-transition"
      offset-y
      max-width="290px"
      min-width="290px"
  >
    <template v-slot:activator="{ on }">
      <v-text-field
          :label="label"
          prepend-icon="mdi-clock"
          readonly
          v-on="on"
          :value="time"
      ></v-text-field>
    </template>
    <v-time-picker
        v-if="open"
        full-width
        format="24hr"
        @click:minute="$refs.menu.save(time)"
        @input="updateTime"
        :value="time"
    ></v-time-picker>
  </v-menu>
</template>

<script>
  export default {
    name: "TimePicker",
    props: {
      label: String,
      value: String,
    },
    data: function () {
      return {
        open: false,
        time: this.value,
      };
    },
    methods: {
      updateTime(time) {
        this.time = time;
        this.$emit('timeSet', time);
      }
    }
  };
</script>

<style scoped>

</style>
