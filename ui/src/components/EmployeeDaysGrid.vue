<template>
  <v-data-table
      :headers="headers"
      :items="items"
  />
</template>

<script>
  export default {
    name: "EmployeeDaysGrid",
    data: () => {
      return {
        headers: [
          {
            text: 'Day',
            value: 'day'
          },
          {
            text: 'Start',
            value: 'start'
          },
          {
            text: 'End',
            value: 'end'
          }
        ]
      };
    },
    props: {
      employeeWeek: Object
    },
    computed: {
      items: function () {
        const items = [];

        if (this.employeeWeek) {
          for (let day in this.employeeWeek) {
            if (!this.employeeWeek.hasOwnProperty(day) || !this.employeeWeek[day]) {
              continue;
            }

            items.push({
              day: day.charAt(0).toUpperCase() + day.slice(1),
              start: this.employeeWeek[day].start,
              end: this.employeeWeek[day].end
            });
          }
        }

        return items;
      }
    }
  };
</script>

<style scoped>

</style>
