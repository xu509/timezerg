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
            <el-col :md ="16" :xs="16" :sm="16" >
                <el-form ref="form" :model="form" label-width="120px" v-loading = "form.loading">
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

                      <el-form-item>
                        <el-button type="primary" @click="save">更新</el-button>
                      </el-form-item>
                </el-form>
            </el-col>
         </el-tab-pane>
         <el-tab-pane label="相关" :name="tabs.relate">
            <el-card class="box-card" v-loading = "relate.loading">
                <el-row>
                  <inputboxgiant @selectGiant = "selectGiant"></inputboxgiant>
                </el-row>
                <el-row>
                   <template v-if="relate.giants == null || relate.giants.length == 0">
                            <p class="paragraph-content">无数据</p>
                      </template>
                      <template v-if="relate.giants != null && relate.giants.length > 0">
                        <template  v-for="item in relate.giants" >
                            <a @click="clickGiantTag(item)" :key="item.id"> 
                              <el-tag type="warning" class="tag-margin"
                                closable
                                :disable-transitions="false"
                                @close="closeGiantTag(item)">
                                {{item.name}}
                              </el-tag>
                            </a>
                        </template>
                        
                      </template>
                </el-row>
                <el-row>
                  <inputboxinstitution @selectInstitution = "selectInstitution"></inputboxinstitution>
                </el-row>
                <el-row>
                   <template v-if="relate.institutions == null || relate.institutions.length == 0">
                            <p class="paragraph-content">无数据</p>
                      </template>
                      <template v-if="relate.institutions != null && relate.institutions.length > 0">
                          <template v-for="item in relate.institutions">
                            <a :key="item.id" @click="clickInstitutionTag(item)">
                              <el-tag type="warning" class="tag-margin"
                                closable
                                :disable-transitions="false"
                                @close="closeInstitutionTag(item)">
                                {{item.title}}
                              </el-tag>
                            </a>
                          </template>
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
                        <template v-for="item in relate.references">
                            <a :key="item.id" @click="clickReferenceTag(item)">
                              <el-tag type="warning" class="tag-margin"
                                closable
                                :disable-transitions="false"
                                @close="closeReferenceTag(item)">
                                {{item.title}}
                              </el-tag>
                            </a>
                          </template>
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
                        <template v-for="item in relate.periods">
                          <a :key="item.id" @click="clickPeriodTag(item)">
                            <el-tag type="warning" class="tag-margin"
                              closable
                              :disable-transitions="false"
                              @close="closePeriodTag(item)">
                              {{item.title}}
                            </el-tag>
                          </a>
                        </template>
                      </template>
                </el-row>
            </el-card>
         </el-tab-pane>
         <el-tab-pane label="贴条" :name="tabs.tag"> 
            <el-card class="box-card" v-loading = "tag.loading">
              <el-col :md="12"  :xs="16" :sm="16" >
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
                      @close="closeTag(item)">
                      {{item.title}}
                    </el-tag>
                </template>
              </el-row>
              </el-col>
            </el-card>
          </el-tab-pane>

          <el-tab-pane label="节点" :name="tabs.nodes"> 
              <el-row>
              <el-col :md="2">
                  <el-button type="primary" v-loading = "nodes.loading" @click = "clickSyncBtn">同步</el-button>
              </el-col>
              <el-col :md="6">
                <selectboxlevel @selectLevel = "selectLevel"></selectboxlevel>  
              </el-col>
              <el-col :md="6">
                <el-input v-model="searchContentNode" placeholder="搜索标题"></el-input>
              </el-col>
           </el-row>

            <el-row v-loading = "nodes.loading">
              <el-table :data = "nodes.nodes">
                  <el-table-column label = "时间" prop="ddate">
                  </el-table-column>
                  <el-table-column prop = "title" label = "标题" ></el-table-column>
                  <el-table-column prop = "levelstr" label = "类型" ></el-table-column>
                  <el-table-column fixed="right" label="操作">
                      <template slot-scope="scope">
                        <el-button size="small" type="primary" v-if="scope.row.l != 1" @click="updateLevel(scope.row.nnid,1)">设为重要</el-button>
                        <el-button size="small" v-if="scope.row.l == 1" @click="updateLevel(scope.row.nnid,0)">设为普通</el-button>
                        <el-button size="small" type="danger" @click="deleteNode(scope.row.nnid)">删除</el-button>
                      </template>
                  </el-table-column>
              </el-table>
            </el-row>

            <el-row>
                  <el-pagination @size-change="handleSizeChange"
                                @current-change="handleCurrentChange"
                                :current-page="current_page"
                                :page-sizes="[10, 20, 50, 100]"
                                :page-size="page_size"
                                layout="sizes,total,next, pager,prev,jumper "
                                :total="nodes.total">
                  </el-pagination>
              </el-row>
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
import inputboxreference from "./plugin/inputboxreference.vue";
import inputboxperiod from "./plugin/inputboxperiod.vue";
import selectboxlevel from "./plugin/selectboxlevel.vue";

export default {
  name: "nationedit",
  data() {
    return {
      id: "",
      activeTab: "1",
      tabs: {
        basic: "1",
        relate: "2",
        tag: "3",
        nodes: "4"
      },
      form: {
        loading: false,
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
        AD: 1,
        eAD: 1
      },
      tag: {
        loading: false,
        tags: []
      },
      relate: {
        loading: false,
        giants: [],
        institutions: [],
        periods: []
      },
      nodes: {
        loading: true,
        nodes: [],
        total: null
      },
      switchc: {
        activev: 1,
        inactivev: 0
      },

      // 需要 watch 的
      current_page: 1,
      page_size: 20,
      level: 0,
      searchContentNode: null
    };
  },
  components: {
    inputboxnation,
    inputboxgiant,
    inputboxinstitution,
    inputboxtag,
    inputboxreference,
    inputboxperiod,
    selectboxlevel
  },
  methods: {
    init() {
      if (this.activeTab == "1") {
        this.initTabBasic();
      } else if (this.activeTab == "2") {
        this.initTabRelate();
      } else if (this.activeTab == "3") {
        this.initTagTab();
      } else {
        this.initTabNodes();
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
    initTabRelate() {
      var _this = this;
      _this.relate.loading = true;

      axios
        .post(_this.GLOBAL.url_nation_edit_init_relate, {
          id: _this.id
        })
        .then(function(response) {
          if (response.data.result == 0) {
            var data = response.data.data;

            _this.form.title = data.title;
            _this.relate.giants = data.giants;
            _this.relate.institutions = data.institutions;
            _this.relate.references = data.references;
            _this.relate.periods = data.periods;

            // console.log(nation.giant);

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
            .post(_this.GLOBAL.url_nation_edit_relate_giant_save, {
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
    closeGiantTag(item) {
      var _this = this;
      //提交
      var content = "确认删除人物： " + item.name + "？";

      this.$confirm(content, "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          //提交上去
          axios
            .post(_this.GLOBAL.url_nation_edit_relate_giant_delete, {
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
    selectInstitution(item) {
      var _this = this;
      //提交
      var content = "确认添加制度： " + item.title + "？";

      this.$confirm(content, "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "info"
      })
        .then(() => {
          //提交上去
          axios
            .post(_this.GLOBAL.url_nation_edit_relate_institution_save, {
              nid: _this.id,
              institution: item
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
    closeInstitutionTag(item) {
      var _this = this;
      //提交
      var content = "确认删除制度： " + item.title + "？";

      this.$confirm(content, "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          //提交上去
          axios
            .post(_this.GLOBAL.url_nation_edit_relate_institution_delete, {
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
      var content = "确认添加依据： " + item.title + "？";

      this.$confirm(content, "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "info"
      })
        .then(() => {
          //提交上去
          axios
            .post(_this.GLOBAL.url_nation_edit_relate_reference_save, {
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
      var content = "确认删除依据： " + item.title + "？";

      this.$confirm(content, "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          //提交上去
          axios
            .post(_this.GLOBAL.url_nation_edit_relate_reference_delete, {
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
            .post(_this.GLOBAL.url_nation_edit_relate_period_save, {
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
    closePeriodTag(item) {
      var _this = this;
      //提交
      var content = "确认删除时代： " + item.title + "？";

      this.$confirm(content, "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          //提交上去
          axios
            .post(_this.GLOBAL.url_nation_edit_relate_period_delete, {
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
    clickGiantTag(item) {
      var gid = item.gid;
      this.$router.push({
        path: "/giant/edit/" + gid
      });
    },
    clickInstitutionTag(item) {
      var id = item.iid;
      this.$router.push({
        path: "/institution/edit/" + id
      });
    },
    clickPeriodTag(item) {
      var id = item.pid;
      this.$router.push({
        path: "/period/edit/" + id
      });
    },
    clickReferenceTag(item) {
      var id = item.rid;
      this.$router.push({
        path: "/reference/edit/" + id
      });
    },
    //

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
            _this.tag.tags = data.tags;
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
              if (response.data.result == 0) {
                _this.init();
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

    // node 相关
    initTabNodes() {
      var _this = this;
      _this.nodes.loading = true;

      var start = (_this.current_page - 1) * _this.page_size;
      var size = _this.page_size;

      axios
        .post(_this.GLOBAL.url_nation_edit_init_nodes, {
          id: _this.id,
          level: _this.level,
          title: _this.searchContentNode,
          start: start,
          size: size
        })
        .then(function(response) {
          if (response.data.result == 0) {
            var data = response.data.data;
            console.log(data);

            _this.form.title = data.nation.title;
            _this.nodes.nodes = data.nodes;
            _this.nodes.total = data.nodestotal;

            _this.nodes.loading = false;
          } else {
            _this.GLOBAL.showErrorMsg(response, _this);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    updateLevel(id, level) {
      var _this = this;
      _this.nodes.loading = true;

      axios
        .post(_this.GLOBAL.url_nation_edit_nodes_updatelevel, {
          id: id,
          level: level
        })
        .then(function(response) {
          if (response.data.result == 0) {
            var data = response.data.data;
            _this.$message({
              type: "success",
              message: "修改成功!"
            });
            _this.init();
          } else {
            _this.GLOBAL.showErrorMsg(response, _this);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    deleteNode(id) {
      var _this = this;
      _this.nodes.loading = true;

      console.log("delete : " + id);

      axios
        .delete(_this.GLOBAL.url_nodenation_delete, {
          data: {
            id: id
          }
        })
        .then(function(response) {
          if (response.data.result == 0) {
            var data = response.data.data;

            var message = "已成功删除";

            _this.$message({
              type: "success",
              message: message
            });
            _this.init();
          } else {
            _this.GLOBAL.showErrorMsg(response, _this);
            _this.init();
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    clickSyncBtn() {
      var _this = this;
      _this.nodes.loading = true;

      axios
        .post(_this.GLOBAL.url_nation_edit_nodes_sync, {
          id: _this.id
        })
        .then(function(response) {
          if (response.data.result == 0) {
            var data = response.data.data;

            var message = "";
            if (data == 0) {
              message = "已完成同步，无更新";
            } else {
              message = "同步成功，共更新了 " + data + " 条数据";
            }

            _this.$message({
              type: "success",
              message: message
            });
            _this.init();
          } else {
            _this.GLOBAL.showErrorMsg(response, _this);
            _this.init();
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },

    selectLevel(value) {
      this.level = value;
    },

    handleSizeChange(size) {
      this.page_size = size;
    },
    handleCurrentChange(currentPage) {
      this.current_page = currentPage;
    }
  },
  watch: {
    activeTab: function(at) {
      this.init();
    },
    current_page(cp) {
      this.init();
    },
    page_size(ps) {
      this.init();
    },
    level(l) {
      (this.current_page = 1), (this.page_size = 20), this.init();
    },
    searchContentNode(scn) {
      (this.current_page = 1), (this.page_size = 20), this.init();
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
