<template>
    <v-app>
        <v-app-bar app clipped-left v-if="isLoggedIn">
            <v-app-bar-nav-icon @click.stop="changeShowConversations"></v-app-bar-nav-icon>
            <v-toolbar-title>MChat</v-toolbar-title>
            <v-btn @click.stop="logout"> Logout</v-btn>
            <v-btn @click.stop="changeTheme"> Theme</v-btn>
            <current-user-status />
        </v-app-bar>
        
        <conversations v-if="isLoggedIn" />

        <landing-page v-if="!isLoggedIn"></landing-page>

        <v-footer app>
            <span class="white--text">- MChat 2019 -</span>
        </v-footer>
    </v-app>
</template>


<script>
import Vue from "vue";
import Vuex from "vuex";
import { mapState } from "vuex";
import "es6-promise/auto";
import VueNativeSock from "vue-native-websocket";

import {
    VApp,
    VAppBar,
    VAppBarNavIcon,
    VToolbarTitle,
    VBtn,
    VFooter
} from "vuetify/lib";

import store from "./store/store.js";
import LandingPage from "./components/LandingPage";
import Conversations from "./components/Conversations";
import CurrentUserStatus from "./components/CurrentUserStatus";

Vue.use(VueNativeSock, "ws://localhost:7070/ws", {
    format: "json",
    reconnection: true,
    store: store
});
Vue.use(Vuex);

export default {
    name: "MChatMain",
    components: {
        VApp,
        VAppBar,
        VAppBarNavIcon,
        VToolbarTitle,
        VBtn,
        VFooter,
        LandingPage,
        Conversations,
        CurrentUserStatus
    },

    computed: {
        ...mapState({
            isLoggedIn: state => state.currentUser.loggedIn,
            showConversations: state => state.showConversations
        })
    },

data(){
    return {
        darkMode: false
    }
},
    beforeMount() {},

    methods: {
        logout(){
            store.commit('logout');
        },
        changeShowConversations(){
            store.dispatch("setShowConversations", !this.showConversations);
        },
        changeTheme(){
            this.darkMode = !this.darkMode;
            this.$vuetify.theme.dark = this.darkMode;
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
