import hasRole from "@/lib/hasRole";
import {Roles} from "@/lib/Constants";

export function hasAdmin(user) {
  return hasRole(user, Roles.admin) || hasRole(user, Roles.superAdmin);
}
