<template>
    <v-app>
        <v-navigation-drawer v-if="showConversations" app clipped>
            <v-list dense two-line>
                <v-list-item
                    v-for="conversation in conversations"
                    v-bind:key="conversation.uuid"
                    @click.stop="selectConversation(conversation)"
                >
                    <v-list-item-action>
                        <v-icon>mdi-face</v-icon>
                    </v-list-item-action>
                    <v-list-item-content>
                        <v-list-item-title v-if="conversation.uuid == selectedConversation.uuid"><h3>{{ conversation.name }}</h3></v-list-item-title>
                        <v-list-item-title v-if="conversation.uuid != selectedConversation.uuid">{{ conversation.name }}</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
                <v-list-item @click.stop="clickNewConversation">
                    <v-list-item-action>
                        <v-icon>mdi-comment-plus</v-icon>
                    </v-list-item-action>
                    <v-list-item-content>
                        <v-list-item-title>New conversation</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
            </v-list>
        </v-navigation-drawer>

        <chat-conversation v-if="selectedConversation.uuid != null" />
        <new-conversation v-if="showNewConversation"/>
    </v-app>
</template>

<script>
import ChatConversation from "./ChatConversation";
import NewConversation from "./NewConversation";
import store from "../store/store.js";
import { mapState } from "vuex";

import {
    VNavigationDrawer,
    VList,
    VListItem,
    VListItemAction,
    VListItemContent,
    VListItemTitle,
    VIcon,
    VApp
} from "vuetify/lib";

export default {
    components: {
        ChatConversation,
        NewConversation,
        VApp,
        VNavigationDrawer,
        VList,
        VListItem,
        VListItemAction,
        VListItemContent,
        VListItemTitle,
        VIcon
    },

    data() {
        return {
            newConversations: false,
            selectedUsers: []
        };
    },

    computed: {
        ...mapState({
            conversations: state => state.conversations,
            isLoggedIn: state => state.currentUser.loggedIn,
            selectedConversation: state => state.selectedConversation,
            users: state => state.users,
            showConversations: state => state.showConversations,
            showNewConversation: state => state.newConversation
        })
    },

    methods: {
        selectConversation(conversation) {
            store.dispatch("setNewConversation", false);
            store.dispatch("setSelectedConversation", conversation);
        },
        clickNewConversation() {
            store.dispatch("setSelectedConversation", "");
            store.dispatch("setNewConversation", !this.showNewConversation);
        }
    }
};
</script>