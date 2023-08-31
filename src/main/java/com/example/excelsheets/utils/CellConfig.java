package com.example.excelsheets.utils;

public class CellConfig {
    private int columnIndex;
    private String fieldName;

    public CellConfig(int columnIndex, String fieldName) {
        this.columnIndex = columnIndex;
        this.fieldName = fieldName;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
