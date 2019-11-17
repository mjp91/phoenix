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
          ></v-text-field>
          <company-year-select @select="companyYearSelected"/>
          <v-switch
              :label="dateSelection ? 'Range' : 'Single Date'"
              v-model="dateSelection">
          </v-switch>
          <v-select
              v-if="!dateSelection"
              v-model="holidayRequest.holidayPeriod"
              label="Duration"
              :items="types"
              value="ALL_DAY"
          />
        </v-col>
      </v-row>
      <v-row v-if="holidayRequest.companyYearId">
        <v-col sm="4">
          <v-subheader>From</v-subheader>
          <v-date-picker
              v-model="holidayRequest.startDate"
              :color="dateSelection ? 'green' : 'primary'"
              :min="getCompanyYearStart"
              :max="getCompanyYearEnd"
              @input="validateDates">
          </v-date-picker>
        </v-col>
        <v-col v-if="dateSelection" sm="4">
          <v-subheader>To</v-subheader>
          <v-date-picker
              v-model="holidayRequest.endDate"
              color="red"
              :min="getCompanyYearStart"
              :max="getCompanyYearEnd"
              @input="validateDates">
          </v-date-picker>
        </v-col>
      </v-row>
    </v-form>
  </div>
</template>

<script>
  import moment from 'moment';
  import {mapActions, mapGetters, mapMutations} from 'vuex';
  import store from '../../store';
  import CompanyYearSelect from "../../components/CompanyYearSelect";
  import FormHeader from "../../components/FormHeader";

  export default {
    name: "HolidayForm",
    components: {FormHeader, CompanyYearSelect},
    data() {
      return {
        dateSelection: false,
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
        companyYears: 'getCompanyYears'
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
      validateDates() {
        const startDate = this.holidayRequest.startDate;
        const endDate = this.holidayRequest.endDate;
        if (startDate && endDate) {
          const invalid = moment(startDate).isAfter(moment(endDate));
          if (invalid) {
            this.holidayRequest.endDate = startDate;
          }
        }
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

        if (this.dateSelection) {
          this.holidayRequest.holidayPeriod = 'ALL_DAY';
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
        fetchCompanyYears: 'fetchCompanyYears',
      })
    },
    beforeMount() {
      this.fetchCompanyYears();
    }
  };
</script>

<style scoped>

</style>
