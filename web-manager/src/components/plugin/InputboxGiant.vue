<template>
      <el-autocomplete popper-class="my-autocomplete" clearable v-model="name"
                        :fetch-suggestions="queryGiant"
                          placeholder="搜索人物"
                          @select="selectGiant">
                          <template slot-scope="props">
                                    <div class="name">
                                          <i class="el-icon-circle-plus-outline" v-if="props.item.isnew"></i>&nbsp;{{ props.item.name }}
                                      </div>
                          </template>
      </el-autocomplete>
</template>

<script>
import axios from "axios";

export default {
  name: "inputboxgiant",
  data() {
    return {
      name: null,
      saving: false,
      loading: true
    };
  },
  methods: {
    queryGiant(queryString, cb) {
      var _this = this;
      var sr = [];
      if (queryString != undefined && queryString.length > 0) {
        axios
          .post(_this.GLOBAL.url_search_giant, {
            sw: queryString
          })
          .then(function(response) {
            var r = response.data;
            if (r.result == 0) {
              var data = r.data.data;
              if (!r.data.same) {
                var item = {
                  id: "11",
                  name: queryString,
                  isnew: true
                };
                sr.push(item);
              }

              for (var i = 0; i < data.length; i++) {
                sr.push(data[i]);
              }

              cb(sr);
            } else {
              cb(sr);
            }
          });
      } else {
        cb(sr);
      }
    },
    selectGiant(item) {
      this.name = null;
      this.$emit("selectGiant", item);
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
