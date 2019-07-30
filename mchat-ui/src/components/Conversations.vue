<template>
  <div class="left-nav">
    <h3>Conversations</h3>
    <button id="loadConversations" @click="loadConversations">Load conversations</button>
    <div v-for="conversation in conversations" :key="conversation.uuid">
      <p>A conversation {{conversation.uuid}}</p>
      <div v-for="participant in conversation.participants" :key="participant">
          <p> {{participant}} </p>
      </div>
    </div>
    <br />
  </div>
</template>

<script>
import conversationsGet from "../messages/conversationsGet.json";

export default {
  components: {},

  props: {
    conversations: Array
  },

  methods: {
    loadConversations() {
      this.$socket.sendObj(conversationsGet);
    }
  }
};
</script>