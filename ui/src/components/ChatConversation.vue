<template>
    <div>
        <v-timeline dense>
            <v-timeline-item v-for="message in orderedMessages" v-bind:key="message.uuid" small>
                <template v-slot:icon>
                    <v-avatar tile>
                        <img v-bind:src="getAvatarUrl(message.userUuid)" />
                    </v-avatar>
                </template>
                <v-card class="elevation-3 float-left" max-width="90%">
                    <div class="ml-2 mr-2">
                        <span
                            class="font-weight-light mt-1 mr-2 text-left"
                        >{{getUsername(message.userUuid) }}</span>
                        <span class="text-right overline">{{ getTimeString(message.dateCreated) }}</span>
                        <div class="mb-1 ml-1 text-primary">{{message.message }}</div>
                    </div>
                    <!-- <span>{{ getDateString(message.dateCreated) }} - {{ getTimeString(message.dateCreated) }}</span> -->
                </v-card>
            </v-timeline-item>
            <div
                id="scrollToHere"
                v-observe-visibility="{callback: visibilityChanged, throttle: 300}"
            />
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
                            <v-text-field
                                aria-autocomplete="off"
                                autofocus
                                outlined
                                clearable
                                full-width
                                placeholder="What you wanna say?"
                                v-model="newMessage"
                            ></v-text-field>
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
import Vue from "vue";
import VueObserveVisibility from "vue-observe-visibility";
Vue.use(VueObserveVisibility);

export default {
    components: {},
    props: {
        conversationUuid: {
            type: String,
            required: true
        },
    },
    data() {
        return {
            newMessage: ""
        };
    },

    computed: {
        ...mapState({
            selectedConversationUuid: state => state.selectedConversationUuid,
            currentUser: state => state.currentUser,
            users: state => state.users,
            usersTyping: state => state.usersTyping,
            serverUrl: state => state.serverUrl,
            conversations: state => state.conversations,
            conversationInView: state => state.conversationInView
        }),
        orderedMessages: function() {
            if (
                this.conversations[this.selectedConversationUuid] == undefined
            ) {
                return [];
            }
            return this.lodash.orderBy(
                this.conversations[this.selectedConversationUuid].messages,
                "dateCreated"
            );
        },
        filteredUsersTyping: function() {
            return "";
        }
    },

    mounted(){
    },

    updated() {
        this.visibilityChanged(true);
        this.scrollToLastMessage();
    },

    methods: {
        visibilityChanged(isVisible) {
            if (isVisible) {
                store.commit("conversationInView", this.conversationUuid);
            }else {
                store.commit("conversationInView", undefined);
            }
        },
        getAvatarUrl(userUuid) {
            let user = this.users.filter(user => user.uuid == userUuid)[0];
            if (user == undefined) {
                return "";
            }
            return this.serverUrl + user.avatarUrl;
        },
        keyPressed() {
            // var userTypingRequest = userTypingMessage;
            // userTypingMessage.conversationUuid = this.selectedConversation.uuid;
            // store.dispatch("sendMessage", userTypingRequest);
        },
        sendMessage() {
            var sendMessageRequest = sendMessage;
            sendMessageRequest.conversationUuid = this.selectedConversationUuid;
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

.v-timeline-item {
    padding-bottom: 12px;
}
</style>