/**
 * 管理初始化
 */
var WorkerCard = {
    id: "WorkerCardTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
WorkerCard.initColumn = function () {
    return [
        // {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '制作者姓名', field: 'fName', visible: true, align: 'center', valign: 'middle'},
            {title: '收卡人姓名', field: 'sName', visible: true, align: 'center', valign: 'middle'},
            {title: '制作者邮箱', field: 'email', visible: true, align: 'center', valign: 'middle'},
            {title: '贺卡内容', field: 'content', visible: true, align: 'center', valign: 'middle'},
            {title: '图片', field: 'imgUrl', visible: false, align: 'center', valign: 'middle'},
            {title: '制作时间', field: 'sTime', visible: true, align: 'center', valign: 'middle'},
            {title: '操作', field: 'Button', visible: true, align: 'center', valign: 'middle',
                formatter: operateFormatter,events: operateEvents
            },
            // {title: '自定义二', field: 'flag2', visible: true, align: 'center', valign: 'middle'}
    ];
};

function operateFormatter(value, row, index) {
    return [
        '<input type="button" value="查看" class="RoleOfedit btn btn-primary btn-sm"   data-toggle="modal" style="display:inline">',
    ].join('');
}

window.operateEvents = {
    'click .RoleOfedit': function (e, value, row, index) {
        // alert(row.imgUrl);
        var url = Feng.ctxPath+row.flag1;
        layer.open({
            type: 1,
            title: false,
            closeBtn: 1,
            area: ['600px', '800px'],
            skin: 'layui-layer-nobg', //没有背景色
            shadeClose: true,
            content: '<img  style="width:600px;height:800px;" src="'+url+'"/>',
            success: function(layero, index) {
                layer.iframeAuto(index);
            }
        });
    }
};

/**
 * 检查是否选中
 */
WorkerCard.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        WorkerCard.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
WorkerCard.openAddWorkerCard = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/workerCard/workerCard_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看详情
 */
WorkerCard.openWorkerCardDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/workerCard/workerCard_update/' + WorkerCard.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
WorkerCard.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/workerCard/delete", function (data) {
            Feng.success("删除成功!");
            WorkerCard.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("workerCardId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 导出
 */
WorkerCard.export = function () {

    window.location.href=Feng.ctxPath + "/workerCard/export";
};

/**
 * 查询列表
 */
WorkerCard.search = function () {
    var queryData = {};
    // queryData['condition'] = $("#condition").val();
    queryData['fName'] = $("#fName").val();
    queryData['sName'] = $("#sName").val();
    queryData['email'] = $("#email").val();
    WorkerCard.table.refresh({query: queryData});
};

WorkerCard.resetSearch = function () {
    $("#fName").val("");
    $("#sName").val("");
    $("#email").val("");
    WorkerCard.search();
};

$(function () {
    var defaultColunms = WorkerCard.initColumn();
    var table = new BSTable(WorkerCard.id, "/workerCard/list", defaultColunms);
    table.setPaginationType("client");
    WorkerCard.table = table.init();
});
