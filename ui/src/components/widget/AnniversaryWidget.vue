<template>
  <widget title="Anniversaries">
    <v-row>
      <v-col sm="6">
        <h3 class="title layout justify-center">Birthdays</h3>
        <anniversary-list :anniversaries="birthdays" icon="mdi-cake"/>
      </v-col>
      <v-col sm="6">
        <h3 class="title layout justify-center">Service</h3>
        <anniversary-list :anniversaries="serviceAnniversaries"/>
      </v-col>
    </v-row>
  </widget>
</template>

<script>
  import Widget from "./Widget";
  import {mapActions, mapGetters} from "vuex";
  import AnniversaryList from "./AnniversaryList";

  export default {
    name: "AnniversaryWidget",
    components: {AnniversaryList, Widget},
    computed: {
      ...mapGetters({
        birthdays: 'getUpcomingBirthdays',
        serviceAnniversaries: 'getUpcomingServiceAnniversaries'
      })
    },
    methods: {
      ...mapActions({
        fetchUpcomingBirthdays: 'fetchUpcomingBirthdays',
        fetchUpcomingServiceAnniversaries: 'fetchUpcomingServiceAnniversaries'
      })
    },
    beforeMount() {
      this.fetchUpcomingBirthdays();
      this.fetchUpcomingServiceAnniversaries();
    }
  };
</script>

