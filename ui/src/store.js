import Vue from 'vue';
import Vuex from 'vuex';
import VueCookie from "vue-cookie";

import conversationsGet from "./messages/conversationsGet.json";
import usersGet from "./messages/usersGetAll.json";
import currentUserGet from "./messages/currentUserGet.json";
import * as MESSAGE_TYPES from './constants/inboundMessageTypes.js';

const AUTH_TOKEN_COOKIE_NAME = "mchat-authentication";
const SETTINGS_COOKIE_NAME = "mchat-settings";

const arrayToObject = (array, keyField) =>
  array.reduce((obj, item) => {
    obj[item[keyField]] = item
    return obj
  }, {});

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
    usersTyping: {},
    users: [],
    conversations: {},
    selectedConversationUuid: null,
    conversationsInView: {},
    showConversations: null,
    newConversation: false,
    darkTheme: false,
    serverUrl: null
  },
  mutations: {
    SOCKET_ONOPEN(state, event) {
      Vue.prototype.$socket = event.currentTarget;
      this.commit("initializeConnection");
    },
    SOCKET_ONCLOSE(state) {
      state.socket.isConnected = false;
    },
    SOCKET_ONERROR(state, event) {
      console.error(state, event);
    },
    SOCKET_ONMESSAGE(state, message) {
      switch (message.type) {
        case MESSAGE_TYPES.ERROR:
          console.error("Error from server: " + message.body.message);
          break;
        case MESSAGE_TYPES.CONVERSATIONS_GET_ALL_RESPONSE:
          state.conversations = arrayToObject(message.body.conversations, "uuid");
          state.currentUser.loggedIn = true;
          break;
        case MESSAGE_TYPES.LOGIN_RESPONSE:
          VueCookie.set(message.body.cookieName, message.body.authenticationToken, message.body.cookieExpiry);
          state.currentUser.authenticationToken = message.body.authenticationToken;
          state.currentUser.loggedIn = true;
          state.showConversations = true;
          this.dispatch('sendMessage', currentUserGet);
          this.dispatch('sendMessage', conversationsGet);
          this.dispatch("sendMessage", usersGet);
          break;
        case MESSAGE_TYPES.CHAT_MESSAGE:
          if (state.conversations[message.body.conversationUuid] != undefined) {
            if(state.conversationsInView[message.body.conversationUuid] == undefined){
              state.conversationsInView[message.body.conversationUuid] = {};
            }
            if(state.conversationsInView[message.body.conversationUuid].newMessages == undefined){
              state.conversationsInView[message.body.conversationUuid].newMessages = 0;
            }
            state.conversationsInView[message.body.conversationUuid].newMessages++;
            state.conversations[message.body.conversationUuid].messages.push(message.body);
          }
          break;
        case MESSAGE_TYPES.CURRENT_USER:
          state.currentUser.loggedIn = true;
          state.currentUser.username = message.body.username;
          break;
        case MESSAGE_TYPES.USERS_ALL:
          state.users = message.body;
          break;
        case MESSAGE_TYPES.USER_TYPING:
          // var id = Math.random().toString(36);

          // if (state.usersTyping[message.body.conversationUuid] == undefined) {
          //   state.usersTyping[message.body.conversationUuid] = { usersTyping: [] };
          // }

          // state.usersTyping[message.body.conversationUuid].usersTyping.push(
          //   {
          //     id: id,
          //     userUuid: message.body.userUuid
          //   }
          // );

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
      state;
      count;
      this.commit("initializeConnection");
    },
    SOCKET_RECONNECT_ERROR(state) {
      state.socket.reconnectError = true;
    },
    logout(state) {
      state.showConversations = null;
      VueCookie.delete("mchat-authentication");
      state.currentUser.loggedIn = false;
      state.currentUser.authenticationToken = null;
    },
    initializeConnection(state) {
      state.socket.isConnected = true;
      var settingsCookie = VueCookie.get(SETTINGS_COOKIE_NAME);

      if (settingsCookie != undefined) {
        settingsCookie = JSON.parse(settingsCookie);
        var selectedConversationUuidFromCookie = settingsCookie.selectedConversationUuid;
        if (selectedConversationUuidFromCookie != undefined) {
          state.selectedConversationUuid = selectedConversationUuidFromCookie;
        }

        this.dispatch('changeTheme', settingsCookie.darkTheme);
      }

      //check if there is an existing auth token we can save
      var authenticationToken = VueCookie.get(AUTH_TOKEN_COOKIE_NAME);
      if (authenticationToken != undefined) {
        state.currentUser.authenticationToken = authenticationToken;

        //lets just load everything here to start off with fuck it
        this.dispatch('sendMessage', conversationsGet);
        this.dispatch("sendMessage", usersGet);
      }
    },
    conversationInView(state, conversationUuid) {
      if(state.conversationsInView[conversationUuid] == undefined){
        state.conversationsInView[conversationUuid] = {};
      }
      state.conversationsInView[conversationUuid].isInView = true;
      state.conversationsInView[conversationUuid].newMessages = 0;
    }
  },
  actions: {
    sendMessage: (context, message) => {
      //add auth token to message
      message.authenticationToken = context.state.currentUser.authenticationToken;
      Vue.prototype.$socket.sendObj(message);
    },
    setSelectedConversation: (context, conversationUuid) => {
      context.state.selectedConversationUuid = conversationUuid;
      context.dispatch('saveSettingsToCookie');
    },
    setShowConversations: (context, showConversationsState) => {
      context.state.showConversations = showConversationsState;
    },
    setNewConversation: (context, newConversationState) => {
      context.state.newConversation = newConversationState;
    },
    changeTheme: (context, newDarkThemeSetting) => {
      context.state.darkTheme = newDarkThemeSetting;
      context.dispatch('saveSettingsToCookie');
    },
    saveSettingsToCookie: (context) => {
      var settings = {
        darkTheme: context.state.darkTheme
      };

      if (context.state.selectedConversationUuid != undefined) {
        settings.selectedConversationUuid = context.state.selectedConversationUuid;
      }

      VueCookie.set(SETTINGS_COOKIE_NAME, JSON.stringify(settings), 365);
    },
    serverUrl: (context, url) => {
      context.state.serverUrl = url;
    }
  },
  getters: {}
});