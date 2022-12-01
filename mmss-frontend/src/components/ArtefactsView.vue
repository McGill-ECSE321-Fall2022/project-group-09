<template>
    <div id="ArtefactsView">
        <b-container>
            <b-row align-v="center">
                <custom-card v-for="artefact in artefacts" :key="artefact.artefactId" v-bind="artefact" />
            </b-row>
        </b-container>   
        <ErrorHandler :message="errorMessage" />     
    </div>
</template>

<script>
import Card from '@/components/ArtefactCard'
import ErrorHandler from './ErrorPopUp.vue';
import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})
export default {
    name: "ArtefactsView",
    components: {
        "custom-card": Card,
        ErrorHandler
    },
    data() {
    return {
        artefacts: [],
        errorMessage: ''
    }},
    created: function () {
        AXIOS.get('/artefact', {}, {})
        .then(response => {
        this.artefacts = response.data
        console.log(artefacts)
        })
        .catch(error => {
            console.log("Hello form errror")
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
}
</script>