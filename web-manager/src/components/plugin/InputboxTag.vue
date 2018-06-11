<template>
      <el-autocomplete popper-class="my-autocomplete" clearable v-model="title"
                        :fetch-suggestions="queryTag"
                          placeholder="搜索Tag"
                          @select="selectTag">
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
  name: "InputboxTag",
  data() {
    return {
      title: null,
      loading: true
    };
  },
  methods: {
    queryTag(queryString, cb) {
      var _this = this;
      var sr = [];
      if (queryString != undefined && queryString.length > 0) {
        axios
          .post(_this.GLOBAL.url_search_tag, {
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
    selectTag(item) {
      this.title = null;
      this.$emit("selectTag", item);
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
