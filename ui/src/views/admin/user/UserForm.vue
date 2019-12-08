<template>
  <div>
    <form-header
        :title="user.fullName"
        :cancel="close"
        :save="save"
    />
    <v-form ref="form">
      <v-tabs grow>
        <v-tab>General</v-tab>
        <v-tab>Employee</v-tab>
        <v-tab>Contact Information</v-tab>
        <v-tab>Working Hours</v-tab>
        <v-tab>Holiday Entitlement</v-tab>
        <v-tab-item>
          <v-card flat class="pa-4">
            <user-general :user="this.user"/>
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

  export default {
    name: "UserForm",
    data: () => {
      return {};
    },
    computed: {
      ...mapGetters({
        user: 'getUser',
        employee: 'getEmployee',
      })
    },
    methods: {
      ...mapActions({
        fetchUser: 'fetchUser',
        fetchEmployeeByUserId: 'fetchByUserId',
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
    },
    beforeMount() {
      const userId = this.$route.params.id;
      this.fetchUser(userId);
      this.fetchEmployeeByUserId(userId);
    },
    components: {
      UserEmployeeContactInformation,
      FormHeader,
      UserEmployeeEntitlement,
      UserEmployeeDays,
      UserEmployee,
      UserGeneral,
    }
  };
</script>

<style scoped>

</style>
