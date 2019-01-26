/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.stylefeng.guns.modular.api;

import cn.stylefeng.guns.config.properties.GunsProperties;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.shiro.ShiroUser;
import cn.stylefeng.guns.core.util.JwtTokenUtil;
import cn.stylefeng.guns.modular.system.dao.UserMapper;
import cn.stylefeng.guns.modular.system.model.User;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

/**
 * 接口控制器提供
 *
 * @author stylefeng
 * @Date 2018/7/20 23:39
 */
@RestController
@RequestMapping("/gunsApi")
@Api(tags="1.接口控制器",description="")
public class ApiController extends BaseController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GunsProperties gunsProperties;

//    /**
//     * api登录接口，通过账号密码获取token
//     */
//    @ApiOperation("登录")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "username", value = "账号", required = true, dataType = "String"),
//            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
//    })
//    @GetMapping("/auth")
//    public Object auth(@RequestParam("username") String username,
//                       @RequestParam("password") String password) {
//
//        //封装请求账号密码为shiro可验证的token
//        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password.toCharArray());
//
//        //获取数据库中的账号密码，准备比对
//        User user = userMapper.getByAccount(username);
//
//        String credentials = user.getPassword();
//        String salt = user.getSalt();
//        ByteSource credentialsSalt = new Md5Hash(salt);
//        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
//                new ShiroUser(), credentials, credentialsSalt, "");
//
//        //校验用户账号密码
//        HashedCredentialsMatcher md5CredentialsMatcher = new HashedCredentialsMatcher();
//        md5CredentialsMatcher.setHashAlgorithmName(ShiroKit.hashAlgorithmName);
//        md5CredentialsMatcher.setHashIterations(ShiroKit.hashIterations);
//        boolean passwordTrueFlag = md5CredentialsMatcher.doCredentialsMatch(
//                usernamePasswordToken, simpleAuthenticationInfo);
//
//        if (passwordTrueFlag) {
//            HashMap<String, Object> result = new HashMap<>();
//            result.put("token", JwtTokenUtil.generateToken(String.valueOf(user.getId())));
//            return result;
//        } else {
//            return new ErrorResponseData(500, "账号密码错误！");
//        }
//    }

    /**
     * 测试接口是否走鉴权
     */
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ApiOperation("测试接口是否走鉴权")
    public Object test() {
        BaseController baseController = new BaseController();

        return SUCCESS_TIP;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ApiOperation("图片上传")
    public String upload(@RequestPart("file") MultipartFile picture) {

        String pictureName = UUID.randomUUID().toString() + "." + ToolUtil.getFileSuffix(picture.getOriginalFilename());
        try {
            String fileSavePath = gunsProperties.getFileUploadPath();
            picture.transferTo(new File(fileSavePath + pictureName));
        } catch (Exception e) {
            throw new ServiceException(BizExceptionEnum.UPLOAD_ERROR);
        }
        return pictureName;
    }

}

