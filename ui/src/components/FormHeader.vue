<template>
  <v-row>
    <v-col>
      <h1 class="headline">
        {{title}}
        <slot name="chips"/>
      </h1>
    </v-col>
    <v-col>
      <v-row align="end" justify="end">
        <v-btn v-if="cancel" class="mr-2" @click="cancel">Cancel</v-btn>
        <slot name="actions"/>
        <v-btn v-if="save" class="mr-2" color="success" @click="save">Save</v-btn>
      </v-row>
    </v-col>
  </v-row>
</template>

<script>
  export default {
    name: "FormHeader",
    props: {
      title: String,
      cancel: Function,
      save: Function
    }
  };
</script>

<style scoped>

</style>
