<template>
  <div>
    <form-header :title="title" :save="() => save(jobRole)" :cancel="close"/>
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

export default {
  name: 'JobRoleForm',
  components: {FormHeader},
  data() {
    return {
      jobRole: {}
    };
  },
  props: {
    title: {},
    close: {},
    populateWith: {},
    save: {}
  },
  mounted() {
    if (this.populateWith) {
      this.jobRole = this.populateWith;
    }
  }
};
</script>
