//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    motto: 'Hello World',
    array:[ { id: 0, name:'全部' },
            { id: 1, name: '非常重要' },
            { id: 2, name: '重要' },
            { id: 3, name: '普通' },
            { id: 4, name: '细微' },
    ],
    cid:null,
    civilization:null,
    level:3,
    nodes:[],
    hasUserInfo: false,
    listNoMore: false,
    p:0,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },
  //事件处理函数
  go: function (e) {
    var id = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: '../node/node?id=' + id
    })
  },
  onLoad: function (options) {
    // var cid = options.cid;
    var _this = this;
    var id = "931704728708976640"
    console.log(id);
    _this.data.cid = id;
    _this.initTimeLine();

  },
  initTimeLine:function(){
    var _this = this;

    if (!_this.data.listNoMore){
      wx.request({
        url: 'http://127.0.0.1:8081/civilization/timeline', //仅为示例，并非真实的接口地址
        method: 'post',
        data: {
          id: _this.data.cid,
          level: _this.data.level,
          p: _this.data.p
        },
        header: {
          'content-type': 'application/json' // 默认值
        },
        success: function (res) {
          if (res.data.result == "0") {
            var rdata = res.data.data;

            if (_this.data.p == 0) {
              //在尾部增加
              _this.setData({
                nodes: rdata.timeline,
                civilization: rdata.civilization,
                p : _this.data.p + 1
              })
              // _this.data.p++;
            } else {
              //在尾部增加
              if (rdata.timeline.length == 0){
                _this.setData({
                  listNoMore: true
                })
              }else{
                for (var i = 0; i < rdata.timeline.length ; i++){
                  _this.data.nodes.push(rdata.timeline[i]);
                }

                _this.setData({
                  nodes: _this.data.nodes,
                  p : _this.data.p + 1
                })

              }
            }
          }
        }
      })
    }

  },
  getUserInfo: function (e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  },
  bindPickerChange:function(e){
    var _this = this;
    _this.customData.level = e.detail.value;

    this.setData({
      level: e.detail.value,
    })

    _this.initTimeLine();
  },
  onReachBottom:function(){
    this.initTimeLine();

    console.log(this.data.listNoMore);

  }
})
