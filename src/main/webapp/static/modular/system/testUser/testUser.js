/**
 * 测试-test管理初始化
 */
var TestUser = {
    id: "TestUserTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
TestUser.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '年纪', field: 'age', visible: true, align: 'center', valign: 'middle'},
            {title: '性别 ', field: 'sixName', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
TestUser.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        TestUser.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加测试-test
 */
TestUser.openAddTestUser = function () {
    var index = layer.open({
        type: 2,
        title: '添加测试-test',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/testUser/testUser_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看测试-test详情
 */
TestUser.openTestUserDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '测试-test详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/testUser/testUser_update/' + TestUser.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除测试-test
 */
TestUser.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/testUser/delete", function (data) {
            Feng.success("删除成功!");
            TestUser.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("testUserId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询测试-test列表
 */
TestUser.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    TestUser.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = TestUser.initColumn();
    var table = new BSTable(TestUser.id, "/testUser/list", defaultColunms);
    table.setPaginationType("client");
    TestUser.table = table.init();
});
