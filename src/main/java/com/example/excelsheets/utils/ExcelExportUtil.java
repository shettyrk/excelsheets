package com.example.excelsheets.utils;


import com.example.excelsheets.UserActionLogDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExcelExportUtil {
    public static ByteArrayOutputStream createExcel(List<UserActionLogDTO> logs) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("User Action Logs");


        XSSFFont titleFont = workbook.createFont();
        titleFont.setFontName("Arial");
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short)14);

        XSSFCellStyle titleCellStyle = workbook.createCellStyle();
        titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
        titleCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleCellStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.index);
        titleCellStyle.setBorderBottom(BorderStyle.MEDIUM);
        titleCellStyle.setBorderRight(BorderStyle.MEDIUM);
        titleCellStyle.setBorderTop(BorderStyle.MEDIUM);
        titleCellStyle.setFont(titleFont);
        titleCellStyle.setWrapText(true);

        XSSFFont dataFont = workbook.createFont();
        dataFont.setFontName("Arial");
        dataFont.setBold(false);
        dataFont.setFontHeightInPoints((short) 12);

        XSSFCellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setWrapText(true);
        dataStyle.setAlignment(HorizontalAlignment.CENTER);
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setFont(dataFont);



        insertFieldNameAsTitleToWorkbook(ExportConfig.customerExport.getCellExportConfigList(),sheet,titleCellStyle);
        int rowIdx = 1;
        for (UserActionLogDTO log : logs) {
            sheet.autoSizeColumn(rowIdx);
            Row row = sheet.createRow(rowIdx);
            row.setRowStyle(dataStyle);
            row.createCell(0).setCellValue(log.getId());
            row.createCell(1).setCellValue(log.getAction());
            if (log.getCreated_timestamp() != null) {
                double timestampValue = log.getCreated_timestamp().doubleValue();
                Date date = new Date((long) timestampValue);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = dateFormat.format(date);
                row.createCell(2).setCellValue(formattedDate);
            }
            row.createCell(3).setCellValue(log.getEmail());
            row.createCell(4).setCellValue(log.getMessage());
            row.createCell(5).setCellValue(log.getStatus());
            row.createCell(6).setCellValue(log.getType());
            rowIdx++;
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream;
    }

    private static <T> void insertFieldNameAsTitleToWorkbook(List<CellConfig> cellConfigs, Sheet isheet, CellStyle titleCellStyle){
        int currentRow = isheet.getTopRow();
        Row row = isheet.createRow(currentRow);
        int i=0;
        isheet.autoSizeColumn(currentRow);
        for(CellConfig cellConfig:  cellConfigs){
            Cell currentCell = row.createCell(i);
            String fieldName = cellConfig.getFieldName();
            currentCell.setCellValue(fieldName);
            currentCell.setCellStyle(titleCellStyle);
            isheet.autoSizeColumn(i);
            i++;
        }
    }
}
