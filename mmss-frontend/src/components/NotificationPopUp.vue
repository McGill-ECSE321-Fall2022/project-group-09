<template>
    <!-- Popup to send notification to selected visitors-->
    <div id="NotificationPopUp">
        <!-- Popup with input for message-->
        <b-modal size="lg" hide-footer id="NotificationPopUp" centered title="Send Notification">
            <b-form-textarea v-model="message" id="input-default" placeholder="Enter your message (Max 300 Characters)" :state="messageState" rows="3"></b-form-textarea>
            <br>

            <b-button block v-bind:disabled="((!message.trim()))" variant="success" @click="doSendNotification(message)">Send</b-button>

        </b-modal>
    </div>

</template>

<script>
// setup axios
import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})


export default {
    name: 'NotificationPopUp',
    // the error message that the parent component will supply
    props: { 
        selectedAccounts: Array
    }
    ,
    data() {
        return {
            message: '',
            request: {
                username: '',
                message: ''
            }, 
            errorMessage: ''
        }
    },
    methods: {
        // send the notification
        async doSendNotification() {
            const self = this
            // to all accounts
            for (var i = 0; i < self.selectedAccounts.length; i++) {

                self.request.username = self.selectedAccounts[i].userName
                self.request.message = self.message
                console.log("Sending notification to " + self.request.username)
                console.log("sending notification with message " + self.request.message)
                // post request to backend
                await AXIOS.post('/notification', self.request, {})
                .then((response => {
                   console.log("response recieved") 
                }))
                .catch((error) => {
                    console.log("error")
                })
                // reset message at end, hide popup
                self.request.message = ''; 
                this.$bvModal.hide('NotificationPopUp');

            }
        self.message=''; 
        }
    }, 
    // make sure message is valid 
    computed: { 
        messageState() {
            return (this.message.length <301) && (!(this.message.length===0)) && !(!this.message.trim())
        }
    }

}

</script>

<style>

</style>