<template>
  <div>
    <form-header title="Account" :save="save">
      <template v-if="!user.ldap" slot="actions">
        <v-btn to="/account/change-password" class="mr-2" color="primary">Change Password</v-btn>
      </template>
    </form-header>
    <v-form>
      <v-tabs grow>
        <v-tab>General</v-tab>
        <v-tab>Employee</v-tab>
        <v-tab>Contact Information</v-tab>
        <v-tab>Working Hours</v-tab>
        <v-tab>Holiday Entitlement</v-tab>
        <v-tab-item>
          <v-card flat class="pa-4">
            <v-row>
              <v-col sm="6">
                <v-text-field
                    label="Name"
                    :value="user.fullName"
                    readonly
                />
                <v-text-field
                    label="Email"
                    :value="user.email"
                    readonly
                />
                <v-row>
                  <v-col sm="10">
                    <clipboard-input
                        label="Calendar Feed"
                        :value="url"
                        @copied="copied"
                    />
                  </v-col>
                  <v-col sm="2">
                    <v-btn :href="url" target="_blank" outlined small fab class="mt-3">
                      <v-icon>mdi-download</v-icon>
                    </v-btn>
                  </v-col>
                </v-row>
              </v-col>
            </v-row>
          </v-card>
        </v-tab-item>
        <v-tab-item>
          <user-employee :employee="this.employee" read-only/>
        </v-tab-item>
        <v-tab-item>
          <user-employee-contact-information :employee="this.employee"/>
        </v-tab-item>
        <v-tab-item>
          <employee-days-grid :employee-week="this.employee.employeeWeek"/>
        </v-tab-item>
        <v-tab-item>
          <user-employee-entitlement v-if="this.employee" :employee="this.employee" read-only/>
        </v-tab-item>
      </v-tabs>
    </v-form>
  </div>
</template>

<script>
import {mapActions, mapGetters} from 'vuex';
import ClipboardInput from "../components/ClipboardInput";
import getBaseUrl from "../lib/getBaseUrl";
import store from "../store";
import ImageUpload from "../components/ImageUpload";
import FormHeader from "../components/FormHeader";
import UserEmployeeContactInformation from "./admin/user/UserEmployeeContactInformation";
import UserEmployee from "./admin/user/UserEmployee";
import UserEmployeeEntitlement from "./admin/user/UserEmployeeEntitlement";
import EmployeeDaysGrid from "../components/EmployeeDaysGrid";

export default {
  name: "Account",
  components: {
    EmployeeDaysGrid,
    UserEmployeeEntitlement,
    UserEmployee,
    UserEmployeeContactInformation,
    FormHeader,
    ClipboardInput,
    ImageUpload
  },
    computed: {
      ...mapGetters({
        user: 'getAuthUser',
        employee: 'getEmployee'
      }),
      url() {
        return getBaseUrl() + `/calendar/${this.user.username}/${this.user.calendarToken}`;
      },
    },
    methods: {
      copied() {
        store.commit('addAlert', {
          type: 'success',
          message: 'URL copied to clipboard'
        });
      },
      save() {
        store.dispatch('saveEmployee', this.employee).then(() => {
          store.commit('addAlert', {
            type: 'success',
            message: 'User updated'
          });
        });
      },
      ...mapActions({
        fetchEmployeeByUserId: 'fetchByUserId',
      })
    },
    beforeMount() {
      this.fetchEmployeeByUserId(this.user.id);
    }
  };
</script>

<style scoped>

</style>
