<template>
  <v-text-field
      ref="input"
      :label="label"
      append-icon="mdi-content-copy"
      :value="value"
      @click:append="copy"
      readonly
  />
</template>

<script>
  export default {
    name: "ClipboardInput",
    props: {
      label: String,
      value: String,
    },
    methods: {
      copy() {
        const input = this.$refs.input;
        const data = input.value;

        navigator.clipboard.writeText(data).then(() => {
          this.$emit('copied');
        });
      },
    }
  };
</script>

<style scoped>

</style>
