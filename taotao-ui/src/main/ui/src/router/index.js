import Vue from 'vue'
import Router from 'vue-router'
import moren from '@/components/Default.vue'
import index from '@/components/index.vue'
import login from '@/components/login.vue'
import owner from '@/components/owner/Owner.vue'
import collection from '@/components/owner/Collection.vue'
import release from '@/components/owner/Release.vue'
import personal from '@/components/owner/Personal.vue'
import HouseBasic from '@/components/house/HouseBasic.vue'

Vue.use(Router)

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/login',
      name: 'login',
      component: login
    },
    {
      path: '/',
      name: 'moren',
      component: moren,
      redirect: '/index',
      children: [
        {
          path: '/index',
          name: 'index',
          component: index
        },
        {
          path: '/owner',
          name: 'owner',
          component: owner,
          meta: {
            requiresAuth: true
          },
          children: [
            {
              path: '/release',
              name: 'release',
              component: release
            },
            {
              path: '/collection',
              name: 'collection',
              component: collection
            },
            {
              path: '/personal',
              name: 'personal',
              component: personal
            }
          ]
        },
        {
          path: '/house/basic/:id',
          name: 'HouseBasic',
          component: HouseBasic
        }
      ]
    }
  ]
})

router.beforeEach((to, from, next) => {
  if (to.meta.requiresAuth) {
  if (localStorage.getItem('user_session')) {
    next()
  } else {
    next({
      path: '/login'
    })
  }
} else {
  next()
}
})

export default router
