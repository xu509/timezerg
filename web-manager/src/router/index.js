import Vue from 'vue'
import Router from 'vue-router'
import index from '@/components/index'
import node from '@/components/node'
import nodeAdd from '@/components/nodeAdd'
import nodeEdit from '@/components/nodeEdit'
import civilization from '@/components/civilization'
import civilizationAdd from '@/components/civilizationAdd'
import civilizationEdit from '@/components/civilizationedit'
import dynasty from '@/components/dynasty'
import dynastyAdd from '@/components/dynastyAdd'
import continentAdd from '@/components/continent'
import nation from '@/components/nation'
import nationAdd from '@/components/nationadd'
import nationEdit from '@/components/nationedit'
import period from '@/components/period'
import periodAdd from '@/components/periodadd'
import periodEdit from '@/components/periodedit'
import timeline from '@/components/timeline'
import timelineAdd from '@/components/timelineAdd'
import timelineEdit from '@/components/timelineEdit'
import giant from '@/components/giant'
import giantAdd from '@/components/giantadd'
import giantEdit from '@/components/giantEdit'
import institution from '@/components/institution'
import institutionAdd from '@/components/institutionAdd'

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
    path: '/civilization/edit/:id',
    name: 'civilizationEdit',
    component: civilizationEdit
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
  }, {
    path: '/nation/edit/:id',
    name: 'nationEdit',
    component: nationEdit
  }, {
    path: '/period',
    name: 'period',
    component: period
  }, {
    path: '/period/add',
    name: 'periodAdd',
    component: periodAdd
  }, {
    path: '/period/edit/:id',
    name: 'periodEdit',
    component: periodEdit
  }, {
    path: '/timeline',
    name: 'timeline',
    component: timeline
  }, {
    path: '/timeline/add',
    name: 'timelineAdd',
    component: timelineAdd
  }, {
    path: '/timeline/edit/:id',
    name: 'timelineEdit',
    component: timelineEdit
  }, {
    path: '/giant',
    name: 'giant',
    component: giant
  }, {
    path: '/giant/add',
    name: 'giantAdd',
    component: giantAdd
  }, {
    path: '/giant/edit/:id',
    name: 'giantEdit',
    component: giantEdit
  }, {
    path: '/institution',
    name: 'institution',
    component: institution
  }, {
    path: '/institution/add',
    name: 'institutionAdd',
    component: institutionAdd
  }]
})
