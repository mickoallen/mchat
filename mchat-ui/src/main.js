import Vue from 'vue';
import App from './App.vue';
import vuetify from '@/plugins/vuetify';
import store from './store/store.js';
import VueLodash from 'vue-lodash'

const options = { name: 'lodash' } 
Vue.use(VueLodash, options)
Vue.config.productionTip = false

new Vue({
  render: h => h(App),
  vuetify,
  store
}).$mount('#app')
