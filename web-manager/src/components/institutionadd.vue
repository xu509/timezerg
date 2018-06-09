<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to = "{path : '/institution'}">制度</el-breadcrumb-item>
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
                <el-form-item label="描述">
                  <el-input v-model="form.content"></el-input>
                </el-form-item>
                <el-form-item label="类别">
                    <el-select v-model="form.type" placeholder="类别">
                        <el-option v-for="item in options" :key = "item.value" :label = "item.label" :value = "item.value"></el-option>
                    </el-select>
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

export default {
  name: "InstitutionAdd",
  data() {
    return {
      form: {
        title: "",
        content: "",
        type: 1
      },
      options: [
        {
          value: 1,
          label: "经济"
        },
        {
          value: 2,
          label: "文化"
        },
        {
          value: 3,
          label: "政治"
        },
        {
          value: 4,
          label: "社会"
        }
      ],
      saving: false
    };
  },
  methods: {
    save() {
      var _this = this;

      _this.saving = true;
      axios
        .post(_this.GLOBAL.url_institution_add, {
          title: _this.form.title,
          content: _this.form.content,
          type: _this.form.type
        })
        .then(function(response) {
          if (response.data.result == 0) {
            _this.$message({
              message: _this.form.title + " 添加成功！",
              type: "success"
            });

            _this.form.title = null;
            _this.form.content = null;
            _this.form.type = 1;
          } else {
            _this.$message.error(response.data.msg);
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
