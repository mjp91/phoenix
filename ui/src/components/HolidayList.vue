<template>
  <v-list v-if="holidays && holidays.length > 0" two-line>
    <template v-for="(item, index) in holidays">
      <div :key="item.id">
        <v-list-item>
          <v-list-item-content>
            <v-list-item-title>
              <div v-if="title === 'employee'">
                {{item.employee.user.fullName}}
              </div>
              <div v-else>
                {{item.name ? item.name : item.employee.user.fullName}}
              </div>
            </v-list-item-title>
            <v-list-item-subtitle>
              <v-icon small>mdi-palm-tree</v-icon>
              {{ getHolidayRange(item).range }}
            </v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
        <v-divider v-if="index !== holidays.length - 1"/>
      </div>
    </template>
  </v-list>
</template>

<script>
import getHolidayRange from '../lib/getHolidayRange';

export default {
    name: "HolidayList",
    props: {
      holidays: Array,
      title: String
    },
    methods: {
      getHolidayRange
    }
  };
</script>

<style scoped>

</style>
