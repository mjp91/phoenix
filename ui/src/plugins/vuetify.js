import Vue from 'vue';
import Vuetify from 'vuetify/lib';
import '@mdi/font/css/materialdesignicons.css';
import colors from 'vuetify/lib/util/colors';

Vue.use(Vuetify);

export default new Vuetify({
  theme: {
    dark: true,
    themes: {
      dark: {
        primary: colors.deepOrange.base,
        secondary: colors.cyan.base,
        error: colors.red.base,
        success: colors.green.darken2
      }
    }
  },
  icons: {
    iconfont: 'mdi',
  },
});
