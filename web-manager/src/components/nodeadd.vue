<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to = "{path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to = "{path : '/node'}">节点</el-breadcrumb-item>
          <el-breadcrumb-item>增加</el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
    </el-row>
    <el-row>
        <el-col :sm="24" :xs="24" :md="4" :xl="4" >
           <router-link :to="'/node/edit/' + add_id" target = "_blank">
              <el-card class="box-card" v-if = "add_id != null">
                  <p class="paragraph-content">{{add_title}}</p>
              </el-card>
           </router-link>
        </el-col>
    </el-row>

    <el-row>
      <el-col :sm="24" :xs="24" :md="12" :xl="12" >
          <el-form ref="form" :model="form" label-width="120px">
                <el-form-item label="标题">
                  <el-input v-model="form.title"></el-input>
                </el-form-item>
                <el-form-item label="时间">
                  <el-row :gutter="20">
                    <el-col :md = "4" :xl="4" :sm="12" :xs="12">
                      <el-input v-model="form.year" placeholder="年" @change="updateClosestDate"/>
                    </el-col>
                    <el-col :md = "3" :xl="3" :sm="12" :xs="12">
                      <el-input v-model="form.month" placeholder="月" @change="updateClosestDate"/>
                    </el-col>
                    <el-col :md = "3" :xl="3" :sm="12" :xs="12">
                      <el-input v-model="form.day" placeholder="日" @change="updateClosestDate"/>
                    </el-col>
                    <el-col :md = "4" :xl="4" :sm="12" :xs="12">
                      <el-input v-model="form.hour" placeholder="时"/>
                    </el-col>
                    <el-col :md = "4" :xl="4" :sm="12" :xs="12">
                      <el-input v-model="form.minute" placeholder="分"/>
                    </el-col>
                    <el-col :md = "4" :xl="4" :sm="12" :xs="12">
                      <el-input v-model="form.second" placeholder="秒"/>
                    </el-col>
                  </el-row>
                </el-form-item>
                <el-form-item label="显示的时间">
                  <el-input v-model="form.ddate"></el-input>
                </el-form-item>
                <el-form-item label="是否是公元后">
                  <el-radio-group v-model="form.AD">
                    <el-radio label="0">BC</el-radio>
                    <el-radio label="1">AD</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item label="详细">
                  <el-input
                    type="textarea"
                    :rows="2"
                    placeholder="请输入内容"
                    v-model="form.content">
                  </el-input>
                </el-form-item>
                 <el-form-item label="重要度">
                    <el-select v-model="form.level" placeholder="重要度">
                        <el-option v-for="item in options" :key = "item.value" :label = "item.label" :value = "item.value"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="相关国家">
                    <inputboxnation @selectNation = "selectNation"></inputboxnation>
                    <el-tag
                      :key="nation.id"
                      v-for="(nation,index) in form.nations"
                      closable
                      :disable-transitions="false"
                      @close="closeNationTag(index)">
                      {{nation.title}}
                    </el-tag>
                </el-form-item>

                <el-form-item label="相关文明">
                   <inputboxcivilization @selectCivilization = "selectCivilization"></inputboxcivilization>
                    <el-tag type="warning"
                      :key="civilization.id" 
                      v-for="(civilization,index) in form.civilizations"
                      closable
                      :disable-transitions="false"
                      @close="closeCivilizationTag(index)">
                      {{civilization.title}}
                    </el-tag>
                </el-form-item>

                <el-form-item label="相关人物">
                  <inputboxgiant @selectGiant = "selectGiant"></inputboxgiant>
                  <el-tag type="warning"
                      :key="giant.id"
                      v-for="(giant,index) in form.giants"
                      closable
                      :disable-transitions="false"
                      @close="closeGiantTag(index)">
                      {{giant.name}}
                    </el-tag>
                </el-form-item>

                <el-form-item>
                  <el-button type="primary" @click="save" :loading="saving">创建</el-button>
                </el-form-item>
          </el-form>
      </el-col>
      <el-col :md="6" :xl="6" :push="1" >
        <el-card class="box-card" v-if = "closest_nodes.length > 0" v-loading = "loading_closest_nodes">
           <ul class="paragraph-title-small">相近的节点
              <li v-for="item in closest_nodes" :key="item.id" class="paragraph-content-small">{{item.ddate}} - {{item.title}}</li>
          </ul>
        </el-card>
        <el-card class="box-card" v-if = "similar_nodes.length > 0" v-loading = "loading_similar_nodes">
           <ul class="paragraph-title-small">相似的节点
              <li v-for="item in similar_nodes" :key="item.id" class="paragraph-content-small">{{item.ddate}} - {{item.title}}</li>
          </ul>
        </el-card>

      </el-col>
    </el-row>
  </div>
</template>

<script>
import axios from "axios";
import inputboxnation from "./plugin/inputboxnation.vue";
import inputboxcivilization from "./plugin/inputboxcivilization.vue";
import inputboxgiant from "./plugin/inputboxgiant.vue";

export default {
  name: "nodeadd",
  data() {
    return {
      form: {
        title: "",
        content: "",
        ddate: "",
        year: null,
        month: null,
        day: null,
        hour: null,
        minute: null,
        second: null,
        AD: "1",
        level: 0,
        nation: null,
        nations: [],
        civilization: null,
        civilizations: [],
        giants: []
      },
      options: [
        {
          value: 0,
          label: "待定"
        },
        {
          value: 1,
          label: "非常重要"
        },
        {
          value: 2,
          label: "重要"
        },
        {
          value: 3,
          label: "普通"
        },
        {
          value: 4,
          label: "细节"
        }
      ],
      saving: false,
      add_id: null,
      add_title: null,
      closest_nodes: [],
      loading_closest_nodes: false,
      similar_nodes: [],
      loading_similar_nodes: false
    };
  },
  components: {
    inputboxnation,
    inputboxcivilization,
    inputboxgiant
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
      _this.form.hour = null;
      _this.form.minute = null;
      _this.form.second = null;
      _this.form.AD = "1";
      _this.form.level = 0;
      _this.form.nations = [];
      _this.form.nation = null;
      _this.form.civilizations = [];
      _this.form.giants = [];
      _this.form.civilization = null;
      _this.saving = false;
    },
    save() {
      var _this = this;

      _this.saving = true;
      axios
        .post(_this.GLOBAL.url_node_add, {
          title: _this.form.title,
          content: _this.form.content,
          ddate: _this.form.ddate,
          year: _this.form.year,
          month: _this.form.month,
          day: _this.form.day,
          minute: _this.form.minute,
          second: _this.form.second,
          AD: _this.form.AD,
          level: _this.form.level,
          nations: _this.form.nations,
          civilizations: _this.form.civilizations,
          giants: _this.form.giants
        })
        .then(function(response) {
          if (response.data.result == 0) {
            _this.$message({
              message: _this.form.title + " 添加成功！",
              type: "success"
            });
            _this.add_id = response.data.data.id;
            _this.add_title = response.data.data.title;
            _this.init();
          } else {
            _this.GLOBAL.showErrorMsg(response, _this);
            _this.init();
          }
          _this.saving = false;
        })
        .catch(function(error) {
          _this.saving = false;
        });
    },
    selectNation(item) {
      this.form.nations.push(item);
    },
    closeNationTag(index) {
      this.form.nations.splice(index, 1);
    },
    selectCivilization(item) {
      this.form.civilizations.push(item);
    },
    closeCivilizationTag(index) {
      this.form.civilizations.splice(index, 1);
    },
    selectGiant(item) {
      this.form.giants.push(item);
    },
    closeGiantTag(index) {
      this.form.giants.splice(index, 1);
    },
    updateClosestDate() {
      var _this = this;
      _this.loading_closest_nodes = true;
      if (!(_this.form.year > 0)) {
        _this.loading_closest_nodes = [];
        return;
      }

      axios
        .post(_this.GLOBAL.url_node_closest_date, {
          year: _this.form.year,
          month: _this.form.month,
          day: _this.form.day
        })
        .then(function(response) {
          if (response.data.result == 0) {
            var data = response.data.data;
            console.log(data);

            _this.closest_nodes = data;
            _this.loading_closest_nodes = false;
          } else {
            _this.loading_closest_nodes = false;
            this.$message.error(response.data.data);
          }
        })
        .catch(function(error) {});
    },
    updateSimilarNode() {
      console.log("updateSimilarNode");
      var _this = this;
      _this.loading_similar_nodes = true;
      if (_this.form.title.length <= 0) {
        _this.similar_nodes = [];
        _this.loading_similar_nodes = false;
        return;
      }

      axios
        .post(_this.GLOBAL.url_search_node, {
          sw: _this.form.title
        })
        .then(function(response) {
          if (response.data.result == 0) {
            var data = response.data.data;
            _this.similar_nodes = data.data;
            _this.loading_similar_nodes = false;
          } else {
            _this.loading_similar_nodes = false;
            this.$message.error(response.data.data);
          }
        })
        .catch(function(error) {});
    }
  },
  computed: {
    year() {
      return this.form.year;
    },
    month() {
      return this.form.month;
    },
    day() {
      return this.form.day;
    },
    title() {
      return this.form.title;
    }
  },
  watch: {
    year() {
      this.updateClosestDate();
    },
    month() {
      this.updateClosestDate();
    },
    day() {
      this.updateClosestDate();
    },
    title() {
      this.updateSimilarNode();
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
