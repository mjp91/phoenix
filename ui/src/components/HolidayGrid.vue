<template>
  <v-data-table
      :headers="this.headers"
      :items="this.data"
      item-key="name"
      :single-expand="true"
      show-expand
  >
    <template v-slot:top>
      <slot name="top"/>
      <v-row justify="center">
        <v-dialog v-model="dialog" persistent max-width="500">
          <v-card>
            <v-card-title class="headline">Reason for Disapproval</v-card-title>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12">
                    <v-textarea
                        v-model="disapprovalReason"
                        label="Reason">
                    </v-textarea>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>
            <v-card-actions>
              <v-spacer/>
              <v-btn @click="dialog = false">Cancel</v-btn>
              <v-btn color="error" @click="closeDisapprovalModal">Disapprove</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-row>
    </template>

    <template v-slot:item.employee="{ item }">
      {{ item.employee.user.fullName }}
    </template>

    <template v-slot:item.companyYear="{ item }">
      {{ item.companyYear.name }}
    </template>

    <template v-slot:item.createdDate="{ item }">
      {{ formatDate(item.createdDate) }}
    </template>

    <template v-slot:item.approved="{ item }">
      <v-icon v-if="item.approved === true" color="success">mdi-thumb-up</v-icon>
      <v-icon v-else-if="item.approved === false" color="error">mdi-thumb-down</v-icon>
      <v-icon v-else color="warning">mdi-clock</v-icon>
    </template>

    <template v-slot:item.action="{ item }">
      <template v-if="approvalOptions">
        <v-icon
            class="mr-2"
            @click.stop="approveHoliday(item)"
            color="success"
        >
          mdi-thumb-up
        </v-icon>
        <v-icon
            class="mr-2"
            color="error"
            @click.stop="openDisapprovalModal(item)"
        >
          mdi-thumb-down
        </v-icon>
      </template>
      <v-icon
          v-if="cancelOption"
          class="mr-2"
          @click.stop="cancelHoliday(item)"
      >
        mdi-delete
      </v-icon>
    </template>

    <template v-slot:expanded-item="{ headers, item }">
      <td :colspan="headers.length">
        <holiday-grid-expanded-row :holiday="item" :holiday-range="getHolidayRange(item)"/>
      </td>
    </template>
  </v-data-table>
</template>

<script>
import getHolidayRange from "../lib/getHolidayRange";
import store from "../store";
import formatDateTime from "../lib/formatDateTime";
import HolidayGridExpandedRow from "./HolidayGridExpandedRow";

export default {
    name: "HolidayGrid",
    data: () => {
      return {
        selectedHoliday: null,
        dialog: false,
        disapprovalReason: null,
      };
    },
    props: {
      data: Array,
      headers: {
        type: Array,
        default: () => {
          return [
            {
              text: 'Name',
              value: 'name'
            },
            {
              text: 'Year',
              value: 'companyYear'
            },
            {
              text: 'Requested',
              value: 'createdDate'
            },
            {
              text: 'Approved',
              value: 'approved',
            },
            {
              text: 'Actions',
              value: 'action',
              sortable: false
            }
          ];
        }
      },
      cancelOption: Boolean,
      approvalOptions: Boolean,
    },
    methods: {
      formatDate(dateStr) {
        return formatDateTime(dateStr);
      },
      getHolidayRange,
      cancelHoliday(holiday) {
        confirm("Are you sure you want to cancel this holiday?")
        && store.dispatch('cancelHoliday', holiday);
      },
      approveHoliday(holiday) {
        store.dispatch('approveHoliday', holiday);
      },
      disapproveHoliday(holiday) {
        store.dispatch('disapproveHoliday', {
          holiday,
          reason: this.disapprovalReason
        });
      },
      openDisapprovalModal(holiday) {
        this.selectedHoliday = holiday;
        this.dialog = true;
      },
      closeDisapprovalModal() {
        this.dialog = false;
        this.disapproveHoliday(this.selectedHoliday);

        // reset data
        this.selectedHoliday = null;
        this.disapprovalReason = null;
      }
    },
    components: {HolidayGridExpandedRow},
  };
</script>

<style scoped>

</style>
