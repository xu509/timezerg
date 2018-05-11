<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to = "{path : '/dynasty'}">朝代</el-breadcrumb-item>
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
                <el-form-item label="开始时间">
                  <el-row :gutter="20">
                    <el-col :span = "4">
                      <el-input v-model="form.year" placeholder="年"/>
                    </el-col>
                    <el-col :span = "3">
                      <el-input v-model="form.month" placeholder="月"/>
                    </el-col>
                    <el-col :span = "3">
                      <el-input v-model="form.day" placeholder="日"/>
                    </el-col>
                  </el-row>
                </el-form-item>

                <el-form-item label="结束时间">
                  <el-row :gutter="20">
                    <el-col :span = "4">
                      <el-input v-model="form.dyear" placeholder="年"/>
                    </el-col>
                    <el-col :span = "3">
                      <el-input v-model="form.dmonth" placeholder="月"/>
                    </el-col>
                    <el-col :span = "3">
                      <el-input v-model="form.dday" placeholder="日"/>
                    </el-col>
                  </el-row>
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

var url_save = "http://192.168.1.112:8081/dynasty/save";

export default {
  name: "DynastyAdd",
  data() {
    return {
      form: {
        title: "",
        year: null,
        month: null,
        day: null,
        dyear: null,
        dmonth: null,
        dday: null
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
          title: _this.form.title,
          year: _this.form.year,
          month: _this.form.month,
          day: _this.form.day,
          dyear: _this.form.dyear,
          dmonth: _this.form.dmonth,
          dday: _this.form.dday
        })
        .then(function(response) {
          if (response.data.result == 0) {
            _this.$message({
              message: _this.form.title + " 添加成功！",
              type: "success"
            });

            _this.form.title = null;
            _this.form.year = null;
            _this.form.month = null;
            _this.form.day = null;

            _this.form.dyear = null;
            _this.form.dmonth = null;
            _this.form.dday = null;
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
