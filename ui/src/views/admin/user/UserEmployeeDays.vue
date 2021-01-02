<template>
  <v-row>
    <v-col sm="6">
      <v-card flat class="pa-4">
        <v-expansion-panels ref="employeeDays">
          <v-expansion-panel
              :key="key"
              v-for="key in Object.keys(employee.employeeWeek)">
            <v-expansion-panel-header>
              <div>{{key.charAt(0).toUpperCase() + key.slice(1)}}</div>
            </v-expansion-panel-header>
            <v-expansion-panel-content>
              <employee-day
                  v-model="employee.employeeWeek[key]"/>
            </v-expansion-panel-content>
          </v-expansion-panel>
        </v-expansion-panels>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import EmployeeDay from "../../../components/EmployeeDay";

export default {
    name: "UserEmployeeDays",
    props: {
      employee: Object
    },
    components: {
      EmployeeDay
    }
  };
</script>

<style scoped>

</style>
