<template>
  <div class="hello" v-loading = "loading">
      <el-row>
          <el-col :span="22">&nbsp;</el-col>
          <el-col :span="2"><i class="el-icon-setting" @click="clickSettingIcon"></i></el-col>
      </el-row>

      <el-row class="setting-box" v-show="showSetting">
          <el-col :md="3" :xs="9" :sm="9"><p class="common-title">显示的文明：</p></el-col>
          <el-col :md="6" :xs="12" :sm="12"><template v-if="civilizations.length > 0"><el-tag v-for="item in civilizations" :key="item.id" closable class="tag-civilization"  @close="handleCloseTag(item)">{{item.title}}</el-tag></template>
            <template v-if="civilizations.length == 0">
                <p class = "content-warning">无显示的文明</p>
            </template>
          </el-col>
          <el-col :md="2" :xs="1" :sm="1"></el-col>
          <el-col :md="6" :xs="20" :sm="20" class="search-box">
            <el-autocomplete popper-class="my-autocomplete" clearable
                              :fetch-suggestions="queryCivilization"
                                placeholder="增加显示的文明"
                                @select="selectCivilization">
                                <template slot-scope="props">
                                          <div class="name">
                                                {{ props.item.title }}
                                            </div>
                                </template>
            </el-autocomplete>
            <el-button type="primary" icon="el-icon-plus" circle  @click="addCivilizationIndex"></el-button>
          </el-col>

          <el-col :md="6" :xs="20" :sm="20" id="level-box">
              <el-select v-model="level" placeholder="请选择">
                <el-option
                  v-for="item in levels"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
          </el-col>




      </el-row>

      <el-row :gutter="20" class="title">
        <el-col :md="width" :xs="width" :sm="width">
            时间
        </el-col>
        <el-col :md="width" :xs="width" :sm="width" v-for="item in civilizations" :key="item.id">
          {{item.title}}
        </el-col>
      </el-row>


      <el-row>
          <el-col :md="width" :xs="width" :sm="width" class="detail">
              <template v-for="item in times">
                    <el-row :key="item.ddate"><div class="table-item">{{item.ddate}}</div></el-row>
              </template> 
          </el-col>

          <template v-for="item in timelines">
             <el-col :md="width" :xs="width" :sm="width" class="detail" :key="item.cid">
                 <template v-for="(it,index) in item.beans">
                      <el-row :key="index">
                        <div class="table-item">
                          <template v-for="it2 in it.nodes">
                                {{it2.title}}
                          </template>
                        </div>
                      </el-row>
                 </template>
             </el-col>
          </template> 

      </el-row>
        
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "index",
  data() {
    return {
      add_civilization_id: null,
      add_civilization_name: null,
      show_civilizations: [],
      level: 3,
      levels: [
        { value: 1, label: "非常重要" },
        { value: 2, label: "重要" },
        { value: 3, label: "普通" },
        { value: 4, label: "琐事" }
      ],
      datas: [],
      civilizations: [],
      times: [],
      timelines: [],
      loading: false,
      width: 0,
      showSetting: false
    };
  },
  methods: {
    init() {
      this.showSetting = false;

      this.initData();
    },
    initData() {
      var _this = this;
      _this.loading = true;

      //获取显示的文明
      axios
        .post(_this.GLOBAL.url_civilization_index_list, {
          p: 1
        })
        .then(function(response) {
          if (response.data.result == 0) {
            _this.civilizations = response.data.data;

            var cnum = _this.civilizations.length;

            _this.width = 24 / (cnum + 1);
          }
          // console.log(response.data.data[0].id);
        });

      axios
        .post(_this.GLOBAL.url_index, {
          level: _this.level,
          start: 1
        })
        .then(function(response) {
          if (response.data.result == 0) {
            var times = response.data.data.times;
            var timelines = response.data.data.timelines;

            _this.times = times;
            _this.timelines = timelines;
            // console.log(times);

            _this.loading = false;
          }
        })
        .catch(function(error) {
          // console.log(111);
          console.log(error);
        });
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
        this.add_civilization_id = null;
        cb(sr);
      }
    },
    selectCivilization(item) {
      this.add_civilization_id = item.id;
      this.add_civilization_name = item.title;
    },
    //增加文明
    addCivilizationIndex() {
      var _this = this;
      axios
        .post(_this.GLOBAL.url_civilization_index_add, {
          cid: _this.add_civilization_id
        })
        .then(function(response) {
          if (response.data.result == 0) {
            _this.$message({
              message: "增加成功！",
              type: "success"
            }); // console.log(response.data.data);
            _this.init();
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },

    handleCloseTag(item) {
      var _this = this;
      axios
        .post(_this.GLOBAL.url_civilization_index_del, {
          id: item.id
        })
        .then(function(response) {
          if (response.data.result == 0) {
            _this.$message({
              message: "删除成功",
              type: "success"
            }); // console.log(response.data.data);
            _this.init();
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },

    clickSettingIcon() {
      this.showSetting = !this.showSetting;
    }
  },
  watch: {
    level() {
      this.init();
      // console.log(this.level);
    }
  },
  mounted: function() {
    this.init();
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.hello {
  overflow-x: scroll;
}

.hello .title {
  font-size: 15px;
  text-align: center;
  font-weight: 500;
  border-bottom: solid 1px #e6e6e6;
  /* position: relative; */
}

.hello .detail {
  font-size: 12px;
  text-align: center;
}

.row_civ {
  width: 120px;
}

.tag-civilization {
  padding-right: 5px;
}

.search-box {
  padding-left: 15px;
}

.setting-box {
  border: solid 1px #e6e6e6;
  display: flex;
  align-items: center;
  flex-wrap: wrap;
}

.table-item {
  height: 60px;
  /* word-wrap: break-word; */
  border: solid 1px #e6e6e6;
  overflow-y: auto;
  /* line-height: 20px; */
}

#level-box {
  padding-left: 20px;
}
</style>
