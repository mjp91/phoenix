<template>
  <v-data-table
      :headers="headers"
      :items="this.jobRoles"
      @click:row="onRowClick"
  >
    <template v-slot:top>
      <v-toolbar flat>
        <div class="flex-grow-1"></div>
        <v-btn color="success" to="/admin/job-role/new">New</v-btn>
      </v-toolbar>
    </template>
  </v-data-table>
</template>

<script>
import {mapActions, mapGetters} from "vuex";

export default {
  name: "JobRoleManagement",
  data: () => {
    return {
      headers: [
        {
          text: 'Description',
          value: 'description'
        }
      ]
    };
    },
    computed: {
      ...mapGetters({
        jobRoles: 'getJobRoles'
      })
    },
    methods: {
      ...mapActions({
        fetchJobRoles: 'fetchJobRoles'
      }),
      onRowClick(item) {
        this.$router.push({
          name: 'job-role',
          params: {
            id: item.id
          }
        });
      }
    },
    beforeMount() {
      this.fetchJobRoles();
    }
  };
</script>

<style scoped>

</style>
