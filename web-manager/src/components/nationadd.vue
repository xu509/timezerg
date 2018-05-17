<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to = "{path : '/nation'}">国家</el-breadcrumb-item>
          <el-breadcrumb-item>增加</el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
          <el-form ref="form" :model="form" label-width="120px">
                <el-form-item label="标题">
                  <el-input v-model="form.title"></el-input>
                </el-form-item>
                <el-form-item label="启">
                  <el-row :gutter="20">
                    <el-col :span = "4">
                      <el-input v-model="form.year" placeholder="年"/>
                    </el-col>
                    <el-col :span = "3">
                      <el-input v-model="form.month" placeholder="月"/>
                    </el-col>
                    <el-col :span = "3">
                      <el-input v-model="form.day" placeholder="日"/>
                    </el-col>
                     <el-col :span= "3">
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
                    <el-col :span = "4">
                      <el-input v-model="form.eyear" placeholder="年"/>
                    </el-col>
                    <el-col :span = "3">
                      <el-input v-model="form.emonth" placeholder="月"/>
                    </el-col>
                    <el-col :span = "3">
                      <el-input v-model="form.eday" placeholder="日"/>
                    </el-col>
                    <el-col :span= "3">
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
  name: "NationAdd",
  data() {
    return {
      form: {
        title: "",
        ddate: "",
        pid: "",
        year: null,
        month: null,
        day: null,
        eyear: null,
        emonth: null,
        eday: null,
        AD: 1,
        eAD: 1,
        pnation: null
      },
      switchc: {
        activev: 1,
        inactivev: 0
      },
      saving: false
    };
  },
  methods: {
    save() {
      var _this = this;

      _this.saving = true;
      axios
        .post(_this.GLOBAL.url_nation_add, {
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
          pid: _this.form.pid
        })
        .then(function(response) {
          if (response.data.result == 0) {
            _this.$message({
              message: _this.form.title + " 添加成功！",
              type: "success"
            });

            _this.form.title = null;
            _this.form.ddate = null;
            _this.form.year = null;
            _this.form.month = null;
            _this.form.day = null;
            _this.form.eyear = null;
            _this.form.emonth = null;
            _this.form.eday = null;
            _this.form.AD = 1;
            _this.form.eAD = 1;
            _this.form.pid = null;
            _this.form.pnation = null;
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
      this.form.pid = item.id;
      this.form.pnation = item.title;
    },
    closeNationTag(nation) {
      this.form.pid = null;
      this.form.pnation = null;
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
