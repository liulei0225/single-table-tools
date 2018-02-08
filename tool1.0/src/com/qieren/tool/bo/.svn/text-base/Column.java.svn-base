package com.qieren.tool.bo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ThinkPad
 * Date: 13-10-22
 * Time: 下午4:40
 * To change this template use File | Settings | File Templates.
 */
public class Column {
    private Table table;
    /**
     * 字段
     */
    private String columnName;
    /**
     * bo属性
     */
    private String property;
    /**
     * 主键
     */
    private boolean pk;
    /**
     * 外键
     */
    private Column fkColumn;
    /**
     * 数据类型
     */
    private String dataTypeName;

    private int dataType;

    private String remark;

    private String displayName;
    /**
     * 输入类型
     */
    private String inputType;

    private String typeClass;

    private int columnSize;
    private int decimalDigits;
    private boolean nullAble;

    /**
     * 生成类型,1:新增时系统自动填值,以created开头的字段,2:修改时系统自动填值,以updated开关的字段,0:用户输入, 3:不用生成,如id
     */
    private int geneType;

    //跨列
    private int colSpan;
    //跨行
    //private int rowSpan;

    private boolean query;

    private boolean listShow;

    private int order = Integer.MAX_VALUE;

    private Map.Entry<String, String> fkTableColumn;

    private Map<String, String> extProp = Collections.emptyMap();

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public boolean getPk() {
        return pk;
    }

    public void setPk(boolean pk) {
        this.pk = pk;
    }

    public Column getFkColumn() {
        return fkColumn;
    }

    public void setFkColumn(Column fkColumn) {
        this.fkColumn = fkColumn;
    }

    public String getDataTypeName() {
        return dataTypeName;
    }

    public void setDataTypeName(String dataTypeName) {
        this.dataTypeName = dataTypeName;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public int getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(int decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public boolean getNullAble() {
        return nullAble;
    }

    public void setNullAble(boolean nullAble) {
        this.nullAble = nullAble;
    }

    public int getGeneType() {
        return geneType;
    }

    public void setGeneType(int geneType) {
        this.geneType = geneType;
    }

    public Map.Entry<String, String> getFkTableColumn() {
        return fkTableColumn;
    }

    public void setFkTableColumn(Map.Entry<String, String> fkTableColumn) {
        this.fkTableColumn = fkTableColumn;
    }

    public int getColSpan() {
        return colSpan;
    }

    public void setColSpan(int colSpan) {
        this.colSpan = colSpan;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    /*public int getRowSpan() {
        return rowSpan;
    }

    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
    }*/

    public String getTypeClass() {
        return typeClass;
    }

    public void setTypeClass(String typeClass) {
        this.typeClass = typeClass;
    }

    public boolean isQuery() {
        return query;
    }

    public void setQuery(boolean query) {
        this.query = query;
    }

    public boolean isListShow() {
        return listShow;
    }

    public void setListShow(boolean listShow) {
        this.listShow = listShow;
    }

    public Map<String, String> getExtProp() {
        return extProp;
    }

    public void setExtProp(Map<String, String> extProp) {
        this.extProp = extProp;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
