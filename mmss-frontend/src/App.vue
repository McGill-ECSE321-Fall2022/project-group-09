<template>
  <div id="app">
    <div id="globalNavBar">
      <NavBar></NavBar>
    </div>
    <div>
      <router-view>
      </router-view>
    </div>
  </div>
</template>

<script>
// loading the navigation bar on top of every page
import NavBar from './components/NavBar.vue';
export default {
  name: 'app',
  components: {
    NavBar
  }
}
</script>

<!-- Styling the global and secondary bars -->
<style>
@import './styles.css';
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
#globalNavBar .NavBar{
  height: 90px;
}
.secondaryBar{
  background-color: 	#787878!important;
}
.secondaryBar .btn{
  margin-right: 10px!important;
  margin-left: 10px!important;
}
.secondaryBar .custom-select{
  margin-right: 10px!important;
  margin-left: 10px!important;
}
</style>
