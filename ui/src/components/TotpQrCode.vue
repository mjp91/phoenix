<template>
  <v-row align="center" justify="center">
    <v-card raised>
      <v-img
          v-if="totpUrl"
          :src="totpUrl"
          :width="200"
          :height="200"
      />
    </v-card>
  </v-row>
</template>
<script>
  export default {
    name: 'totp-qr-code',
    props: {
      totpUrl: String
    }
  };
</script>
