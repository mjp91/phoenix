<template>
  <v-form>
    <v-btn color="success" class="float-right ma-4" @click="saveCompany(companyProxy)">Update</v-btn>
    <v-row>
      <v-col class="pa-4" sm="6">
        <v-text-field
            v-if="company"
            v-model="company.name"
            label="Name"
        ></v-text-field>
        <v-text-field
            v-if="company"
            v-model="company.defaultHolidayEntitlementHours"
            label="Default Entitlement (Hours)"
            type="number"
        />
        <div class="mb-3">
          <v-label for="employeeDays">Default Working Hours</v-label>
        </div>
        <template v-if="company" ref="employeeDays">
          <v-expansion-panels>
            <v-expansion-panel
                :key="key"
                v-for="key in Object.keys(company.defaultEmployeeWeek)">
              <v-expansion-panel-header>
                <div>{{key.charAt(0).toUpperCase() + key.slice(1)}}</div>
              </v-expansion-panel-header>
              <v-expansion-panel-content>
                <v-card flat>
                  <employee-day
                      v-model="company.defaultEmployeeWeek[key]"/>
                </v-card>
              </v-expansion-panel-content>
            </v-expansion-panel>
          </v-expansion-panels>
        </template>
      </v-col>
    </v-row>
  </v-form>
</template>

<script>
  import {mapActions} from 'vuex';
  import EmployeeDay from "../../components/EmployeeDay";

  export default {
    name: "CompanyForm",
    components: {EmployeeDay},
    data: () => {
      return {
        companyProxy: null,
      };
    },
    computed: {
      company: {
        get() {
          return this.companyProxy !== null ? this.companyProxy : this.$store.getters.getCompany;
        },
        set(val) {
          this.companyProxy = val;
        }
      },
    },
    methods: {
      ...mapActions({
        fetchCompany: 'fetchCompany',
        saveCompany: 'saveCompany'
      }),
    },
    watch: {
      company(newState) {
        this.companyProxy = newState;
      }
    },
    beforeMount() {
      this.fetchCompany();
    }
  };
</script>

<style scoped>

</style>
