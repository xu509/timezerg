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
      page_size: 10, //page大小
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
          size: _this.page_size
        })
        .then(function(response) {
          if (response.data.result == 0) {
            _this.datas = response.data.data.data;
            _this.total = response.data.data.total;
          }
        })
        .catch(function(error) {});
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
    }
  },
  mounted: function() {
    this.init();
  }
};
</script>

<style scoped>
</style>
