<template>
  <div v-loading = "loading">
    <el-row>
      <el-col :span="24">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to = "{path : '/demo'}">DEMO</el-breadcrumb-item>
          <el-breadcrumb-item>文明列表</el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <el-row>
        <el-col :md="4">
          &nbsp;
        </el-col>
        <el-col :md="15">
          <template v-for="item in civilizations">
            <el-row :key="item.id">
              <div class="civilizationbox" @click = "clickCivilizationBox(item)">
                <p class="paragraph-content">{{item.title}}</p>
              </div>
            </el-row>
          </template>
        </el-col>
        <el-col :md="4">
          &nbsp;
        </el-col>
    </el-row>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "demo",
  data() {
    return {
      id: null,
      civilizations: [],
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
        .post(_this.GLOBAL.url_api_civilization_list, {
          p: 1
        })
        .then(function(response) {
          if (response.data.result == 0) {
            _this.civilizations = response.data.data;

            console.log(response.data.data);
          }
          // console.log(response.data.data[0].id);
        });
    },
    clickCivilizationBox(item) {
      var path = "/demo/civilization/";
      path += item.id;
      path += "/periods";

      this.$router.push({
        path: path
      });
    }
  },
  watch: {},
  mounted: function() {
    this.init();
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.civilizationbox {
  display: flex;
  align-items: center;
  cursor: pointer;
}
</style>
