<template>
  <v-data-table
      :headers="headers"
      :items="this.absenceAuthorisations"
  >
    <template v-slot:top>
      <slot name="top"></slot>
      <v-row justify="center">
        <v-dialog v-model="dialog" persistent max-width="500">
          <v-card>
            <v-card-title class="headline">Reason for Unauthorization</v-card-title>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12">
                    <v-textarea
                        v-model="unauthoriseReason"
                        label="Reason">
                    </v-textarea>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn @click="dialog = false">Cancel</v-btn>
              <v-btn color="error" @click="closeUnauthorizedModal">Unauthorise</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-row>
    </template>

    <template v-slot:item.action="{ item }">
      <v-icon
          class="mr-2"
          @click.stop="authorise(item)"
          color="success"
      >
        mdi-thumb-up
      </v-icon>
      <v-icon
          class="mr-2"
          color="error"
          @click.stop="openUnauthorizedModal(item)"
      >
        mdi-thumb-down
      </v-icon>
      <v-icon
          class="mr-2"
          @click.stop="cancel(item)"
      >
        mdi-delete
      </v-icon>
    </template>
  </v-data-table>
</template>

<script>
import {mapActions, mapGetters} from "vuex";
import store from "../../store";

export default {
    name: "AbsenceAuthorisation",
    data: () => {
      return {
        headers: [
          {
            text: 'Employee',
            value: 'employee.user.fullName'
          },
          {
            text: 'Year',
            value: 'companyYear.name'
          },
          {
            text: 'Start',
            value: 'start'
          },
          {
            text: 'End',
            value: 'end'
          },
          {
            text: 'Actions',
            value: 'action',
            sortable: false
          }
        ],
        dialog: false,
        unauthoriseReason: null,
        selectedAbsence: null
      };
    },
    computed: {
      ...mapGetters({
        absenceAuthorisations: 'getAbsenceAuthorisations'
      })
    },
    methods: {
      ...mapActions({
        fetchAbsenceAuthorisations: 'fetchAbsenceAuthorisations'
      }),
      authorise(absence) {
        store.dispatch('authoriseAbsence', absence);
      },
      unauthorise(absence) {
        store.dispatch('unauthoriseAbsence', {
          absence,
          reason: this.unauthoriseReason
        });
      },
      cancel(absence) {
        confirm("Are you sure you want to cancel this absence?")
        && store.dispatch('cancelAbsence', absence);
      },
      openUnauthorizedModal(absence) {
        this.selectedAbsence = absence;
        this.dialog = true;
      },
      closeUnauthorizedModal() {
        this.dialog = false;
        this.unauthorise(this.selectedAbsence);

        // reset data
        this.selectedAbsence = null;
        this.unauthoriseReason = null;
      }
    },
    beforeMount() {
      this.fetchAbsenceAuthorisations();
    }
  };
</script>

<style scoped>

</style>
