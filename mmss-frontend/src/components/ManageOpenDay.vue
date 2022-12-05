<template>
    <div id="ManageOpenDay">
        <br>
        <h1>
            OpenDays
        </h1>
        <b-calendar v-model="value" @context="onContext" :date-disabled-fn="dateDisabled" locale="en-US" width="800px"></b-calendar>
        <br>
        <br>
        <b-button variant="success" class="my-2 my-sm-0" @click="createOpenDay()">Create OpenDay</b-button>
        <br>
        <br>
        <b-navbar type="dark" variant="info">
                <b-navbar-brand>All OpenDay</b-navbar-brand>
                
                <b-navbar-nav class="ml-auto">
                    <b-button class="my-2 my-sm-0" @click="refreshTable()">Refresh Table</b-button>
                    <b-button class="my-2 my-sm-0" variant="danger" @click="doDeleteOpenDays()">Delete OpenDays</b-button>
                </b-navbar-nav>
            </b-navbar>

        <b-table ref="OpenDayTable" striped hover sticky-header="600px" :items="openDays" selectable :select-mode="selectMode"
            @row-selected="onRowSelected"></b-table>
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
          openDays: [],
          selectedOpenDays: [],  
          value: '',
          context: null
        }
      },
      created: function () {
        AXIOS.get('/openday', {}, {})
            .then(response => {
                // add response to all donations
                this.openDays = response.data
            })
        .catch(error => {
            // logic on the error status. Display backend error message if status is below 450
            // otherwise display something went wrong
            if (error.response.status >= 450) {
                self.errorMessage = "Oops! An error occured. Please contact the musuem directly.";
            } else {
                self.errorMessage = error.response.data;
            }
            // call the error handler component modal (named errorPopUp) to display the error message
            self.$bvModal.show('errorPopUp');
        })
    },
      methods: {
        onRowSelected(selectedRows) {
            this.selectedOpenDays = selectedRows;
        },
        clearSelected() {
            this.selectedOpenDays = [];
            this.$refs.OpenDayTable.clearSelected();
        },
        refreshTable() {
          AXIOS.get('/openday', {}, {})
            .then(response => {
                // add response to all donations
                this.openDays = response.data
            })
            .catch(error => {
            // logic on the error status. Display backend error message if status is below 450
            // otherwise display something went wrong
            if (error.response.status >= 450) {
                self.errorMessage = "Oops! An error occured. Please contact the musuem directly.";
            } else {
                self.errorMessage = error.response.data;
            }
            // call the error handler component modal (named errorPopUp) to display the error message
            self.$bvModal.show('errorPopUp');
        })
        },
        async doDeleteOpenDays() {
            for (let i = 0; i < this.selectedOpenDays.length; i++) {
                let date = this.selectedOpenDays[i].date;
                AXIOS.delete('/openday/' + date, {}, {})
                    .then(response => {
                        //refresh the table on the last request
                        this.refreshTable();
                    })
                    .catch(error => {
                        if (error.response.status >= 450) {
                            this.errorMessage = "Oops! An error occured. Please contact the musuem directly.";
                        } else {
                            this.errorMessage = error.response.data;
                        }
                        // call the error handler component modal (named errorPopUp) to display the error message
                        this.$bvModal.show('errorPopUp');
                    });
            }
            this.clearSelected();
            this.refreshTable();
        },
            onContext(ctx) {
            this.context = ctx
            },
            dateDisabled(ymd, date) {
            // Disable weekends (Sunday = `0`, Saturday = `6`)
            const weekday = date.getDay()
            // Return `true` if the date should be disabled
            return weekday === 0 || weekday === 6
        },
        async createOpenDay(){
            console.log(this.value)
            await AXIOS.post('/openday/?date=' + this.value, {}, {})
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
            this.refreshTable();
        }
      }
    }
  </script>