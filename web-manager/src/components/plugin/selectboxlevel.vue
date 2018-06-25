<template>
  <el-select v-model="value" placeholder="请选择LEVEL" @change="selectLevel">
      <el-option
        v-for="item in options"
        :key="item.value"
        :label="item.label"
        :value="item.value">
      </el-option>
  </el-select>
</template>

<script>
import axios from "axios";

export default {
  name: "inputboxgiant",
  data() {
    return {
      value:'',
      options: [{
          value: 1,
          label: '非常重要'
        }, {
          value: 0,
          label: '全部'
        }]
    };
  },
  methods: {
    selectLevel() {
      this.$emit("selectLevel", this.value);
      // this.form.tags.push(item);
    }
  },
  mounted: function() {
    // this.init();
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
