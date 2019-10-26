<template>
  <v-data-table
      :headers="headers"
      :items="this.holidayYears"
  >
    <template v-slot:top>
      <v-toolbar flat>
        <div class="flex-grow-1"></div>
        <v-btn color="success" to="/admin/holiday-year/new">New</v-btn>
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
    name: "HolidayYearManagement",
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
        holidayYears: 'getHolidayYears'
      })
    },
    methods: {
      ...mapActions({
        fetchHolidayYears: 'fetchHolidayYears'
      }),
      formatDate(dateStr) {
        return formatDate(dateStr);
      }
    },
    beforeMount() {
      this.fetchHolidayYears();
    }
  };
</script>

<style scoped>

</style>
