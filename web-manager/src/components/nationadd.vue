<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to = "{path : '/nation'}">国家</el-breadcrumb-item>
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
            <el-form-item label="时间">
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
                    <el-col :span = "4">
                      <el-input v-model="form.hour" placeholder="时"/>
                    </el-col>
                    <el-col :span = "4">
                      <el-input v-model="form.minute" placeholder="分"/>
                    </el-col>
                    <el-col :span = "4">
                      <el-input v-model="form.second" placeholder="秒"/>
                    </el-col>
                  </el-row>
                </el-form-item>
                <el-form-item label="显示的时间">
                  <el-input v-model="form.ddate"></el-input>
                </el-form-item>
                <el-form-item label="是否是公元后">
                  <el-radio-group v-model="form.AD">
                    <el-radio label="0">BC</el-radio>
                    <el-radio label="1">AD</el-radio>
                  </el-radio-group>
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

var url_save = "http://192.168.1.112:8081/nation/save";

export default {
  name: "NationAdd",
  data() {
    return {
      form: {
        title: "",
        ddate: "",
        year: null,
        month: null,
        day: null,
        hour: null,
        minute: null,
        second: null,
        AD: "1"
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
          ddate: _this.form.ddate,
          year: _this.form.year,
          month: _this.form.month,
          day: _this.form.day,
          minute: _this.form.minute,
          second: _this.form.second,
          AD: _this.form.AD
        })
        .then(function(response) {
          if (response.data.result == 0) {
            _this.$message({
              message: _this.form.title + " 添加成功！",
              type: "success"
            });

            _this.form.title = null;
            _this.form.ddate = null;
            _this.form.year = null;
            _this.form.month = null;
            _this.form.day = null;
            _this.form.minute = null;
            _this.form.second = null;
            _this.form.AD = "1";
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
