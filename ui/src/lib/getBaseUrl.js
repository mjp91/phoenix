export default () => {
  return process.env.VUE_APP_SERVER_URL ? process.env.VUE_APP_SERVER_URL : 'http://localhost:8080/api';
};
