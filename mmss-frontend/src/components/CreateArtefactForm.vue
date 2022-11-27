<!-- 
    TODO:
    fees as double
    no fees shown if not available for loan
    Show numbers of chars left 
    Size of page (pup up)
    Remove form data result
 -->
<template>
    <div id="CreateArtefactForm">
      <b-form @submit="onSubmit" @reset="onReset" v-if="show">
       
        <b-form-group 
            id="input-group-1" 
            label="Name:" 
            label-for="input-1">
            <b-form-input 
                id="input-1" 
                v-model="request.artefactName" 
                type="text" 
                :state="request.artefactName.length <= 50"
                placeholder="Enter artefact's name (maximum 50 characters)" 
                required>
            </b-form-input>
        </b-form-group>

        <b-form-group
            id="input-group-2" 
            label="Description:" 
            label-for="textarea-1">
            <b-form-textarea
                id="textarea-1"
                v-model="request.description"
                :state="request.description.length <= 300"
                placeholder="Enter artefact's description (maximum 300 characters)"
                rows="3"
                required>
            </b-form-textarea>
        </b-form-group>
  
        <b-form-group 
            id="input-group-3" 
            label="Loan Availability" 
            label-for="radio-group-1"
            v-slot="{ ariaDescribedby }">
            <b-form-radio-group
                id="radio-group-1"
                v-model="request.canLoan"
                :options="availabilities"
                :aria-describedby="ariaDescribedby"
                name="radio-options"
                required>
            </b-form-radio-group>
        </b-form-group>

        <b-form-group 
            id="input-group-4" 
            label="Insurance Fee:" 
            label-for="input-2">
            <b-form-input
                id="input-2"
                v-model="request.insuranceFee"
                type="number"
                placeholder="Enter artefact's insurance fee"
                required
            ></b-form-input>
        </b-form-group> 

        <b-form-group 
            id="input-group-5" 
            label="Loan Fee:" 
            label-for="input-3">
            <b-form-input
                id="input-3"
                v-model="request.loanFee"
                type="number"
                placeholder="Enter artefact's loan fee"
                required
            ></b-form-input>
        </b-form-group> 
    
        <b-form-group 
            id="input-group-6" 
            label="Room:" 
            label-for="input-4">
            <b-form-select
                id="input-4"
                v-model="request.roomId"
                :options="roomOptions"
                required
            ></b-form-select>
        </b-form-group>
  
        <b-button type="submit" variant="primary">Create</b-button>
        <b-button type="reset" variant="danger">Reset</b-button>
      </b-form>
        <!-- The component that displays the error message. Links the message of that component to -->
        <ErrorHandler :message="errorMessage" />    
      <b-card class="mt-3" header="Form Data Result">
        <pre class="m-0">{{ request }}</pre>
      </b-card>
    </div>
  </template>
  
  <script>
import axios from 'axios'
// Import the component that displays the error message
import ErrorHandler from './ErrorPopUp.vue'; // This is the error component
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    components: {
        ErrorHandler
    },
    data() {
        return {
            roomOptions:[
                { value: '', text: 'Storage' },
                { value: '', text: 'Large1' },
                { value: '', text: 'Large2' },
                { value: '', text: 'Large3' },
                { value: '', text: 'Large4' },
                { value: '', text: 'Large5' },
                { value: '', text: 'Small1' },
                { value: '', text: 'Small2' },
                { value: '', text: 'Small3' },
                { value: '', text: 'Small4' },
                { value: '', text: 'Small5' }
            ],
            request: {
                artefactName: '',
                description: '',
                canLoan: false,
                insuranceFee: '0.00',
                loanFee: '0.00',
                roomId: ''
            },
            availabilities: [
                { value: 'true', text: 'Available for loan' },
                { value: 'false', text: 'Not available for loan' },
            ],
            errorMessage: '',
            show: true
        }
      },
    created: function () {
        // Call getAllRooms, 3 args necessary
        AXIOS.get('/room', {}, {})
        .then(response => {
        //Assign the roomId to the room names
        const rooms = response.data
        let smallIndex = 0
        let largeIndex = 0
        for (let i = 0; i < rooms.length; i++) {
            let room = rooms[i]
            if (room.roomtype == 'Storage'){
                this.roomOptions[0].value = room.roomId
            }
            else if (room.roomtype == 'Large'){
                this.roomOptions[1 + largeIndex].value = room.roomId
                largeIndex++
            }
            else if (room.roomtype == 'Small'){
                this.roomOptions[6 + smallIndex].value = room.roomId
                smallIndex++
            }
        }
        console.log(rooms)
        })
        .catch((error) => {
            // logic on the error status. Display backend error message if status is below 450
            // otherwise display something went wrong
            if (error.response.status >= 450) {
                this.errorMessage = "Oops! An error occured. Please contact the musuem directly.";
            } else {
                this.errorMessage = error.response.data;
            }
            // call the error handler component modal (named errorPopUp) to display the error message
            this.$bvModal.show('errorPopUp');
        })
    },
      methods: {
        onSubmit(event) {
            event.preventDefault()
            // Call getAllRooms, 3 args necessary
            AXIOS.post('/artefact', this.request, {})
            .then((response) => {
                // Show response
                alert(JSON.stringify(response))
                // Empty the form
                this.resetVariables()
                //onReset
                    // send to some page eventually
                    //this.$router.push('/');
            })
            .catch((error) => {
                if (error.response.status >= 450) {
                    this.errorMessage = "Oops! An error occured. Please contact the musuem directly.";
                } else {
                    this.errorMessage = error.response.data;
                }
                // call the error handler component modal (named errorPopUp) to display the error message
                this.$bvModal.show('errorPopUp');
            });

        },
        onReset(event) {
          event.preventDefault()
          // Reset our form values
          this.resetVariables()
          // Trick to reset/clear native browser form validation state
          this.show = false
          this.$nextTick(() => {
            this.show = true
          })
        },
        resetVariables() {
            this.request.artefactName = ''
            this.request.description = ''
            this.request.canLoan = false
            this.request.insuranceFee = '0.00'
            this.request.loanFee = '0.00'
            this.request.roomId = ''
        }
      }
    }
  </script>