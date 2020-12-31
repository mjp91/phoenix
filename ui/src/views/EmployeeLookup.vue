<template>
  <v-card v-if="this.departmentEmployees.length > 0">
    <v-sheet class="pl-4 pr-4 pt-4">
      <v-text-field
          v-model="search"
          label="Search"
          flat
          solo-inverted
          clearable
      />
    </v-sheet>
    <v-card-text>
      <v-row>
        <v-col cols="6">
          <v-treeview
              :items="this.departmentEmployees"
              :search="search"
              :open.sync="open"
              :active.sync="active"
              activatable
              open-on-click
              transition
              @update:active="employeeSelected"
          >
            <template v-slot:prepend="{ item }">
              <v-icon v-if="item.id.startsWith('D')">mdi-folder-account</v-icon>
              <v-icon v-else>mdi-account</v-icon>
            </template>
          </v-treeview>
        </v-col>
        <v-divider vertical/>
        <v-col class="d-flex text-center">
          <v-scroll-y-transition mode="out-in">
            <v-card
                v-if="this.active.length && this.employee"
                class="pt-6 mx-auto"
                flat
                max-width="400"
            >
              <v-card-text>
                <v-avatar size="88" class="mb-6">
                  <v-img :src="getUrl()"/>
                </v-avatar>
                <h3 class="headline mb-2">
                  <template v-if="hasAdmin()">
                    <router-link :to="`/admin/user/${this.employee.user.id}`">
                      {{ this.employee.user.fullName }}
                    </router-link>
                  </template>
                  <template v-else>
                    {{ this.employee.user.fullName }}
                  </template>
                </h3>
                <div class="font-weight-light">{{ this.employee.jobRole.description }}</div>
                <div>
                  <v-icon>mdi-email</v-icon>&nbsp;
                  <a :href="`mailto:${this.employee.user.email}`">{{this.employee.user.email}}</a>
                </div>
                <div v-if="this.employee.extensionNumber">
                  <v-icon>mdi-phone</v-icon>&nbsp;
                  {{ this.employee.extensionNumber }}
                </div>
              </v-card-text>
            </v-card>
          </v-scroll-y-transition>
        </v-col>
      </v-row>
    </v-card-text>
  </v-card>
  <div v-else>
    No data to display
  </div>
</template>

<script>
import {mapActions, mapGetters} from "vuex";
import getBaseUrl from "../lib/getBaseUrl";
import UserMixin from "../mixins/UserMixin";

export default {
  name: "EmployeeLookup",
  data: () => {
    return {
      search: null,
      open: [],
      active: []
    };
  },
  computed: {
    ...mapGetters({
        departmentEmployees: 'getDepartmentEmployees',
        employee: 'getEmployee'
      }),
    },
    methods: {
      getUrl() {
        const fileName = this.employee.profileFileName ? this.employee.profileFileName : 'default-profile.png';
        return getBaseUrl() + `/resource/image/${fileName}`;
      },
      ...mapActions({
        fetchDepartmentEmployees: 'fetchDepartmentEmployees',
        fetchEmployee: 'fetchEmployee'
      }),
      employeeSelected(active) {
        if (!active.length) {
          return;
        }

        this.fetchEmployee(active[0].substr(1));
      }
    },
    beforeMount() {
      this.fetchDepartmentEmployees();
    },
    mixins: [UserMixin],
  };
</script>

<style scoped>

</style>
