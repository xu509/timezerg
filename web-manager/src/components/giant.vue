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
            <inputboxgiant @selectGiant = "selectGiant"></inputboxgiant>
            <el-tag type="success" v-if="name != null" closable  @close="closeSearchName()">
                  {{sname}}
            </el-tag>
       </el-col>
    </el-row>

    <el-row v-loading = "loading_table">
      <el-col :span="24" >
        <el-table :data = "datas" >
            <el-table-column type="expand" >
                 <template slot-scope="props">
                   <p class="paragraph-content-small" v-if="props.row.cgiants != null">别名：<template v-for="item in props.row.cgiants">{{item.name}}&nbsp;</template></p>
                   <p class="paragraph-content-small">{{props.row.content}}</p>
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
import inputboxgiant from "./plugin/inputboxgiant.vue";

export default {
  name: "Node",
  data() {
    return {
      loading_table: true,
      sname: null,
      name: null,
      datas: [],
      page_size: 10, //page大小
      current_page: 1, // 当前游码
      total: 0 // 总数
    };
  },
  components: {
    inputboxgiant
  },
  methods: {
    init() {
      this.initTable();
    },
    initTable() {
      var _this = this;
      _this.loading_table = true;
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
            _this.loading_table = false;
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
      this.sname = item.sname;
      // console.log("giant id: " + item.id);
      // console.log("giant name: " + item.name);
      // console.log("giant sname: " + item.sname);
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
