import {createRouter, createWebHistory,} from 'vue-router'
import {getToken} from "../lib/auth";

// 开启历史模式
// vue2中使用 mode: history 实现
const routerHistory = createWebHistory();
// const routerHistory = createWebHashHistory()
const routes = [
    {
        path: '/',
        component: () => import('../views/index.vue'),
    },
    {
        path: '/login',
        name: "Login",
        component: () => import('../views/Login.vue'),
    },
    {
        path: '/404',
        name: '404',
        component: () => import('/src/views/error/404.vue')
    }
]

const router = createRouter({
    history: routerHistory,
    routes,
})

router.beforeEach((to, from, next) => {
    const token = getToken();
    if (token == null && to.path !== '/login') {
        next({name: 'Login'})
    } else {
        next()
    }
})

export default router
