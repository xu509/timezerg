<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to = "{path : '/node'}">节点</el-breadcrumb-item>
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
                    <el-radio label="BC"></el-radio>
                    <el-radio label="AD"></el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="save">创建</el-button>
                </el-form-item>
          </el-form>
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
      form: {
        title: "",
        cdate: "",
        ddate: "",
        year: null,
        month: null,
        day: null,
        hour: null,
        minute: null,
        second: null,
        AD: 1
      }
    };
  },
  methods: {
    save() {
      console.log("save");
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
