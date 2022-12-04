<!-- 
    Form to update an existing artefact
    TODO:
    fees as double
    no fees shown if not available for loan
    Show numbers of chars left 
    Remove form data result
 -->
 <template>
    <div>
      <b-form @submit="onSubmit" @reset="onReset" v-if="show">
        <!-- Name -->
        <b-form-group 
            id="input-group-1" 
            label="Name:" 
            label-for="input-1">
            <b-form-input 
                id="input-1" 
                v-model="request.artefactName" 
                type="text" 
                :state="request.artefactName.length <= 50"
                required>
            </b-form-input>
        </b-form-group>
        <!-- Description -->
        <b-form-group
            id="input-group-2" 
            label="Description:" 
            label-for="textarea-1">
            <b-form-textarea
                id="textarea-1"
                v-model="request.description"
                :state="request.description.length <= 300"
                rows="3"
                required>
            </b-form-textarea>
        </b-form-group>
        <!-- Loan availability -->
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
        <!-- Insurance fee -->
        <b-form-group 
            id="input-group-4" 
            label="Insurance Fee:" 
            label-for="input-2">
            <b-form-input
                id="input-2"
                v-model="request.insuranceFee"
                type="number"
                required
            ></b-form-input>
        </b-form-group> 
        <!-- Loan fee -->
        <b-form-group 
            id="input-group-5" 
            label="Loan Fee:" 
            label-for="input-3">
            <b-form-input
                id="input-3"
                v-model="request.loanFee"
                type="number"
                required
            ></b-form-input>
        </b-form-group> 
        <!-- Image URL -->
        <b-form-group 
            id="input-group-6" 
            label="Image URL:" 
            label-for="input-4">
            <b-form-input 
                id="input-4" 
                v-model="request.imageUrl" 
                type="text" 
                required>
            </b-form-input>
        </b-form-group>
  
        <b-button type="submit" variant="primary">Update</b-button>
        <b-button type="reset" variant="danger">Reset</b-button>
      </b-form>
        <!-- The component that displays the error message. Links the message of that component to -->
        <ErrorHandler :message="errorMessage" />    
    </div>
  </template>
  
  <script>
import axios from 'axios'
import ErrorHandler from './ErrorPopUp.vue'

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
            roomOptions:[],
            request: {
                artefactId: 0,
                artefactName: '',
                description: '',
                canLoan: false,
                insuranceFee: '0.00',
                loanFee: '0.00',
                roomId: '',
                imageUrl: '',
            },
            availabilities: [
                { value: 'true', text: 'Available for loan' },
                { value: 'false', text: 'Not available for loan' },
            ],
            errorMessage: '',
            show: true
        }
    },
    props: {
        artefactId : Number,
    },
    created: function () {
        const self = this       
        AXIOS.get(`/artefact/${self.artefactId}`, {}, {})
        .then(response => {
        //Assign the artefacts'field to the request object
        const artefact = response.data
        console.log(artefact) 
        self.request.artefactId = artefact.artefactId
        self.request.artefactName = artefact.artefactName
        self.request.description = artefact.description
        self.request.canLoan = artefact.canLoan
        self.request.insuranceFee = artefact.insuranceFee
        self.request.loanFee = artefact.loanFee
        self.request.roomId = artefact.roomId
        self.request.imageUrl = artefact.imageUrl
        })
        .catch((error) => {
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
        // When the Update button is clicked
        onSubmit(event) {
            event.preventDefault()
            const self = this
            AXIOS.put('/artefact', self.request, {})
            .then((response) => {
                // Show response
                alert('The artefact was successfully updated.')
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

        },
        // When the Reset button is clicked
        onReset(event) {
          event.preventDefault()
          const self = this
          // Reset our form values
          self.resetVariables()
          // Trick to reset/clear native browser form validation state
          self.show = false
          self.$nextTick(() => {
            self.show = true
          })
        },
        // Reset the values of the request object for a particular artefact
        resetVariables() {
            const self = this       
            AXIOS.get(`/artefact/${self.artefactId}`, {}, {})
            .then(response => {
            //Assign the artefacts'field to the request object
            const artefact = response.data
            console.log(artefact)
            self.request.artefactId = artefact.artefactId
            self.request.artefactName = artefact.artefactName
            self.request.description = artefact.description
            self.request.canLoan = artefact.canLoan
            self.request.insuranceFee = artefact.insuranceFee
            self.request.loanFee = artefact.loanFee
            self.request.roomId = artefact.roomId
            self.request.imageUrl = artefact.imageUrl
            })
            .catch((error) => {
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
        }
      }
    }
  </script>