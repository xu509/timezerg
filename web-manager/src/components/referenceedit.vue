<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to = "{path : '/reference'}">依据</el-breadcrumb-item>
          <el-breadcrumb-item>修改</el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <el-row>
        <p class="paragraph-title">{{form.title}}</p>      
    </el-row>
    <el-row>
      <el-tabs tab-position="top" style="height: 100%;" v-model="activeTab">
         <el-tab-pane label="基础" :name="tabs.basic">
            <el-col :md ="12" :xs="16" :sm="16" >
                <el-form ref="form" label-width="120px" v-loading = "form.loading">
                    <el-form-item label="标题">
                        <el-input v-model="form.title"></el-input>
                    </el-form-item>
                    <el-form-item label="详细">
                      <el-input
                        type="textarea"
                        :rows="4"
                        placeholder="请输入内容"
                        v-model="form.content">
                      </el-input>
                    </el-form-item>

                    <el-form-item label="类别">
                    <el-select v-model="form.type" placeholder="请选择">
                        <el-option
                          v-for="item in form.options"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
                        </el-option>
                      </el-select>
                    </el-form-item>
                    
                    <el-form-item label="隶属于">
                      <inputboxreference @selectReference="selectReference"></inputboxreference>
                      <el-tag type="warning" class="tag-margin" v-if="form.preference != null"
                          closable
                          :disable-transitions="false"
                          @close="closePReferenceTag(form.preference)">
                          {{form.preference.title}}
                        </el-tag>
                    </el-form-item>


                      <el-form-item>
                        <el-button type="primary" @click="save">更新</el-button>
                      </el-form-item>
                </el-form>
            </el-col>
         </el-tab-pane>

          <el-tab-pane label="相关" :name="tabs.relate" >
            <el-card class="box-card" v-loading = "relate.loading">
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

              <el-row>
                  <inputboxnode @selectNode = "selectNode"></inputboxnode>
              </el-row>
              <el-row>
                <template v-if="relate.nodes == null || relate.nodes.length == 0">
                    <p class="paragraph-content">无数据</p>
                </template>
                <template v-if="relate.nodes != null && relate.nodes.length > 0">
                    <el-tag type="warning" v-for="item in relate.nodes" class="tag-margin"
                      :key="item.id"
                      closable
                      :disable-transitions="false"
                      @close="closeNodeTag(item)">
                      {{item.title}}
                    </el-tag>
                </template>
              </el-row>

              <el-row>
                  <inputboxperiod @selectPeriod = "selectPeriod"></inputboxperiod>
              </el-row>
              <el-row>
                <template v-if="relate.periods == null || relate.periods.length == 0">
                    <p class="paragraph-content">无数据</p>
                </template>
                <template v-if="relate.periods != null && relate.periods.length > 0">
                    <el-tag type="warning" v-for="item in relate.periods" class="tag-margin"
                      :key="item.id"
                      closable
                      :disable-transitions="false"
                      @close="closePeriodTag(item)">
                      {{item.title}}
                    </el-tag>
                </template>
              </el-row>

              <el-row>
                  <inputboxnation @selectNation = "selectNation"></inputboxnation>
              </el-row>
              <el-row>
                <template v-if="relate.nations == null || relate.nations.length == 0">
                    <p class="paragraph-content">无数据</p>
                </template>
                <template v-if="relate.nations != null && relate.nations.length > 0">
                    <el-tag type="warning" v-for="item in relate.nations" class="tag-margin"
                      :key="item.id"
                      closable
                      :disable-transitions="false"
                      @close="closeNationTag(item)">
                      {{item.title}}
                    </el-tag>
                </template>
              </el-row>

            </el-card>
            <el-row></el-row>
   
          </el-tab-pane>

         <el-tab-pane label="贴条" :name="tabs.tag"> 
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
import inputboxtag from "./plugin/inputboxtag.vue";
import inputboxcivilization from "./plugin/inputboxcivilization.vue";
import inputboxgiant from "./plugin/inputboxgiant.vue";
import inputboxnation from "./plugin/inputboxnation.vue";
import inputboxreference from "./plugin/inputboxreference.vue";
import inputboxnode from "./plugin/inputboxnode.vue";
import inputboxperiod from "./plugin/inputboxperiod.vue";

export default {
  name: "referenceedit",
  data() {
    return {
      id: "",
      activeTab: "1",
      tabs: {
        basic: "1",
        tag: "3",
        relate: "2"
      },
      form: {
        loading: true,
        title: "",
        content: "",
        type: 0,
        preference: null,
        options: [
          {
            value: 0,
            label: "文献"
          },
          {
            value: 1,
            label: "遗迹"
          }
        ]
      },
      relate: {
        loading: true,
        giants: [],
        civilizations: [],
        nations: []
      },
      tag: {
        loading: true,
        tags: []
      },
      switchc: {
        activev: 1,
        inactivev: 0
      },
      saving: false,
      loading: true,
      loadingTagTab: true
    };
  },
  components: {
    inputboxtag,
    inputboxcivilization,
    inputboxnation,
    inputboxreference,
    inputboxgiant,
    inputboxnode,
    inputboxperiod
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

    // 基础
    save() {
      var _this = this;

      _this.form.loading = true;

      axios
        .post(_this.GLOBAL.url_reference_edit_save, {
          id: _this.id,
          title: _this.form.title,
          content: _this.form.content,
          type: _this.form.type,
          preference: _this.form.preference
        })
        .then(function(response) {
          if (response.data.result == 0) {
            _this.$message({
              message: _this.form.title + " 更新成功！",
              type: "success"
            });
            _this.init();
          }
        });
    },
    initTabBasic() {
      var _this = this;
      _this.form.loading = true;

      axios
        .post(_this.GLOBAL.url_reference_edit_init, {
          id: _this.id
        })
        .then(function(response) {
          if (response.data.result == 0) {
            var reference = response.data.data;

            _this.form.title = reference.title;
            _this.form.content = reference.content;
            _this.form.type = reference.type;
            _this.form.preference = reference.preference;

            _this.form.loading = false;
          } else {
            this.$message.error(response.data.data);
          }
        })
        .catch(function(error) {});
    },

    selectReference(item) {
      this.form.preference = item;
    },
    closePReferenceTag() {
      this.form.preference = null;
    },

    // 基础结束

    //相关信息
    initTabRelate() {
      console.log("init tab relate");
      var _this = this;
      _this.relate.loading = true;

      axios
        .post(_this.GLOBAL.url_reference_edit_init_relate, {
          id: _this.id
        })
        .then(function(response) {
          console.log(response);

          if (response.data.result == 0) {
            var data = response.data.data;
            console.log(data);

            _this.form.title = data.reference.title;
            _this.relate.giants = data.giants;
            _this.relate.nodes = data.nodes;
            _this.relate.periods = data.periods;
            _this.relate.nations = data.nations;
            _this.relate.loading = false;
          } else {
            this.$message.error(response.data.data);
          }
        })
        .catch(function(error) {});
    },

    selectGiant(item) {
      var _this = this;
      //提交
      var content = "确认添加人物： " + item.name + "？";

      this.$confirm(content, "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "info"
      })
        .then(() => {
          //提交上去
          axios
            .post(_this.GLOBAL.url_reference_relate_giant_save, {
              rid: _this.id,
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
            .post(_this.GLOBAL.url_reference_relate_giant_delete, {
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
    selectNode(item) {
      var _this = this;
      //提交
      var content = "确认添加节点： " + item.title + "？";

      this.$confirm(content, "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "info"
      })
        .then(() => {
          //提交上去
          axios
            .post(_this.GLOBAL.url_reference_relate_node_save, {
              rid: _this.id,
              node: item
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
    closeNodeTag(item) {
      var _this = this;
      //提交
      var content = "确认删除节点： " + item.title + "？";

      this.$confirm(content, "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "info"
      })
        .then(() => {
          //提交上去
          axios
            .post(_this.GLOBAL.url_reference_relate_node_delete, {
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
            .post(_this.GLOBAL.url_reference_relate_period_save, {
              rid: _this.id,
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
            .post(_this.GLOBAL.url_reference_relate_period_delete, {
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
            .post(_this.GLOBAL.url_reference_relate_nation_save, {
              rid: _this.id,
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
            .post(_this.GLOBAL.url_reference_relate_nation_delete, {
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
    //贴条
    initTagTab() {
      var _this = this;
      _this.tag.loading = true;

      axios
        .post(_this.GLOBAL.url_reference_edit_init_tag, {
          id: _this.id
        })
        .then(function(response) {
          if (response.data.result == 0) {
            var data = response.data.data;
            _this.form.title = data.reference.title;
            _this.tag.tags = data.tags;
            _this.tag.loading = false;
          } else {
            this.$message.error(response.data.data);
          }
        })
        .catch(function(error) {});
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
            .post(_this.GLOBAL.url_reference_tag_save, {
              rid: _this.id,
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
            .post(_this.GLOBAL.url_reference_tag_delete, {
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
  watch: {
    activeTab: function(at) {
      this.init();
    }
  },
  mounted: function() {
    this.id = this.$route.params.id;
    this.init();
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
