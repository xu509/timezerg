<template>
  <div class="hello">
      <el-row :gutter="20">
        <el-col :span="6">
            时间
        </el-col>
        <el-col :span="6" v-for="item in civilizations" :key="item.id">
          {{item.title}}
        </el-col>
      </el-row>
         <template v-for="item in datas">
           <el-row :gutter="20" :key="item.id">
            <el-col :span="6">
               {{item.ddate}}
            </el-col>
            <template v-for = "j in item.detail">
                <template v-for = "(i,index) in civilizations">
                    <template v-if = "j.cid != civilizations[index].id">
                      <el-col :span="6" :key = "i.id">
                              &nbsp;
                      </el-col>
                    </template>
                    <template v-if = "j.cid == civilizations[index].id">
                      <el-col :span="6" :key = "i.id">
                              {{j.nname}}
                      </el-col>
                    </template>
                </template>
            </template>
           </el-row>
        </template> 
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "index",
  data() {
    return {
      datas: [],
      civilizations: [],
      times: []
    };
  },
  methods: {
    init() {
      this.initData();
    },
    initData() {
      var _this = this;
      axios
        .post(_this.GLOBAL.url_index, {
          start: 1
        })
        .then(function(response) {
          if (response.data.result == 0) {
            _this.datas = response.data.data.data;
            _this.civilizations = response.data.data.civilizations;
            console.log(response.data.data.times);
          }
        })
        .catch(function(error) {
          console.log(111);
          console.log(error);
        });
    }
  },
  mounted: function() {
    this.init();
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1,
h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
