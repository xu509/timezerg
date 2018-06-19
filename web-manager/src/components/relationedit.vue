<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to = "{path : '/relation'}">依据</el-breadcrumb-item>
          <el-breadcrumb-item>修改</el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <el-row>
        <p class="paragraph-title">{{form.title}}</p>      
    </el-row>
    <el-row>
        <el-col :md ="12" :xs="16" :sm="16" >
            <el-form ref="form" label-width="120px" v-loading = "form.loading">
                <el-form-item label="标题">
                    <el-input v-model="form.title"></el-input>
                </el-form-item>

                <el-form-item label="类别">
                  <el-select v-model="form.type" placeholder="请选择">
                    <el-option
                      v-for="item in form.options"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
                
                  <el-form-item>
                    <el-button type="primary" @click="save">更新</el-button>
                  </el-form-item>
            </el-form>
        </el-col>
    </el-row>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "relationedit",
  data() {
    return {
      id: "",
      form: {
        loading: true,
        title: "",
        options: [
          {
            value: 0,
            label: "文献"
          },
          {
            value: 1,
            label: "遗迹"
          }
        ]
      },
      saving: false,
      loading: true
    };
  },
  components: {},
  methods: {
    init() {},

    // 基础
    save() {
      var _this = this;

      _this.form.loading = true;

      axios
        .post(_this.GLOBAL.url_relation_edit_save, {
          id: _this.id,
          title: _this.form.title,
          content: _this.form.content,
          type: _this.form.type,
          prelation: _this.form.prelation
        })
        .then(function(response) {
          if (response.data.result == 0) {
            _this.$message({
              message: _this.form.title + " 更新成功！",
              type: "success"
            });
            _this.init();
          }
        });
    }
  },
  mounted: function() {
    this.id = this.$route.params.id;
    this.init();
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
