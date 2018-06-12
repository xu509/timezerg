<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to = "{path : '/period'}">时代</el-breadcrumb-item>
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
                <el-form-item label="启">
                  <el-row :gutter="20">
                    <el-col :md = "5" :xs="18" :sm="18">
                      <el-input v-model="form.year" placeholder="年"/>
                    </el-col>
                    <el-col :md = "4" :xs="18" :sm="18">
                      <el-input v-model="form.month" placeholder="月"/>
                    </el-col>
                    <el-col :md = "4" :xs="18" :sm="18">
                      <el-input v-model="form.day" placeholder="日"/>
                    </el-col>
                    <el-col :md = "4" :xs="18" :sm="18">
                      <el-switch
                          v-model="form.AD"
                          active-text="AD"
                          inactive-text="BC"
                          :active-value= "switchc.activev"
                          :inactive-value="switchc.inactivev">
                        </el-switch>
                    </el-col>
                  </el-row>
                </el-form-item>
                <el-form-item label="终">
                  <el-row :gutter="20">
                    <el-col :md = "5" :xs="18" :sm="18">
                      <el-input v-model="form.eyear" placeholder="年"/>
                    </el-col>
                    <el-col :md = "4" :xs="18" :sm="18">
                      <el-input v-model="form.emonth" placeholder="月"/>
                    </el-col>
                    <el-col :md = "4" :xs="18" :sm="18">
                      <el-input v-model="form.eday" placeholder="日"/>
                    </el-col>
                    <el-col :md = "4" :xs="18" :sm="18">
                      <el-switch
                          v-model="form.eAD"
                          active-text="AD"
                          inactive-text="BC"
                          :active-value= "switchc.activev"
                          :inactive-value="switchc.inactivev">
                        </el-switch>
                    </el-col>
                  </el-row>
                </el-form-item>
                <el-form-item label="显示的时间">
                  <el-input v-model="form.ddate"></el-input>
                </el-form-item>
                <el-form-item label="详细">
                  <el-input
                    type="textarea"
                    :rows="2"
                    placeholder="请输入内容"
                    v-model="form.content">
                  </el-input>
                </el-form-item>
                <el-form-item label="所属文明">
                    <inputboxcivilization @selectCivilization = "selectCivilization"></inputboxcivilization>
                    <el-tag type="warning" v-for="(item,index) in form.civilizations" class="tag-margin"
                      :key="item.id"
                      closable
                      :disable-transitions="false"
                      @close="closeCivilization(index)">
                      {{item.title}}
                    </el-tag>
                </el-form-item>
                <el-form-item label="参与国家">
                    <inputboxnation @selectNation = "selectNation"></inputboxnation>
                    <el-tag type="warning" v-for="(item,index) in form.nations" class="tag-margin"
                      :key="item.id"
                      closable
                      :disable-transitions="false"
                      @close="closeNation(index)">
                      {{item.title}}
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
import inputboxcivilization from "./plugin/inputboxcivilization.vue";
import inputboxnation from "./plugin/inputboxnation.vue";

export default {
  name: "periodAdd",
  data() {
    return {
      form: {
        title: "",
        content: "",
        ddate: "",
        pid: "",
        year: null,
        month: null,
        day: null,
        eyear: null,
        emonth: null,
        eday: null,
        AD: 1,
        eAD: 1,
        civilizations: [],
        nations: []
      },
      switchc: {
        activev: 1,
        inactivev: 0
      },
      saving: false
    };
  },
  methods: {
    init() {
      var _this = this;
      _this.form.title = null;
      _this.form.content = null;
      _this.form.ddate = null;
      _this.form.year = null;
      _this.form.month = null;
      _this.form.day = null;
      _this.form.eyear = null;
      _this.form.emonth = null;
      _this.form.eday = null;
      _this.form.AD = 1;
      _this.form.eAD = 1;
    },
    save() {
      var _this = this;

      _this.saving = true;
      axios
        .post(_this.GLOBAL.url_period_add, {
          title: _this.form.title,
          content: _this.form.content,
          ddate: _this.form.ddate,
          year: _this.form.year,
          month: _this.form.month,
          day: _this.form.day,
          eyear: _this.form.eyear,
          emonth: _this.form.emonth,
          eday: _this.form.eday,
          AD: _this.form.AD,
          eAD: _this.form.eAD
        })
        .then(function(response) {
          if (response.data.result == 0) {
            _this.$message({
              message: _this.form.title + " 添加成功！",
              type: "success"
            });
            _this.init();
          } else {
            _this.$message.error(response.data.msg);
          }
          _this.saving = false;
        })
        .catch(function(error) {
          _this.saving = false;
        });
    },
    selectCivilization(item) {
      this.form.civilizations.push(item);
      // console.log(item.title);
    },
    closeCivilization(index) {
      this.form.civilizations.splice(index, 1);
    },
    selectNation(item) {
      this.form.nations.push(item);
      // console.log(item.title);
    },
    closeNation(index) {
      this.form.nations.splice(index, 1);
    }
  },
  components: {
    inputboxcivilization,
    inputboxnation
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
