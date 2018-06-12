<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to = "{path : '/nation'}">国家</el-breadcrumb-item>
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
                        <el-form-item label="重要发明">
                        <el-input v-model="form.invent"></el-input>
                      </el-form-item>
                      <el-form-item label="承接">
                          <inputboxnation @selectNation="selectPNation"></inputboxnation>
                          <el-tag type="warning" v-if = "form.pnation != null"
                            :key="form.pid"
                            closable
                            :disable-transitions="false"
                            @close="closePNationTag()">
                            {{form.pnation}}
                          </el-tag>
                      </el-form-item>
                      <el-form-item label="附属">
                          <inputboxnation @selectNation="selectFNation"></inputboxnation>
                          <el-tag type="warning" v-if = "form.fnation != null"
                            :key="form.id"
                            closable
                            :disable-transitions="false"
                            @close="closeFNationTag()">
                            {{form.fnation}}
                          </el-tag>
                      </el-form-item>

                      <el-form-item label="名人">
                          <inputboxgiant @selectGiant = "selectGiant"></inputboxgiant>
                          <el-tag type="warning" v-if = "form.giant != null" v-for="(item,index) in form.giant"
                            :key="item.gid"
                            closable
                            :disable-transitions="false"
                            @close="closeGiantTag(index)">
                            {{item.name}}
                          </el-tag>
                      </el-form-item>

                      <el-form-item label="制度">
                        <inputboxinstitution @select ="selectInstitution"></inputboxinstitution>
                          <el-tag type="warning" v-if = "form.institution != null" v-for="(item,index) in form.institution"
                            :key="item.id"
                            closable
                            :disable-transitions="false"
                            @close="closeInstitutionTag(index)">
                            {{item.title}}
                          </el-tag>
                      </el-form-item>


                      <el-form-item>
                        <el-button type="primary" @click="save" :loading="saving">更新</el-button>
                      </el-form-item>
                </el-form>
            </el-col>
         </el-tab-pane>
         <el-tab-pane label="贴条" :name="tabs.tag"> 
           <el-col :md="12"  :xs="16" :sm="16" >
              <el-row>
                  <inputboxtag @selectTag = "selectTag"></inputboxtag>
              </el-row>
              <el-row>
                <template v-if="tags == null || tags.length == 0">
                    <p class="paragraph-content">无数据</p>
                </template>
                <template v-if="tags != null && tags.length > 0">
                    <el-tag type="warning" v-for="item in tags" class="tag-margin"
                      :key="item.id"
                      closable
                      :disable-transitions="false"
                      @close="closeTag(item)">
                      {{item.title}}
                    </el-tag>
                </template>
              </el-row>

           </el-col>
          </el-tab-pane>
      </el-tabs>
    </el-row>
  </div>
</template>

<script>
import axios from "axios";
import inputboxnation from "./plugin/inputboxnation.vue";
import inputboxgiant from "./plugin/inputboxgiant.vue";
import inputboxinstitution from "./plugin/inputboxinstitution.vue";
import inputboxtag from "./plugin/inputboxtag.vue";

export default {
  name: "NationEdit",
  data() {
    return {
      id: "",
      activeTab: "2",
      tabs: {
        basic: "1",
        tag: "2"
      },
      form: {
        title: "",
        ddate: "",
        year: null,
        month: null,
        day: null,
        eyear: null,
        emonth: null,
        eday: null,
        pid: null,
        pnation: "",
        fid: null,
        fnation: "",
        giant: [],
        institution: [],
        AD: 1,
        eAD: 1
      },
      tags: [],
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
    inputboxnation,
    inputboxgiant,
    inputboxinstitution,
    inputboxtag
  },
  methods: {
    init() {
      if (this.activeTab == "1") {
        this.initTabBasic();
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
        .post(_this.GLOBAL.url_nation_edit_save, {
          id: _this.id,
          title: _this.form.title,
          ddate: _this.form.ddate,
          year: _this.form.year,
          month: _this.form.month,
          day: _this.form.day,
          eyear: _this.form.eyear,
          emonth: _this.form.emonth,
          eday: _this.form.eday,
          AD: _this.form.AD,
          eAD: _this.form.eAD,
          invent: _this.form.invent,
          giant: _this.form.giant,
          pid: _this.form.pid,
          fid: _this.form.fid,
          institution: _this.form.institution
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
        .post(_this.GLOBAL.url_nation_edit_init, {
          id: _this.id
        })
        .then(function(response) {
          if (response.data.result == 0) {
            var nation = response.data.data;

            _this.form.title = nation.title;
            _this.form.ddate = nation.ddate;
            _this.form.year = nation.year;
            _this.form.month = nation.month;
            _this.form.day = nation.day;
            _this.form.eyear = nation.eyear;
            _this.form.emonth = nation.emonth;
            _this.form.eday = nation.eday;

            _this.form.pid = nation.pid;
            _this.form.fid = nation.fid;
            _this.form.fnation = nation.fnation;
            _this.form.pnation = nation.pnation;

            _this.form.AD = nation.aD;
            _this.form.eAD = nation.eAD;
            _this.form.invent = nation.invent;
            _this.form.giant = nation.giant;
            _this.form.institution = nation.institution;

            // console.log(nation.giant);

            _this.loading = false;
          } else {
            this.$message.error(response.data.data);
          }
        })
        .catch(function(error) {});
    },
    selectPNation(item) {
      this.form.pid = item.id;
      this.form.pnation = item.title;
    },
    closePNationTag() {
      this.form.pid = null;
      this.form.pnation = null;
    },
    selectFNation(item) {
      this.form.fid = item.id;
      this.form.fnation = item.title;
    },
    closeFNationTag() {
      this.fid = null;
      this.fnation = null;
    },
    selectGiant(item) {
      //增加
      if (this.form.giant) {
        console.log(111);
        this.form.giant.push(item);
      } else {
        console.log(222);
        this.form.giant = new Array();
        this.form.giant.push(item);
      }

      // this.form.pid = item.id;
      // this.form.pnation = item.title;
    },
    closeGiantTag(index) {
      this.form.giant.splice(index, 1);
    },
    selectInstitution(item) {
      //增加
      if (this.form.institution) {
        console.log(111);
        this.form.institution.push(item);
      } else {
        console.log(222);
        this.form.institution = new Array();
        this.form.institution.push(item);
      }

      // this.form.pid = item.id;
      // this.form.pnation = item.title;
    },
    closeInstitutionTag(index) {
      this.form.institution.splice(index, 1);
    },
    // 基础结束

    //贴条
    initTagTab() {
      var _this = this;
      _this.loadingTagTab = true;

      axios
        .post(_this.GLOBAL.url_nation_edit_init_tag, {
          id: _this.id
        })
        .then(function(response) {
          if (response.data.result == 0) {
            var data = response.data.data;
            _this.tags = data.tags;
            _this.form.title = data.nation.title;
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
            .post(_this.GLOBAL.url_nation_tag_save, {
              nid: _this.id,
              tag: item
            })
            .then(function(response) {
              console.log(222);
              if (response.data.result == 0) {
                _this.initTagTab();
                _this.$message({
                  type: "success",
                  message: "添加成功!"
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
    closeTag(item) {
      var _this = this;
      //提交
      var content = "确认删除贴条： " + item.title + "？";

      this.$confirm(content, "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          //提交上去
          axios
            .post(_this.GLOBAL.url_nation_tag_delete, {
              id: item.id
            })
            .then(function(response) {
              console.log(222);
              if (response.data.result == 0) {
                _this.initTagTab();
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
      if (at == "1") {
        this.initTabBasic();
      } else {
        this.initTagTab();
      }
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
