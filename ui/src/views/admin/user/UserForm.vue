<template>
  <div>
    <v-row justify="center">
      <v-dialog v-model="dialog" persistent max-width="500">
        <v-card>
          <v-card-title class="headline">Leaver</v-card-title>
          <v-card-text>
            <div class="mb-1">
              Select the employee's leave date and confirm.
              Access will be revoked immediately.
            </div>
            <v-row justify="center">
              <v-date-picker
                  v-model="leaveDate"
                  color="error"
                  :max="new Date().toISOString()"
              />
            </v-row>
          </v-card-text>
          <v-card-actions>
            <v-spacer/>
            <v-btn @click="dialog = false">Cancel</v-btn>
            <v-btn color="error" @click="leave" :disabled="leaveDate === null">Confirm</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>
    <form-header
        :title="employee.user.fullName"
        :cancel="close"
        :save="save"
    >
      <template slot="chips">
        <v-chip v-if="employee.serviceEndDate" color="error">Left</v-chip>
      </template>
      <template slot="actions">
        <template v-if="!employee.serviceEndDate">
          <v-btn
              v-if="!employee.user.ldap && employee.user.totpEnabled"
              class="mr-2"
              color="primary"
              @click="resetTwoFactorAuth">
            Reset 2FA
          </v-btn>
          <v-btn
              v-if="hasAdmin"
              class="mr-2"
              color="error"
              @click="dialog = true">
            Leave
          </v-btn>
        </template>
      </template>
    </form-header>
    <v-form ref="form">
      <v-tabs grow>
        <v-tab>General</v-tab>
        <v-tab>Employee</v-tab>
        <v-tab>Contact Information</v-tab>
        <v-tab>Working Hours</v-tab>
        <v-tab>Holiday Entitlement</v-tab>
        <v-tab-item>
          <v-card flat class="pa-4">
            <user-general :user="this.employee.user"/>
          </v-card>
        </v-tab-item>
        <v-tab-item v-if="this.employee">
          <v-card flat>
            <user-employee :employee="this.employee"/>
          </v-card>
        </v-tab-item>
        <v-tab-item v-if="this.employee">
          <v-card flat>
            <user-employee-contact-information :employee="this.employee"/>
          </v-card>
        </v-tab-item>
        <v-tab-item v-if="this.employee">
          <v-card flat>
            <user-employee-days :employee="this.employee"/>
          </v-card>
        </v-tab-item>
        <v-tab-item v-if="this.employee">
          <v-card flat>
            <user-employee-entitlement :employee="this.employee"/>
          </v-card>
        </v-tab-item>
      </v-tabs>
    </v-form>
  </div>
</template>

<script>
import {mapActions, mapGetters} from 'vuex';
import store from '../../../store';
import UserGeneral from "./UserGeneral";
import UserEmployee from "./UserEmployee";
import UserEmployeeDays from "./UserEmployeeDays";
import UserEmployeeEntitlement from "./UserEmployeeEntitlement";
import FormHeader from "../../../components/FormHeader";
import UserEmployeeContactInformation from "./UserEmployeeContactInformation";
import UserMixin from "../../../mixins/UserMixin";

export default {
  name: "UserForm",
  data: () => {
    return {
      dialog: false,
      leaveDate: null
    };
  },
  computed: {
    ...mapGetters({
      employee: 'getEmployee',
      })
    },
    methods: {
      ...mapActions({
        fetchEmployeeByUserId: 'fetchByUserId',
        reset2fa: 'reset2fa',
        leaveEmployee: 'leaveEmployee'
      }),
      save() {
        Promise.all([
          store.dispatch('saveUser', this.user),
          store.dispatch('saveEmployee', this.employee)
        ]).then(() => {
          store.commit('addAlert', {
            type: 'success',
            message: 'User updated'
          });
        }).then(() => {
            this.close();
          }
        );
      },
      close() {
        this.$router.push({
          name: 'user-management',
        });
      },
      resetTwoFactorAuth() {
        this.reset2fa(this.user.username).then(() => {
          store.commit('addAlert', {
            type: 'success',
            message: '2FA reset successfully'
          });
        });
      },
      leave() {
        this.leaveEmployee({
          employeeId: this.employee.id,
          leaveDate: this.leaveDate
        }).then(() => {
          store.commit('addAlert', {
            type: 'success',
            message: 'Employee left successfully'
          });
          this.dialog = false;
          this.close();
        });
      }
    },
    beforeMount() {
      const userId = this.$route.params.id;
      this.fetchEmployeeByUserId(userId);
    },
    components: {
      UserEmployeeContactInformation,
      FormHeader,
      UserEmployeeEntitlement,
      UserEmployeeDays,
      UserEmployee,
      UserGeneral,
    },
    mixins: [UserMixin],
  };
</script>

<style scoped>

</style>
