<template>
  <div>
    <div class="left-nav">
      <conversations :conversations="conversationList" />
    </div>
    <button id="show-modal" @click="showLoginModal">Login</button>
    <!-- use the modal component, pass in the prop -->
    <login-modal ref="loginModal" v-if="showLogin" ></login-modal>
    <chat-conversation></chat-conversation>
  </div>
</template>

<script>
import Vue from "vue";
import Vuex from "vuex";
import "es6-promise/auto";
import store from "./store/store.js";

import VueNativeSock from "vue-native-websocket";

import ChatConversation from "./components/ChatConversation";
import Conversations from "./components/Conversations";

import LoginModal from "./components/LoginModal";
import VueCookie from "vue-cookie";

Vue.use(VueNativeSock, "ws://localhost:7070/ws", {
  format: "json",
  reconnection: true,
  store: store
});
Vue.use(VueCookie);
Vue.use(Vuex);

export default {
  name: "MChatMain",
  components: {
    conversations: Conversations,
    "chat-conversation": ChatConversation,
    "login-modal": LoginModal
  },
  props: {
    msg: String
  },

  computed: {
    showLogin() {
      return store.state.modals.showLogin;
    }
  },

  data() {
    return {
      yourmom: true,
      conversationList: []
    };
  },

  created() {
    this.$on("hideLoginModal", this.hideLoginModal);
  },

  beforeMount() {},

  methods: {
    showLoginModal(){
      store.commit('updateModalState', {name: 'showLogin', newState:true });
    }
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
