<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to = "{path : '/giant'}">人物</el-breadcrumb-item>
          <el-breadcrumb-item>增加</el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <el-row>
      <el-col :md ="18" :xs="24" :sm="24" >
          <el-form ref="form" :model="form" label-width="120px" v-loading = "loading">
                <el-form-item label="姓名">
                  <el-input v-model="form.name"></el-input>
                </el-form-item>

                <el-form-item label="描述">
                  <el-input
                      type="textarea"
                      :rows="2"
                      placeholder="描述"
                      v-model="form.content">
                  </el-input>
                </el-form-item>

                <el-form-item label="特征">
                    <inputboxtag @selectTag = "selectTag"></inputboxtag>
                    <el-tag type="warning" v-if = "form.tags != null" v-for="(item,index) in form.tags"
                      :key="item.id"
                      closable
                      :disable-transitions="false"
                      @close="closeTag(index)">
                      {{item.title}}
                    </el-tag>
                </el-form-item>

                <el-form-item label="所属国家">
                  <inputboxnation @selectNation = "selectNation"></inputboxnation>
                  <el-tag type="warning" v-if = "form.nations != null" v-for="(item,index) in form.nations"
                      :key="item.id"
                      closable
                      :disable-transitions="false"
                      @close="closeNation(index)">
                      {{item.title}}
                    </el-tag>
                </el-form-item>


                <el-form-item>
                  <el-button type="primary" @click="save" :loading="saving">增加</el-button>
                </el-form-item>
          </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import axios from "axios";
import inputboxnation from "./plugin/inputboxnation.vue";
import inputboxtag from "./plugin/inputboxtag.vue";

export default {
  name: "GiantAdd",
  data() {
    return {
      form: {
        name: "",
        content: "",
        tags: [],
        nations: []
      },
      saving: false,
      loading: true
    };
  },
  components: {
    inputboxnation,
    inputboxtag
  },
  methods: {
    save() {
      var _this = this;

      _this.saving = true;
      _this.loading = true;

      axios
        .post(_this.GLOBAL.url_giant_add, {
          id: _this.id,
          content: _this.form.content,
          name: _this.form.name,
          tags: _this.form.tags,
          nations: _this.form.nations
        })
        .then(function(response) {
          console.log(response);
          if (response.data.result == 0) {
            _this.$message({
              message: _this.form.name + " 添加成功！",
              type: "success"
            });
            _this.init();
          } else {
            _this.$message.error(response.data.msg);
            _this.init();
          }
        })
        .catch(function(error) {
          _this.$message.error(response.data.msg);
          _this.init();
        });
    },
    init() {
      var _this = this;
      _this.form.name = null;
      _this.form.content = null;
      _this.form.tags = new Array();
      _this.form.nations = new Array();
      _this.saving = false;
      _this.loading = false;
    },
    selectTag(item) {
      this.form.tags.push(item);
    },
    closeTag(index) {
      this.form.tags.splice(index, 1);
    },
    selectNation(item) {
      this.form.nations.push(item);
      console.log(item.title);
    },
    closeNation(index) {
      this.form.nations.splice(index, 1);
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
