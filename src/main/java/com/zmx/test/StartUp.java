package com.zmx.test;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author : 钟鸣星
 * @date : 2019年12月05日
 */
@SpringBootApplication
//MapperScan必须要指定包名或者类名，否则报错
@MapperScan(basePackages = {"com.zmx.test"})
@RestController
@RequestMapping("/")
public class StartUp {
    public static void main(String[] args) {
        SpringApplication.run(StartUp.class, args);
    }

    @Autowired
    private TestDAO testDAO;
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private ShopDAO shopDAO;

    private ApplicationContext context;

    /**
     * 传统的POI百万数据写出示例
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/test")
    public String findAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet();
        int count = testDAO.fincCount();
        String[] fields = {"序号", "名称", "地址", "头衔", "备注"};
        String[] values = {"id", "name", "addr", "title", "note"};
        int startIndex = 0;
        int endIndex = 10000;

        XSSFRow titleRow = sheet.createRow(0);

        for (int i = 0; i < fields.length; i++) {
            String field = fields[i];
            XSSFCell cell = titleRow.createCell(i);
            cell.setCellValue(field);
        }
        int m = 0;
        //每次取1000行数据
        //纯粹的POI百万级写出，一个是IO的写入到后期很慢，另一个是越往后会出现OOM内存溢出
        while (startIndex < 1000000) {
            long sqlStartTime = System.currentTimeMillis();
            //纯粹的使用limit做分页是不行的，越往后，sql执行的时间越长
            List<Map> list = testDAO.findAll(startIndex, endIndex);
            long sqlEndTime = System.currentTimeMillis();
            long sqlCountTime = (sqlEndTime - sqlStartTime) / 1000;
            System.out.println("sql执行时长------>" + sqlCountTime);
            long startTime = System.currentTimeMillis();
            for (int j = 0; j < list.size(); j++) {
                XSSFRow row = sheet.createRow(1 + m);
                Map map = list.get(j);
                for (int k = 0; k < map.size(); k++) {
                    String columnName = values[k];
                    //不断的创建临时对象
                    XSSFCell cell = row.createCell(k);
                    String value = map.get(columnName) == null ? "" : map.get(columnName).toString();
                    //并设置对象的值，让该对象变大了，导致内存溢出
                    cell.setCellValue(value);
                }
                m++;
            }
            long endTime = System.currentTimeMillis();
            long countTime = (endTime - startTime) / 1000;
            System.out.println("写入IO的时长-------->" + countTime);
            startIndex = startIndex + 10000;
            list.clear();
        }

        //写出数据
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        response.setContentType("multipart/form-data");
        String downloadFileName = "测试表" + "_" + sdf.format(date) + ".xlsx";
        response.setHeader("Content-Disposition", "attachment;fileName=" + new String(downloadFileName.getBytes("ISO8859-1"), "UTF-8"));
        ServletOutputStream out = response.getOutputStream();
        wb.write(out);
        out.flush();
        out.close();
        return "调用成功";
    }

    /**
     * 采用EasyExcel一张表多张sheet写入方法
     *
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/testEasyExcel")
    @ResponseBody
    public String testEasyExcel(HttpServletResponse response) throws IOException {

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("测试", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        //单元格写入拦截器，可在其中做数据的校验工作
        MyCellWriteHandler myCellWriteHandler = new MyCellWriteHandler();
        //总写入对象
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
        //产品表所有数据写出
        writeExcel(productDAO, excelWriter, ProductEntity.class, "产品表", myCellWriteHandler, 0);
        //商品表所有数据写出
        writeExcel(shopDAO, excelWriter, ShopEntity.class, "商品表", myCellWriteHandler, 1);
        //finish()方法很重要，没有写的话，会导致下载的excel表有问题
        excelWriter.finish();
        return "调用成功";
    }

    /**
     * 通用的写出方法，其中ExportExcelBaseDAO是做多态转换，是通用方法的核心,多态的底层还是具体的对象,并未被抹除掉
     *
     * @param dao
     * @param excelWriter
     */
    private void writeExcel(ExportExcelBaseDAO dao, ExcelWriter excelWriter, Class clazz, String tableNameCN,
                            MyCellWriteHandler myCellWriteHandler, Integer sheetNo) {
        //TODO 通过表名获取校验信息

        //设置该表的校验信息
        myCellWriteHandler.setVerifyMap(null);

        WriteSheet sheet = EasyExcel.writerSheet(sheetNo, tableNameCN).head(clazz).
                registerWriteHandler(myCellWriteHandler).build();

        int count = dao.findCount();
        int startIndex = 0;
        int offset = 100;
        while (startIndex < count) {
            //分页获取数据，批量写入
            List list = dao.findAll(startIndex, offset);
            excelWriter.write(list, sheet);
            startIndex = startIndex + 100;
        }
        //清除该表的校验信息
        myCellWriteHandler.clearVerifyMap();
    }

}
