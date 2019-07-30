<template>
  <div class="conversation">
      <p>{{ username }}</p>
      <div>
          <textarea v-model="message"></textarea>
      </div>
      <button class="modal-default-button" @click="sendMessage">Send message</button>
  </div>
</template>

<script>
// @ is an alias to /src
// import { CometChat } from "@cometchat-pro/chat";
// import NavBar from "../components/NavBar.vue";
// import Spinner from "../components/Spinner.vue";


export default {
  name: "chat-conversation",
  components: {
  },
  data() {
    return {
      username: "your fat mom",
      avatar: "",
      uid: "",
      sendingMessage: false,
      chatMessage: "",
      loggingOut: false,
      message: "",
      groupMessages: [],
      loadingMessages: false
    };
  },
  mounted() {
  },
  created() {
    
  },
  methods: {
    sendMessage(){
      this.$socket.sendObject()
    }
  }
};
</script>