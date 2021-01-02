<template>
  <v-container class="fill-height" fluid>
    <v-row justify="center" align="center">
      <v-col cols="12" sm="8" md="4">
        <alert/>
        <v-card class="elevation-12">
          <v-toolbar
              color="deep-orange"
              dark
              flat
          >
            <v-toolbar-title>Holibyte</v-toolbar-title>
            <div class="flex-grow-1"></div>
            <v-subheader>{{this.title}}</v-subheader>
          </v-toolbar>
          <v-card-text>
            <slot/>
          </v-card-text>
          <v-card-actions>
            <div class="flex-grow-1"></div>
            <slot name="actions"/>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import Alert from "./Alert";

export default {
    name: "LoginTemplate",
    props: {
      title: String,
    },
    components: {Alert}
  };
</script>

<style scoped>

</style>
