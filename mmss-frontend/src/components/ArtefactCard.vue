<template>
    <div>
        <!-- img-src="link" img-alt="Image" img-top 
        Add eventually for pictures-->
        <b-col>
            <b-card
            @click="$bvModal.show(String(artefact.artefactId))"
            :title=artefact.artefactName
            img-src="https://picsum.photos/600/300/?image=25"
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
                        <b-button v-if="(artefact.canLoan && !artefact.currentlyOnLoan)">Loan</b-button>
                        <b-button @click="$bvModal.show('UpdateArtefactForm')">Edit</b-button>
                    </div>
                </div>
                </b-row>
            </b-container>
        </b-modal>  
        <b-modal 
            id='UpdateArtefactForm'
            title="Update an Artefact"
            centered 
            size="xl" 
            scrollable
            hide-footer>
            <update-form :artefactId="artefact.artefactId"/>
        </b-modal>  
        <ErrorHandler :message="errorMessage" />       
    </div>    
</template>


<script>
import axios from 'axios'
import UpdateArtefactForm from './UpdateArtefactForm.vue'
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
        }
    },
    components: {
        ErrorHandler,
        "update-form": UpdateArtefactForm,
    },
    props: {
        artefact : Object
    },
    created: function() {
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
    }
}
</script>