<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to = "{path : '/node'}">节点</el-breadcrumb-item>
          <el-breadcrumb-item>修改</el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <el-row>
        <el-tabs tab-position="top" style="height: 100%;" v-model="activeTab">
          <el-tab-pane label="基础" :name="tabs.basic" v-loading = "form.loading">
                <el-col :sm="24" :xs="24" :md="14" :xl="14" >
                  <el-form ref="form" :model="form" label-width="120px">
                        <el-form-item label="标题">
                          <el-input v-model="form.title"></el-input>
                        </el-form-item>
                        <el-form-item label="时间">
                          <el-row :gutter="20">
                            <el-col :md = "4" :xl="4" :sm="12" :xs="12">
                              <el-input v-model="form.year" placeholder="年"/>
                            </el-col>
                            <el-col :md = "3" :xl="3" :sm="12" :xs="12">
                              <el-input v-model="form.month" placeholder="月"/>
                            </el-col>
                            <el-col :md = "3" :xl="3" :sm="12" :xs="12">
                              <el-input v-model="form.day" placeholder="日"/>
                            </el-col>
                            <el-col :md = "4" :xl="4" :sm="12" :xs="12">
                              <el-input v-model="form.hour" placeholder="时"/>
                            </el-col>
                            <el-col :md = "3" :xl="3" :sm="12" :xs="12">
                              <el-input v-model="form.minute" placeholder="分"/>
                            </el-col>
                            <el-col :md = "3" :xl="3" :sm="12" :xs="12">
                              <el-input v-model="form.second" placeholder="秒"/>
                            </el-col>
                          </el-row>
                        </el-form-item>
                        <el-form-item label="显示的时间">
                          <el-input v-model="form.ddate"></el-input>
                        </el-form-item>
                        <el-form-item label="是否是公元后">
                          <el-radio-group v-model="form.AD">
                            <el-radio :label="adoption.bc">BC</el-radio>
                            <el-radio :label="adoption.ad">AD</el-radio>
                          </el-radio-group>
                        </el-form-item>
                        <el-form-item label="详细">
                          <el-input
                            type="textarea"
                            :rows="2"
                            placeholder="请输入内容"
                            v-model="form.content">
                          </el-input>
                        </el-form-item>
                        <el-form-item label="重要度">
                            <el-select v-model="form.level" placeholder="重要度">
                                <el-option v-for="item in options" :key = "item.value" :label = "item.label" :value = "item.value"></el-option>
                            </el-select>
                        </el-form-item>
                    
                        <el-form-item>
                          <el-button type="primary" @click="save">更新</el-button>
                        </el-form-item>
                  </el-form>
              </el-col>
          </el-tab-pane>
          <el-tab-pane label="相关" :name="tabs.relate" v-loading = "relate.loading">
              <el-card class="box-card" v-loading = "relate.loading">
                <el-row>
                    <el-button type="primary" v-loading = "relate.loading" @click = "clickSyncBtn">同步</el-button>
                </el-row>
                <el-row>
                    <inputboxcivilization @selectCivilization = "selectCivilization"></inputboxcivilization>
                </el-row>
                <el-row>
                  <template v-if="relate.civilizations == null || relate.civilizations.length == 0">
                      <p class="paragraph-content">无数据</p>
                  </template>
                  <template v-if="relate.civilizations != null && relate.civilizations.length > 0">
                      <el-tag :type="item.level == 1 ? 'danger':'info'" v-for="item in relate.civilizations" class="tag-margin"
                        :key="item.id"
                        closable
                        :disable-transitions="false"
                        @close="closeCivilizationTag(item)"><i :class="(item.level == 1 ? 'el-icon-star-on':'el-icon-star-off') + ' i-star'" @click="clickStarTag(item,'civilization')"></i>
                        {{item.title}}
                      </el-tag>
                  </template>
                </el-row>
                <el-row></el-row>

                <el-row>
                    <inputboxperiod @selectPeriod = "selectPeriod"></inputboxperiod>
                </el-row>
                <el-row>
                  <template v-if="relate.periods == null || relate.periods.length == 0">
                      <p class="paragraph-content">无数据</p>
                  </template>
                  <template v-if="relate.periods != null && relate.periods.length > 0">
                      <el-tag :type="item.level == 1 ? 'danger':'info'" v-for="item in relate.periods" class="tag-margin"
                        :key="item.id"
                        closable
                        :disable-transitions="false"
                        @close="closePeriodTag(item)"><i :class="(item.level == 1 ? 'el-icon-star-on':'el-icon-star-off') + ' i-star'" @click="clickStarTag(item,'period')"></i>
                        {{item.title}}
                      </el-tag>
                  </template>
                </el-row>
                <el-row></el-row>

                <el-row>
                    <inputboxnation @selectNation = "selectNation"></inputboxnation>
                </el-row>
                <el-row>
                  <template v-if="relate.nations == null || relate.nations.length == 0">
                      <p class="paragraph-content">无数据</p>
                  </template>
                  <template v-if="relate.nations != null && relate.nations.length > 0">
                      <el-tag :type="item.level == 1 ? 'danger':'info'" v-for="item in relate.nations" class="tag-margin"
                        :key="item.id"
                        closable
                        :disable-transitions="false"
                        @close="closeNationTag(item)"><i :class="(item.level == 1 ? 'el-icon-star-on':'el-icon-star-off') + ' i-star'" @click="clickStarTag(item,'nation')"></i>
                        {{item.title}}
                      </el-tag>
                  </template>
                </el-row>
                <el-row></el-row>
                <el-row>
                    <inputboxgiant @selectGiant = "selectGiant"></inputboxgiant>
                </el-row>
                <el-row>
                  <template v-if="relate.giants == null || relate.giants.length == 0">
                      <p class="paragraph-content">无数据</p>
                  </template>
                  <template v-if="relate.giants != null && relate.giants.length > 0">
                      <el-tag type="warning" v-for="item in relate.giants" class="tag-margin"
                        :key="item.id"
                        closable
                        :disable-transitions="false"
                        @close="closeGiantTag(item)">
                        {{item.name}}
                      </el-tag>
                  </template>
                </el-row>
                <el-row></el-row>

                <el-row>
                    <inputboxreference @selectReference = "selectReference"></inputboxreference>
                </el-row>
                <el-row>
                  <template v-if="relate.references == null || relate.references.length == 0">
                      <p class="paragraph-content">无数据</p>
                  </template>
                  <template v-if="relate.references != null && relate.references.length > 0">
                      <el-tag type="warning" v-for="item in relate.references" class="tag-margin"
                        :key="item.id"
                        closable
                        :disable-transitions="false"
                        @close="closeReferenceTag(item)">
                        {{item.title}}
                      </el-tag>
                  </template>
                </el-row>
                <el-row></el-row>
              </el-card>
          </el-tab-pane>
          <el-tab-pane label="贴条" :name="tabs.tag" v-loading = "tag.loading">
              <el-card class="box-card" v-loading = "tag.loading">
                  <el-row>
                    <inputboxtag @selectTag = "selectTag"></inputboxtag>
                </el-row>
                <el-row>
                  <template v-if="tag.tags == null || tag.tags.length == 0">
                      <p class="paragraph-content">无数据</p>
                  </template>
                  <template v-if="tag.tags != null && tag.tags.length > 0">
                      <el-tag type="warning" v-for="item in tag.tags" class="tag-margin"
                        :key="item.id"
                        closable
                        :disable-transitions="false"
                        @close="closeTagTag(item)">
                        {{item.title}}
                      </el-tag>
                  </template>
                </el-row>
              </el-card>
          </el-tab-pane>
        </el-tabs>
    </el-row>
  </div>
</template>

<script>
import axios from "axios";
import inputboxnation from "./plugin/inputboxnation.vue";
import inputboxcivilization from "./plugin/inputboxcivilization.vue";
import inputboxperiod from "./plugin/inputboxperiod.vue";
import inputboxgiant from "./plugin/inputboxgiant.vue";
import inputboxreference from "./plugin/inputboxreference.vue";
import inputboxtag from "./plugin/inputboxtag.vue";

export default {
  name: "nodeedit",
  data() {
    return {
      id: "",
      activeTab: "1",
      tabs: {
        basic: "1",
        relate: "2",
        tag: "3"
      },
      form: {
        loading: false,
        title: "",
        ddate: "",
        content: "",
        year: null,
        month: null,
        day: null,
        hour: null,
        minute: null,
        second: null,
        AD: 1,
        level: 0
      },
      relate: {
        loading: false,
        nations: [],
        periods: [],
        civilizations: [],
        giants: [],
        references: [],
        tags: []
      },
      tag: {
        loading: false,
        tags: []
      },
      options: [
        {
          value: 0,
          label: "待定"
        },
        {
          value: 1,
          label: "非常重要"
        },
        {
          value: 2,
          label: "重要"
        },
        {
          value: 3,
          label: "普通"
        },
        {
          value: 4,
          label: "细节"
        }
      ],
      adoption: {
        bc: 0,
        ad: 1
      }
    };
  },
  components: {
    inputboxnation,
    inputboxcivilization,
    inputboxperiod,
    inputboxgiant,
    inputboxreference,
    inputboxtag
  },
  methods: {
    init() {
      if (this.activeTab == "1") {
        this.initTabBasic();
      } else if (this.activeTab == "2") {
        this.initTabRelate();
      } else {
        this.initTagTab();
      }
    },
    initTabBasic() {
      var _this = this;
      _this.form.loading = true;

      axios
        .post(_this.GLOBAL.url_node_edit_init, {
          id: _this.id
        })
        .then(function(response) {
          if (response.data.result == 0) {
            var data = response.data.data;
            _this.form.title = data.title;
            _this.form.content = data.content;
            _this.form.ddate = data.ddate;
            _this.form.year = data.year;
            _this.form.month = data.month;
            _this.form.day = data.day;
            _this.form.hour = data.hour;
            _this.form.minute = data.minute;
            _this.form.second = data.second;
            _this.form.AD = data.AD;

            _this.form.level = data.level;
            // _this.form.nations = data.nations;
            // _this.form.civilizations = data.civilizations;
            // _this.form.giants = data.giants;

            _this.form.loading = false;
          }
        });
    },
    save() {
      var _this = this;

      axios
        .post(_this.GLOBAL.url_node_edit_save, {
          id: _this.id,
          title: _this.form.title,
          content: _this.form.content,
          ddate: _this.form.ddate,
          year: _this.form.year,
          month: _this.form.month,
          day: _this.form.day,
          minute: _this.form.minute,
          second: _this.form.second,
          AD: _this.form.AD,
          level: _this.form.level,
          content: _this.form.content
        })
        .then(function(response) {
          console.log(response);
          if (response.data.result == 0) {
            _this.$message({
              message: _this.form.title + " 更新成功！",
              type: "success"
            });

            _this.init();
          } else {
            _this.$notify.error({
              title: response.data.msg,
              message: response.data.data,
              duration: 0
            });
          }
        })
        .catch(function(error) {});
    },

    selectNation(item) {
      var _this = this;
      //提交
      var content = "确认添加国家： " + item.title + "？";

      this.$confirm(content, "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "info"
      })
        .then(() => {
          //提交上去
          axios
            .post(_this.GLOBAL.url_node_edit_relate_nation_save, {
              nid: _this.id,
              nation: item
            })
            .then(function(response) {
              if (response.data.result == 0) {
                _this.init();
                _this.$message({
                  type: "success",
                  message: "添加成功!"
                });
              } else {
                _this.$notify.error({
                  title: response.data.msg,
                  message: response.data.data,
                  duration: 0
                });
              }
            })
            .catch(function(error) {
              console.log(error);
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消"
          });
        });
    },
    closeNationTag(item) {
      var _this = this;
      //提交
      var content = "确认删除国家： " + item.title + "？";

      this.$confirm(content, "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "info"
      })
        .then(() => {
          //提交上去
          axios
            .post(_this.GLOBAL.url_node_edit_relate_nation_delete, {
              id: item.id
            })
            .then(function(response) {
              if (response.data.result == 0) {
                _this.init();
                _this.$message({
                  type: "success",
                  message: "删除成功!"
                });
              }
            })
            .catch(function(error) {
              console.log(error);
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消"
          });
        });
    },

    selectCivilization(item) {
      var _this = this;
      //提交
      var content = "确认添加文明： " + item.title + "？";

      this.$confirm(content, "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "info"
      })
        .then(() => {
          //提交上去
          axios
            .post(_this.GLOBAL.url_node_edit_relate_civilization_save, {
              nid: _this.id,
              civilization: item
            })
            .then(function(response) {
              if (response.data.result == 0) {
                _this.init();
                _this.$message({
                  type: "success",
                  message: "添加成功!"
                });
              } else {
                _this.$notify.error({
                  title: response.data.msg,
                  message: response.data.data,
                  duration: 0
                });
              }
            })
            .catch(function(error) {
              console.log(error);
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消"
          });
        });
    },
    closeCivilizationTag(item) {
      var _this = this;
      //提交
      var content = "确认删除文明： " + item.title + "？";

      this.$confirm(content, "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "info"
      })
        .then(() => {
          //提交上去
          axios
            .post(_this.GLOBAL.url_node_edit_relate_civilization_delete, {
              id: item.id
            })
            .then(function(response) {
              if (response.data.result == 0) {
                _this.init();
                _this.$message({
                  type: "success",
                  message: "删除成功!"
                });
              }
            })
            .catch(function(error) {
              console.log(error);
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消"
          });
        });
    },

    selectPeriod(item) {
      var _this = this;
      //提交
      var content = "确认添加时代： " + item.title + "？";

      this.$confirm(content, "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "info"
      })
        .then(() => {
          //提交上去
          axios
            .post(_this.GLOBAL.url_node_edit_relate_period_save, {
              nid: _this.id,
              period: item
            })
            .then(function(response) {
              if (response.data.result == 0) {
                _this.init();
                _this.$message({
                  type: "success",
                  message: "添加成功!"
                });
              } else {
                _this.$notify.error({
                  title: response.data.msg,
                  message: response.data.data,
                  duration: 0
                });
              }
            })
            .catch(function(error) {
              console.log(error);
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消"
          });
        });
    },
    closePeriodTag(item) {
      var _this = this;
      //提交
      var content = "确认删除时代： " + item.title + "？";

      this.$confirm(content, "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "info"
      })
        .then(() => {
          //提交上去
          axios
            .post(_this.GLOBAL.url_node_edit_relate_period_delete, {
              id: item.id
            })
            .then(function(response) {
              if (response.data.result == 0) {
                _this.init();
                _this.$message({
                  type: "success",
                  message: "删除成功!"
                });
              }
            })
            .catch(function(error) {
              console.log(error);
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消"
          });
        });
    },

    selectGiant(item) {
      var _this = this;

      var name = item.name;
      var id;
      if (item.pid != null) {
        name = item.sname;
        item.gid = item.pid;
      }

      //提交
      var content = "确认添加人物： " + name + "？";

      this.$confirm(content, "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "info"
      })
        .then(() => {
          //提交上去
          axios
            .post(_this.GLOBAL.url_node_edit_relate_giant_save, {
              nid: _this.id,
              giant: item
            })
            .then(function(response) {
              if (response.data.result == 0) {
                _this.init();
                _this.$message({
                  type: "success",
                  message: "添加成功!"
                });
              } else {
                _this.$notify.error({
                  title: response.data.msg,
                  message: response.data.data,
                  duration: 0
                });
              }
            })
            .catch(function(error) {
              console.log(error);
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消"
          });
        });
    },
    closeGiantTag(item) {
      var _this = this;
      //提交
      var content = "确认删除人物： " + item.name + "？";

      this.$confirm(content, "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "info"
      })
        .then(() => {
          //提交上去
          axios
            .post(_this.GLOBAL.url_node_edit_relate_giant_delete, {
              id: item.id
            })
            .then(function(response) {
              if (response.data.result == 0) {
                _this.init();
                _this.$message({
                  type: "success",
                  message: "删除成功!"
                });
              }
            })
            .catch(function(error) {
              console.log(error);
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消"
          });
        });
    },
    selectReference(item) {
      var _this = this;
      //提交
      var content = "确认添加依据： " + item.title + "？";

      this.$confirm(content, "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "info"
      })
        .then(() => {
          //提交上去
          axios
            .post(_this.GLOBAL.url_node_edit_relate_reference_save, {
              nid: _this.id,
              reference: item
            })
            .then(function(response) {
              if (response.data.result == 0) {
                _this.init();
                _this.$message({
                  type: "success",
                  message: "添加成功!"
                });
              } else {
                _this.$notify.error({
                  title: response.data.msg,
                  message: response.data.data,
                  duration: 0
                });
              }
            })
            .catch(function(error) {
              console.log(error);
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消"
          });
        });
    },
    closeReferenceTag(item) {
      var _this = this;
      //提交
      var content = "确认删除依据： " + item.title + "？";

      this.$confirm(content, "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "info"
      })
        .then(() => {
          //提交上去
          axios
            .post(_this.GLOBAL.url_node_edit_relate_reference_delete, {
              id: item.id
            })
            .then(function(response) {
              if (response.data.result == 0) {
                _this.init();
                _this.$message({
                  type: "success",
                  message: "删除成功!"
                });
              }
            })
            .catch(function(error) {
              console.log(error);
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消"
          });
        });
    },
    initTabRelate() {
      var _this = this;
      _this.relate.loading = true;

      axios
        .post(_this.GLOBAL.url_node_edit_init_relate, {
          id: _this.id
        })
        .then(function(response) {
          if (response.data.result == 0) {
            var data = response.data.data;
            _this.form.title = data.title;
            _this.relate.nations = data.nations;
            _this.relate.periods = data.periods;
            _this.relate.civilizations = data.civilizations;
            _this.relate.giants = data.giants;
            _this.relate.references = data.references;

            _this.relate.loading = false;
          }
        });
    },

    clickStarTag(item, type) {
      var _this = this;
      _this.relate.loading = true;
      var url = null;
      if (type == "nation") {
        url = _this.GLOBAL.url_nodenation_level_change;
      } else if (type == "period") {
        url = _this.GLOBAL.url_nodeperiod_level_change;
      } else if ((type = "civilization")) {
        url = _this.GLOBAL.url_nodecivilization_level_change;
      }

      if (url == null) return;

      axios
        .post(url, {
          id: item.id
        })
        .then(function(response) {
          if (response.data.result == 0) {
            _this.relate.loading = false;
            _this.init();
          } else {
            _this.GLOBAL.showErrorMsg(response, _this);
            _this.relate.loading = false;
          }
        });
    },
    clickSyncBtn() {
      // 同步段落
      var _this = this;
      _this.relate.loading = true;

      axios
        .post(_this.GLOBAL.url_node_edit_relate_sync, {
          id: _this.id
        })
        .then(function(response) {
          if (response.data.result == 0) {
            _this.$message({
              type: "success",
              message: "同步成功!"
            });
            _this.relate.loading = false;
            _this.init();
          } else {
            _this.GLOBAL.showErrorMsg(response, _this);
            _this.relate.loading = false;
          }
        });
    },

    initTagTab() {
      var _this = this;
      _this.tag.loading = true;

      axios
        .post(_this.GLOBAL.url_node_edit_init_tag, {
          id: _this.id
        })
        .then(function(response) {
          console.log(response);

          if (response.data.result == 0) {
            var data = response.data.data;
            _this.form.title = data.title;
            _this.tag.tags = data.tags;

            _this.tag.loading = false;
          }
        });
    },
    selectTag(item) {
      var _this = this;
      //提交
      var content = "确认添加贴条： " + item.title + "？";

      this.$confirm(content, "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "info"
      })
        .then(() => {
          //提交上去
          axios
            .post(_this.GLOBAL.url_node_edit_tag_save, {
              nid: _this.id,
              tag: item
            })
            .then(function(response) {
              if (response.data.result == 0) {
                _this.init();
                _this.$message({
                  type: "success",
                  message: "添加成功!"
                });
              } else {
                _this.$notify.error({
                  title: response.data.msg,
                  message: response.data.data,
                  duration: 0
                });
              }
            })
            .catch(function(error) {
              console.log(error);
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消"
          });
        });
    },
    closeTagTag(item) {
      var _this = this;
      //提交
      var content = "确认删除贴条： " + item.title + "？";

      this.$confirm(content, "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "info"
      })
        .then(() => {
          //提交上去
          axios
            .post(_this.GLOBAL.url_node_edit_tag_delete, {
              id: item.id
            })
            .then(function(response) {
              if (response.data.result == 0) {
                _this.init();
                _this.$message({
                  type: "success",
                  message: "删除成功!"
                });
              }
            })
            .catch(function(error) {
              console.log(error);
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消"
          });
        });
    }
  },
  mounted: function() {
    this.id = this.$route.params.id;
    this.init();
  },
  watch: {
    activeTab: function(at) {
      this.init();
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.i-star {
  cursor: pointer;
}
</style>
