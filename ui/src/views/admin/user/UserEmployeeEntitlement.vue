<template>
  <v-data-table
      v-if="this.employee"
      :headers="headers"
      :items="this.holidayEntitlements"
  >
    <template v-if="!readOnly" v-slot:top>
      <v-toolbar flat>
        <div class="flex-grow-1"></div>
        <v-btn color="success" :to="`/admin/user/${$route.params.id}/holiday-entitlement/new`">New</v-btn>
      </v-toolbar>
    </template>
    <template v-slot:items="props">
      <tr>
        <td>{{ props.item.companyYear.name }}</td>
        <td>{{ props.item.holidayEntitlementHours }}</td>
      </tr>
    </template>
  </v-data-table>
</template>

<script>
import {mapActions, mapGetters} from 'vuex';

export default {
  name: "UserEmployeeEntitlement",
  data: () => {
    return {
      headers: [
        {
          text: 'Year',
          value: 'companyYear.name'
        },
        {
          text: 'Entitlement (Hours)',
          value: 'holidayEntitlementHours'
        }
      ]
    };
  },
  props: {
    employee: Object,
    readOnly: {
      type: Boolean,
      default: false
    }
  },
  computed: {
    ...mapGetters({
      holidayEntitlements: 'getHolidayEntitlement'
    })
  },
  methods: {
    ...mapActions({
      fetchHolidayEntitlement: 'fetchHolidayEntitlement'
    })
  },
  beforeMount() {
    this.fetchHolidayEntitlement({employeeId: this.employee.id});
  }
};
</script>

<style scoped>

</style>
