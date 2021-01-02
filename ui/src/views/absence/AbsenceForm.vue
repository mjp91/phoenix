<template>
  <div>
    <form-header
        title="Record Absence"
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
          <date-range @rangeSet="rangeSet"/>
          <v-file-input
              v-model="files"
              label="Attachments"
              multiple
              chips
          />
        </v-col>
      </v-row>
    </v-form>
  </div>
</template>

<script>
import FormHeader from "../../components/FormHeader";
import DateRange from "../../components/DateRange";
import store from "../../store";

export default {
    name: "AbsenceForm",
    data: () => {
      return {
        absence: {
          start: null,
          end: null,
          reason: null,
          attachments: []
        },
        files: []
      };
    },
    methods: {
      rangeSet(range) {
        this.absence.start = range.startDate;
        this.absence.end = range.endDate;
      },
      close() {
        this.$router.push({
          name: 'absence',
        });
      },
      save() {
        store.dispatch('uploadResource', this.files).then((fileNames) => {
          fileNames.forEach((fileName) => {
            this.absence.attachments.push({
              fileName
            });
          });
          store.dispatch('createAbsence', this.absence).then(() => {
            this.close();
          });
        });
      }
    },
    components: {DateRange, FormHeader}
  };
</script>

<style scoped>

</style>
