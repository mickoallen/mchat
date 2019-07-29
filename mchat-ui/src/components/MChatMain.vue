<template>
  <div>
    <button id="show-modal" @click="showLogin = true">Login</button>
    <!-- use the modal component, pass in the prop -->
    <login-modal v-if="showLogin" @close="showLogin = false">
    </login-modal>
    <chat-conversation></chat-conversation>
  </div>
</template>

<script>
import Vue from "vue";
import VueNativeSock from "vue-native-websocket";
import ChatConversation from "./ChatConversation";
import conversationGet from "../messages/conversationsGet.json";
import LoginModal from "./LoginModal";
import VueCookie from "vue-cookie";

Vue.use(VueNativeSock, "ws://localhost:7070/ws", {
  format: "json",
  reconnection: true
});
Vue.use(VueCookie);

export default {
  name: "MChatMain",
  components: {
    "chat-conversation": ChatConversation,
    "login-modal": LoginModal
  },
  props: {
    msg: String
  },

  data() {
    return {
      conversations: [],
      friends: [],
      yourmom: true,
      showLogin: false
    };
  },

  beforeMount() {
    this.$socket.onopen = () => {
      console.log("WebSocket opened");

      // registering listeners
      this.$socket.onmessage = data => console.log("Received data:", data.data);

      // sending data
      console.log("Sending data");
      this.$socket.sendObj(conversationGet);
    };
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
