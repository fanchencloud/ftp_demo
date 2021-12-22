<template>
  <AppProvider>
    <RouterView/>
  </AppProvider>
</template>

<script>
import {AppProvider} from "./components/Application";
import {defineComponent} from 'vue';

export default defineComponent({
  name: 'App',
  components: {AppProvider},
  setup() {
  }
})
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
</style>
