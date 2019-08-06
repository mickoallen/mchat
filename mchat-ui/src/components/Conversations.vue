<template>
    <div>
        <v-navigation-drawer v-if="isLoggedIn" app clipped permanent>
            <v-list dense two-line>
                <v-list-item
                    v-for="conversation in getConversations"
                    v-bind:key="conversation.uuid"
                    @click.stop="selectConversation"
                >
                    <v-list-item-action>
                        <v-icon>mdi-face</v-icon>
                    </v-list-item-action>
                    <v-list-item-content>
                        <v-list-item-title>{{ conversation.name }}</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
                <v-list-item @click.stop="newConversations = !newConversations">
                    <v-list-item-action>
                        <v-icon>mdi-comment-plus</v-icon>
                    </v-list-item-action>
                    <v-list-item-content>
                        <v-list-item-title>New conversation</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
            </v-list>
        </v-navigation-drawer>
        <br />
        <v-navigation-drawer app fixed clipped left temporary v-model="newConversations">
            <v-btn @click.stop="createConversation">Create conversation</v-btn>
        </v-navigation-drawer>

        <v-content>
            {{ selectedConversation.name }}
        </v-content>
    </div>
</template>

<script>
import conversationsGet from "../messages/conversationsGet.json";
import createConversationRequest from "../messages/createConversation.json";
import store from "../store/store.js";

import {
    VNavigationDrawer,
    VList,
    VListItem,
    VListItemAction,
    VListItemContent,
    VListItemTitle,
    VIcon,
    VBtn,
    VContent
} from "vuetify/lib";

export default {
    components: {
        VNavigationDrawer,
        VList,
        VListItem,
        VListItemAction,
        VListItemContent,
        VListItemTitle,
        VIcon,
        VBtn,
        VContent
    },

    data() {
        return {
            newConversations: false,
            selectedConversation: null
        };
    },

    computed: {
        getConversations() {
            return store.getters.getConversations;
        },
        isLoggedIn() {
            return store.getters.getIsLoggedIn;
        }
    },

    methods: {
        loadConversations() {
            this.$socket.sendObj(conversationsGet);
        },
        createConversation() {
            //yourmom
            console.log("creating conversation");
            store.dispatch("sendMessage", createConversationRequest);
        },
        selectConversation(conversation) {
            this.selectedConversation = conversation;
        }
    }
};
</script>