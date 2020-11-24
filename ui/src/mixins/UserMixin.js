import store from "../store";
import hasRole from "../lib/hasRole";
import getBaseUrl from "../lib/getBaseUrl";
import {Roles} from "@/lib/Constants";

export default {
  methods: {
    getBaseUrl() {
      return getBaseUrl();
    },
    hasAdmin() {
      const user = store.getters.getAuthUser;

      return hasRole(user, Roles.admin);
    }
  }
};
