<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>节点</el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>

    <el-row>
       <el-button @click="add">+</el-button>
    </el-row>


    <el-row>
      <el-col :span="6">
          <el-autocomplete popper-class="my-autocomplete"
                              :fetch-suggestions="queryCivilization"
                                placeholder="搜索文明"
                                @select="selectCivilization">
                                <template slot-scope="props">
                                          <div class="name">
                                                {{ props.item.title }}
                                            </div>
                                </template>
          </el-autocomplete>
      </el-col>           
      <el-col :span = "6">
            <el-tag type="warning"
                      :key="civilization.id"
                      v-for="civilization in civilizations"
                      closable
                      :disable-transitions="false"
                      @close="closeCivilizationTag(civilization)">
                      {{civilization.title}}
            </el-tag>
      </el-col>
    </el-row>


    <el-row>
      <el-col :span="24">
        <el-table :data = "datas">
            <el-table-column fixed prop = "ddate" label = "日期" width="160"></el-table-column>
            <el-table-column prop = "title" label = "标题"></el-table-column>
            <el-table-column label = "时代">
                <template slot-scope="scrope">
                    <template v-for="item in scrope.row.nations">
                        {{item.title}} &nbsp;
                    </template>
                </template>
            </el-table-column>
            <el-table-column fixed="right" width="200" label="操作">
                <template slot-scope="scope">
                    <el-button type="text" size="small" @click = "go('nodeEdit', scope.row.id )">编辑</el-button>
                </template>
            </el-table-column>
        </el-table>
      </el-col>
    </el-row>

    <el-row>
      
    </el-row>

    <el-row>
      <el-col :span="24">
        <el-pagination @size-change="handleSizeChange"
                      @current-change="handleCurrentChange"
                      :current-page="current_page"
                      :page-sizes="[10, 20, 50, 100]"
                      :page-size="page_size"
                      layout="sizes,total,next, pager,prev,jumper "
                      :total="total">
        </el-pagination>
      </el-col>
    </el-row>

  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Node",
  data() {
    return {
      datas: [],
      civilizations: [],
      cids: [],
      page_size: 20, //page大小
      current_page: 2, // 当前游码
      total: 0 // 总数
    };
  },
  methods: {
    init() {
      this.page_size = this.GLOBAL.table_size_node;
      this.current_page = this.GLOBAL.table_page_node;
      this.initTable();
    },
    initTable() {
      var _this = this;
      var start = (_this.current_page - 1) * _this.page_size;

      axios
        .post(_this.GLOBAL.url_node_list, {
          start: start,
          size: _this.page_size,
          cids: _this.cids
        })
        .then(function(response) {
          if (response.data.result == 0) {
            _this.datas = response.data.data.data;
            _this.total = response.data.data.total;
          }
        })
        .catch(function(error) {
          console.log(111);
          console.log(error);
        });
    },
    add() {
      this.$router.push("node/add");
    },
    go(name, params) {
      const id = params;
      this.$router.push({
        path: "/node/edit/" + id
      });
    },
    handleSizeChange(size) {
      this.page_size = size;
    },
    handleCurrentChange(currentPage) {
      this.current_page = currentPage;
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
      var civ = {
        id: item.id,
        title: item.title
      };

      var f = false;

      for (var i = 0; i < this.civilizations.length; i++) {
        if (this.civilizations[i].id == item.id) {
          f = true;
          break;
        }
      }

      if (f) {
        this.$message({
          message: "已存在",
          type: "warning"
        });
      } else {
        this.civilizations.push(civ);
        this.cids.push(item.id);
      }
    },

    closeCivilizationTag(civilization) {
      var index = this.civilizations.indexOf(civilization);
      this.civilizations.splice(index, 1);

      var index2 = this.cids.indexOf(civilization.id);
      this.cids.splice(index2, 1);
    }
  },
  watch: {
    page_size: function(ps) {
      this.GLOBAL.table_size_node = ps;
      this.GLOBAL.table_page_node = 1;
      this.initTable();
    },
    current_page: function(cp) {
      this.GLOBAL.table_page_node = cp;
      this.initTable();
    },
    cids: function(c) {
      console.log("1111");
      this.initTable();
    }
  },
  mounted: function() {
    this.init();
  }
};
</script>

<style scoped>
</style>
