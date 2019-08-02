<template>
    <div class="conversation">
        <p>{{ allChat }}</p>
        <div>
            <textarea v-model="newMessage"></textarea>
        </div>
        <button class="modal-default-button" @click="sendMessage">Send message</button>
    </div>
</template>

<script>
import store from "../store/store.js";
import chatMessageRequest from "../messages/sendMessage.json";

export default {
    data() {
        return {
          newMessage: ""
        };
    },

    computed: {
        allChat() {
            return store.getters.getAllChat;
        }
    },
    methods: {
        sendMessage() {
            var request = chatMessageRequest;
            chatMessageRequest.message = this.newMessage;
            this.$socket.sendObj(request);
        }
    }
};
</script>