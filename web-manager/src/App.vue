<template>
  <el-container>
    <el-header></el-header>
    <el-container>
      <el-aside width="20%">
        <!-- 菜单 -->
        <el-row class="tac">
            <el-col :span="24">
              <el-menu default-active="1" class="el-menu-vertical-demo" v-bind:router = "true">
                <el-menu-item index="1"  route = "/">
                  <template slot="title">
                    <i class="el-icon-location"></i>
                    <span>首页</span>
                  </template>
                </el-menu-item>
                <el-menu-item index="2" route = "/node">
                  <i class="el-icon-menu"></i>
                  <span slot="title">节点</span>
                </el-menu-item>
              </el-menu>
            </el-col>
        </el-row>

      </el-aside>
      <el-container>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: "App"
};
</script>

<style>
.el-header {
  background-color: #ffffff;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #ffffff;
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-main {
  background-color: #ffffff;
  color: #333;
  line-height: 60px;
}

body > .el-container {
  margin-bottom: 40px;
}

.tac {
  width: 100%;
}

.el-row {
  margin: 20px 0 20px 0;
}
</style>
