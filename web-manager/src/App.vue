<template>
  <el-container>
    <el-header>
        <div class="mobile-tab hidden-md-and-up">
             <i class="el-icon-menu" id="mobile-menu-icon" @click="clickMenuIcon"></i>
        </div>

        <transition name="el-fade-in">
            <div id="cover" v-show="showMoblieMenu" @click="clickMenuIcon"></div>
        </transition>

        <transition name="el-fade-in-linear">
        <div class="mobile-menu hidden-md-and-up" v-show="showMoblieMenu">

            <el-menu class="el-menu-vertical-demo" :router = "true" >
                <el-menu-item index="1"  route = "/">
                  <template slot="title">
                    <i class="el-icon-location"></i>
                    <span>首页</span>
                  </template>
                </el-menu-item>
                <el-menu-item index="2" route = "/timeline">
                  <i class="el-icon-star-on"></i>
                  <span slot="title">时间线</span>
                </el-menu-item>
                <el-menu-item index="3" route = "/demo">
                  <i class="el-icon-star-on"></i>
                  <span slot="title">demo</span>
                </el-menu-item>
                <el-menu-item index="4" route = "/node">
                  <i class="el-icon-menu"></i>
                  <span slot="title">节点</span>
                </el-menu-item>
                <el-menu-item index="5" route = "/civilization">
                  <i class="el-icon-menu"></i>
                  <span slot="title">文明</span>
                </el-menu-item>
                <el-menu-item index="6" route = "/nation">
                  <i class="el-icon-menu"></i>
                  <span slot="title">国家</span>
                </el-menu-item>
                <el-menu-item index="7" route = "/period">
                  <i class="el-icon-menu"></i>
                  <span slot="title">时代</span>
                </el-menu-item>
                <el-menu-item index="8" route = "/giant">
                  <i class="el-icon-menu"></i>
                  <span slot="title">人物</span>
                </el-menu-item>
                <!-- <el-menu-item index="5" route = "/dynasty">
                  <i class="el-icon-menu"></i>
                  <span slot="title">朝代</span>
                </el-menu-item> -->

                <el-submenu index="9">
                  <template slot="title">
                    <i class="el-icon-location"></i>
                    <span>设置</span>
                  </template>
                  <el-menu-item index="9-1" route = "/continent/add">大洲</el-menu-item>
                  <el-menu-item index="9-2" route = "/institution">制度</el-menu-item>
                  <el-menu-item index="9-3" route = "/reference">依据</el-menu-item>
                  <el-menu-item index="9-4" route = "/relation">关系</el-menu-item>
                </el-submenu>
              </el-menu>

        </div>
        </transition>


    </el-header>
    <el-container>
      <el-aside width="20%" class=" hidden-sm-and-down">
        <!-- 菜单 -->
        <el-row class="tac">
            <el-col :span="24">
              <el-menu class="el-menu-vertical-demo" :router = "true" >
                <el-menu-item index="1"  route = "/">
                  <template slot="title">
                    <i class="el-icon-location"></i>
                    <span>首页</span>
                  </template>
                </el-menu-item>
                <el-menu-item index="2" route = "/timeline">
                  <i class="el-icon-star-on"></i>
                  <span slot="title">时间线</span>
                </el-menu-item>
                <el-menu-item index="3" route = "/demo">
                  <i class="el-icon-star-on"></i>
                  <span slot="title">demo</span>
                </el-menu-item>
                <el-menu-item index="4" route = "/node">
                  <i class="el-icon-menu"></i>
                  <span slot="title">节点</span>
                </el-menu-item>
                <el-menu-item index="5" route = "/civilization">
                  <i class="el-icon-menu"></i>
                  <span slot="title">文明</span>
                </el-menu-item>
                <el-menu-item index="6" route = "/nation">
                  <i class="el-icon-menu"></i>
                  <span slot="title">国家</span>
                </el-menu-item>
                <el-menu-item index="7" route = "/period">
                  <i class="el-icon-menu"></i>
                  <span slot="title">时代</span>
                </el-menu-item>
                <el-menu-item index="8" route = "/giant">
                  <i class="el-icon-menu"></i>
                  <span slot="title">人物</span>
                </el-menu-item>
                <!-- <el-menu-item index="5" route = "/dynasty">
                  <i class="el-icon-menu"></i>
                  <span slot="title">朝代</span>
                </el-menu-item> -->

                <el-submenu index="9">
                  <template slot="title">
                    <i class="el-icon-location"></i>
                    <span>设置</span>
                  </template>
                  <el-menu-item index="9-1" route = "/continent/add">大洲</el-menu-item>
                  <el-menu-item index="9-2" route = "/institution">制度</el-menu-item>
                  <el-menu-item index="9-3" route = "/reference">依据</el-menu-item>
                  <el-menu-item index="9-4" route = "/relation">关系</el-menu-item>
                  <!-- <el-menu-item index="6-4" route = "/yearbook">yearbook</el-menu-item> -->
                </el-submenu>
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
  name: "App",
  data() {
    return {
      showMoblieMenu: false
    };
  },
  methods: {
    clickMenuIcon() {
      this.showMoblieMenu = !this.showMoblieMenu;
    }
  }
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

.common-title {
  text-align: right;
  font-size: 16px;
  font-family: "Hiragino Sans GB";
}

.content-warning {
  text-align: center;
  font-size: 14px;
  font-family: "Hiragino Sans GB";
  color: #e6a23c;
}

.mobile-tab {
  top: 0;
  left: 0;
  position: fixed;
  display: flex;
  height: 60px;
  align-items: center;
  width: 100%;
  text-align: left;
  border-bottom: solid 1px #e6e6e6;
  margin-bottom: 10px;
  box-shadow: 0 0 2px rgba(0, 0, 0, 0.25);
  background-color: #ffffff;
  z-index: 9;
}

#mobile-menu-icon {
  margin-left: 20px;
  font-size: 1.5em;
}

.mobile-menu {
  position: fixed;
  top: 0;
  left: 0;
  background-color: #ffffff;
  z-index: 11;
  height: 100%;
}

#cover {
  top: 0;
  left: 0;
  z-index: 10;
  position: fixed;
  width: 100%;
  height: 100%;
  background-color: #5c5c5c;
  opacity: 0.2;
}

.tag-margin {
  margin-left: 5px;
}

.paragraph-title-main {
  font-size: 20px;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
    "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}

.paragraph-title {
  font-size: 18px;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
    "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}

.paragraph-title-small {
  font-size: 16px;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
    "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}

.paragraph-content {
  font-size: 14px;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
    "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}

.paragraph-content-small {
  font-size: 13px;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
    "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}

.paragraph-assist {
  font-size: 12px;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
    "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}

.clear-line-height {
  line-height: 0;
}

.row-item-align-center {
  display: flex;
  align-items: center;
}
</style>
