// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'
import js_cookie from 'js-cookie'
let qs = require('qs');
let moment = require('moment')

axios.defaults.baseURL ='http://127.0.0.1:9091'

Vue.prototype.$axios = axios
Vue.prototype.$cookie = js_cookie
Vue.prototype.$qs = qs
Vue.prototype.$moment = moment

Vue.use(ElementUI)
Vue.use(ElementUI)
Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
