<template>
  <div>
    <form-header title="New Company Year" :cancel="close" :save="save"/>
    <v-form ref="form">
      <v-row>
        <v-col sm="6">
          <v-text-field
              label="Name"
              v-model="companyYear.name"
          />
          <date-range @rangeSet="rangeSet"/>
        </v-col>
      </v-row>
    </v-form>
  </div>
</template>

<script>
import store from "../../store";
import DateRange from "../../components/DateRange";
import FormHeader from "../../components/FormHeader";

export default {
    name: "CompanyYearForm",
    components: {FormHeader, DateRange},
    data: () => {
      return {
        companyYear: {
          name: null,
          yearStart: null,
          yearEnd: null
        }
      };
    },
    methods: {
      save() {
        store.dispatch('saveCompanyYear', this.companyYear).then(() => {
          this.close();
        });
      },
      rangeSet(range) {
        this.companyYear.yearStart = range.startDate;
        this.companyYear.yearEnd = range.endDate;
      },
      close() {
        this.$router.push({
          name: 'company-year-management',
        });
      },
    }
  };
</script>

<style scoped>

</style>
