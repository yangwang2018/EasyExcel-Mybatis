package com.zmx.test;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.style.AbstractCellStyleStrategy;
import org.apache.poi.ss.usermodel.*;

import java.util.List;
import java.util.Map;

/**
 * 对每个单元格的写入做拦截，可在afterCellDispose方法中进行数据的校验工作
 * @author : 钟鸣星
 * @date : 2019年12月09日
 */
public class MyCellWriteHandler extends AbstractCellStyleStrategy implements CellWriteHandler {

    private Map<Integer, String> verifyMap;
    private CellStyle titleColorStyle;
    private CellStyle dataIsNullErrorStyle;
    private CellStyle dataTypeErrorStyle;
    private CellStyle outlierErrorStyle;
    private CellStyle plusErrorStyle;
    private boolean initFlag = true;

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer integer, Integer relativeRowIndex, Boolean isHead) {
        if(initFlag) {
            Sheet sheet = writeSheetHolder.getSheet();
            //设置该表整体的单元格宽度
            sheet.setDefaultColumnWidth(18);
            Workbook workbook = sheet.getWorkbook();
            //初始化单元格样式
            initCellStyle(workbook);
            initFlag = false;
            System.out.println("初始化表格样式成功------------>");
        }
    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
    }

    /**
     *
     * @param writeSheetHolder
     * @param writeTableHolder
     * @param list 是单元格数值
     * @param cell
     * @param head 字段名称
     * @param relativeRowIndex 行数,注意标题行和正式数据的第一行的index索引值均为0
     * @param isHead 是否为标题行
     */
    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> list, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        if(isHead){
            //设置表头样式,必须要调用该方法才能实现表头样式的设置
            setHeadCellStyle(cell, head, relativeRowIndex);
            return;
        }
        Integer columnIndex = head.getColumnIndex();
        boolean isNeedVerify = verifyMap.get(columnIndex) == null;
        if(isNeedVerify){
            //通过字段下标获取该字段需要校验的数据
            String verifyInfo = verifyMap.get(columnIndex);
            //获取字段的数据信息
            String value = list.get(0).toString();
            //TODO 进行校验工作，根据不同的校验结果，设置不同单元格的样式

        }
    }

    public void setVerifyMap(Map<Integer, String> verifyMap) {
        this.verifyMap = verifyMap;
    }

    public void clearVerifyMap(){
        verifyMap.clear();
    }

    /**如下方法是继承AbstractCellStyleStrategy需要实现，但是无法直接调用，可以间接调用，可能是该类同时实现了CellWriteHandler接口的冲突产生的原因**/

    @Override
    protected void initCellStyle(Workbook workbook) {
        //表头样式
        titleColorStyle = workbook.createCellStyle();
        titleColorStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        //必填，如果不填写的话，设置的单元格背景颜色将不起作用
        titleColorStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //字体的排列方式，居中、左对齐、右对齐
        titleColorStyle.setAlignment(HorizontalAlignment.LEFT);

        //数据为空样式
        dataIsNullErrorStyle = workbook.createCellStyle();
        dataIsNullErrorStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        dataIsNullErrorStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    }

    @Override
    protected void setHeadCellStyle(Cell cell, Head head, Integer relativeRowIndex) {
       cell.setCellStyle(titleColorStyle);
    }

    @Override
    protected void setContentCellStyle(Cell cell, Head head, Integer relativeRowIndex) {

    }

}
