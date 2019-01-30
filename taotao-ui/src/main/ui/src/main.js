// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'
import { Loading,Message } from 'element-ui'
import js_cookie from 'js-cookie'
let qs = require('qs');
let moment = require('moment')

Vue.prototype.$cookie = js_cookie

axios.defaults.baseURL ='http://127.0.0.1:9091'
axios.defaults.timeout = 5000

let loadinginstace
axios.interceptors.request.use(
  config => {
    loadinginstace = Loading.service(
      {
        fullscreen: true,
        text: '正在拼命加载...'
      }
    )
    if (config.url.includes('api')) {
      let token = sessionStorage.getItem('user_session')
      if (!token) {
        Message.error({message: '请先登录系统'})
        router.replace({path: '/login'})
        return 403
      }
    }
    return config
  },
  error => {
    loadinginstace.close()
    Message.error({message: '请求超时'})
    return Promise.reject(error)
  })

axios.interceptors.response.use(
  data => {
    loadinginstace.close()
    return data
  },
  error => {
    loadinginstace.close()
    Message.error({message: '请求失败'})
    return Promise.reject(error)
})

Vue.prototype.$axios = axios
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
