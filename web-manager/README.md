<pre><code>

            _this.GLOBAL.showErrorMsg(response, _this);


_this.$notify.error({
                  title: response.data.msg,
                  message: response.data.data,
                  duration: 0
});

 _this.$message({
              message: _this.form.title + " 修改成功！",
              type: "success"
            });

{
  "exist": true,
  "same": true,
  "data": [
    {
      "aD": 1,
      "cdate": -62104348800000,
      "gid": "946179014794416128",
      "pname": "鲁隐公",
      "sname": "鲁隐公 - 隐公",
      "name": "隐公",
      "eAD": 1,
      "pid": "946179014794416128",
      "id": "946179014794416128",
      "content": "鲁隐公（？－前712年，前722年-前712年在位），姬姓，名息姑，鲁国第十四代国君。鲁惠公之子，生母是声子。传世的鲁国史书《春秋》及其三传的记事都是从鲁隐公开始的。"
    },
    {
      "aD": 1,
      "gid": "946179014794416128",
      "sname": "鲁隐公",
      "name": "鲁隐公",
      "eAD": 1,
      "id": "946179014794416128"
    }
  ]
}

</code></pre>