<template>
  <div>
    <form-header title="New Department" :save="save" :cancel="close"/>
    <v-form>
      <v-row>
        <v-col sm="6">
          <v-text-field
              label="Title"
              v-model="department.title"
          />
          <v-select
              v-model="department.parent"
              label="Parent"
              :items="this.departments"
              item-text="title"
              :item-value="parentValue"
              :clearable="true"
          />
        </v-col>
      </v-row>
    </v-form>
  </div>
</template>

<script>
import FormHeader from "../../components/FormHeader";
import {mapActions, mapGetters} from "vuex";
import store from "../../store";

export default {
    name: "DepartmentForm",
    data: () => {
      return {
        department: {
          title: null,
          parent: null
        }
      };
    },
    computed: {
      ...mapGetters({
        departments: 'getDepartments'
      })
    },
    methods: {
      ...mapActions({
        fetchDepartments: 'fetchDepartments'
      }),
      parentValue(department) {
        return department;
      },
      save() {
        store.dispatch('saveDepartment', this.department).then(() => {
          this.close();
        });
      },
      close() {
        this.$router.push({
          name: 'department-management',
        });
      }
    },
    beforeMount() {
      this.fetchDepartments();
    },
    components: {FormHeader}
  };
</script>

<style scoped>

</style>
