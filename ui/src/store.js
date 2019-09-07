import Vue from 'vue';
import Vuex from 'vuex';
import VueCookie from "vue-cookie";

import conversationsGet from "./messages/conversationsGet.json";
import usersGet from "./messages/usersGetAll.json";
import currentUserGet from "./messages/currentUserGet.json";
import * as MESSAGE_TYPES from './constants/inboundMessageTypes.js';
import * as ACTIVE_SCREENS from './constants/screens.js';

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
      authenticationToken: "",
      uuid: undefined
    },
    darkTheme: false,
    serverUrl: null,

    activeScreen: ACTIVE_SCREENS.NOTHING,
    usersTyping: {},
    users: [],

    conversations: {},
    selectedConversationUuid: null,
    conversationsUnreadMessage: {},
    conversationInView: '',
    showConversations: null,
    newConversation: false,

    error: {},
    success: {}
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
      state.socket.isConnected = false;
    },
    SOCKET_ONMESSAGE(state, message) {
      switch (message.type) {
        case MESSAGE_TYPES.ERROR:
          console.error("Error from server: " + message.body.message);
          state.error = message.body;
          break;
        case MESSAGE_TYPES.CONVERSATIONS_GET_ALL_RESPONSE:
          Vue.set(state, 'conversations', arrayToObject(message.body.conversations, "uuid"));
          state.currentUser.loggedIn = true;
          break;
        case MESSAGE_TYPES.LOGIN_RESPONSE:
          VueCookie.set(message.body.cookieName, message.body.authenticationToken, message.body.cookieExpiry);
          state.currentUser.authenticationToken = message.body.authenticationToken;
          state.currentUser.loggedIn = true;
          this.dispatch('sendMessage', currentUserGet);
          this.dispatch('sendMessage', conversationsGet);
          this.dispatch("sendMessage", usersGet);
          break;
        case MESSAGE_TYPES.CHAT_MESSAGE:
          var conversationUuid = message.body.conversationUuid;
          if (state.conversations[conversationUuid] != undefined) {
            if (state.conversationInView != conversationUuid) {
              if (state.conversationsUnreadMessage[conversationUuid] == undefined) {
                Vue.set(state.conversationsUnreadMessage, conversationUuid, 1);
              } else {
                Vue.set(state.conversationsUnreadMessage, conversationUuid, state.conversationsUnreadMessage[conversationUuid] + 1);
              }
            }

            state.conversations[conversationUuid].messages.push(message.body);
            
            for(var conversation in state.conversations){
              if(state.conversationsUnreadMessage[conversation] > 0){
                document.title = "MChat *";
                break;
              }
            }
          }else{
            console.error("undefined conversation wtf");
          }
          
          break;
        case MESSAGE_TYPES.CURRENT_USER:
          state.currentUser.loggedIn = true;
          state.currentUser.username = message.body.username;
          state.currentUser.uuid = message.body.uuid;
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
      VueCookie.delete("mchat-authentication");
      state.currentUser.loggedIn = false;
      state.currentUser.authenticationToken = null;
      state.activeScreen = ACTIVE_SCREENS.NOTHING;
    },
    initializeConnection(state) {
      state.socket.isConnected = true;
      var settingsCookie = VueCookie.get(SETTINGS_COOKIE_NAME);

      if (settingsCookie != undefined) {
        settingsCookie = JSON.parse(settingsCookie);
        var selectedConversationUuidFromCookie = settingsCookie.selectedConversationUuid;
        if (selectedConversationUuidFromCookie != undefined) {
          state.selectedConversationUuid = selectedConversationUuidFromCookie;
          state.activeScreen = ACTIVE_SCREENS.CHAT_CONVERSATIONS;
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
      if(conversationUuid == undefined){
        return;
      }
      state.conversationInView = conversationUuid;
      var unreadMessages = state.conversationsUnreadMessage;
      unreadMessages[conversationUuid] = 0;

      state.conversationsUnreadMessage = unreadMessages;
      var cleanTitleState = true;
      //check if we need to clear title state
      for(var conversation in state.conversations){
        if(state.conversationsUnreadMessage[conversation] > 0){
          cleanTitleState = false;
        }
      }

      if(cleanTitleState){
        document.title = "MChat";
      }
    },
    changeActiveScreen(state, newActiveScreen) {
      state.activeScreen = newActiveScreen;
    },
    showSuccess(state, message) {
      state.success = {
        message: message,
        time: new Date().getTime()
      };
    },
    setSelectedConversation(state, conversationUuid){
      state.selectedConversationUuid = conversationUuid;
      state.activeScreen = ACTIVE_SCREENS.CHAT_CONVERSATIONS;
      this.dispatch('saveSettingsToCookie');
    },
    setNewConversation(state) {
      state.activeScreen = ACTIVE_SCREENS.NEW_CONVERSATION;
    }
  },
  actions: {
    sendMessage: (context, message) => {
      //add auth token to message
      message.authenticationToken = context.state.currentUser.authenticationToken;
      Vue.prototype.$socket.sendObj(message);
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