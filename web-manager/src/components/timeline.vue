<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>时间线</el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>

    <el-row>
       <el-button @click="add">+</el-button>
    </el-row>

    <el-row>
      <el-col :span="24">
        <el-table :data = "datas">
            <el-table-column label = "#" width = "300">
              <template slot-scope="scope">
                  <img :src="scope.row.cover" class="cover" v-if="scope.row.cover != null"/>
              </template>
            </el-table-column>
            <el-table-column prop = "title" label = "标题" width = "200"></el-table-column>
            <el-table-column label = "大洲">
                <template slot-scope="scrope">
                    <template v-for="item in scrope.row.continents">
                        {{item.title}} &nbsp;
                    </template>
                </template>
            </el-table-column>
            <el-table-column fixed="right" width="200" label="操作">
                <template slot-scope="scope">
                    <el-button type="text" size="small" @click = "go('civilizationEdit', scope.row.id )">编辑</el-button>
                    <el-button type="text" size="small" @click = "first(scope.row.id,scope.$index)" v-if="scope.$index != 0">↑</el-button>
                    <el-button type="text" size="small" @click = "down(scope.row.id,scope.$index)">↓</el-button>
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
      current_page: 1, // 当前游码
      total: 0, // 总数
      imageDomain: this.GLOBAL.doamin_image
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
        .post(_this.GLOBAL.url_civilization_list, {
          start: start,
          size: _this.page_size
        })
        .then(function(response) {
          if (response.data.result == 0) {
            _this.datas = response.data.data.data;

            _this.total = response.data.data.total;
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    first(id, index) {
      var _this = this;
      axios
        .post(_this.GLOBAL.url_civilization_first, {
          id: id
        })
        .then(function(response) {
          if (response.data.result == 0) {
            //调换位置
            _this.$message({
              message: "调序成功",
              type: "success"
            });
            _this.initTable();
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    down(id, index) {
      var _this = this;
      axios
        .post(_this.GLOBAL.url_civilization_down, {
          id: id,
          tid: _this.datas[index + 1].id
        })
        .then(function(response) {
          if (response.data.result == 0) {
            //调换位置
            _this.$message({
              message: "调序成功",
              type: "success"
            });
            _this.initTable();
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    add() {
      this.$router.push("timeline/add");
    },
    go(name, params) {
      const id = params;
      this.$router.push({
        path: "/civilization/edit/" + id
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
    }
  },
  mounted: function() {
    this.init();
  }
};
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.cover {
  width: 200px;
  height: 40px;
  margin: 0 auto 0 auto;
}
</style>
