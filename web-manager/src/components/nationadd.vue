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
                    <el-tag type="warning"
                      :key="nation.id"
                      v-for="nation in form.nations"
                      closable
                      :disable-transitions="false"
                      @close="closeNationTag(nation)">
                      {{nation.title}}
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

var url_save = "http://192.168.1.112:8081/nation/save";

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
        AD: "1",
        pnation: null
      },
      saving: false
    };
  },
  methods: {
    save() {
      var _this = this;

      _this.saving = true;
      axios
        .post(url_save, {
          title: _this.form.title,
          ddate: _this.form.ddate,
          year: _this.form.year,
          month: _this.form.month,
          day: _this.form.day,
          AD: _this.form.AD
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
            _this.form.AD = "1";
          }
          _this.saving = false;
        })
        .catch(function(error) {
          _this.saving = false;
        });
    },
    queryNation(queryString, cb) {
      var sr = [];
      if (queryString != undefined && queryString.length > 0) {
        axios
          .post(url_search_nation, {
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
      this.pid = item.id;
      this.pnation = item.title;
    },
    closeNationTag(nation) {
      this.pid = null;
      this.pnation = null;
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
