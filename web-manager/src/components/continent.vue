<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to = "{path : '/continent'}">洲</el-breadcrumb-item>
          <el-breadcrumb-item>增加</el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
          <el-form ref="form" :model="form" label-width="120px">
                <el-form-item label="标题">
                  <el-input v-model="form.title"></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="save" :loading="saving">创建</el-button>
                </el-form-item>
          </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import axios from "axios";

var url_save = "http://192.168.1.112:8081/continent/save";

export default {
  name: "ContinentAdd",
  data() {
    return {
      form: {
        title: ""
      },
      saving: false
    };
  },
  methods: {
    save() {
      var _this = this;

      _this.saving = true;
      axios
        .post(url_save, {
          title: _this.form.title
        })
        .then(function(response) {
          if (response.data.result == 0) {
            _this.$message({
              message: _this.form.title + " 添加成功！",
              type: "success"
            });

            _this.form.title = null;
          }
          _this.saving = false;
        })
        .catch(function(error) {
          _this.saving = false;
        });
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
