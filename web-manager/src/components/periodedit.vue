<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to = "{path : '/period'}">时代</el-breadcrumb-item>
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
                <el-form ref="form" :model="form" label-width="120px" v-loading = "loading">
                      <el-form-item label="标题">
                        <el-input v-model="form.title"></el-input>
                      </el-form-item>
                      <el-form-item label="启">
                        <el-row :gutter="20">
                          <el-col :md = "4" :xs="18" :sm="18">
                            <el-input v-model="form.year" placeholder="年"/>
                          </el-col>
                          <el-col :md = "3" :xs="18" :sm="18">
                            <el-input v-model="form.month" placeholder="月"/>
                          </el-col>
                          <el-col :md = "3" :xs="18" :sm="18">
                            <el-input v-model="form.day" placeholder="日"/>
                          </el-col>
                          <el-col :md = "3" :xs="18" :sm="18">
                            <el-switch
                                v-model="form.AD"
                                active-text="AD"
                                inactive-text="BC"
                                :active-value= "switchc.activev"
                                :inactive-value="switchc.inactivev">
                              </el-switch>
                          </el-col>
                        </el-row>
                      </el-form-item>
                      <el-form-item label="终">
                        <el-row :gutter="20">
                          <el-col :md = "4" :xs="18" :sm="18">
                            <el-input v-model="form.eyear" placeholder="年"/>
                          </el-col>
                          <el-col :md = "3" :xs="18" :sm="18">
                            <el-input v-model="form.emonth" placeholder="月"/>
                          </el-col>
                          <el-col :md = "3" :xs="18" :sm="18">
                            <el-input v-model="form.eday" placeholder="日"/>
                          </el-col>
                          <el-col :md = "3" :xs="18" :sm="18">
                            <el-switch
                                v-model="form.eAD"
                                active-text="AD"
                                inactive-text="BC"
                                :active-value= "switchc.activev"
                                :inactive-value="switchc.inactivev">
                              </el-switch>
                          </el-col>
                        </el-row>
                      </el-form-item>
                      <el-form-item label="显示的时间">
                        <el-input v-model="form.ddate"></el-input>
                      </el-form-item>
                      <el-form-item label="详细">
                        <el-input
                          type="textarea"
                          :rows="4"
                          placeholder="请输入内容"
                          v-model="form.content">
                        </el-input>
                      </el-form-item>


                      <el-form-item>
                        <el-button type="primary" @click="save" :loading="saving">更新</el-button>
                      </el-form-item>
                </el-form>
            </el-col>
         </el-tab-pane>

          <el-tab-pane label="相关" :name="tabs.relate" >
            <el-card class="box-card" v-loading = "relate.loading">
              <el-row>
                  <inputboxcivilization @selectCivilization = "selectCivilization"></inputboxcivilization>
              </el-row>
              <el-row>
                <template v-if="relate.civilizations == null || relate.civilizations.length == 0">
                    <p class="paragraph-content">无数据</p>
                </template>
                <template v-if="relate.civilizations != null && relate.civilizations.length > 0">
                    <el-tag type="warning" v-for="item in relate.civilizations" class="tag-margin"
                      :key="item.id"
                      closable
                      :disable-transitions="false"
                      @close="closeCivilizationTag(item)">
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
                    <el-tag type="warning" v-for="item in relate.nations" class="tag-margin"
                      :key="item.id"
                      closable
                      :disable-transitions="false"
                      @close="closeNationTag(item)">
                      {{item.title}}
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


            </el-card>
          </el-tab-pane>

         <el-tab-pane label="贴条" :name="tabs.tag" :loading = "tag.loading"> 
            <el-card class="box-card">
              <el-row>
                  <inputboxtag @selectTag="selectTag"></inputboxtag>
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
import inputboxnation from "./plugin/inputboxnation.vue";
import inputboxreference from "./plugin/inputboxreference.vue";

export default {
  name: "periodedit",
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
        title: "",
        content: "",
        ddate: "",
        year: null,
        month: null,
        day: null,
        eyear: null,
        emonth: null,
        eday: null,
        AD: 1,
        eAD: 1
      },
      relate: {
        loading: true,
        civilizations: [],
        nations: [],
        references:[]
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
    inputboxreference
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

      _this.saving = true;
      _this.loading = true;

      axios
        .post(_this.GLOBAL.url_period_edit_save, {
          id: _this.id,
          title: _this.form.title,
          content: _this.form.content,
          ddate: _this.form.ddate,
          year: _this.form.year,
          month: _this.form.month,
          day: _this.form.day,
          eyear: _this.form.eyear,
          emonth: _this.form.emonth,
          eday: _this.form.eday,
          AD: _this.form.AD,
          eAD: _this.form.eAD
        })
        .then(function(response) {
          if (response.data.result == 0) {
            _this.$message({
              message: _this.form.title + " 更新成功！",
              type: "success"
            });
            _this.initTabBasic();
          }
        });
    },
    initTabBasic() {
      var _this = this;
      _this.saving = false;
      _this.loading = true;

      axios
        .post(_this.GLOBAL.url_period_edit_init, {
          id: _this.id
        })
        .then(function(response) {
          if (response.data.result == 0) {
            var period = response.data.data;

            _this.form.title = period.title;
            _this.form.content = period.content;
            _this.form.ddate = period.ddate;
            _this.form.year = period.year;
            _this.form.month = period.month;
            _this.form.day = period.day;
            _this.form.eyear = period.eyear;
            _this.form.emonth = period.emonth;
            _this.form.eday = period.eday;

            _this.form.AD = period.aD;
            _this.form.eAD = period.eAD;

            // console.log(period.giant);

            _this.loading = false;
          } else {
            this.$message.error(response.data.data);
          }
        })
        .catch(function(error) {});
    },
    // 基础结束

    //相关信息
    initTabRelate() {
      var _this = this;
      _this.relate.loading = true;

      axios
        .post(_this.GLOBAL.url_period_edit_init_relate, {
          id: _this.id
        })
        .then(function(response) {
          if (response.data.result == 0) {
            var data = response.data.data;
            _this.form.title = data.period.title;
            _this.relate.civilizations = data.civilizations;
            _this.relate.nations = data.nations;
            _this.relate.references = data.references;
            _this.relate.loading = false;
          } else {
            this.$message.error(response.data.data);
          }
        })
        .catch(function(error) {});
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
            .post(_this.GLOBAL.url_period_relate_civilization_save, {
              pid: _this.id,
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
            .post(_this.GLOBAL.url_period_relate_civilization_delete, {
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
            .post(_this.GLOBAL.url_period_relate_nation_save, {
              pid: _this.id,
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
            .post(_this.GLOBAL.url_period_relate_nation_delete, {
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
            .post(_this.GLOBAL.url_period_relate_reference_save, {
              pid: _this.id,
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
            .post(_this.GLOBAL.url_period_relate_reference_delete, {
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
        .post(_this.GLOBAL.url_period_edit_init_tag, {
          id: _this.id
        })
        .then(function(response) {
          if (response.data.result == 0) {
            var data = response.data.data;
            _this.form.title = data.period.title;
            _this.tag.tags = data.tags;
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
            .post(_this.GLOBAL.url_period_tag_save, {
              pid: _this.id,
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
            .post(_this.GLOBAL.url_period_tag_delete, {
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
