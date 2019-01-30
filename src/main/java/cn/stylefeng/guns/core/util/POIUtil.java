package cn.stylefeng.guns.core.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
public class POIUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(POIUtil.class);

    public static final String XLSX = ".xlsx";
    public static final String XLS=".xls";

    /**
     * Excel导出
     * @param title 导出Excel文件名称
     * @param rowList 第一个List为表头，其余行为表数据
     * @param resp HttpServletResponse 对象
     * @throws IOException
     */
    public static void writeExcel(String title,List<List<Object>> rowList,HttpServletResponse resp) throws IOException{
        if (resp == null) {
            throw new NullPointerException("the HttpServletResponse is null");
        }
        HSSFWorkbook book = warpSingleWorkbook(title, rowList, false);
        // 响应客户端
        String filename = new String(title.getBytes("UTF-8"), "ISO-8859-1");
        resp.reset();
        resp.setHeader("Content-disposition", "attachment; filename=" + filename +XLS);
        resp.setContentType("application/vnd.ms-excel;charset=UTF-8");
        // 输出Excel文件
        book.write(resp.getOutputStream());
        book.close();
    }

    /**
     * Excel导出设置Workbook
     * @param title 导出Excel文件名称
     * @param rowList 第一个List为表头，其余行为表数据
     * @param downLoadPic 是否下载图片
     * @throws IOException
     */
    public static HSSFWorkbook warpSingleWorkbook(String title,List<List<Object>> rowList, Boolean downLoadPic) throws IOException {
        String filename = title;
        if (StringUtils.isBlank(title)) {
            filename = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
        }
        if (rowList == null || rowList.isEmpty()) {
            throw new NullPointerException("the row list is null");
        }
        HSSFWorkbook book = new HSSFWorkbook();
        // 创建表
        HSSFSheet sheet = book.createSheet(filename);
        // 设置单元格默认宽度为15个字符
        sheet.setDefaultColumnWidth(15);
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // 设置表头样式
        HSSFCellStyle style = book.createCellStyle();
        // 设置居左
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        // 检测表头数据（表头不允许数据为空）
        List<Object> head = rowList.get(0);
        for (Object key : head) {
            if (StringUtils.isBlank(key.toString())) {
                book.close();
                throw new NullPointerException("there is a blank exist head row");
            }
        }
        // 写数据
        int size = rowList.get(0).size();
        for (int i = 0; i < rowList.size(); i++) {
            List<Object> row = rowList.get(i);
            if (row == null || row.isEmpty()) {
                book.close();
                throw new NullPointerException("the "+(i+1)+"th row is null");
            }
            if (size != row.size()) {
                book.close();
                throw new IllegalArgumentException("the cell number of "+(i+1)+"th row is different form the first");
            }
            HSSFRow sr = sheet.createRow(i);
            for (int j = 0; j < row.size(); j++) {
                if (downLoadPic && i > 0 && j == 0) {
                    sr.setHeight((short) (800));
                    drawPictureInfoExcel(book, patriarch, i, row.get(0).toString());
                } else {
                    setExcelValue(sr.createCell(j), row.get(j), style);
                }
            }
        }
        return book;
    }


    private static void drawPictureInfoExcel(HSSFWorkbook wb,HSSFPatriarch patriarch,int rowIndex,String pictureUrl){
        //rowIndex代表当前行
        try {

            if(StringUtils.isNoneBlank(pictureUrl)) {
                URL url = new URL(pictureUrl);
                //打开链接
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                //设置请求方式为"GET"
                conn.setRequestMethod("GET");
                //超时响应时间为5秒
                conn.setConnectTimeout(5 * 1000);
                //通过输入流获取图片数据
                InputStream inStream = conn.getInputStream();
                //得到图片的二进制数据，以二进制封装得到数据，具有通用性
                byte[] data = readInputStream(inStream);
                //anchor主要用于设置图片的属性
                HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 250,(short) 0, rowIndex, (short) 0, rowIndex);
                //Sets the anchor type （图片在单元格的位置）
                //0 = Move and size with Cells, 2 = Move but don't size with cells, 3 = Don't move or size with cells.
                anchor.setAnchorType(3);
                patriarch.createPicture(anchor, wb.addPicture(data, HSSFWorkbook.PICTURE_TYPE_JPEG));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static byte[] pictureTobyte(String pictureUrl){
        //rowIndex代表当前行
        byte[] data = null;
        try {
            if (StringUtils.isNoneBlank(pictureUrl)) {
                URL url = new URL(pictureUrl);
                //打开链接
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //设置请求方式为"GET"
                conn.setRequestMethod("GET");
                //超时响应时间为5秒
                conn.setConnectTimeout(5 * 1000);
                //通过输入流获取图片数据
                InputStream inStream = conn.getInputStream();
                //得到图片的二进制数据，以二进制封装得到数据，具有通用性
                data = readInputStream(inStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    private static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while( (len=inStream.read(buffer)) != -1 ){
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }

    /**
     * 设置Excel浮点数可做金额等数据统计
     * @param cell 单元格类
     * @param value 传入的值
     */
    public static void setExcelValue(HSSFCell cell, Object value, HSSFCellStyle style){
        // 写数据
        if (value == null) {
            cell.setCellValue("");
        }else {
            if (value instanceof Integer || value instanceof Long) {
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                cell.setCellValue(Long.valueOf(value.toString()));
            } else if (value instanceof BigDecimal) {
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                cell.setCellValue(((BigDecimal)value).setScale(2, RoundingMode.HALF_UP).doubleValue());
            } else {
                cell.setCellValue(value.toString());
            }
            cell.setCellStyle(style);
        }
    }
}
