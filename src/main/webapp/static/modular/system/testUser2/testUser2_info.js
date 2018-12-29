/**
 * 初始化测试2详情对话框
 */
var TestUser2InfoDlg = {
    testUser2InfoData : {}
};

/**
 * 清除数据
 */
TestUser2InfoDlg.clearData = function() {
    this.testUser2InfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TestUser2InfoDlg.set = function(key, val) {
    this.testUser2InfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TestUser2InfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TestUser2InfoDlg.close = function() {
    parent.layer.close(window.parent.TestUser2.layerIndex);
}

/**
 * 收集数据
 */
TestUser2InfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('age')
    .set('six')
    .set('img');
}

/**
 * 提交添加
 */
TestUser2InfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/testUser2/add", function(data){
        Feng.success("添加成功!");
        window.parent.TestUser2.table.refresh();
        TestUser2InfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.testUser2InfoData);
    ajax.start();
}

/**
 * 提交修改
 */
TestUser2InfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/testUser2/update", function(data){
        Feng.success("修改成功!");
        window.parent.TestUser2.table.refresh();
        TestUser2InfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.testUser2InfoData);
    ajax.start();
}

$(function() {
    var avatarUp = new $WebUpload("img");

    avatarUp.setUploadBarId("progressBar");

    avatarUp.init();
});
