package cn.stylefeng.guns.modular.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 贺卡管理
 * </p>
 *
 * @author stylefeng
 * @since 2019-01-26
 */
@TableName("worker_card")
public class WorkerCard extends Model<WorkerCard> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 收卡人姓名
     */
    @TableField("s_name")
    @Excel(name = "收卡人姓名", orderNum = "0")
    private String sName;
    /**
     * 制作者姓名
     */
    @TableField("f_name")
    @Excel(name = "制作者姓名", orderNum = "1")
    private String fName;
    /**
     * 贺卡内容
     */
    @Excel(name = "贺卡内容", orderNum = "3",width = 50)
    private String content;
    /**
     * 制作者邮箱
     */
    @Excel(name = "制作者邮箱", orderNum = "2",width = 20)
    private String email;
    /**
     * 图片
     */
    @TableField("img_url")
    private String imgUrl;
    /**
     * 制作时间
     */
    @TableField("s_time")
    @Excel(name = "制作时间",exportFormat = "yyyy-MM-dd HH:mm:ss", orderNum = "4",width=20)
    private Date sTime;
    /**
     * 自定义一
     */
//    @Excel(name = "贺卡图片", orderNum = "5",type = 2,imageType = 1,height = 30)
    private String flag1;
    /**
     * 自定义二
     */
    private String flag2;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Date getsTime() {
        return sTime;
    }

    public void setsTime(Date sTime) {
        this.sTime = sTime;
    }

    public String getFlag1() {
        return flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public String getFlag2() {
        return flag2;
    }

    public void setFlag2(String flag2) {
        this.flag2 = flag2;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "WorkerCard{" +
        ", id=" + id +
        ", sName=" + sName +
        ", fName=" + fName +
        ", content=" + content +
        ", email=" + email +
        ", imgUrl=" + imgUrl +
        ", sTime=" + sTime +
        ", flag1=" + flag1 +
        ", flag2=" + flag2 +
        "}";
    }
}
