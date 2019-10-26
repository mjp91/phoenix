<template>
  <v-row>
    <v-col sm="6">
      <v-card flat class="pa-4">
        <h2 class="subtitle-1">Home Address</h2>
        <v-text-field
            label="Address Line"
            v-model="employee.address.line"
        />
        <v-text-field
            label="City"
            v-model="employee.address.city"
        />
        <v-text-field
            label="State/Province/Region"
            v-model="employee.address.province"
        />
        <v-text-field
            label="Postal/Zip Code"
            v-model="employee.address.postalCode"
        />
        <v-select
            label="Country"
            v-model="employee.address.country"
            :items="countries"
            item-text="name"
            item-value="value"
        />
      </v-card>
    </v-col>
    <v-col sm="6">
      <v-card flat class="pa-4">
        <h2 class="subtitle-1">Contact Numbers</h2>
        <v-text-field
            label="Telephone"
            type="tel"
            v-model="employee.telephoneNumber"
            prepend-icon="mdi-phone"
        />
        <v-text-field
            label="Mobile"
            type="tel"
            v-model="employee.mobileNumber"
            prepend-icon="mdi-cellphone-iphone"
        />
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
  export default {
    name: "UserEmployeeContactInformation",
    data: () => {
      return {
        countries: [
          {
            name: 'United Kingdom',
            value: 'UNITED_KINGDOM'
          }
        ]
      };
    },
    props: {
      employee: Object
    }
  };
</script>

<style scoped>

</style>
