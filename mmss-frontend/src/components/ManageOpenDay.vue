<template>
    <div id="ManageOpenDay">
        <br>
        <h1>
            OpenDays
        </h1>
        <b-calendar v-model="value" @context="onContext" :date-disabled-fn="dateDisabled" locale="en-US" width="800px"></b-calendar>
        <br>
        <br>
        <b-button variant="success" class="my-2 my-sm-0" @click="createOpenDay()">Create OpenDays</b-button>
        <br>
    </div>
  </template>
  
  <script>
    import axios from 'axios'
    // Import the component that displays the error message
    var config = require('../../config')
    var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
    var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
    var AXIOS = axios.create({
        baseURL: backendUrl,
        headers: { 'Access-Control-Allow-Origin': frontendUrl }
    })
    export default {
      data() {
        return {  
          value: [],
          context: null
        }
      },
      methods: {
            onContext(ctx) {
            this.context = ctx
            },
            dateDisabled(ymd, date) {
            // Disable weekends (Sunday = `0`, Saturday = `6`)
            const weekday = date.getDay()
            // Return `true` if the date should be disabled
            return weekday === 0 || weekday === 6
        },
        createOpenDay(){
            console.log(this.value)
            AXIOS.post('/openday/?date=' + this.value, {}, {})
            .then((response) => {
                // Show response
                //alert('The OpenDay was successfully created.')
            })
            .catch((error) => {
                if (error.response.status >= 450) {
                    self.errorMessage = "Oops! An error occured. Please contact the musuem directly.";
                } else {
                    self.errorMessage = error.response.data;
                }
                // call the error handler component modal (named errorPopUp) to display the error message
                self.$bvModal.show('errorPopUp');
            });
        }
      }
    }
  </script>