<template>
  <div>
    <form-header title="Holiday Entitlement" :cancel="cancel" :save="save"/>
    <v-form ref="form">
      <v-row>
        <v-col sm="6">
          <v-text-field v-if="this.employee" label="Employee" :value="this.employee.user.fullName" readonly/>
          <holiday-year-select @select="holidayYearSelected"/>
          <v-text-field label="Entitlement (Hours)" type="number"
                        v-model="holidayEntitlement.holidayEntitlementHours"/>
        </v-col>
      </v-row>
    </v-form>
  </div>
</template>

<script>
  import FormHeader from "../../../components/FormHeader";
  import HolidayYearSelect from "../../../components/HolidayYearSelect";
  import {mapActions, mapGetters} from "vuex";

  export default {
    name: "UserEmployeeEntitlementForm",
    data: function () {
      return {
        holidayEntitlement: {
          employee: null,
          holidayYear: null,
          holidayEntitlementHours: 0
        }
      };
    },
    computed: {
      ...mapGetters({
        employee: 'getEmployee',
      })
    },
    methods: {
      holidayYearSelected(holidayYear) {
        this.holidayEntitlement.holidayYear = holidayYear;
      },
      cancel() {
        this.$router.push({
          name: 'user',
          params: {
            id: this.$route.params.id
          }
        });
      },
      save() {
        this.holidayEntitlement.employee = this.employee;
        this.saveHolidayEntitlement(this.holidayEntitlement).then(() => {
          this.cancel();
        });
      },
      ...mapActions({
        saveHolidayEntitlement: 'saveHolidayEntitlement',
        fetchByUserId: 'fetchByUserId'
      })
    },
    beforeMount() {
      this.fetchByUserId(this.$route.params.id);
    },
    components: {FormHeader, HolidayYearSelect}
  };
</script>

<style scoped>

</style>
