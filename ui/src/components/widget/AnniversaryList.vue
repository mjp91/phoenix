<template>
  <v-list v-if="anniversaries && anniversaries.length > 0" two-line>
    <template v-for="(item, index) in anniversaries">
      <v-list-item :key="`anniversary-date-${index}`">
        <v-list-item-content>
          <v-list-item-title>
            {{formatDate(item.date)}}
          </v-list-item-title>
          <v-list-item-subtitle v-for="(employee, index) in item.employees">
            <div :key="`employee-anniversary-${index}`">
              <v-icon small>{{icon ? icon : 'mdi-calendar'}}</v-icon>
              {{' ' + employee.user.fullName}}
              <slot name="years" :date="item.date" :employee="employee.employee"/>
            </div>
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
      <v-divider v-if="index !== anniversaries.length - 1"/>
    </template>
  </v-list>
  <div v-else>None to display</div>
</template>
<script>
import {formatDate} from "@/lib/formatDateTime";

export default {
  name: 'anniversary-list',
  props: {
    anniversaries: Array,
    icon: String,
  },
  methods: {
    formatDate(dateStr) {
      return formatDate(dateStr);
    }
  }
  };
</script>
