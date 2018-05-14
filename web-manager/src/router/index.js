import Vue from 'vue'
import Router from 'vue-router'
import index from '@/components/index'
import node from '@/components/node'
import nodeAdd from '@/components/nodeAdd'
import nodeEdit from '@/components/nodeEdit'
import civilization from '@/components/civilization'
import civilizationAdd from '@/components/civilizationAdd'
import dynasty from '@/components/dynasty'
import dynastyAdd from '@/components/dynastyAdd'
import continentAdd from '@/components/continent'
import nation from '@/components/nation'
import nationAdd from '@/components/nationAdd'

Vue.use(Router)

export default new Router({
  routes: [{
    path: '/',
    name: 'index',
    component: index
  }, {
    path: '/node',
    name: 'node',
    component: node
  }, {
    path: '/node/add',
    name: 'nodeAdd',
    component: nodeAdd
  }, {
    path: '/node/edit/:id',
    name: 'nodeEdit',
    component: nodeEdit
  }, {
    path: '/civilization',
    name: 'civilization',
    component: civilization
  }, {
    path: '/civilization/add',
    name: 'civilizationAdd',
    component: civilizationAdd
  }, {
    path: '/dynasty',
    name: 'dynasty',
    component: dynasty
  }, {
    path: '/dynasty/add',
    name: 'dynastyAdd',
    component: dynastyAdd
  }, {
    path: '/continent/add',
    name: 'continentAdd',
    component: continentAdd
  }, {
    path: '/nation',
    name: 'nation',
    component: nation
  }, {
    path: '/nation/add',
    name: 'nationAdd',
    component: nationAdd
  }]
})
