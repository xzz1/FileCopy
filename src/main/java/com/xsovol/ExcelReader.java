package com.xsovol;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;

/**
 *  读取解析excel
 */
public class ExcelReader {
    public static void main(String[] args) {
        excelReader4Test();
    }

    public static void excelReader4Test() {
        try {
            // 打开 Excel 文件
            FileInputStream fis = new FileInputStream("/Users/user/Desktop/excel-read-4-test.xlsx");

            // 创建一个工作簿对象
            Workbook workbook = WorkbookFactory.create(fis);

            // 获取第一个工作表
            Sheet sheet = workbook.getSheetAt(0);

            // 获取第四行
            Row row = sheet.getRow(3); // 第四行的索引是3，因为索引从0开始

            // 获取第三列（索引为2）的单元格
            Cell cell = row.getCell(2); // 第三列的索引是2，因为索引从0开始

            // 读取单元格的数据
            String data = cell.getStringCellValue();

            System.out.println("第三列第四行的数据是: " + data);

            // 关闭文件流
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
