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
      <el-col :md ="18" :xs="24" :sm="24" >
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
                   <el-autocomplete popper-class="my-autocomplete"
                              :fetch-suggestions="queryNation"
                                placeholder="搜索"
                                @select="selectNation">
                                <template slot-scope="props">
                                          <div class="name">
                                            {{ props.item.title }}
                                          </div>
                                </template>
                    </el-autocomplete>
                    <el-tag type="warning" v-if = "form.pnation != null"
                      :key="form.pid"
                      closable
                      :disable-transitions="false"
                      @close="closeNationTag()">
                      {{form.pnation}}
                    </el-tag>
                </el-form-item>

                <el-form-item label="名人">
                   <el-autocomplete popper-class="my-autocomplete"
                              :fetch-suggestions="queryGiant"
                                placeholder="搜索"
                                @select="selectGiant">
                                <template slot-scope="props">
                                          <div class="name">
                                                <i class="el-icon-circle-plus-outline" v-if="props.item.isnew"></i>&nbsp;&nbsp;{{ props.item.name }}
                                          </div>
                                </template>
                    </el-autocomplete>
                    <el-tag type="warning" v-if = "form.giant != null" v-for="(item,index) in form.giant"
                      :key="item.id"
                      closable
                      :disable-transitions="false"
                      @close="closeGiantTag(index)">
                      {{item.name}}
                    </el-tag>
                </el-form-item>


                <el-form-item>
                  <el-button type="primary" @click="save" :loading="saving">更新</el-button>
                </el-form-item>
          </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import axios from "axios";

// var url_save = "http://192.168.1.112:8081/nation/edit/save",
//   url_search_nation = "http://192.168.1.112:8081/nation/search",
//   url_init = "http://192.168.1.112:8081/nation/edit/init";
export default {
  name: "NationEdit",
  data() {
    return {
      id: "",
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
        giant: [],
        AD: 1,
        eAD: 1
      },
      switchc: {
        activev: 1,
        inactivev: 0
      },
      saving: false,
      loading: true
    };
  },
  methods: {
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
          pid: _this.form.pid
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
            _this.$message.error(response.data.msg);
            _this.init();
          }
        })
        .catch(function(error) {
          _this.$message.error(response.data.msg);
          _this.init();
        });
    },
    init() {
      var _this = this;
      _this.saving = false;

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
            _this.form.pnation = nation.pnation;

            _this.form.AD = nation.aD;
            _this.form.eAD = nation.eAD;
            _this.form.invent = nation.invent;
            _this.form.giant = nation.giant;

            _this.loading = false;
          } else {
            this.$message.error(response.data.data);
          }
        })
        .catch(function(error) {});
    },
    queryNation(queryString, cb) {
      var _this = this;
      var sr = [];
      if (queryString != undefined && queryString.length > 0) {
        axios
          .post(_this.GLOBAL.url_search_nation, {
            sw: queryString
          })
          .then(function(response) {
            var r = response.data;
            if (r.result == 0) {
              var data = r.data.data;
              if (r.data.exist) {
                cb(data);
              } else {
                cb(data);
              }
            } else {
              cb(sr);
            }
          });
      } else {
        cb(sr);
      }
    },
    selectNation(item) {
      this.form.pid = item.id;
      this.form.pnation = item.title;
    },
    closeNationTag() {
      this.pid = null;
      this.pnation = null;
    },
    queryGiant(queryString, cb) {
      var _this = this;
      var sr = [];
      if (queryString != undefined && queryString.length > 0) {
        axios
          .post(_this.GLOBAL.url_search_giant, {
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
                  name: queryString,
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
