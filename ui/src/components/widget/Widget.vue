<template>
  <v-card>
    <v-card-title v-if="title" primary-title class="layout justify-center">
      <h2 class="display-1">{{title}}</h2>
    </v-card-title>
    <div class="px-4 pb-4">
      <slot/>
    </div>
  </v-card>
</template>
<script>
  export default {
    name: 'widget',
    props: {
      title: String
    }
  };
</script>
