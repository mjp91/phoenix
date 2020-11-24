<template>
  <job-role
      v-if="this.$route.params.id === 'new' || jobRole"
      :title="title"
      :populate-with="jobRole"
      :close="close"
      :save="save"
  />
</template>

<script>
import store from "../../store";
import JobRole from "@/views/admin/JobRole";
import Vue from 'vue';

export default {
  name: "JobRoleForm",
  data() {
    return {
      jobRole: null
    };
  },
  computed: {
    title() {
      return (this.$route.params.id !== 'new' ? "Edit" : "New") + " Job Role";
    },
  },
  methods: {
    save(jobRole) {
      store.dispatch('saveJobRole', jobRole).then(() => {
        this.close();
      });
    },
    close() {
      this.$router.push({
        name: 'job-role-management',
      });
    },
  },
  mounted() {
    if (this.$route.params.id !== 'new') {
      Vue.axios.get(`/job-role/${this.$route.params.id}`).then(response => {
        this.jobRole = response.data;
      });
    }
  },
  components: {JobRole},
};
</script>

