package cn.stylefeng.guns.modular.api;

import cn.stylefeng.guns.config.properties.GunsProperties;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.util.Base64ToMultipart;
import cn.stylefeng.guns.core.util.CacheUtil;
import cn.stylefeng.guns.core.util.SignUtil;
import cn.stylefeng.guns.core.util.WinxinUtil;
import cn.stylefeng.guns.modular.system.model.WorkerCard;
import cn.stylefeng.guns.modular.system.service.IWorkerCardService;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.reqres.response.SuccessResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static cn.stylefeng.guns.core.util.WinxinUtil.cacheAccessTokenKey;
import static cn.stylefeng.guns.core.util.WinxinUtil.cachejsapiTicketKey;
import static cn.stylefeng.guns.core.util.WinxinUtil.getAccessToken;
import static cn.stylefeng.guns.core.util.WinxinUtil.getTicket;

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
    public ResponseData upload(@RequestPart("file") MultipartFile picture) {

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

    @RequestMapping(method = RequestMethod.POST, path = "/uploadBase64")
    @ApiOperation("图片上传Base64")
    public ResponseData uploadBase64(@RequestParam("base") String base, @RequestParam("id") String id) {
        ResponseData responseData = upload(Base64ToMultipart.base64ToMultipart(base));
        String data = (String) responseData.getData();
        WorkerCard workerCard = workerCardService.selectById(id);
        workerCard.setFlag1(data);
        workerCardService.updateById(workerCard);
        responseData.setData(id);
        return responseData;
    }


    /**
     * 新增
     */
    @PostMapping(value = "/add")
    @ApiOperation("生成贺卡")
    public ResponseData add(WorkerCard workerCard) {
        workerCard.setsTime(new Date());
        workerCardService.insert(workerCard);
        SuccessResponseData successResponseData = new SuccessResponseData();
        successResponseData.setData(workerCard.getId());
        return successResponseData;
    }
    /**
     * 详情
     */
    @PostMapping(value = "/detail")
    @ApiOperation("获取详情")
    public ResponseData detail(@RequestParam(value = "workerCardId", required = true) Integer id) {
        SuccessResponseData successResponseData = new SuccessResponseData();
        successResponseData.setData(workerCardService.selectById(id));
        return successResponseData;
    }

    @GetMapping(value = "/sign")
    @ApiOperation("微信SDK使用权限签名")
    public ResponseData sign(String url) {
        String jsapiTicket = CacheUtil.get("CONSTANT", cachejsapiTicketKey);
        if (StringUtils.isBlank(jsapiTicket)) {
            jsapiTicket = getTicket();
        }
        Map<String, String> sign = SignUtil.sign(jsapiTicket, url);
        SuccessResponseData successResponseData = new SuccessResponseData();
        successResponseData.setData(sign);
        return successResponseData;
    }


//    /**
//     * 新增
//     */
//    @GetMapping(value = "/hello")
//    @ApiOperation("hell0")
//    public ResponseData hello(String aa) {
//        SuccessResponseData successResponseData = new SuccessResponseData();
//        successResponseData.setData(aa);
//        return successResponseData;
//    }
}
