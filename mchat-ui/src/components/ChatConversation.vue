<template>
  <div class="conversation">
      <p>{{ username }}</p>
      <div>
          <textarea></textarea>
      </div>
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
      groupMessages: [],
      loadingMessages: false
    };
  },
  mounted() {
  },
  created() {
    
  },
  methods: {
    
  }
};
</script>