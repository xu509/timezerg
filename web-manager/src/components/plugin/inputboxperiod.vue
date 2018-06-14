<template>
      <el-autocomplete popper-class="my-autocomplete" clearable v-model="name"
                        :fetch-suggestions="queryPeriod"
                          placeholder="搜索时期"
                          @select="selectPeriod">
                          <template slot-scope="props">
                                    <div class="name">
                                          <i class="el-icon-circle-plus-outline" v-if="props.item.isnew"></i>&nbsp;&nbsp;{{ props.item.title }}
                                      </div>
                          </template>
      </el-autocomplete>
</template>

<script>
import axios from "axios";

export default {
  name: "inputboxnode",
  data() {
    return {
      name: "",
      saving: false,
      loading: true
    };
  },
  methods: {
    queryPeriod(queryString, cb) {
      var _this = this;
      var sr = [];
      if (queryString != undefined && queryString.length > 0) {
        axios
          .post(_this.GLOBAL.url_search_period, {
            sw: queryString
          })
          .then(function(response) {
            var r = response.data;
            if (r.result == 0) {
              var data = r.data.data;
              if (r.data.exist) {
                cb(data);
              } else {
                var item = {
                  id: "11",
                  title: queryString,
                  isnew: true
                };

                sr.push(item);
                cb(sr);
              }
            } else {
              cb(sr);
            }
          });
      } else {
        cb(sr);
      }
    },
    selectPeriod(item) {
      this.name = "";
      this.$emit("selectPeriod", item);
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
