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
            <el-tab-pane label="基础" name="1" :loading = "form.loading">
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
                <el-card class="box-card">
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
            </el-tab-pane>

            <el-tab-pane label="贴条" name="3">
                <el-row>
                    <inputboxtag @selectTag = "selectTag"></inputboxtag>
                </el-row>
                <el-row>
                  <template v-if="relate.nations == null || relate.nations.length == 0">
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
        nations: []
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
    inputboxgiant
  },
  methods: {
    init() {
      if (this.activeTab == "1") {
        this.initTabBasic();
      } else if (this.activeTab == "2") {
        // this.initTabRelate();
      } else {
        // this.initTagTab();
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

            console.log(giant);

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

    queryTag(queryString, cb) {
      var _this = this;
      var sr = [];
      if (queryString != undefined && queryString.length > 0) {
        axios
          .post(_this.GLOBAL.url_search_tag, {
            sw: queryString
          })
          .then(function(response) {
            var r = response.data;
            if (r.result == 0) {
              var data = r.data.data;
              if (r.data.exist) {
                cb(data);
              } else {
                var item = {
                  id: "11",
                  title: queryString,
                  isnew: true
                };

                sr.push(item);
                cb(sr);
              }
            } else {
              cb(sr);
            }
          });
      } else {
        cb(sr);
      }
    },
    selectTag(item) {
      this.form.tags.push(item);
    },
    closeTag(index) {
      this.form.tags.splice(index, 1);
    },
    selectNation(item) {
      this.form.nations.push(item);
      console.log(item.title);
    },
    closeNation(index) {
      this.form.nations.splice(index, 1);
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
