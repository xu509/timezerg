<template>
  <div v-loading = "loading">
    <el-row>
      <el-col :span="24">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>DEMO</el-breadcrumb-item>
          <el-breadcrumb-item :to = "{path : '/demo'}">文明列表</el-breadcrumb-item>
          <el-breadcrumb-item>{{civilization.title}}</el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <el-row>
        <el-col :md="6" :offset="2">
          <template v-for="item in periods">
            <el-row :key="item.id">
              <div class="civilizationbox">
                <p class="paragraph-content">{{item.title}}</p>
              </div>
            </el-row>
          </template>
        </el-col>
        <el-col :md="12">
          <ul>
            <li v-for="item in timelines" :key="item.id">
              {{item.ddate}} - {{item.title}}
            </li>
          </ul>
        </el-col>
    </el-row>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "demoperiods",
  data() {
    return {
      id: null,
      timelines: [],
      periods: [],
      civilization: {},
      loading: false,
      width: 0
    };
  },
  methods: {
    init() {
      this.initData();
    },
    initData() {
      var _this = this;
      // _this.loading = true;

      //获取显示的文明
      axios
        .post(_this.GLOBAL.url_api_period_list, {
          cid: _this.id
        })
        .then(function(response) {
          console.log(response);
          if (response.data.result == 0) {
            var data = response.data.data;
            _this.civilization = data.civilization;
            _this.periods = data.periods;
          }
        });

      _this.initTimeline();
    },
    initTimeline() {
      var _this = this;
      //获取该文明的世界线
      axios
        .post(_this.GLOBAL.url_api_civilization_timeline, {
          id: _this.id
        })
        .then(function(response) {
          console.log(response);
          if (response.data.result == 0) {
            var data = response.data.data;
            _this.timelines = data;
          }
        });
    }
  },
  watch: {},
  mounted: function() {
    this.id = this.$route.params.id;
    this.init();
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.civilizationbox {
  display: flex;
  align-items: center;
}
</style>
