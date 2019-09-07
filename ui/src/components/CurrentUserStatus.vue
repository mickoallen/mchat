<template>
    <div>
        <v-tooltip bottom>
            <template v-slot:activator="{ on }">
                <v-icon v-on="on" v-if="socket.isConnected">mdi-access-point</v-icon>
            </template>
            <span>Connected to MChat servers</span>
        </v-tooltip>
        <v-tooltip bottom>
            <template v-slot:activator="{ on }">
                <v-icon v-if="!socket.isConnected">mdi-access-point-network-off</v-icon>
            </template>
            <span>Not connected to MChat servers</span>
        </v-tooltip>
        
        
    </div>
</template>

<script>
import store from "../store.js";
import currentUserGet from "../messages/currentUserGet.json";
import { setInterval } from "timers";
import { mapState } from "vuex";

export default {
    components: {},
    computed: {
        ...mapState({
            currentUser: state => state.currentUser,
            socket: state => state.socket
        })
    },
    mounted() {
        store.dispatch("sendMessage", currentUserGet);
        setInterval(function() {
            if (this.loggedIn) {
                store.dispatch("sendMessage", currentUserGet);
            }
        }, 4000);
    },
    methods: {
        logout() {
            store.commit("logout");
        }
    }
};
</script>