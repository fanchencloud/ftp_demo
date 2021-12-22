import axios from 'axios'
// import {getData, getToken} from "/lib/auth"
import {getToken, setToken} from "./auth"
import {tansParams} from "../utils/common"
import router from "../router";

// 导入基础路径
import {BASE_URL} from "../config"

const {$message: Message, $dialog: Dialog} = window;

const _this = this

// 设置请求头
// axios.defaults.headers['Content-Type'] = 'multipart/form-data;charset=utf-8'
axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
// axios.defaults.headers['Content-Type'] = 'application/x-www-form-urlencoded'
// axios.defaults.withCredentials = true
// 创建axios实例
const service = axios.create({
    // axios中请求配置有baseURL选项，表示请求URL公共部分
    baseURL: BASE_URL, //设置axios为form-data
    // headers: {'Content-Type': 'application/x-www-form-urlencoded'}, // headers: {'Content-Type': 'multipart/form-data'},
    // 超时
    timeout: 10000
})

// request拦截器
service.interceptors.request.use((config) => {
    // 是否需要设置 token
    const isToken = config.headers.isToken || false
    if (isToken) {
        delete config.headers.isToken
        config.headers['taken'] = getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
    }
    // get请求映射params参数
    if (config.method.toLowerCase() === 'get' && config.params) {
        let url = config.url + '?' + tansParams(config.params);
        url = url.slice(0, -1);
        config.params = {};
        config.url = url;
    }
    return config
}, (error) => {
    Promise.reject(error).then(() => console.log(error))
})

// 响应拦截器
service.interceptors.response.use(res => {
    // 未设置状态码则默认成功状态
    const code = res.data.code || 200;
    // 获取错误信息
    const msg = res.data.message
    // 二进制数据则直接返回
    if (res.request.responseType === 'blob' || res.request.responseType === 'arraybuffer') {
        return res.data
    }
    if (code === 101) {
        const urlPath = router.currentRoute.value.path
        if (urlPath !== '/login') {
            Dialog.error({
                content: '未登录，即将跳转到登录页',
                negativeText: '前往登录',
                bordered: false,
                showIcon: false,
                closable: false,
                maskClosable: false,
                onNegativeClick: () => {
                    router.push('/login')
                }
            })
        }
    } else if (code === 401) {
        Message.error("无权访问")
    } else {
        return res.data
    }
    return res.data
}, error => {
    console.log('err' + error)
    let {msg} = error;
    if (msg === "Network Error") {
        msg = "后端接口连接异常";
    } else if (msg.includes("timeout")) {
        msg = "系统接口请求超时";
    } else if (msg.includes("Request failed with status code")) {
        msg = "系统接口" + msg.substr(msg.length - 3) + "异常";
    }
    Message.error(msg)
    return Promise.reject(error)
})

export default service
