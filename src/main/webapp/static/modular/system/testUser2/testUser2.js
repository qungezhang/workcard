/**
 * 测试2管理初始化
 */
var TestUser2 = {
    id: "TestUser2Table",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
TestUser2.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
            {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '名字', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '年龄', field: 'age', visible: true, align: 'center', valign: 'middle'},
            {title: '性别', field: 'sixName', visible: true, align: 'center', valign: 'middle'},
            {title: '图片', field: 'img', visible: true, align: 'center', valign: 'middle',
                formatter:function(value,row,index){//显示图片
                    var s;
                    if(row.img!=null){
                        var url = Feng.ctxPath+'/kaptcha/'+row.img;
                        s = '<img style="width:60px;height:60px;"  src="'+url+'" />';
                    }
                    return s;
                },
            }

    ];
};

/**
 * 检查是否选中
 */
TestUser2.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        TestUser2.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加测试2
 */
TestUser2.openAddTestUser2 = function () {
    var index = layer.open({
        type: 2,
        title: '添加测试2',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/testUser2/testUser2_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看测试2详情
 */
TestUser2.openTestUser2Detail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '测试2详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/testUser2/testUser2_update/' + TestUser2.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除测试2
 */
TestUser2.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/testUser2/delete", function (data) {
            Feng.success("删除成功!");
            TestUser2.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("testUser2Id",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询测试2列表
 */
TestUser2.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    TestUser2.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = TestUser2.initColumn();
    var table = new BSTable(TestUser2.id, "/testUser2/list", defaultColunms);
    table.setPaginationType("client");
    TestUser2.table = table.init();
});
