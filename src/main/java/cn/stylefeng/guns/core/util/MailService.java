package cn.stylefeng.guns.core.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    // 获取application.properties中的值赋给from
//    @Value("${spring.mail.username}")
    private static final String from="2498876294@qq.com";

    /**
     * @param toUsers 收件人
     * @param ccUsers 抄送人
     * @param title   标题
     * @param text    内容
     * @param imgPath 图片路径
     * @param imgId   图片id
     * @param sign   签名
     * @return true-成功，false-失败
     */
    public boolean sendImgMail(String[] toUsers, String[] ccUsers, String title, String text, String imgPath, String imgId,String sign) {
        boolean isSend = true;
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);// 发件人
            helper.setTo(toUsers);// 收件人
            //helper.setCc(ccUsers);//抄送人，使用Cc还是Bcc大家根据具体场景自己选择
//            helper.setBcc(ccUsers);//秘密抄送（发件人，收件人，抄送人都不会看到抄送给谁了）
            helper.setSubject(title);// 标题
            /* 创建html内容，若不创建html标签,则图片会以附件的形式发送，而并非直接以内容显示 */
            String content = "<html><body>" + text + "<img src=\'cid:" + imgId + "\'></img>" + sign + "</body></html>";
            helper.setText(content, true);// text：内容，true:为HTML邮件（false则为普通文本邮件）
            File file = new File(imgPath);// 创建图片文件
            FileSystemResource resource = new FileSystemResource(file);
            helper.addInline(imgId, resource);
            mailSender.send(mimeMessage);// 发送邮件
        } catch (MessagingException e) {
            isSend = false;
            e.printStackTrace();
        }
        return isSend;
    }

    public static void inputstreamtofile(InputStream ins,File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}