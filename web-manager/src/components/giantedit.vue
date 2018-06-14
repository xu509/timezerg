<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to = "{path : '/giant'}">人物</el-breadcrumb-item>
          <el-breadcrumb-item>修改</el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>

    <el-row>
        <p class="paragraph-title">{{form.name}}</p>     
    </el-row>
    <el-row>
          <el-tabs tab-position="top" style="height: 100%;" v-model="activeTab">
            <el-tab-pane label="基础" name="1" v-loading = "form.loading">
                <el-row>
                  <el-col :md ="18" :xs="24" :sm="24" >
                      <el-form ref="form" :model="form" label-width="120px">
                            <el-form-item label="姓名">
                              <el-input v-model="form.name"></el-input>
                            </el-form-item>

                            <el-form-item label="描述">
                              <el-input
                                  type="textarea"
                                  :rows="4"
                                  placeholder="描述"
                                  v-model="form.content">
                              </el-input>
                            </el-form-item>

                            <el-form-item label="主体">
                                <inputboxgiant @selectGiant = "selectPGiant"></inputboxgiant>
                                <el-tag type="warning" v-if="form.pgiant != null" class="tag-margin" closable :disable-transitions="false" @close="closePGiantTag()">
                                    {{form.pgiant.name}}
                                </el-tag>
                            </el-form-item>

                            <el-form-item>
                              <el-button type="primary" @click="save">更新</el-button>
                            </el-form-item>
                      </el-form>
                  </el-col>
                </el-row>
            </el-tab-pane>

            <el-tab-pane label="相关" name="2">
                <el-card class="box-card" v-loading = "relate.loading">
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
                </el-card>
            </el-tab-pane>

            <el-tab-pane label="贴条" name="3">
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
import inputboxtag from "./plugin/inputboxtag.vue";
import inputboxgiant from "./plugin/inputboxgiant.vue";
import inputboxreference from "./plugin/inputboxreference.vue";
import inputboxnode from "./plugin/inputboxnode.vue";

export default {
  name: "giantedit",
  data() {
    return {
      id: "",
      activeTab: "1",
      form: {
        loading: true,
        pgiant: "",
        name: "",
        content: "",
        ddate: ""
      },
      relate: {
        loading: true,
        nations: [],
        references:[],
        nodes:[]
      },
      tag: {
        loading: true,
        tags: []
      }
    };
  },
  components: {
    inputboxnation,
    inputboxtag,
    inputboxgiant,
    inputboxreference,
    inputboxnode
  },
  methods: {
    init() {
      if (this.activeTab == "1") {
        this.initTabBasic();
      } else if (this.activeTab == "2") {
        this.initTabRelate();
      } else {
        this.initTabTag();
      }
    },

    // 基本信息
    initTabBasic() {
      var _this = this;
      _this.form.loading = true;

      axios
        .post(_this.GLOBAL.url_giant_edit_init, {
          id: _this.id
        })
        .then(function(response) {
          if (response.data.result == 0) {
            var giant = response.data.data;
            _this.form.name = giant.name;
            _this.form.content = giant.content;
            _this.form.pgiant = giant.pgiant;
            _this.form.loading = false;
          } else {
            this.$message.error(response.data.data);
          }
        })
        .catch(function(error) {});
    },
    save() {
      var _this = this;

      _this.saving = true;
      _this.loading = true;

      axios
        .post(_this.GLOBAL.url_giant_edit_save, {
          id: _this.id,
          name: _this.form.name,
          content: _this.form.content,
          pgiant: _this.form.pgiant
        })
        .then(function(response) {
          console.log(response);
          if (response.data.result == 0) {
            _this.$message({
              message: "更新成功！",
              type: "success"
            });
            _this.init();
          } else {
            _this.$message.error(response.data.msg);
            _this.init();
          }
        })
        .catch(function(error) {
          _this.$message.error(response.data.msg);
          _this.init();
        });
    },
    selectPGiant(item) {
      this.form.pgiant = item;
    },
    closePGiantTag() {
      this.form.pgiant = null;
    },

    // 相关
    initTabRelate() {
      // console.log("init tab relate");
      var _this = this;
      _this.relate.loading = true;

      axios
        .post(_this.GLOBAL.url_giant_edit_init_relate, {
          id: _this.id
        })
        .then(function(response) {
          if (response.data.result == 0) {
            var data = response.data.data;
            _this.form.name = data.name;
            _this.relate.nations = data.nations;
            _this.relate.nodes = data.nodes;
            _this.relate.references = data.references;
            // _this.form.pgiant = giant.pgiant;
            _this.relate.loading = false;
          } else {
            this.$message.error(response.data.data);
          }
        })
        .catch(function(error) {
          console.log(error);
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
            .post(_this.GLOBAL.url_giant_edit_relate_nation_save, {
              gid: _this.id,
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
                _this.$message({
                  type: "error",
                  message: response.data.msg + " - " + response.data.data
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
        type: "warning"
      })
        .then(() => {
          //提交上去
          axios
            .post(_this.GLOBAL.url_giant_edit_relate_nation_delete, {
              id: item.id
            })
            .then(function(response) {
              if (response.data.result == 0) {
                _this.init();
                _this.$message({
                  type: "success",
                  message: "删除成功!"
                });
              } else {
                _this.$message({
                  type: "error",
                  message: response.data.msg + " - " + response.data.data
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
      var content = "确认添加参照： " + item.title + "？";

      this.$confirm(content, "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "info"
      })
        .then(() => {
          //提交上去
          axios
            .post(_this.GLOBAL.url_giant_edit_relate_reference_save, {
              gid: _this.id,
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
                _this.$message({
                  type: "error",
                  message: response.data.msg + " - " + response.data.data
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
      var content = "确认删除参照： " + item.title + "？";

      this.$confirm(content, "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          //提交上去
          axios
            .post(_this.GLOBAL.url_giant_edit_relate_reference_delete, {
              id: item.id
            })
            .then(function(response) {
              if (response.data.result == 0) {
                _this.init();
                _this.$message({
                  type: "success",
                  message: "删除成功!"
                });
              } else {
                _this.$message({
                  type: "error",
                  message: response.data.msg + " - " + response.data.data
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
            .post(_this.GLOBAL.url_giant_edit_relate_node_save, {
              gid: _this.id,
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
                _this.$message({
                  type: "error",
                  message: response.data.msg + " - " + response.data.data
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
        type: "warning"
      })
        .then(() => {
          //提交上去
          axios
            .post(_this.GLOBAL.url_giant_edit_relate_node_delete, {
              id: item.id
            })
            .then(function(response) {
              if (response.data.result == 0) {
                _this.init();
                _this.$message({
                  type: "success",
                  message: "删除成功!"
                });
              } else {
                _this.$message({
                  type: "error",
                  message: response.data.msg + " - " + response.data.data
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

    // 贴条
    initTabTag() {
      var _this = this;
      _this.tag.loading = true;

      axios
        .post(_this.GLOBAL.url_giant_edit_init_tag, {
          id: _this.id
        })
        .then(function(response) {
          if (response.data.result == 0) {
            var data = response.data.data;
            _this.form.name = data.name;
            _this.tag.tags = data.tags;
            // _this.form.pgiant = giant.pgiant;
            _this.tag.loading = false;
          } else {
            this.$message.error(response.data.data);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    selectTag(item) {
       var _this = this;
      //提交
      var content = "确认添加tag： " + item.title + "？";

      this.$confirm(content, "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "info"
      })
        .then(() => {
          //提交上去
          axios
            .post(_this.GLOBAL.url_giant_edit_tag_save, {
              gid: _this.id,
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
                _this.$message({
                  type: "error",
                  message: response.data.msg + " - " + response.data.data
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
      var content = "确认删除tag： " + item.title + "？";

      this.$confirm(content, "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          //提交上去
          axios
            .post(_this.GLOBAL.url_giant_edit_tag_delete, {
              id: item.id
            })
            .then(function(response) {
              if (response.data.result == 0) {
                _this.init();
                _this.$message({
                  type: "success",
                  message: "删除成功!"
                });
              } else {
                _this.$message({
                  type: "error",
                  message: response.data.msg + " - " + response.data.data
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
