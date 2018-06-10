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
      <el-col :md ="18" :xs="24" :sm="24" >
          <el-form ref="form" :model="form" label-width="120px" v-loading = "loading">
                <el-form-item label="姓名">
                  <el-input v-model="form.name"></el-input>
                </el-form-item>

                <el-form-item label="描述">
                  <el-input
                      type="textarea"
                      :rows="2"
                      placeholder="描述"
                      v-model="content">
                  </el-input>
                </el-form-item>

                <el-form-item label="特征">
                   <el-autocomplete popper-class="my-autocomplete"
                              :fetch-suggestions="queryTag"
                                placeholder="搜索"
                                @select="selectTag">
                                <template slot-scope="props">
                                    <div class="name">
                                          <i class="el-icon-circle-plus-outline" v-if="props.item.isnew"></i>&nbsp;&nbsp;{{ props.item.title}}
                                    </div>
                                </template>
                    </el-autocomplete>
                    <el-tag type="warning" v-if = "form.tags != null" v-for="(item,index) in form.tags"
                      :key="item.id"
                      closable
                      :disable-transitions="false"
                      @close="closeTag(index)">
                      {{item.title}}
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

export default {
  name: "GiantEdit",
  data() {
    return {
      id: "",
      form: {
        name: "",
        content : "",
        ddate: "",
        tags:[]
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
        .post(_this.GLOBAL.url_giant_edit_save, {
          id: _this.id,
          name: _this.form.name,
          tags: _this.form.tags
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
    init() {
      var _this = this;
      _this.saving = false;

      axios
        .post(_this.GLOBAL.url_giant_edit_init, {
          id: _this.id
        })
        .then(function(response) {
          if (response.data.result == 0) {
            var giant = response.data.data;

            _this.form.name = giant.name;
            _this.form.content = giant.content;
            _this.form.tags = giant.tags;

            // console.log(nation.giant);

            _this.loading = false;
          } else {
            this.$message.error(response.data.data);
          }
        })
        .catch(function(error) {});
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
      this.form.tags.splice(index,1);
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
