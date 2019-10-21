export default (user, role) => {
  if (!user) {
    return false;
  }

  return user.roles.map(role => role.name).includes(role);
};
