package cn.stylefeng.guns.modular.system.controller;


import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.core.util.FileUtil;
import cn.stylefeng.guns.modular.system.model.WorkerCard;
import cn.stylefeng.guns.modular.system.service.IWorkerCardService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 控制器
 *
 * @author fengshuonan
 * @Date 2019-01-26 14:57:22
 */
@Controller
@RequestMapping("/workerCard")
public class WorkerCardController extends BaseController {

    private String PREFIX = "/system/workerCard/";

    @Autowired
    private IWorkerCardService workerCardService;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "workerCard.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/workerCard_add")
    public String workerCardAdd() {
        return PREFIX + "workerCard_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/workerCard_update/{workerCardId}")
    public String workerCardUpdate(@PathVariable Integer workerCardId, Model model) {
        WorkerCard workerCard = workerCardService.selectById(workerCardId);
        model.addAttribute("item",workerCard);
        LogObjectHolder.me().set(workerCard);
        return PREFIX + "workerCard_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String fName,String sName,String email) {
        EntityWrapper<WorkerCard> wrapper = new EntityWrapper<>();
        wrapper.like(StringUtils.isNotBlank(fName),"f_name", fName).
                like(StringUtils.isNotBlank(sName),"s_name", sName).
                like(StringUtils.isNotBlank(email),"email", email);
        return workerCardService.selectList(wrapper);
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(WorkerCard workerCard) {
        workerCardService.insert(workerCard);
        return SUCCESS_TIP;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer workerCardId) {
        workerCardService.deleteById(workerCardId);
        return SUCCESS_TIP;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(WorkerCard workerCard) {
        workerCardService.updateById(workerCard);
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{workerCardId}")
    @ResponseBody
    public Object detail(@PathVariable("workerCardId") Integer workerCardId) {
        return workerCardService.selectById(workerCardId);
    }


    @RequestMapping("export")
    public void export(HttpServletResponse response, HttpServletRequest request){
        List<WorkerCard> workerCards = workerCardService.selectList(new EntityWrapper<>());

        //导出操作
        String fileName = "贺卡";
        FileUtil.exportExcel(workerCards, "贺卡数据", "贺卡数据", WorkerCard.class, fileName, response);
    }

}
