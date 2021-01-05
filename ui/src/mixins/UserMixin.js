import store from "../store";
import getBaseUrl from "../lib/getBaseUrl";
import {hasAdmin} from "@/lib/hasAdmin";

export default {
  methods: {
    getBaseUrl() {
      return getBaseUrl();
    },
    hasAdmin() {
      const user = store.getters.getAuthUser;

      return hasAdmin(user);
    }
  }
};
