package cn.stylefeng.guns.modular.system.controller;

import cn.stylefeng.guns.modular.system.warpper.TestWarpper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.system.model.TestUser2;
import cn.stylefeng.guns.modular.system.service.ITestUser2Service;

import java.util.List;
import java.util.Map;

/**
 * 测试2控制器
 *
 * @author fengshuonan
 * @Date 2018-12-29 21:58:03
 */
@Controller
@RequestMapping("/testUser2")
public class TestUser2Controller extends BaseController {

    private String PREFIX = "/system/testUser2/";

    @Autowired
    private ITestUser2Service testUser2Service;

    /**
     * 跳转到测试2首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "testUser2.html";
    }

    /**
     * 跳转到添加测试2
     */
    @RequestMapping("/testUser2_add")
    public String testUser2Add() {
        return PREFIX + "testUser2_add.html";
    }

    /**
     * 跳转到修改测试2
     */
    @RequestMapping("/testUser2_update/{testUser2Id}")
    public String testUser2Update(@PathVariable Integer testUser2Id, Model model) {
        TestUser2 testUser2 = testUser2Service.selectById(testUser2Id);
        model.addAttribute("item",testUser2);
        LogObjectHolder.me().set(testUser2);
        return PREFIX + "testUser2_edit.html";
    }

    /**
     * 获取测试2列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<Map<String, Object>> maps = testUser2Service.selectMaps(null);
        return new TestWarpper(maps).wrap();
    }

    /**
     * 新增测试2
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(TestUser2 testUser2) {
        testUser2Service.insert(testUser2);
        return SUCCESS_TIP;
    }

    /**
     * 删除测试2
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer testUser2Id) {
        testUser2Service.deleteById(testUser2Id);
        return SUCCESS_TIP;
    }

    /**
     * 修改测试2
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(TestUser2 testUser2) {
        testUser2Service.updateById(testUser2);
        return SUCCESS_TIP;
    }

    /**
     * 测试2详情
     */
    @RequestMapping(value = "/detail/{testUser2Id}")
    @ResponseBody
    public Object detail(@PathVariable("testUser2Id") Integer testUser2Id) {
        return testUser2Service.selectById(testUser2Id);
    }
}
