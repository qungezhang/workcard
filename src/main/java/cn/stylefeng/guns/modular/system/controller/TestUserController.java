package cn.stylefeng.guns.modular.system.controller;

import cn.stylefeng.guns.modular.system.warpper.TestWarpper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.system.model.TestUser;
import cn.stylefeng.guns.modular.system.service.ITestUserService;

import java.util.List;
import java.util.Map;

/**
 * 测试-test控制器
 *
 * @author fengshuonan
 * @Date 2018-12-12 10:02:28
 */
@Controller
@RequestMapping("/testUser")
public class TestUserController extends BaseController {

    private String PREFIX = "/system/testUser/";

    @Autowired
    private ITestUserService testUserService;

    /**
     * 跳转到测试-test首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "testUser.html";
    }

    /**
     * 跳转到添加测试-test
     */
    @RequestMapping("/testUser_add")
    public String testUserAdd() {
        return PREFIX + "testUser_add.html";
    }

    /**
     * 跳转到修改测试-test
     */
    @RequestMapping("/testUser_update/{testUserId}")
    public String testUserUpdate(@PathVariable Integer testUserId, Model model) {
        TestUser testUser = testUserService.selectById(testUserId);
        model.addAttribute("item",testUser);
        LogObjectHolder.me().set(testUser);
        return PREFIX + "testUser_edit.html";
    }

    /**
     * 获取测试-test列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<Map<String, Object>> maps = testUserService.selectMaps(new EntityWrapper<>());
        return super.warpObject(new TestWarpper(maps));
    }

    /**
     * 新增测试-test
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(TestUser testUser) {
        testUserService.insert(testUser);
        return SUCCESS_TIP;
    }

    /**
     * 删除测试-test
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer testUserId) {
        testUserService.deleteById(testUserId);
        return SUCCESS_TIP;
    }

    /**
     * 修改测试-test
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(TestUser testUser) {
        testUserService.updateById(testUser);
        return SUCCESS_TIP;
    }

    /**
     * 测试-test详情
     */
    @RequestMapping(value = "/detail/{testUserId}")
    @ResponseBody
    public Object detail(@PathVariable("testUserId") Integer testUserId) {
        return testUserService.selectById(testUserId);
    }
}
