import Vue from 'vue'
import App from './App'
import uView from "uview-ui";
import store from '@/store';
import webim from 'webim.js';
import * as filters from '@/filter/index.js'
const vuexStore = require("@/store/$u.mixin.js");

Vue.mixin(vuexStore);
Vue.use(uView);

Object.keys(filters).forEach(name=>{
	Vue.filter(name,filters[name])
})

Vue.config.productionTip = false
Vue.prototype.$socket = webim;
Vue.prototype.$url = 'http://120.27.95.106:8081/upload/'
Vue.prototype.$uploadUrl = 'http://120.27.95.106:9998/file/upload'
Vue.prototype.$ws = 'ws://120.27.95.106:9999/chat'
Vue.prototype.$api = 'http://120.27.95.106:9998'
App.mpType = 'app'

Promise.prototype.finally = function(callback) {
    let constructor = this.constructor;
    return this.then(function(value) {
        return constructor.resolve(callback()).then(function() {
            return value
        })
    }, function(issue) {
        return constructor.resolve(callback()).then(function() {
            throw issue
        })
    })
}

const app = new Vue({
	store,
    ...App
})
app.$mount()
