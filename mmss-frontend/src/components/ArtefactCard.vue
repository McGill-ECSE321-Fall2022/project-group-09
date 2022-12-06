<template>
    <div>
        <b-col>
            <!-- Card to display the artefacts as list 
                 Passed an artefact DTO as prop -->
            <b-card
            @click="$bvModal.show(String(artefact.artefactId))"
            :title=artefact.artefactName
            :img-src=artefact.imageUrl
            img-alt="Image"
            img-top
            tag="artefact"
            style="max-width: 20rem;"
            class="mb-2"
            >
            <b-list-group flush>
                <b-list-group-item>{{ (artefact.canLoan && !artefact.currentlyOnLoan)? 'Available for loan' : 'Not available for loan' }}</b-list-group-item>
                <b-list-group-item>In {{ room.roomName }}</b-list-group-item>
            </b-list-group>
            </b-card>  
        </b-col> 
        <!-- Pop up for more information on a particular artefact -->
        <b-modal 
            :id=String(artefact.artefactId) 
            :title=artefact.artefactName 
            centered 
            size="xl" 
            scrollable
            hide-footer>
            <b-container fluid>
                <b-row>
                    <b-col>
                        <b-img fluid-grow :src=artefact.imageUrl alt="Image"></b-img>
                    </b-col>
                    <b-col>
                            <b-row align-self="stretch"> <b> Description: </b> {{ artefact.description }} <br> </b-row>
                            <b-row align-v="end"><br><br> <b> In {{ room.roomName }}</b> <br><br></b-row>
                            <b-row align-v="end"> <br><b> {{ (artefact.canLoan && !artefact.currentlyOnLoan)? 'Available for loan' : 'Not available for loan' }} </b><br></b-row>
                            <b-row align-v="end" v-if="(artefact.canLoan && !artefact.currentlyOnLoan)"><br> <b>Insurance fee:</b> <br> &nbsp&nbsp${{ artefact.insuranceFee }}</b-row>
                            <b-row align-v="end" v-if="(artefact.canLoan && !artefact.currentlyOnLoan)"> <b>Loan fee: </b>  &nbsp&nbsp${{ artefact.loanFee }}</b-row>
                    </b-col>
                    <div class="modal-header">
                        <!-- Buttons depending on the user -->
                        <div class="ml-auto">
                        <b-button variant ="success" v-if="(loggedInVisitor && artefact.canLoan && !artefact.currentlyOnLoan)" @click="requestLoan(JSON.parse(loggedInVisitor).username)">Loan</b-button>
                        <b-button v-if="(loggedInEmployee || loggedInManager)" @click="$bvModal.show('Edit' + String(artefact.artefactId))">Edit</b-button>
                        <b-button v-if="(loggedInEmployee || loggedInManager)" @click="$bvModal.show('Move' + String(artefact.artefactId))">Move</b-button>
                    </div>
                </div>
                </b-row>
            </b-container>
        </b-modal> 
       <!-- Form to edit an artefact, called by Edit button -->
        <b-modal 
            :id="'Edit' + String(artefact.artefactId)"
            title="Update an Artefact"
            centered 
            size="xl" 
            scrollable
            hide-footer>
            <update-form :artefactId="artefact.artefactId"/>
        </b-modal>  
        <!-- Form to move an artefact to another room, called by Move button -->
        <b-modal 
            :id="'Move' + String(artefact.artefactId)"
            title="Move an Artefact"
            centered 
            size="xl" 
            scrollable
            hide-footer>
            <move-form :artefactId="artefact.artefactId" :roomId="artefact.roomId"/>
        </b-modal>     
    </div>    
</template>


<script>
import axios from 'axios'
import UpdateArtefactForm from './UpdateArtefactForm.vue'
import MoveToRoomForm from './MoveToRoomForm.vue'
import ErrorHandler from './ErrorPopUp.vue'

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
            errorMessage: '',
            room: '',
            loggedInVisitor: '',
            loggedInEmployee: '',
            loggedInManager: '',
            username: ''
        }
    },
    components: {
        ErrorHandler,
        "update-form": UpdateArtefactForm,
        "move-form": MoveToRoomForm,
    },
    props: {
        artefact : Object
    },
    created: function() {
        // Get session info
        this.loggedInVisitor = sessionStorage.getItem('loggedInVisitor');
        this.loggedInEmployee = sessionStorage.getItem('loggedInEmployee');
        this.loggedInManager = sessionStorage.getItem('loggedInManager');

        if (this.loggedInVisitor) { 
            this.username = JSON.parse(this.loggedInVisitor).userName;

        }
        // Get info on the artefact's room
        const self = this
        AXIOS.get(`/room/${self.artefact.roomId}`, {}, {})
        .then(response => {
        const room = response.data
        // console.log(room)
        self.room = room
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
        // Once the Loan button is clicked
        requestLoan(username) {
            const self = this
            AXIOS.post('/loan', { visitorId: self.username, artefactId: self.artefact.artefactId }, {})
            .then(response => {
            })
            .catch(error => {
                if (error.response.status >= 450) {
                    self.errorMessage = "Oops! An error occured. Please contact the musuem directly.";
                } else {
                    self.errorMessage = error.response.data;
                }
                self.$bvModal.show('errorPopUp');
            })
           
        }
    }   
}
</script>