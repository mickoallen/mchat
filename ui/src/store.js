import Vue from 'vue';
import Vuex from 'vuex';
import VueCookie from "vue-cookie";

import conversationsGet from "./messages/conversationsGet.json";
import usersGet from "./messages/usersGetAll.json";
import currentUserGet from "./messages/currentUserGet.json";
import * as MESSAGE_TYPES from './constants/inboundMessageTypes.js';

const AUTH_TOKEN_COOKIE_NAME = "mchat-authentication";
const SELECTED_CONVERSATION_COOKIE_NAME = "mchat-selected-conversation";

Vue.use(Vuex);
Vue.use(VueCookie);

export default new Vuex.Store({
  state: {
    socket: {
      isConnected: false,
      message: '',
      reconnectError: false,
    },
    currentUser: {
      loggedIn: false,
      username: "",
      authenticationToken: ""
    },
    modals: {
      showLogin: false,
      createAccount: false
    },
    usersTyping: {},
    users: [],
    conversations: [],
    showConversations: null,
    newConversation: false,
    selectedConversation: {

    }
  },
  mutations: {
    SOCKET_ONOPEN(state, event) {
      Vue.prototype.$socket = event.currentTarget;
      this.dispatch("initializeConnection");
    },
    SOCKET_ONCLOSE(state) {
      state.socket.isConnected = false;
    },
    SOCKET_ONERROR(state, event) {
      console.error(state, event);
    },
    SOCKET_ONMESSAGE(state, message) {
      console.log("Received message: " + JSON.stringify(message));

      switch (message.type) {
        case MESSAGE_TYPES.ERROR:
          console.error("Error from server: " + message.body.message);
          break;
        case MESSAGE_TYPES.CONVERSATIONS_GET_ALL_RESPONSE:
          state.conversations = message.body.conversations;

          //reload previously selected conversation
          if (state.selectedConversation.uuid != undefined) {
            var selectedConversationFromServer = state.conversations.filter(conversation => conversation.uuid == state.selectedConversation.uuid)[0];

            if (selectedConversationFromServer != undefined) {
              state.selectedConversation = selectedConversationFromServer;
            }
          }

          state.currentUser.loggedIn = true;
          break;
        case MESSAGE_TYPES.LOGIN_RESPONSE:
          console.log("Login success!");
          VueCookie.set(message.body.cookieName, message.body.authenticationToken, message.body.cookieExpiry);
          state.currentUser.authenticationToken = message.body.authenticationToken;
          state.currentUser.loggedIn = true;
          state.showConversations = true;
          this.dispatch('sendMessage', currentUserGet);
          this.dispatch('sendMessage', conversationsGet);
          this.dispatch("sendMessage", usersGet);
          break;
        case MESSAGE_TYPES.CHAT_MESSAGE:
          state.conversations.filter(conversation => conversation.uuid == message.body.conversationUuid)[0].messages.push(message.body);
          break;
        case MESSAGE_TYPES.CURRENT_USER:
          state.currentUser.loggedIn = true;
          state.currentUser.username = message.body.username;
          break;
        case MESSAGE_TYPES.USERS_ALL:
          state.users = message.body;
          break;
        case MESSAGE_TYPES.USER_TYPING:
          var id = Math.random().toString(36);

          if(state.usersTyping[message.body.conversationUuid] == undefined){
            state.usersTyping[message.body.conversationUuid] = {usersTyping:[]};
          }

          state.usersTyping[message.body.conversationUuid].usersTyping.push(
            {
              id: id,
              userUuid: message.body.userUuid
            }
          );

          setTimeout(function () {
            //remove that record after 1 second
            // state.usersTyping[message.body.conversationUuid].usersTyping =
              // state.usersTyping[message.body.conversationUuid].usersTyping.filter(userTyping => userTyping.id != id);
          }, 1000);
          break;

      }
    },
    // mutations for reconnect methods
    SOCKET_RECONNECT(state, count) {
      console.info("FUCKING WEBSOCKET RECONNECTED" + state, + count);
      this.dispatch("initializeConnection");
    },
    SOCKET_RECONNECT_ERROR(state) {
      state.socket.reconnectError = true;
    },
    logout(state) {
      state.showConversations = null;
      console.log("logging out");
      VueCookie.delete("mchat-authentication");
      state.currentUser.loggedIn = false;
      state.currentUser.authenticationToken = null;
    }
  },
  actions: {
    initializeConnection: (context) => {
      context.state.socket.isConnected = true;

      var selectedConversationUuid = VueCookie.get(SELECTED_CONVERSATION_COOKIE_NAME);
      if (selectedConversationUuid != undefined) {
        context.state.selectedConversation.uuid = selectedConversationUuid;
      }

      //check if there is an existing auth token we can save
      var authenticationToken = VueCookie.get(AUTH_TOKEN_COOKIE_NAME);
      if (authenticationToken != undefined) {
        context.state.currentUser.authenticationToken = authenticationToken;

        //lets just load everything here to start off with fuck it
        context.dispatch('sendMessage', conversationsGet);
        context.dispatch("sendMessage", usersGet);
      }
    },
    sendMessage: (context, message) => {
      //add auth token to message
      message.authenticationToken = context.state.currentUser.authenticationToken;
      console.log("Sending message: " + JSON.stringify(message));
      Vue.prototype.$socket.sendObj(message);
    },
    setSelectedConversation: (context, conversation) => {
      VueCookie.set(SELECTED_CONVERSATION_COOKIE_NAME, conversation.uuid, 1);
      context.state.selectedConversation = conversation;
    },
    setShowConversations: (context, showConversationsState) => {
      context.state.showConversations = showConversationsState;
    },
    setNewConversation: (context, newConversationState) => {
      context.state.newConversation = newConversationState;
    }
  },
  getters: {
    getConversations: (state) => {
      return state.conversations;
    },
    getCurrentUserInfo: (state) => {
      return {
        connected: state.socket.isConnected,
        loggedIn: state.currentUser.loggedIn,
        username: state.currentUser.username,
      };
    },
    getIsLoggedIn: (state) => {
      return state.currentUser.loggedIn;
    },
    getCreateAccountModalState: (state) => {
      return state.modals.createAccount;
    },
    getSelectedConversation: (state) => {
      return state.selectedConversation;
    }
  }
});