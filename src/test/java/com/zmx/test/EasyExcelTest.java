package com.zmx.test;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.read.metadata.holder.ReadWorkbookHolder;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * @author : 钟鸣星
 * @date : 2019年12月05日
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class EasyExcelTest {

    @Autowired
    private TestDAO testDAO;

    @Test
    public void test01() throws FileNotFoundException {
        File file = new File("E:\\___2019-12-03 (2).xls");
        FileInputStream fileInputStream = new FileInputStream(file);
        ExcelListener excelListener = new ExcelListener();
        ExcelReader excelReader = new ExcelReader(fileInputStream, ExcelTypeEnum.XLS, null, excelListener);
        excelReader.read();
        int size = excelReader.getSheets().size();
        List<List<String>> datas = excelListener.getDatas();
    }

    @Test
    public void test02(){

    }

}
