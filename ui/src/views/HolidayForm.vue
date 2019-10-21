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
          <holiday-year-select @select="holidayYearSelected"/>
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
      <v-row v-if="holidayRequest.holidayYearId">
        <v-col sm="4">
          <v-subheader>From</v-subheader>
          <v-date-picker
              v-model="holidayRequest.startDate"
              :color="dateSelection ? 'green' : 'primary'"
              :min="getHolidayYearStart"
              :max="getHolidayYearEnd"
              @input="validateDates">
          </v-date-picker>
        </v-col>
        <v-col v-if="dateSelection" sm="4">
          <v-subheader>To</v-subheader>
          <v-date-picker
              v-model="holidayRequest.endDate"
              color="red"
              :min="getHolidayYearStart"
              :max="getHolidayYearEnd"
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
  import store from '../store';
  import HolidayYearSelect from "../components/HolidayYearSelect";
  import FormHeader from "../components/FormHeader";

  export default {
    name: "HolidayForm",
    components: {FormHeader, HolidayYearSelect},
    data() {
      return {
        dateSelection: false,
        holidayRequest: {
          startDate: null,
          endDate: null,
          holidayYearId: null,
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
        holidayYearRules: [
          v => !!v || 'Period is required'
        ]
      };
    },
    computed: {
      ...mapGetters({
        holidayYears: 'getHolidayYears'
      }),
      getHolidayYearStart() {
        const holidayYear = this.findHolidayYear();
        return holidayYear ? holidayYear.yearStart : null;
      },
      getHolidayYearEnd() {
        const holidayYear = this.findHolidayYear();
        return holidayYear ? holidayYear.yearEnd : null;
      }
    },
    methods: {
      holidayYearSelected(holidayYear) {
        this.holidayRequest.holidayYearId = holidayYear.id;
      },
      findHolidayYear() {
        const holidayYears = this.holidayYears ? this.holidayYears : [];

        return holidayYears.find((holidayYear) => {
          return holidayYear.id === this.holidayRequest.holidayYearId;
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
        fetchHolidayYears: 'fetchHolidayYears',
      })
    },
    beforeMount() {
      this.fetchHolidayYears();
    }
  };
</script>

<style scoped>

</style>
