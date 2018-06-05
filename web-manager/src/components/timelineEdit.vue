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
                <el-form-item label="父文明">
                   <el-autocomplete popper-class="my-autocomplete" clearable v-model="form.pname"
                              :fetch-suggestions="queryCivilization"
                                placeholder="搜索文明"
                                @select="selectCivilization">
                                <template slot-scope="props">
                                          <div class="name">
                                                {{ props.item.title }}
                                            </div>
                                </template>
                    </el-autocomplete>
                </el-form-item>
                <el-form-item label = "封面(360 × 100)">
                  <el-upload
                    class="upload-demo" :limit= "fileLimit"
                    :action="uploadUrl" :on-preview="handlePreview"
                    :on-remove="handleRemove"
                    :file-list="form.covers" :on-success="uploadSuccess"
                    list-type="picture">
                    <img v-if="form.cover" :src="imageDomain + form.cover" class="cover">
                    <el-button size="small" type="primary" v-if="form.cover == null">点击上传</el-button>
                  </el-upload>

                  <el-button size="small" type="danger" @click="deleteCover" v-if="form.cover != null">删除</el-button>

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
  name: "CivilizationEdit",
  data() {
    return {
      form: {
        id: "",
        title: "",
        continents: [],
        pname: null,
        pid: null,
        cover: null,
        covers: []
      },
      fileLimit: 1,
      uploadUrl: this.GLOBAL.url_civilization_upload,
      imageDomain: this.GLOBAL.doamin_image,
      loading: true,
      saving: false
    };
  },
  methods: {
    init() {
      var _this = this;
      _this.saving = false;
      axios
        .post(_this.GLOBAL.url_civilization_edit_init, {
          id: _this.form.id
        })
        .then(function(response) {
          console.log(response);

          if (response.data.result == 0) {
            var civilization = response.data.data;

            console.log(civilization);

            _this.form.title = civilization.title;
            _this.form.continents = civilization.continents;
            _this.form.cover = civilization.cover;
            _this.form.pname = civilization.pname;
            _this.form.pid = civilization.pid;

            _this.loading = false;
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    save() {
      var _this = this;

      _this.saving = true;
      axios
        .post(_this.GLOBAL.url_civilization_edit_save, {
          id: _this.form.id,
          title: _this.form.title,
          continents: _this.form.continents,
          pname: _this.form.pname,
          pid: _this.form.pid,
          cover: _this.form.cover
        })
        .then(function(response) {
          if (response.data.result == 0) {
            _this.$message({
              message: _this.form.title + " 修改成功！",
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
      var _this = this;
      var sr = [];
      if (queryString != undefined && queryString.length > 0) {
        axios
          .post(_this.GLOBAL.url_search_continent, {
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

    // 搜索文明
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
      this.form.pid = item.id;
      this.form.pname = item.title;
    },

    closeContinentTag(continent) {
      var index = this.form.continents.indexOf(continent);
      this.form.continents.splice(index, 1);
    },
    handleRemove(file, fileList) {
      var _this = this;
      _this.form.cover = null;
      _this.form.covers = null;
    },
    handlePreview(file) {
      console.log("preview file");
      console.log(file);
    },
    uploadSuccess(response, file, fileList) {
      var _this = this;
      var data = response.data.path;
      _this.form.cover = data;
    },
    deleteCover() {
      var _this = this;
      _this.form.cover = null;
      _this.form.covers = null;
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
.cover {
  width: 600px;
  height: 200px;
}
</style>
