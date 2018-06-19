<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>yearbook</el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>

    <el-row>
      <el-select v-model="mid" placeholder="请选择">
        <el-option
          v-for="item in menus"
          :key="item.id"
          :label="item.title"
          :value="item.id">
        </el-option>
      </el-select>

      <el-button type="primary" v-if="isadd" @click="save">保存</el-button>
      <el-button type="primary" v-if="isedit" @click="update">更新</el-button>
    </el-row>
    <el-row>
       <div ref="editor" style="text-align:left"></div>
        <button v-on:click="getContent">查看内容</button>
    </el-row>


  </div>
</template>

<script>
import axios from "axios";
import E from 'wangeditor'

export default {
  name: "yearbook",
  data() {
    return {
      pid : '',
      isadd:false,
      isedit:false,
      mid : null,
      editorContent:'',
      loading_table: true,
      menus:[],
      editor: null,
      total: 0 // 总数
    };
  },
  methods: {
    init() {
      var _this = this;

      _this.editor = new E(this.$refs.editor)
      _this.editor.customConfig.onchange = (html) => {
          this.editorContent = html
        }
      _this.editor.customConfig.zIndex = 100
      _this.editor.create()

       var url = "http://yearbook.timezerg.com/api/book/menus";

       axios
        .post(url, {
          name: "111"
        })
        .then(function(response) {
          var menus = response.data.data;
          _this.menus = menus;

          _this.initContent();
         
        })
        .catch(function(error) {
          _this.$message.error(response.data.msg);
        });

    },
    initContent:function(){
       var _this = this;
      _this.editor.txt.clear();


      if(_this.mid == "" || _this.mid == null){
        _this.isadd = false;
        _this.isedit = false;
      }else{
       var url = "http://yearbook.timezerg.com/api/paragraph/init";

       axios
        .post(url, {
          mid: _this.mid
        })
        .then(function(response) {
          var datas = response.data.data;
          if(datas.length > 0){
            var content = datas[0].content;
            _this.editorContent = content;
            _this.pid = datas[0].id;

            _this.editor.cmd.do('insertHTML', content);
            _this.isedit = true;
            _this.isadd = false;
            // editor.html(content);
            console.log("content : " + content);
          }else{
            _this.editorContent = "";
            _this.editor.txt.clear();
            _this.isadd = true;
            _this.isedit = false;
            console.log("content :  empty");
          }
        })
        .catch(function(error) {
          _this.$message.error(error);
        });
      }


      
    },
    getContent: function () {
          alert(this.editorContent)
    },
    addGiant() {
      var _this = this;
      axios
        .post(_this.GLOBAL.url_giant_add, {
          name: _this.name
        })
        .then(function(response) {
          // console.log(response);
          if (response.data.result == 0) {
            _this.$message({
              message: _this.name + " 添加成功！",
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
    save(){
      var _this = this;
      var url = "http://yearbook.timezerg.com/api/paragraph/save";

      axios
        .post(url, {
          mid: _this.mid,
          content : _this.editorContent
        })
        .then(function(response) {
          _this.$message("success");
          _this.initContent();
         
        })
        .catch(function(error) {
          _this.$message.error(response.data.msg);
        });
    },
    update(){
      var _this = this;
      var url = "http://yearbook.timezerg.com/api/paragraph/update";

      axios
        .post(url, {
          id: _this.pid,
          content : _this.editorContent
        })
        .then(function(response) {
          _this.$message("success");
          _this.initContent();
         
        })
        .catch(function(error) {
          _this.$message.error(response.data.msg);
        });
    }
  },
  watch: {
    page_size: function(ps) {
      // this.initTable();
    },
    mid: function(mid){
      this.initContent();
    }
  },
  mounted: function() {
    this.init();
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.el-select-dropdown__list,.el-select-dropdown__item,.el-select-dropdown__item span,.el-scrollbar{
  z-index: 10001;
}
</style>
