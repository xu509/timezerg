//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    motto: 'Hello World',
    node: {},
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
    var id = options.id;
    var _this = this;

    wx.request({
      url: 'http://127.0.0.1:8081/api/node/detail', //仅为示例，并非真实的接口地址
      method: 'post',
      data: {
        id: id
      },
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function (res) {
        if (res.data.result == "0") {

          _this.setData({
            node: res.data.data
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
