<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to = "{path : '/reference'}">依据</el-breadcrumb-item>
          <el-breadcrumb-item>增加</el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <el-row>
      <el-col :md ="10" :xs="24" :sm="24" >
          <el-form ref="form" :model="form" label-width="120px">
                <el-form-item label="标题">
                  <el-input v-model="form.title"></el-input>
                </el-form-item>
                <el-form-item label="详细">
                  <el-input
                    type="textarea"
                    :rows="4"
                    placeholder="请输入内容"
                    v-model="form.content">
                  </el-input>
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
                
                <el-form-item label="隶属于">
                  <inputboxreference @selectReference="selectReference"></inputboxreference>
                  <el-tag type="warning" class="tag-margin" v-if="form.preference != null"
                      closable
                      :disable-transitions="false"
                      @close="closePReferenceTag(form.preference)">
                      {{form.preference.title}}
                    </el-tag>
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
import inputboxreference from "./plugin/inputboxreference.vue";

export default {
  name: "referenceAdd",
  data() {
    return {
      form: {
        title: "",
        content: "",
        type: 0,
        preference: null,
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
      switchc: {
        activev: 1,
        inactivev: 0
      },
      saving: false
    };
  },
  components: {
    inputboxreference
  },
  methods: {
    init() {
      var _this = this;
      _this.form.title = null;
      _this.form.content = null;
      _this.form.type = 0;
      _this.form.preference = null;
    },
    save() {
      var _this = this;

      _this.saving = true;
      axios
        .post(_this.GLOBAL.url_reference_add, {
          title: _this.form.title,
          content: _this.form.content,
          preference: _this.form.preference,
          type: _this.form.type
        })
        .then(function(response) {
          if (response.data.result == 0) {
            _this.$message({
              message: _this.form.title + " 添加成功！",
              type: "success"
            });
            _this.init();
          } else {
            _this.$notify.error({
              title: response.data.msg,
              message: response.data.data,
              duration: 0
            });
          }
          _this.saving = false;
        })
        .catch(function(error) {
          _this.saving = false;
        });
    },
    selectReference(item) {
      this.form.preference = item;
    },
    closePReferenceTag() {
      this.form.preference = null;
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
