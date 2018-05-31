<template>
  <div class="hello">
      <ul>huaxia 
          <li>111</li>
          <li>111</li>
          <li>111</li>
          <li>111</li>
          <li>111</li>
      </ul>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "index",
  data() {
    return {
      datas: []
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
            // console.log(response.data.data.data);
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
