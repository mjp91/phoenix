<template>
  <div>
    <div v-for="(alert, index) in alerts" :key="`alert-${index}`">
      <v-alert
          :value="!alert.shown"
          :type="alert.type"
          transition="scale-transition"
          mode="out-in"
      >
        {{ alert.message }}
      </v-alert>
    </div>
  </div>
</template>

<script>
import {mapGetters, mapMutations} from 'vuex';
import goTo from 'vuetify/lib/services/goto';

export default {
    name: "Alert",
    computed: {
      ...mapGetters({
        alerts: 'getAlerts'
      })
    },
    methods: {
      ...mapMutations({
        alertsShown: 'alertsShown'
      })
    },
    watch: {
      alerts(newValue) {
        const found = newValue.find((alert) => !alert.shown);

        if (found) {
          // go to top
          goTo(0);
          // close after timeout
          setTimeout(() => {
            this.alertsShown();
          }, 5000);
        }
      }
    }
  };
</script>

<style scoped>

</style>
