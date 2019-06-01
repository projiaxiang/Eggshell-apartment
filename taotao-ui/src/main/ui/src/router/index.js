import Vue from 'vue'
import Router from 'vue-router'
import moren from '@/components/Default.vue'
import index from '@/components/index.vue'
import login from '@/components/login.vue'
import register from '@/components/register.vue'
import owner from '@/components/owner/Owner.vue'
import collection from '@/components/owner/Collection.vue'
import release from '@/components/owner/Release.vue'
import order from '@/components/owner/Order.vue'
import personal from '@/components/owner/Personal.vue'
import HouseManage from '@/components/owner/HouseManage.vue'
import UserManage from '@/components/owner/UserManage.vue'
import HouseBasic from '@/components/house/HouseBasic.vue'
import House from '@/components/house/House.vue'

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
      path: '/register',
      name: 'register',
      component: register
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
            },
            {
              path: '/order',
              name: 'order',
              component: order
            },
            {
              path: '/house/manage',
              name: 'HouseManage',
              component: HouseManage
            },
            {
              path: '/user/manage',
              name: 'UserManage',
              component: UserManage
            }
          ]
        },
        {
          path: '/house/basic/:id',
          name: 'HouseBasic',
          component: HouseBasic
        },
        {
          path: '/house/:address/:rent',
          name: 'House',
          component: House
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
