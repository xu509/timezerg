//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    motto: 'Hello World',
    node: {},
    civilization:[],
    closenodes:[],
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },
  //事件处理函数
  bindViewTap: function () {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad: function (options) {

    wx.setNavigationBarTitle({
      title: '时间虫子',
    })
    
    var id = options.id;

    // var id = "931670909217935360";

    var _this = this;

    wx.request({
      url: app.globalData.apiDomain + '/api/node/detail', //仅为示例，并非真实的接口地址
      method: 'post',
      data: {
        id: id
      },
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function (res) {
        if (res.data.result == "0") {
          var rdata = res.data.data

          _this.setData({
            node: rdata.node,
            civilization : rdata.civilization,
            closenodes : rdata.closenodes
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
  },
  go: function (e) {
    var id = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: '../node/node?id=' + id
    })
  }
})
