import {createApp} from 'vue'
import App from './App.vue'
// 引入路由组件
import router from "./router"
// 通用字体
import 'vfonts/Lato.css'
// 等宽字体
import 'vfonts/FiraCode.css'
import { AppProvider } from './components/Application'
// import {
//     // create naive ui
//     create,
//     // component
//     useDialog, useMessage,
// } from 'naive-ui'
const appProvider = createApp(AppProvider);
const app = createApp(App)
//优先挂载一下 Provider 解决路由守卫，Axios中可使用，Dialog，Message 等之类组件
appProvider.mount('#appProvider', true);
// const naive = create({
//     components: [useDialog, useMessage,]
// })
// app.use(naive)

app.use(router)

app.mount('#app')
