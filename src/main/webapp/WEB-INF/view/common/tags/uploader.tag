@/*
上传图片参数的说明:
name : 名称
id : 按钮的id
@*/

<div class="form-group">
    <label class="col-sm-3 control-label head-scu-label">${name}</label>
        <div class="col-sm-7">
            <div id="${id}PreId">
                <div><img width="100px" height="100px"
                          @if(isEmpty(avatarImg)){
                          src="${ctxPath}/static/img/girl.gif"></div>
                @}else{
                src="${ctxPath}/kaptcha/${avatarImg}">
            </div>
            @}
        </div>

        <div class="col-sm-5">
            <div class="head-scu-btn upload-btn" id="${id}BtnId">
                <i class="fa fa-upload"></i>&nbsp;上传
            </div>
        </div>
        <input type="hidden" id="${id}" value="${avatarImg!}"/>
</div>
@if(isNotEmpty(underline) && underline == 'true'){
<div class="hr-line-dashed"></div>
@}