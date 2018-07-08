// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import 'element-ui/lib/theme-chalk/display.css';
// element 加载
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import global_ from './components/common/common';

Vue.config.productionTip = false;
Vue.prototype.GLOBAL = global_;

Vue.use(ElementUI);
// Vue.use(InputboxNation);
Vue.use(global_);

if (module.hot) {
  module.hot.accept();
}

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: {
    App
  },
  template: '<App/>'
})
