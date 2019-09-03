<template>
    <v-container>
        <v-row>
            <v-col>
                <v-card class="elevation-10" max-width="400">
                    <v-card-text>
                            <v-img
                                :src="require('../assets/logo.png')"
                                contain
                                height="200"
                            ></v-img>
                        <v-form
                            v-if="!createAccountOptions"
                            ref="form"
                            v-model="valid"
                            :lazy-validation="false"
                        >
                            <v-text-field v-model="username" label="Username" required></v-text-field>
                            <v-text-field
                                v-model="passwordOne"
                                :type="'password'"
                                label="Password"
                                required
                            ></v-text-field>
                            <v-btn @click.stop="login">Login</v-btn>
                            <v-btn
                                @click.stop="createAccountOptions = !createAccountOptions"
                            >Need to create an account?</v-btn>
                        </v-form>
                        <v-form
                            v-if="createAccountOptions"
                            ref="form"
                            v-model="valid"
                            :lazy-validation="false"
                        >
                            <v-text-field v-model="username" label="Username" required></v-text-field>
                            <v-text-field
                                v-model="passwordOne"
                                :type="'password'"
                                label="Password"
                                required
                            ></v-text-field>
                            <v-text-field
                                v-model="passwordTwo"
                                label="Re-enter Password"
                                :type="'password'"
                                required
                            ></v-text-field>
                            <v-btn
                                @click.stop="createAccountOptions = !createAccountOptions"
                            >Back to login</v-btn>
                            <v-btn right @click.stop="createAccount">Create account</v-btn>
                        </v-form>
                    </v-card-text>
                </v-card>
                <div class="flex-grow-1"></div>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import conversationsGet from "../messages/conversationsGet.json";
import store from "../store.js";
import loginRequest from "../messages/login.json";
import createAccount from "../messages/createAccount.json";

export default {
    components: {},

    data() {
        return {
            createAccountOptions: false,
            username: "",
            passwordOne: "",
            passwordTwo: "",
            valid: false,
            newConversations: false
        };
    },

    computed: {
        isLoggedIn() {
            return store.getters.getCurrentUserInfo.isLoggedIn;
        },
        getConversations() {
            return store.getters.getConversations;
        }
    },

    methods: {
        login() {
            console.log("attempting login");
            loginRequest.username = this.username;
            loginRequest.password = this.passwordOne;
            store.dispatch("sendMessage", loginRequest);
        },
        loadConversations() {
            this.$socket.sendObj(conversationsGet);
        },
        createAccount() {
            console.log("creating account");
            createAccount.username = this.username;
            createAccount.password = this.passwordOne;
            store.dispatch("sendMessage", createAccount);
        }
    }
};
</script>