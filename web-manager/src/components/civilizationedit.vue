<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to = "{path : '/civilization'}">文明</el-breadcrumb-item>
          <el-breadcrumb-item>修改</el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
          <el-form ref="form" :model="form" label-width="120px" v-loading = "loading">
                <el-form-item label="标题">
                  <el-input v-model="form.title"></el-input>
                </el-form-item>
                <el-form-item label="相关大洲">
                   <el-autocomplete popper-class="my-autocomplete"
                              :fetch-suggestions="queryContinent"
                                placeholder="搜索大洲"
                                @select="selectContinent">
                                <template slot-scope="props">
                                          <div class="name">
                                                {{ props.item.title }}
                                            </div>
                                </template>
                    </el-autocomplete>
                    <el-tag type="warning"
                      :key="continent.id"
                      v-for="continent in form.continents"
                      closable
                      :disable-transitions="false"
                      @close="closeNationTag(continent)">
                      {{continent.title}}
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

var url_save = "http://192.168.1.112:8081/civilization/edit/save",
  url_init = "http://192.168.1.112:8081/civilization/edit/init",
  url_continent_search = "http://192.168.1.112:8081/continent/search";

export default {
  name: "CivilizationEdit",
  data() {
    return {
      form: {
        id: "",
        title: "",
        continents: []
      },
      loading: true,
      saving: false
    };
  },
  methods: {
    init() {
      console.log(this.form.id);
      var _this = this;
      _this.saving = false;
      axios
        .post(url_init, {
          id: _this.form.id
        })
        .then(function(response) {
          if (response.data.result == 0) {
            var civilization = response.data.data;

            _this.form.title = civilization.title;
            _this.form.continents = civilization.continents;
            _this.loading = false;
          }
        });
    },
    save() {
      var _this = this;

      _this.saving = true;
      axios
        .post(url_save, {
          id: _this.form.id,
          title: _this.form.title,
          continents: _this.form.continents
        })
        .then(function(response) {
          if (response.data.result == 0) {
            _this.$message({
              message: _this.form.title + " 修改成功成功！",
              type: "success"
            });

            _this.init();
          }
          _this.saving = false;
        })
        .catch(function(error) {
          _this.saving = false;
        });
    },
    queryContinent(queryString, cb) {
      var sr = [];
      if (queryString != undefined && queryString.length > 0) {
        axios
          .post(url_continent_search, {
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
    selectContinent(item) {
      var continent = {
        continentid: item.id,
        title: item.title
      };
      this.form.continents.push(continent);
    },
    closeContinentTag(continent) {
      var index = this.form.continents.indexOf(continent);
      this.form.continents.splice(index, 1);
    }
  },
  mounted: function() {
    this.form.id = this.$route.params.id;
    this.init();
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
