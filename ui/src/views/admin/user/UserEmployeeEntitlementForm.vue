<template>
  <div>
    <form-header title="Holiday Entitlement" :cancel="cancel" :save="save"/>
    <v-form ref="form">
      <v-row>
        <v-col sm="6">
          <v-text-field v-if="this.employee" label="Employee" :value="this.employee.user.fullName" readonly/>
          <company-year-select @select="companyYearSelected"/>
          <v-text-field label="Entitlement (Hours)" type="number"
                        v-model="holidayEntitlement.holidayEntitlementHours"/>
        </v-col>
      </v-row>
    </v-form>
  </div>
</template>

<script>
import FormHeader from "../../../components/FormHeader";
import CompanyYearSelect from "../../../components/CompanyYearSelect";
import {mapActions, mapGetters} from "vuex";

export default {
    name: "UserEmployeeEntitlementForm",
    data: function () {
      return {
        holidayEntitlement: {
          employee: null,
          companyYear: null,
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
      companyYearSelected(companyYear) {
        this.holidayEntitlement.companyYear = companyYear;
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
    components: {FormHeader, CompanyYearSelect}
  };
</script>

<style scoped>

</style>
