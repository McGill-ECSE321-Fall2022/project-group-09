<template>
    <div>
        <!-- img-src="link" img-alt="Image" img-top 
        Add eventually for pictures-->
        <b-col>
            <b-card
            @click="$bvModal.show(String(artefact.artefactId))"
            :title=artefact.artefactName
            img-src="https://www.shutterstock.com/image-photo/los-angeles-usa-sep-26-600w-336733364.jpg"
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
            <!-- Eventually add buttons depending on the user 
            <b-button href="#" variant="primary">Go somewhere</b-button> -->
            </b-card>  
        </b-col> 
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
                        <b-img fluid-grow src="https://picsum.photos/600/300/?image=25" alt="Image"></b-img>
                    </b-col>
                    <b-col>
                            <b-row align-self="stretch">Description: {{ artefact.description }}</b-row>
                            <b-row align-v="end">In {{ room.roomName }}</b-row>
                            <b-row align-v="end">{{ (artefact.canLoan && !artefact.currentlyOnLoan)? 'Available for loan' : 'Not available for loan' }}</b-row>
                            <b-row align-v="end" v-if="(artefact.canLoan && !artefact.currentlyOnLoan)">Insurance fee: {{ artefact.insuranceFee }}</b-row>
                            <b-row align-v="end" v-if="(artefact.canLoan && !artefact.currentlyOnLoan)">Loan fee: {{ artefact.loanFee }}</b-row>
                    </b-col>
                    <div class="modal-header">
                    <div class="ml-auto">
                        <b-button v-if="(loggedInVisitor != null && artefact.canLoan && !artefact.currentlyOnLoan)" @click="requestLoan(JSON.parse(loggedInVisitor).username)">Loan</b-button>
                        <b-button v-if="(loggedInEmployee != null || loggedInManager != null)" @click="$bvModal.show('Edit' + String(artefact.artefactId))">Edit</b-button>
                        <b-button v-if="(loggedInEmployee != null || loggedInManager != null)" @click="$bvModal.show('Move' + String(artefact.artefactId))">Move</b-button>
                    </div>
                </div>
                </b-row>
            </b-container>
        </b-modal>  
        <b-modal 
            :id="'Edit' + String(artefact.artefactId)"
            title="Update an Artefact"
            centered 
            size="xl" 
            scrollable
            hide-footer>
            <update-form :artefactId="artefact.artefactId"/>
        </b-modal>  
        <b-modal 
            :id="'Move' + String(artefact.artefactId)"
            title="Move an Artefact"
            centered 
            size="xl" 
            scrollable
            hide-footer>
            <move-form :artefactId="artefact.artefactId" :roomId="artefact.roomId"/>
        </b-modal>  
        <!-- <ErrorHandler :message="errorMessage" />        -->
    </div>    
</template>


<script>
import axios from 'axios'
import UpdateArtefactForm from './UpdateArtefactForm.vue'
import MoveToRoomForm from './MoveToRoomForm.vue'
import ErrorHandler from './ErrorPopUp.vue'; // This is the error component
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
        this.loggedInVisitor = sessionStorage.getItem('loggedInVisitor');
        this.loggedInEmployee = sessionStorage.getItem('loggedInEmployee');
        this.oggedInManager = sessionStorage.getItem('loggedInManager');

        const self = this
        AXIOS.get(`/room/${self.artefact.roomId}`, {}, {})
        .then(response => {
        const room = response.data
        console.log(room)
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
        requestLoan(username) {
            const self = this
            AXIOS.post('/loan', { visitorId: username, artefactId: self.artefact.artefactId }, {})
            .then(response => {
                alert(JSON.stringify(response))
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