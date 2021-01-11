<template>
  <department-form
      v-if="this.$route.params.id === 'new' || department"
      :title="title"
      :populate-with="department"
      :close="close"
      :save="save"
  />
</template>

<script>
import DepartmentForm from "@/views/admin/DepartmentForm";
import Vue from "vue";
import store from "@/store";

export default {
  name: "DepartmentFormView",
  data() {
    return {
      department: null
    };
  },
  computed: {
    title() {
      return (this.$route.params.id !== 'new' ? "Edit" : "New") + " Department";
    }
  },
  methods: {
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
  mounted() {
    if (this.$route.params.id !== 'new') {
      Vue.axios.get(`/department/${this.$route.params.id}`).then(response => {
        this.department = response.data;
      });
    }
  },
  components: {DepartmentForm}
};
</script>

<style scoped>

</style>
