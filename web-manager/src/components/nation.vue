<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>国家</el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>

    <el-row>
       <el-button @click="add">+</el-button>
    </el-row>

    <el-row>
      <el-radio-group v-model="tt" size="small">
        <el-radio-button :label="tabletypes.default">默认</el-radio-button>
        <el-radio-button :label="tabletypes.top" >顶级</el-radio-button>
        <el-radio-button :label="tabletypes.uncheck">待完善</el-radio-button>
      </el-radio-group>
    </el-row>

    <el-row>
      <el-col :span="24">
        <el-table :data = "datas">
            <el-table-column fixed prop = "ddate" label = "日期"  ></el-table-column>
            <el-table-column prop = "title" label = "标题"></el-table-column>
             <el-table-column fixed="right" label="操作">
                <template slot-scope="scope">
                    <el-button type="text" size="small" @click = "go('nationEdit', scope.row.id )">编辑</el-button>
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
  name: "nation",
  data() {
    return {
      tt: 1,
      tabletypes: {
        default: 1,
        top: 2,
        uncheck: 3
      },
      datas: [],
      page_size: 10, //page大小
      current_page: 1, // 当前游码
      total: 0 // 总数
    };
  },
  methods: {
    init() {
      this.initTable();
    },
    initTable() {
      var _this = this;
      var start = (_this.current_page - 1) * _this.page_size;

      axios
        .post(_this.GLOBAL.url_nation_list, {
          type: _this.tt,
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
      this.$router.push("nation/add");
    },
    go(name, params) {
      const id = params;
      this.$router.push({
        path: "/nation/edit/" + id
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
      this.initTable();
    },
    current_page: function(cp) {
      this.initTable();
    },
    tt: function(tt) {
      this.initTable();
    }
  },
  mounted: function() {
    this.init();
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
