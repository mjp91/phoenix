<template>
  <v-select
      :items="this.companyYears"
      @input="onSelect"
      label="Period"
      item-text="name"
      :item-value="itemValue"
      :rules="companyYearRules"
      single-line>
  </v-select>
</template>

<script>
  import {mapActions, mapGetters} from "vuex";

  export default {
    name: "CompanyYearSelect",
    data: () => {
      return {
        companyYearRules: [
          v => !!v || 'Period is required'
        ]
      };
    },
    computed: {
      ...mapGetters({
        companyYears: 'getCompanyYears'
      }),
    },
    methods: {
      itemValue(companyYear) {
        return companyYear;
      },
      onSelect(e) {
        this.$emit('select', e);
      },
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
