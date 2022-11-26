<template>
    <div id="LoginVisitor">
        <h1>
            Welcome to Marwan's Museum!
        </h1>
        <h2>
            Please login with your account details
        </h2>

        <table>
            <tr>
                <td>
                    Username:
                </td>
                <td>
                    <input type="text" v-model="visitorUsername" @keydown.space.prevent placeholder="Username">
                </td>
            </tr>
            <tr>
                <td>
                    Password:
                </td>
                <td>
                    <input type="password" v-model="visitorPassword" placeholder="Password">
                </td>
            
                <td>
                    <button 
                    v-bind:disabled="!visitorUsername.trim() || !visitorPassword.trim()" 
                    @click="doLoginVisitor(visitorUsername, visitorPassword)">Login</button>
                </td>
            </tr>
            
        </table>
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
    name: 'LoginVisitor',
    data() {
        return {
            visitorUsername: '',
            visitorPassword: '',
            visitorErrormssg: '',
            accountType: '',
        }
    },
    created: function () {
        // initialize necessary things for login
        const loggedInVisitor = sessionStorage.getItem('loggedInVisitor'); 
        if (loggedInVisitor) {
            // redirect to page for visitors
            // this.$router.push('/visitor');
        }
    },
    methods: {
       // the visitor login method
        doLoginVisitor (userName, passWord){
            const self = this; 
            self.visitorUsername = ''; 
            self.visitorPassword = '';
            AXIOS.post('/login', { username: userName, password: passWord})
            .then((response) => { 
                const visitor = response.data; 
                sessionStorage.setItem('loggedInVisitor', JSON.stringify(response.data));
                self.$router.push('/visitor');
            })
            .catch((error) => {
                self.visitorErrormssg = "Invalid username or password";
            });

        }
    }
}

</script> 

<style>

</style>


