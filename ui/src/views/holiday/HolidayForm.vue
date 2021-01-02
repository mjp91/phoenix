<template>
  <div>
    <form-header
        title="Holiday Request"
        :cancel="close"
        :save="saveHoliday"
    />
    <v-form ref="form">
      <v-row>
        <v-col sm="6" class="pb-0">
          <v-text-field
              v-model="holidayRequest.name"
              label="Name"
          />
          <company-year-select @select="companyYearSelected"/>
          <v-select
              v-model="holidayRequest.holidayPeriod"
              label="Duration"
              :items="types"
              value="ALL_DAY"
          />
          <date-range
              v-if="holidayRequest.companyYearId"
              @rangeSet="rangeSet"
              :min="getCompanyYearStart"
              :max="getCompanyYearEnd"
          />
        </v-col>
      </v-row>
    </v-form>
  </div>
</template>

<script>
import {mapActions, mapGetters, mapMutations} from 'vuex';
import store from '../../store';
import CompanyYearSelect from "../../components/CompanyYearSelect";
import FormHeader from "../../components/FormHeader";
import DateRange from "../../components/DateRange";

export default {
    name: "HolidayForm",
    components: {DateRange, FormHeader, CompanyYearSelect},
    data() {
      return {
        holidayRequest: {
          startDate: null,
          endDate: null,
          companyYearId: null,
          holidayPeriod: 'ALL_DAY',
        },
        types: [
          {
            text: 'All Day',
            value: 'ALL_DAY'
          },
          {
            text: 'Morning',
            value: 'AM'
          },
          {
            text: 'Afternoon',
            value: 'PM'
          }
        ],
        companyYearRules: [
          v => !!v || 'Period is required'
        ]
      };
    },
    computed: {
      ...mapGetters({
        companyYears: 'getCurrentAndFutureCompanyYears'
      }),
      getCompanyYearStart() {
        const companyYear = this.findCompanyYear();
        return companyYear ? companyYear.yearStart : null;
      },
      getCompanyYearEnd() {
        const companyYear = this.findCompanyYear();
        return companyYear ? companyYear.yearEnd : null;
      }
    },
    methods: {
      companyYearSelected(companyYear) {
        this.holidayRequest.companyYearId = companyYear.id;
      },
      findCompanyYear() {
        const companyYears = this.companyYears ? this.companyYears : [];

        return companyYears.find((companyYear) => {
          return companyYear.id === this.holidayRequest.companyYearId;
        });
      },
      rangeSet(range) {
        this.holidayRequest.startDate = range.startDate;
        this.holidayRequest.endDate = range.endDate;
      },
      saveHoliday() {
        let valid = this.$refs.form.validate();

        if (!this.holidayRequest.startDate) {
          valid = false;
          this.addAlert({
            message: 'Start date must be supplied',
            type: 'warning'
          });
        }

        if (valid) {
          store.dispatch('saveHoliday', this.holidayRequest).then(() => {
            this.close();
          });
        }
      },
      close() {
        this.$router.push({
          name: 'holiday',
        });
      },
      ...mapMutations({
        addAlert: 'addAlert',
      }),
      ...mapActions({
        fetchCompanyYears: 'fetchCurrentAndFutureCompanyYears',
      })
    },
    beforeMount() {
      this.fetchCompanyYears();
    }
  };
</script>

<style scoped>

</style>
