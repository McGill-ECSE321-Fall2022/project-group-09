<template>
    <div id="login"> 
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
                    <input type="text" v-model="username" @keydown.space.prevent placeholder="Username">
                </td>
            </tr>
            <tr>
                <td>
                    Password: 
                </td>
                <td>
                    <input type="password" v-model="password" @keydown.space.prevent placeholder="Password">
                </td>
            </tr>
            <tr>
                <td>
                    <button @click="login">Login</button>
                </td>
            </tr>
        </table>
    </div>
</template> 

<script>
</script> 

<style>
</style>


