<template>
    <v-app id="inspire">
        <v-app-bar app clipped-left>
            <v-app-bar-nav-icon></v-app-bar-nav-icon>
            <v-toolbar-title>MChat</v-toolbar-title>
            <v-btn>Logout</v-btn>
        </v-app-bar>
        
        <div class="left-nav">
            <conversations />
        </div>

        <landing-page v-if="!isLoggedIn"></landing-page>

        <v-footer app>
            <span class="white--text">- MChat 2019 -</span>
        </v-footer>
    </v-app>
</template>


<script>
import Vue from "vue";
import Vuex from "vuex";
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
        conversations: Conversations,
    },

    computed: {
        isLoggedIn() {
            return store.getters.getIsLoggedIn;
        }
    },

    data() {
        return {
            drawer: true,
            source: "yourmom",
            right: true
        };
    },

    created() {
        this.$vuetify.theme.dark = true;
    },

    beforeMount() {},

    methods: {}
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
