<template>
  <div>
    <form-header
        title="Absence"
        :cancel="close"
        :save="save"
    />
    <v-form>
      <v-row>
        <v-col sm="6">
          <v-textarea
              v-model="absence.reason"
              label="Reason"
              :value="absence.reason"
              hint="Please enter a reason for your absence with appropriate detail"
              no-resize
          />
          <date-range
              :startValue="absence.start"
              :endValue="absence.end"
              read-only
          />
          <v-chip
              v-if="absence.attachments"
              v-for="attachment in absence.attachments"
              :key="attachment.id"
              class="ma-2"
              :link="true"
              :href="getUrl(attachment.fileName)"
          >
            <v-icon left>mdi-paperclip</v-icon>
            {{attachment.fileName}}
          </v-chip>
        </v-col>
      </v-row>
    </v-form>
  </div>
</template>

<script>
import FormHeader from "../../components/FormHeader";
import {mapActions} from "vuex";
import DateRange from "../../components/DateRange";
import getBaseUrl from "../../lib/getBaseUrl";
import store from "../../store";

export default {
    name: "AbsenceFormEdit",
    data: function () {
      return {
        absenceProxy: null
      };
    },
    computed: {
      absence: {
        get() {
          return this.absenceProxy !== null ? this.absenceProxy : this.$store.getters.getAbsence;
        },
        set(val) {
          this.absenceProxy = val;
        }
      },
    },
    methods: {
      getUrl(currentFileName) {
        return getBaseUrl() + `/resource/${currentFileName}`;
      },
      close() {
        this.$router.push({
          name: 'absence',
        });
      },
      save() {
        store.dispatch('saveAbsence', {
          id: this.$route.params.id,
          absenceUpdate: this.absence
        }).then(() => {
          this.close();
        });
      },
      ...mapActions({
        fetchAbsence: 'fetchAbsence'
      })
    },
    beforeMount() {
      this.fetchAbsence(this.$route.params.id);
    },
    components: {FormHeader, DateRange}
  };
</script>

<style scoped>

</style>
