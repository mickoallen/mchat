<template>
    <div>
        <div v-if="socket.isConnected">
            <v-tooltip bottom>
                <template v-slot:activator="{ on }">
                    <div v-on="on">
                        <v-icon>mdi-access-point</v-icon>
                    </div>
                </template>
                <span>Connected to MChat servers</span>
            </v-tooltip>
        </div>
        <div v-if="!socket.isConnected">
            <v-tooltip bottom>
                <template v-slot:activator="{ on }">
                    <div v-on="on">
                        <v-icon>mdi-access-point-network-off</v-icon>
                    </div>
                </template>
                <span>Not connected to MChat servers</span>
            </v-tooltip>
        </div>
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