<template>
  <v-data-table
      :headers="headers"
      :items="this.companyYears"
  >
    <template v-slot:top>
      <v-toolbar flat>
        <div class="flex-grow-1"></div>
        <v-btn color="success" to="/admin/company-year/new">New</v-btn>
      </v-toolbar>
    </template>
    <template v-slot:items="props">
      <tr>
        <td>{{props.item.name}}</td>
        <td>{{formatDate(props.item.yearStart)}}</td>
        <td>{{formatDate(props.item.yearEnd)}}</td>
      </tr>
    </template>
  </v-data-table>
</template>

<script>
import {mapActions, mapGetters} from "vuex";
import {formatDate} from "../../lib/formatDateTime";

export default {
    name: "CompanyYearManagement",
    data: () => {
      return {
        headers: [
          {
            text: 'Name',
            value: 'name'
          },
          {
            text: 'Start',
            value: 'yearStart'
          },
          {
            text: 'End',
            value: 'yearEnd'
          }
        ]
      };
    },
    computed: {
      ...mapGetters({
        companyYears: 'getCompanyYears'
      })
    },
    methods: {
      ...mapActions({
        fetchCompanyYears: 'fetchCompanyYears'
      }),
      formatDate(dateStr) {
        return formatDate(dateStr);
      }
    },
    beforeMount() {
      this.fetchCompanyYears();
    }
  };
</script>

<style scoped>

</style>
