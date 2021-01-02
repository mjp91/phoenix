<template>
  <v-row>
    <v-col sm="6">
      <v-card flat class="pa-4">
        <!--Reference-->
        <v-text-field
            v-model="employee.reference"
            label="Reference"
            :readonly="readOnly"
        />
        <!--Manager-->
        <v-text-field
            v-if="readOnly"
            label="Manager"
            :value="employee.manager ? employee.manager.fullName : 'N/A'"
            readonly
        />
        <v-select
            v-else
            v-model="employee.manager"
            label="Manager"
            :items="this.employees"
            item-text="user.fullName"
            :item-value="managerValue"
            :clearable="true"
            :readonly="readOnly"
        />
        <!--Department-->
        <v-text-field
            v-if="readOnly"
            label="Department"
            :value="employee.department ? employee.department.title : 'N/A'"
            readonly
        />
        <v-select
            v-else
            v-model="employee.department"
            label="Department"
            :items="this.departments"
            item-text="title"
            :item-value="departmentValue"
            :clearable="true"
            :readonly="readOnly"
        />
        <!--Job Role-->
        <v-text-field
            v-if="readOnly"
            label="Job Role"
            :value="employee.jobRole ? employee.jobRole.description : 'N/A'"
            readonly
        />
        <v-select
            v-else
            v-model="employee.jobRole"
            label="Job Role"
            :items="this.jobRoles"
            item-text="description"
            :item-value="jobRoleValue"
            :clearable="true"
            :readonly="readOnly"
        />
        <!--Extension-->
        <v-text-field
            v-model="employee.extensionNumber"
            label="Extension"
            :readonly="readOnly"
        />
        <!--DOB-->
        <v-menu
            ref="dobMenu"
            v-model="dobMenu"
            :close-on-content-click="false"
            transition="scale-transition"
            offset-y
            min-width="290px"
            :disabled="readOnly"
        >
          <template v-slot:activator="{ on }">
            <v-text-field
                v-model="employee.dateOfBirth"
                label="Date of Birth"
                prepend-icon="mdi-cake"
                readonly
                v-on="on"
            />
          </template>
          <v-date-picker
              ref="dobPicker"
              v-model="employee.dateOfBirth"
              :max="new Date().toISOString().substr(0, 10)"
              min="1900-01-01"
              :readonly="readOnly"
          />
        </v-menu>
        <!--Service start-->
        <v-menu
            ref="serviceStartMenu"
            v-model="serviceStartMenu"
            :close-on-content-click="false"
            transition="scale-transition"
            offset-y
            min-width="290px"
            :disabled="readOnly"
        >
          <template v-slot:activator="{ on }">
            <v-text-field
                v-model="employee.serviceStartDate"
                label="Service Start Date"
                prepend-icon="mdi-calendar"
                readonly
                v-on="on"
            />
          </template>
          <v-date-picker
              ref="serviceStartPicker"
              v-model="employee.serviceStartDate"
              :max="new Date().toISOString().substr(0, 10)"
              min="1900-01-01"
              :readonly="readOnly"
          />
        </v-menu>
        <v-text-field
            v-if="employee.serviceEndDate"
            :value="employee.serviceEndDate"
            label="Service End Date"
            prepend-icon="mdi-calendar"
            readonly
        />
      </v-card>
    </v-col>
    <v-col sm="6">
      <v-card flat class="pa-4">
        <image-upload
            label="Profile Picture"
            v-model="employee.profileFileName"
            default-file-name="default-profile.png"
            :tile="false"
        />
        <v-text-field
            v-if="hasAdmin(employee.id)"
            label="Bradford Score"
            :value="bradfordScore.score"
            readonly
        />
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import {mapActions, mapGetters} from "vuex";
import store from "../../../store";
import ImageUpload from "../../../components/ImageUpload";
import UserMixin from "../../../mixins/UserMixin";

export default {
  name: "UserEmployee",
  data: () => {
    return {
      dobMenu: false,
      serviceStartMenu: false
    };
  },
  components: {ImageUpload},
  props: {
    employee: Object,
      readOnly: {
        type: Boolean,
        default: false
      },
      watch: {
        dobMenu(val) {
          val && setTimeout(() => (this.$refs.dobPicker.activePicker = 'YEAR'));
        },
        serviceStartMenu(val) {
          val && setTimeout(() => (this.$refs.serviceStartPicker.activePicker = 'YEAR'));
        }
      },
    },
    computed: {
      employees: function () {
        return store.getters.getEmployees.filter((employee) => {
          return this.employee.id !== employee.id;
        });
      },
      ...mapGetters({
        jobRoles: 'getJobRoles',
        departments: 'getDepartments',
        bradfordScore: 'getBradfordScore'
      })
    },
    methods: {
      managerValue(employee) {
        return employee.id;
      },
      jobRoleValue(jobRole) {
        return jobRole.id;
      },
      departmentValue(department) {
        return department.id;
      },
      ...mapActions({
        fetchEmployees: 'fetchEmployees',
        fetchJobRoles: 'fetchJobRoles',
        fetchDepartments: 'fetchDepartments',
        fetchBradfordScore: 'fetchBradfordScore'
      }),
    },
    beforeMount() {
      if (this.hasAdmin()) {
        this.fetchBradfordScore(this.employee.id);
      }

      if (!this.readOnly) {
        this.fetchEmployees();
        this.fetchJobRoles();
        this.fetchDepartments();
      }
    },
    mixins: [UserMixin],
  };
</script>

<style scoped>

</style>
