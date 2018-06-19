<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to = "{path : '/relation'}">关系</el-breadcrumb-item>
          <el-breadcrumb-item>修改</el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <el-row>
        <p class="paragraph-title">{{form.title}}</p>
        <template v-if="form.trelation != null">
          <p class="paragraph-title-small can-click" @click="goTRelation()"> - {{form.trelation.title}}  </p>
        </template>    
    </el-row>
    <el-row>
        <el-col :md ="12" :xs="16" :sm="16" >
            <el-form ref="form" label-width="120px" v-loading = "form.loading">
                <el-form-item label="标题">
                    <el-input v-model="form.title"></el-input>
                </el-form-item>

                <el-form-item label="对应的关系">
                  <el-select v-model="form.tid" placeholder="请选择"
                            filterable
                            allow-create
                            default-first-option>
                    <el-option
                      v-for="item in form.relations"
                      :key="item.id"
                      :label="item.title"
                      :value="item.id">
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
        tid:null,
        trelation:null,
        relations: []
      },
      saving: false,
      loading: true
    };
  },
  components: {},
  methods: {
    init() {
      var _this = this;  
      _this.form.loading = true;

      axios
        .post(_this.GLOBAL.url_relation_edit_init, {
          id: _this.id,
        })
        .then(function(response) {
          if (response.data.result == 0) {
            var data = response.data.data;
            // console.log(data);
            _this.form.title = data.title;
            _this.form.tid = data.tid;
            _this.form.relations = data.relations;
            _this.form.trelation = data.trelation;
            _this.form.loading = false;
          }
        });
    },

    // 基础
    save() {
      var _this = this;

      _this.form.loading = true;

      axios
        .post(_this.GLOBAL.url_relation_edit_save, {
          id: _this.id,
          title: _this.form.title,
          tid: _this.form.tid
        })
        .then(function(response) {
          if (response.data.result == 0) {
            _this.$message({
              message: _this.form.title + " 更新成功！",
              type: "success"
            });
            _this.init();
          }else{
            _this.$notify.error({
                  title: response.data.msg,
                  message: response.data.data,
                  duration: 0
            });
          }
        });
    },
    goTRelation(){
      // console.log("goTRelation");
      this.$router.push({
        path: "/relation/edit/" + this.form.trelation.id
      });
      this.id = this.form.trelation.id;
      this.init()
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
.can-click{
  cursor: pointer;
}
</style>
