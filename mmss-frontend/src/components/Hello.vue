<template>
  <div id="mainPage">
    <img id="mainLogo" src="../assets/logo.png">
  </div>
</template>

<script>
export default {
  name: 'hello',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
  #mainPage{
    display: flex;
    justify-content: center;
  }
  #mainLogo{
    margin: auto;
    padding: 130px;
  }
</style>
