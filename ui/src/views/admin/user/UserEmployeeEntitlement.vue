<template>
  <v-data-table
      :headers="headers"
      :items="this.employee.holidayEntitlements"
  >
    <template v-if="!readOnly" v-slot:top>
      <v-toolbar flat>
        <div class="flex-grow-1"></div>
        <v-btn color="success" :to="`/admin/user/${$route.params.id}/holiday-entitlement/new`">New</v-btn>
      </v-toolbar>
    </template>
    <template v-slot:items="props">
      <tr>
        <td>{{props.item.holidayYear.name}}</td>
        <td>{{props.item.holidayEntitlementHours}}</td>
      </tr>
    </template>
  </v-data-table>
</template>

<script>
  export default {
    name: "UserEmployeeEntitlement",
    data: () => {
      return {
        headers: [
          {
            text: 'Year',
            value: 'holidayYear.name'
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
    }
  };
</script>

<style scoped>

</style>
