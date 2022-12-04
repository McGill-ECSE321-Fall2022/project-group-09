<template>
    <div id="NotificationPopUp">
        <b-modal size="lg" hide-footer id="NotificationPopUp" centered title="Send Notification">
            <b-form-textarea v-model="message" id="input-default" placeholder="Enter your message (Max 300 Characters)" :state="messageState" rows="3"></b-form-textarea>
            <br>

            <b-button block v-bind:disabled="((!message.trim()))" variant="success" @click="doSendNotification(message)">Send</b-button>

        </b-modal>
    </div>

</template>

<script>
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
        async doSendNotification() {
            const self = this
            for (var i = 0; i < self.selectedAccounts.length; i++) {

                self.request.username = self.selectedAccounts[i].userName
                self.request.message = self.message
                await AXIOS.post('/notification', self.request, {})
                .then((response => {
                    
                }))
                .catch((error) => {

                })
                self.message = ''; 
                this.$bvModal.hide('NotificationPopUp');

            }
        }
    }, 
    computed: { 
        messageState() {
            return this.message.length <301 && !(!this.message.trim())
        }
    }

}

</script>

<style>

</style>