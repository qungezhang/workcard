/**
 * 初始化详情对话框
 */
var WorkerCardInfoDlg = {
    workerCardInfoData : {}
};

/**
 * 清除数据
 */
WorkerCardInfoDlg.clearData = function() {
    this.workerCardInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WorkerCardInfoDlg.set = function(key, val) {
    this.workerCardInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WorkerCardInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
WorkerCardInfoDlg.close = function() {
    parent.layer.close(window.parent.WorkerCard.layerIndex);
}

/**
 * 收集数据
 */
WorkerCardInfoDlg.collectData = function() {
    this
    .set('id')
    .set('sName')
    .set('fName')
    .set('content')
    .set('email')
    .set('imgUrl')
    .set('sTime')
    .set('flag1')
    .set('flag2');
}

/**
 * 提交添加
 */
WorkerCardInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/workerCard/add", function(data){
        Feng.success("添加成功!");
        window.parent.WorkerCard.table.refresh();
        WorkerCardInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.workerCardInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
WorkerCardInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/workerCard/update", function(data){
        Feng.success("修改成功!");
        window.parent.WorkerCard.table.refresh();
        WorkerCardInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.workerCardInfoData);
    ajax.start();
}

$(function() {

});
