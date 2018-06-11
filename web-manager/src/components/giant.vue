<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>人物</el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>

    <el-row>
       <el-button @click="add">+</el-button>
    </el-row>

    <el-row>
       <el-col :span="8">
            <InputboxGiant @selectGiant = "selectGiant"></InputboxGiant>
            <el-tag type="success" v-if="name != null" closable  @close="closeSearchName()">
                  {{name}}
            </el-tag>
       </el-col>
    </el-row>

    <el-row>
      <el-col :span="24">
        <el-table :data = "datas">
            <el-table-column type="expand" >
                 <template slot-scope="props">
                      {{props.row.content}}
                 </template>
            </el-table-column>
            <el-table-column prop = "name" label = "姓名"></el-table-column>
            <el-table-column label = "国">
               <template slot-scope="scope">
                    <el-tag v-for="item in scope.row.nations" :key="item.id">
                      {{item.title}}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column label = "TAG">
               <template slot-scope="scope">
                    <el-tag type="info" v-for="item in scope.row.tags" :key="item.id">
                      {{item.title}}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作">
                <template slot-scope="scope">
                    <el-button type="text" size="small" @click = "go('giantEdit', scope.row.id )">编辑</el-button>
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
import InputboxGiant from "./plugin/InputboxGiant.vue";

export default {
  name: "Node",
  data() {
    return {
      name: null,
      datas: [],
      page_size: 10, //page大小
      current_page: 1, // 当前游码
      total: 0 // 总数
    };
  },
  components: {
    InputboxGiant
  },
  methods: {
    init() {
      this.initTable();
    },
    initTable() {
      var _this = this;
      var start = (_this.current_page - 1) * _this.page_size;

      axios
        .post(_this.GLOBAL.url_giant_list, {
          name: _this.name,
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
      this.$router.push("giant/add");
    },
    go(name, params) {
      const id = params;
      this.$router.push({
        path: "/giant/edit/" + id
      });
    },
    handleSizeChange(size) {
      this.page_size = size;
    },
    handleCurrentChange(currentPage) {
      this.current_page = currentPage;
    },
    selectGiant(item) {
      this.name = item.name;
    },
    closeSearchName() {
      this.name = null;
    }
  },
  watch: {
    page_size: function(ps) {
      this.initTable();
    },
    current_page: function(cp) {
      this.initTable();
    },
    name: function(name) {
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
