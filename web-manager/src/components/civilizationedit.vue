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
      <p class="paragraph-title-main">{{form.title}}</p>

      <el-tabs tab-position="top" style="height: 100%;" v-model="activeTab">
         <el-tab-pane label="基础" name="1">
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
         </el-tab-pane>

         <el-tab-pane label="节点" name="2">
           <el-row>
              <el-col :md="2">
                  <el-button type="primary">同步</el-button>
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
                        <el-button size="small" type="primary" v-if="scope.row.l != 1" @click="updateLevel(scope.row.ncid,1)">设为重要</el-button>
                        <el-button size="small" v-if="scope.row.l == 1" @click="updateLevel(scope.row.ncid,0)">设为普通</el-button>
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
import selectboxlevel from "./plugin/selectboxlevel.vue";

export default {
  name: "CivilizationEdit",
  data() {
    return {
      activeTab: "1",
      form: {
        id: "",
        title: "",
        continents: [],
        pname: null,
        pid: null,
        cover: null,
        covers: []
      },
      nodes: {
        loading: true,
        nodes: [],
        total: null
      },
      // 需要 watch 的
      current_page:1,
      page_size:20,
      level:0,
      searchContentNode:null,

      fileLimit: 1,
      uploadUrl: this.GLOBAL.url_civilization_upload,
      imageDomain: this.GLOBAL.doamin_image,
      loading: true,
      saving: false
    };
  },
  methods: {
    init() {
      if (this.activeTab == "1") {
        this.initBasic();
      } else if (this.activeTab == "2") {
        this.initTabNodes();
      }
    },

    initBasic() {
      var _this = this;
      _this.saving = false;
      axios
        .post(_this.GLOBAL.url_civilization_edit_init, {
          id: _this.form.id
        })
        .then(function(response) {
          if (response.data.result == 0) {
            var civilization = response.data.data;

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
    },

    // tab nodes
    initTabNodes() {
      var _this = this;
      _this.nodes.loading = true;

      var start = (_this.current_page - 1 ) * _this.page_size;
      var size = _this.page_size;

      axios
        .post(_this.GLOBAL.url_civilization_edit_init_nodes, {
          id: _this.form.id,
          level:_this.level,
          title:_this.searchContentNode,
          start:start,
          size: size
        })
        .then(function(response) {
          if (response.data.result == 0) {
            var data = response.data.data;

            _this.form.title = data.civilization.title;
            _this.nodes.nodes = data.nodes;
            _this.nodes.total = data.nodestotal;

            _this.nodes.loading = false;
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    selectLevel(value){
      this.level = value;
    },

    selectRowLevel(value,id){
      console.log("value : " + value);
      console.log("id : " + id);
    },

    updateLevel(id,level){
      console.log("id :" + id);
      var _this = this;
      _this.nodes.loading = true;

      axios
        .post(_this.GLOBAL.url_civilization_edit_nodes_updatelevel, {
          id: id,
          level : level
        })
        .then(function(response) {
          if (response.data.result == 0) {
            var data = response.data.data;
            _this.$message({
                type: "success",
                message: "修改成功!"
            });
            _this.init();
          }else{
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

    },

    handleSizeChange(size){
      this.page_size = size;
    },
    handleCurrentChange(currentPage){
      this.current_page = currentPage;

    }
  },
  mounted: function() {
    this.form.id = this.$route.params.id;
    this.init();
  },
  watch:{
    activeTab(at){
      this.init();
    },
    current_page(cp){
      this.init();
    },
    page_size(ps){
      this.init();
    },
    level(l){
      this.current_page = 1,
      this.page_size = 20,
      this.init();
    },
    searchContentNode(scn){
      this.current_page = 1,
      this.page_size = 20,
      this.init();
    }
  },
  components:{
    selectboxlevel
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
