<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to = "{path : '/node'}">节点</el-breadcrumb-item>
          <el-breadcrumb-item>增加</el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <el-row>
      <el-col :sm="24" xs="24" :md="14" :xl="14" >
          <el-form ref="form" :model="form" label-width="120px">
                <el-form-item label="标题">
                  <el-input v-model="form.title"></el-input>
                </el-form-item>
                <el-form-item label="时间">
                  <el-row :gutter="20">
                    <el-col :md = "4" :xl="4" :sm="12" xs="12">
                      <el-input v-model="form.year" placeholder="年"/>
                    </el-col>
                    <el-col :md = "3" :xl="3" :sm="12" xs="12">
                      <el-input v-model="form.month" placeholder="月"/>
                    </el-col>
                    <el-col :md = "3" :xl="3" :sm="12" xs="12">
                      <el-input v-model="form.day" placeholder="日"/>
                    </el-col>
                    <el-col :md = "4" :xl="4" :sm="12" xs="12">
                      <el-input v-model="form.hour" placeholder="时"/>
                    </el-col>
                    <el-col :md = "4" :xl="4" :sm="12" xs="12">
                      <el-input v-model="form.minute" placeholder="分"/>
                    </el-col>
                    <el-col :md = "4" :xl="4" :sm="12" xs="12">
                      <el-input v-model="form.second" placeholder="秒"/>
                    </el-col>
                  </el-row>
                </el-form-item>
                <el-form-item label="显示的时间">
                  <el-input v-model="form.ddate"></el-input>
                </el-form-item>
                <el-form-item label="是否是公元后">
                  <el-radio-group v-model="form.AD">
                    <el-radio label="0">BC</el-radio>
                    <el-radio label="1">AD</el-radio>
                  </el-radio-group>
                </el-form-item>
                 <el-form-item label="重要度">
                    <el-select v-model="form.level" placeholder="重要度">
                        <el-option v-for="item in options" :key = "item.value" :label = "item.label" :value = "item.value"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="相关国家">
                   <el-autocomplete popper-class="my-autocomplete"
                              :fetch-suggestions="queryNation" v-model="nation"
                                placeholder="搜索国家，比如意大利"
                                @select="selectNation">
                                <template slot-scope="props">
                                          <div class="name">
                                                {{ props.item.title }}
                                            </div>
                                </template>
                    </el-autocomplete>
                    <el-tag
                      :key="nation.id"
                      v-for="nation in form.nations"
                      closable
                      :disable-transitions="false"
                      @close="closeNationTag(nation)">
                      {{nation.title}}
                    </el-tag>
                </el-form-item>

                <el-form-item label="相关文明">
                   <el-autocomplete popper-class="my-autocomplete"
                              :fetch-suggestions="queryCivilization"
                                placeholder="搜索文明" v-model="civilization"
                                @select="selectCivilization">
                                <template slot-scope="props">
                                          <div class="name">
                                                {{ props.item.title }}
                                            </div>
                                </template>
                    </el-autocomplete>
                    <el-tag type="warning"
                      :key="civilization.id" 
                      v-for="civilization in form.civilizations"
                      closable
                      :disable-transitions="false"
                      @close="closeNationTag(civilization)">
                      {{civilization.title}}
                    </el-tag>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="save" :loading="saving">创建</el-button>
                </el-form-item>
          </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "NodeAdd",
  data() {
    return {
      form: {
        title: "",
        ddate: "",
        year: null,
        month: null,
        day: null,
        hour: null,
        minute: null,
        second: null,
        AD: "1",
        level: 0,
        nation: null,
        nations: [],
        civilization: null,
        civilizations: []
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
      saving: false
    };
  },
  methods: {
    init() {
      var _this = this;
      _this.form.title = null;
      _this.form.ddate = null;
      _this.form.year = null;
      _this.form.month = null;
      _this.form.day = null;
      _this.form.hour = null;
      _this.form.minute = null;
      _this.form.second = null;
      _this.form.AD = "1";
      _this.form.level = 0;
      _this.form.nations = [];
      _this.form.nation = null;
      _this.form.civilizations = [];
      _this.form.civilization = null;
      _this.saving = false;
    },
    save() {
      var _this = this;

      _this.saving = true;
      axios
        .post(_this.GLOBAL.url_node_add, {
          title: _this.form.title,
          ddate: _this.form.ddate,
          year: _this.form.year,
          month: _this.form.month,
          day: _this.form.day,
          minute: _this.form.minute,
          second: _this.form.second,
          AD: _this.form.AD,
          level: _this.form.level,
          nations: _this.form.nations,
          civilizations: _this.form.civilizations
        })
        .then(function(response) {
          if (response.data.result == 0) {
            _this.$message({
              message: _this.form.title + " 添加成功！",
              type: "success"
            });

            _this.init();
          } else {
            _this.$message.error(response.data.msg);
          }
          _this.saving = false;
        })
        .catch(function(error) {
          _this.saving = false;
        });
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
      var nation = {
        nationid: item.id,
        title: item.title
      };
      this.form.nations.push(nation);
    },
    closeNationTag(nation) {
      var index = this.form.nations.indexOf(nation);
      this.form.nations.splice(index, 1);
    },
    queryCivilization(queryString, cb) {
      var _this = this;
      var sr = [];
      if (queryString != undefined && queryString.length > 0) {
        axios
          .post(_this.GLOBAL.url_search_civilization, {
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
    selectCivilization(item) {
      var civilization = {
        cid: item.id,
        title: item.title
      };
      this.form.civilizations.push(civilization);
    },
    closeNationTag(civilization) {
      var index = this.form.civilizations.indexOf(civilization);
      this.form.civilizations.splice(index, 1);
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
