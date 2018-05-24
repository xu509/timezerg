//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    motto: 'Hello World',
    nodes:[],
    hasUserInfo: false,
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
    var cid = "931704728708976640"
    console.log(cid);

    var _this = this;

    wx.request({
      url: 'http://127.0.0.1:8081/civilization/timeline', //仅为示例，并非真实的接口地址
      method: 'post',
      data: {
        id: cid
      },
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function (res) {
        if (res.data.result == "0") {

          _this.setData({
            nodes: res.data.data
          })

        }


      }
    })




  },
  getUserInfo: function (e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  }
})
