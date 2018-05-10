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
            <el-table-column fixed prop = "date" label = "日期" width="120"></el-table-column>
            <el-table-column prop = "title" label = "标题" width="120"></el-table-column>
        </el-table>
      </el-col>
    </el-row>
  </div>
</template>

<script>
var url_table = "localhost";

export default {
  name: "HelloWorld",
  data() {
    return {
      msg: "Welcome to Your Vue.js App"
    };
  },
  methods: {
    add() {
      this.$router.push("node/add");
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
