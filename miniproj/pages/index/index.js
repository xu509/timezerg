//index.js
const app = getApp()

Page({
  data: {
    motto: 'Hello World',
    civilizations: [],
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },
  onLoad: function () {

    var _this = this;

    wx.setNavigationBarTitle({
      title: '时间虫子',
    })

    wx.request({
      url: app.globalData.apiDomain + '/civilization/list', //仅为示例，并非真实的接口地址
      method: 'post',
      data: {
        start: 0,
        size: 10
      },
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function (res) {
        if (res.data.result == "0") {
          _this.setData({
            civilizations: res.data.data.data
          })
        }
      },
      fail:function(res){
        console.log(res);  
      }
    })
  },
  getUserInfo: function (e) {
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  },
  go: function (e) {
    var cid = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: '../civilization/civilization?cid=' + cid
    })
  }
})
