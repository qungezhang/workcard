/**
 * 初始化详情对话框
 */
var TestUserInfoDlg = {
    testUserInfoData : {}
};

/**
 * 清除数据
 */
TestUserInfoDlg.clearData = function() {
    this.testUserInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TestUserInfoDlg.set = function(key, val) {
    this.testUserInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TestUserInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TestUserInfoDlg.close = function() {
    parent.layer.close(window.parent.TestUser.layerIndex);
}

/**
 * 收集数据
 */
TestUserInfoDlg.collectData = function() {
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
TestUserInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/testUser/add", function(data){
        Feng.success("添加成功!");
        window.parent.TestUser.table.refresh();
        TestUserInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.testUserInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
TestUserInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/testUser/update", function(data){
        Feng.success("修改成功!");
        window.parent.TestUser.table.refresh();
        TestUserInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.testUserInfoData);
    ajax.start();
}

$(function() {
    var avatarUp = new $WebUpload("img");

    avatarUp.setUploadBarId("progressBar");

    avatarUp.init();
});
