<template>
  <div class="hello">
    <textarea v-model="text" placeholder="chat messages go here"></textarea>
    <br />
    <span>Type message here:</span>
    <p style="white-space: pre-line;">{{ message }}</p>
    <br />
    <textarea v-model="textToSend" placeholder="add multiple lines"></textarea>
    <div id="example-1">
      <button v-on:click="counter += 1">Add 1</button>
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import VueNativeSock from "vue-native-websocket";

Vue.use(VueNativeSock, "ws://localhost:7070/ws");

export default {
  name: "HelloWorld",
  props: {
    msg: String,
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
