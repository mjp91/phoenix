<template>
  <v-card flat color="transparent">
    <v-row align="center" justify="center">
      <v-col sm="3" class="pb-0">
        <v-avatar size="164" :tile="tile">
          <v-img
              :src="getUrl()"
              height="100%"
          />
        </v-avatar>
      </v-col>
      <v-col sm="12" class="pt-0">
        <v-file-input
            :label="this.label"
            prepend-icon="mdi-camera"
            accept="image/*"
            @change="upload"
        />
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
import getBaseUrl from "../lib/getBaseUrl";
import store from "../store";

export default {
    name: "ImageUpload",
    data: function () {
      return {
        currentFileName: this.value ? this.value : this.defaultFileName
      };
    },
    props: {
      value: String,
      label: String,
      tile: {
        type: Boolean,
        default: true
      },
      defaultFileName: {
        type: String,
        default: 'default.png'
      }
    },
    methods: {
      getUrl() {
        return getBaseUrl() + `/resource/image/${this.currentFileName}`;
      },
      upload(file) {
        store.dispatch('uploadResource', file).then((fileNames) => {
          this.currentFileName = fileNames[0];
          this.$emit('input', fileNames[0]);
        });
      }
    }
  };
</script>

<style scoped>

</style>
