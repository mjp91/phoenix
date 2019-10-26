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
      </v-card>
    </v-col>
    <v-col sm="6">
      <image-upload
          label="Profile Picture"
          v-model="employee.profileFileName"
          default-file-name="default-profile.png"
          :tile="false"
      />
    </v-col>
  </v-row>
</template>

<script>
  import {mapActions, mapGetters} from "vuex";
  import store from "../../../store";
  import ImageUpload from "../../../components/ImageUpload";

  export default {
    name: "UserEmployee",
    components: {ImageUpload},
    props: {
      employee: Object,
      readOnly: {
        type: Boolean,
        default: false
      }
    },
    computed: {
      employees: function () {
        return store.getters.getEmployees.filter((employee) => {
          return this.employee.id !== employee.id;
        });
      },
      ...mapGetters({
        jobRoles: 'getJobRoles',
        departments: 'getDepartments'
      })
    },
    methods: {
      managerValue(employee) {
        return employee;
      },
      jobRoleValue(jobRole) {
        return jobRole;
      },
      departmentValue(department) {
        return department;
      },
      ...mapActions({
        fetchEmployees: 'fetchEmployees',
        fetchJobRoles: 'fetchJobRoles',
        fetchDepartments: 'fetchDepartments'
      }),
    },
    beforeMount() {
      if (!this.readOnly) {
        this.fetchEmployees();
        this.fetchJobRoles();
        this.fetchDepartments();
      }
    }
  };
</script>

<style scoped>

</style>
