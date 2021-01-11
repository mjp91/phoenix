<template>
  <div>
    <form-header :title="title" :save="() => save(department)" :cancel="close"/>
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
  props: {
    title: {},
    close: {},
    populateWith: {},
    save: {}
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
  },
  beforeMount() {
    this.fetchDepartments();
  },
  mounted() {
    if (this.populateWith) {
      this.department = this.populateWith;
    }
  },
  components: {FormHeader}
};
</script>

<style scoped>

</style>
