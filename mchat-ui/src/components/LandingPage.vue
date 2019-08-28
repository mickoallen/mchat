<template>
    <div class="landing">
        <v-content>
            <v-container fluid fill-height>
                <v-layout grid align-center justify-center>
                    <v-flex xs12 sm8 md4>
                        <v-card class="elevation-10">
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
                        </v-card>
                    </v-flex>
                </v-layout>
            </v-container>
        </v-content>
    </div>
</template>

<script>
import conversationsGet from "../messages/conversationsGet.json";
import store from "../store/store.js";
import loginRequest from "../messages/login.json";
import createAccount from "../messages/createAccount.json";

import {
    VContent,
    VContainer,
    VLayout,
    VFlex,
    VForm,
    VTextField,
    VBtn,
    VCard
} from "vuetify/lib";

export default {
    components: {
        VContent,
        VContainer,
        VLayout,
        VFlex,
        VForm,
        VTextField,
        VBtn,
        VCard
    },

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