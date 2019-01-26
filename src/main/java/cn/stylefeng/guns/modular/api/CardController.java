package cn.stylefeng.guns.modular.api;

import cn.stylefeng.guns.config.properties.GunsProperties;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.modular.system.model.WorkerCard;
import cn.stylefeng.guns.modular.system.service.IWorkerCardService;
import cn.stylefeng.roses.core.reqres.response.SuccessResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Api(tags="卡片管理",description="")
public class CardController {

    @Autowired
    private GunsProperties gunsProperties;

    @Autowired
    private IWorkerCardService workerCardService;


    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ApiOperation("图片上传")
    public Object upload(@RequestPart("file") MultipartFile picture) {

        String pictureName = UUID.randomUUID().toString() + "." + ToolUtil.getFileSuffix(picture.getOriginalFilename());
        String fileSavePath = null;
        try {
             fileSavePath = gunsProperties.getFileUploadPath();
            picture.transferTo(new File(fileSavePath + pictureName));
        } catch (Exception e) {
            throw new ServiceException(BizExceptionEnum.UPLOAD_ERROR);
        }
        SuccessResponseData successResponseData = new SuccessResponseData();
        successResponseData.setData("images/"+pictureName);
        return successResponseData ;
    }


    /**
     * 新增
     */
    @PostMapping(value = "/add")
    @ApiOperation("生成贺卡")
    public Object add(@RequestBody WorkerCard workerCard) {
        workerCard.setsTime(new Date());
        boolean insert = workerCardService.insert(workerCard);
        SuccessResponseData successResponseData = new SuccessResponseData();
        successResponseData.setData(insert);
        return successResponseData;
    }
}
