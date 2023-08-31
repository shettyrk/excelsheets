package com.example.excelsheets.utils;



import com.example.excelsheets.UserActionLogDTO;

import java.util.ArrayList;
import java.util.List;

public class ExportConfig {
    private int sheetIndex;
    private int startRow;
    private Class<?> dataClass;
    private List<CellConfig> cellExportConfigList;

    public ExportConfig() {
        this.cellExportConfigList = new ArrayList<>();
    }

    public int getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public Class<?> getDataClass() {
        return dataClass;
    }

    public void setDataClass(Class<?> dataClass) {
        this.dataClass = dataClass;
    }

    public List<CellConfig> getCellExportConfigList() {
        return cellExportConfigList;
    }

    public void setCellExportConfigList(List<CellConfig> cellExportConfigList) {
        this.cellExportConfigList = cellExportConfigList;
    }

    public static final ExportConfig customerExport;
    static {
        customerExport = new ExportConfig();
        customerExport.setSheetIndex(0);
        customerExport.setStartRow(1);
        customerExport.setDataClass(UserActionLogDTO.class);
        List<CellConfig> userActionLogs = new ArrayList<>();
        userActionLogs.add(new CellConfig(0, "VDMS-ID"));
        userActionLogs.add(new CellConfig(1, "action"));
        userActionLogs.add(new CellConfig(2, "created_timestamp"));
        userActionLogs.add(new CellConfig(3, "email"));
        userActionLogs.add(new CellConfig(4, "message"));
        userActionLogs.add(new CellConfig(5, "status"));
        userActionLogs.add(new CellConfig(6, "type"));
        customerExport.setCellExportConfigList(userActionLogs);
    }
}
