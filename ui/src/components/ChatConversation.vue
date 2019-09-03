<template>
    <div>
        <v-timeline dense>
            <v-timeline-item v-for="message in orderedMessages" v-bind:key="message.uuid" small>
                <template v-slot:icon>
                    <v-avatar>
                        <img v-bind:src="getAvatarUrl(message.userUuid)"/>
                    </v-avatar>
                </template>
                <v-card class="elevation-5 float-left" max-width="90%">
                    <v-card-text>
                        <span class="body-1 font-weight-bold text--primary">
                            <b>{{ " " + getUsername(message.userUuid) }}</b>
                        </span>
                        <span class="body-2 text--primary">{{" " + message.message }}</span>

                        <!-- <span>{{ getDateString(message.dateCreated) }} - {{ getTimeString(message.dateCreated) }}</span> -->
                    </v-card-text>
                </v-card>
            </v-timeline-item>
            <div id="scrollToHere" />
        </v-timeline>

        <v-footer app inset>
            <v-container>
                <v-row dense>
                    <v-col cols="100%">
                        <v-form
                            @keyup.native="keyPressed"
                            @submit="sendMessage"
                            onSubmit="return false;"
                        >
                            <v-text-field hide-details hint outlined v-model="newMessage"></v-text-field>
                        </v-form>
                    </v-col>
                </v-row>
                <!-- <v-row>
                    <v-col cols="12">
                        Users typing:
                        <v-sheet
                            v-for="userTyping in filteredUsersTyping"
                            v-bind:key="userTyping.id"
                        >{{getUsername(userTyping.userUuid)}} is typing...</v-sheet>
                    </v-col>
                </v-row>-->
            </v-container>
        </v-footer>
    </div>
</template>

<script>
import store from "../store.js";
import sendMessage from "../messages/sendMessage.json";
// import userTypingMessage from "../messages/userTypingMessage.json";
import { mapState } from "vuex";
import goTo from "vuetify/es5/services/goto";

export default {
    components: {},

    data() {
        return {
            newMessage: ""
        };
    },

    computed: {
        ...mapState({
            selectedConversation: state => state.selectedConversation,
            currentUser: state => state.currentUser,
            users: state => state.users,
            usersTyping: state => state.usersTyping
        }),
        orderedMessages: function() {
            return this.lodash.orderBy(
                this.selectedConversation.messages,
                "dateCreated"
            );
        },
        filteredUsersTyping: function() {
            if (this.usersTyping[this.selectedConversation.uuid] == undefined) {
                return [];
            }
            return this.usersTyping[this.selectedConversation.uuid].usersTyping;
        }
    },

    updated() {
        this.scrollToLastMessage();
    },

    methods: {
        getAvatarUrl(userUuid){
            return "https://api.adorable.io/avatars/100/" + this.getUsername(userUuid);
        }, 
        keyPressed() {
            // var userTypingRequest = userTypingMessage;
            // userTypingMessage.conversationUuid = this.selectedConversation.uuid;
            // store.dispatch("sendMessage", userTypingRequest);
        },
        sendMessage() {
            var sendMessageRequest = sendMessage;
            sendMessageRequest.conversationUuid = this.selectedConversation.uuid;
            sendMessageRequest.message = this.newMessage;
            store.dispatch("sendMessage", sendMessageRequest);
            this.newMessage = "";
        },
        getUsername(uuid) {
            let user = this.users.filter(user => user.uuid == uuid)[0];
            if (user == undefined) {
                return "";
            }
            return user.username;
        },
        scrollToLastMessage() {
            goTo("#scrollToHere");
        },
        getTimeString(epoch) {
            return new Date(epoch).toLocaleTimeString("en-US");
        },
        getDateString(epoch) {
            return new Date(epoch).toLocaleDateString("en-US");
        }
    }
};
</script>

<style scoped>

.theme--light.v-footer {
    background-color: #fafafa;
}

.theme--dark.v-footer {
    background-color: #303030;
}

.theme--light.v-timeline::before {
    display: none;
}
.theme--dark.v-timeline::before {
    display: none;
}
</style>