<template>
  <div>
    <form-header title="New Job Role" :save="save" :cancel="close"/>
    <v-form>
      <v-row>
        <v-col sm="6">
          <v-text-field
              label="Description"
              v-model="jobRole.description"
          />
        </v-col>
      </v-row>
    </v-form>
  </div>
</template>

<script>
  import FormHeader from "../../components/FormHeader";
  import store from "../../store";

  export default {
    name: "JobRoleForm",
    data: () => {
      return {
        jobRole: {
          description: null
        }
      };
    },
    methods: {
      save() {
        store.dispatch('saveJobRole', this.jobRole).then(() => {
          this.close();
        });
      },
      close() {
        this.$router.push({
          name: 'job-role-management',
        });
      }
    },
    components: {FormHeader}
  };
</script>

<style scoped>

</style>
