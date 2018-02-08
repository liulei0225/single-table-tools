package com.qieren.tool.core;

import com.qieren.tool.bo.Column;
import com.qieren.tool.bo.Table;
import com.qieren.tool.util.Util;

import java.sql.Types;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ThinkPad
 * Date: 13-10-22
 * Time: 下午6:09
 * To change this template use File | Settings | File Templates.
 */
public class DbBuild {
    public static List<Table> listTables() {
        List<Table> list = new ArrayList<Table>();
        Config.DB db = Config.instance().getDb();
        List<String> includeTables = db.getIncludeTables();
        boolean hasInc = !includeTables.isEmpty();
        List<String> excludeTables = db.getExcludeTables();
        boolean hasExc = !excludeTables.isEmpty();
        for (Table table : buildTables()) {
            if (hasInc && !includeTables.contains(table.getTableName())) {//不在要的里面
                continue;
            }
            if (hasExc && excludeTables.contains(table.getTableName())) {//在不要的里面
                continue;
            }
            list.add(table);
        }
        return list;
    }

    private static List<Table> buildTables() {
        List<Table> tables = DB.listTable();
        for (Table table : tables) {
            //生成bo及property
            //取table的手动配置,如果没有,就按默认的名称
            //getTableConfig(tableName);
            configTable(table);
        }

        return tables;
    }

    private static void configTable(Table table) {
        Config.Table configTable = Config.instance().getTables().get(table.getTableName());
        //bo名
        String boName = Util.buildName(table.getTableName());
        table.setBoName(Config.getValue(configTable, "boName", boName));
        table.setDisplayName(Config.getValue(configTable, "displayName", boName));
        //表格显示列数
        table.setColumnSize(Config.getValue(configTable, "columnSize", 1));
        List<Column> ids = table.getIds();
        for (Column column : ids) {
            configColumn(column, configTable);
        }
        Comparator<Column> columnComparator = new ColumnComparator();
        //排序
        Collections.sort(ids, columnComparator);
        List<Column> columns = table.getColumns();
        for (Column column : columns) {
            configColumn(column, configTable);
        }
        //排序
        Collections.sort(columns, columnComparator);
    }

    static class ColumnComparator implements Comparator<Column>{
        public int compare(Column o1, Column o2) {
            return o1.getOrder()-o2.getOrder();
        }
    }

    private static void configColumn(Column column, Config.Table configTable) {
        String columnName = column.getColumnName();
        String cn = Util.buildName(columnName);
        String property = Config.getValue(configTable, columnName, "property", Util.lowerFirst(cn));
        column.setProperty(property);
        column.setDisplayName(Config.getValue(configTable, columnName, "displayName", Util.isBlank(column.getRemark()) ? cn : column.getRemark()));
        column.setColSpan(Config.getValue(configTable, columnName, "colSpan", 1));//这个值不能大小table.columnSize
        //如果colSpan不能超过table.columnSize
        if (column.getColSpan() > column.getTable().getColumnSize()) {
            column.setColSpan(column.getTable().getColumnSize());
        }
        int geneType = 0;
        if (column.getPk()) {
            geneType = 3;
        } else if (property.startsWith("createdDate") || property.startsWith("createdBy")) {
            geneType = 1;
        } else if (property.startsWith("updatedDate") || property.startsWith("updatedBy")) {
            geneType = 2;
        }
        column.setGeneType(Config.getValue(configTable, columnName, "geneType", geneType));
        String inputType = Config.getValue(configTable, columnName, "inputType", null);
        if (inputType == null){
            inputType = getInputType(column);
        }
        column.setInputType(inputType);
        String typeClass = Config.getValue(configTable, columnName, "typeClass", null);
        if (typeClass == null) {
            typeClass = getTypeClass(column);
        }
        column.setTypeClass(typeClass);

        if (Config.getValue(configTable, "hasQuery", false)) {
            column.setQuery(Config.getValue(configTable, columnName, "query", false));
        } else {
            //默认4个条件
            if (column.getTable().getColumns().indexOf(column) < 4) {
                column.setQuery(true);
            }
        }
        if (Config.getValue(configTable, "hasListShow", false)) {
            column.setListShow(Config.getValue(configTable, columnName, "listShow", false));
        } else {
            //默认6个显示字段
            if (column.getTable().getColumns().indexOf(column) < 6) {
                column.setListShow(true);
            }
        }
        //其它直接来源于数据的字段
        column.setNullAble(Config.getValue(configTable, columnName, "nullAble", column.getNullAble()));
        column.setColumnSize(Config.getValue(configTable, columnName, "columnSize", column.getColumnSize()));
        column.setExtProp(Config.getValue(configTable, columnName, "extProp", new HashMap<String, String>(0)));
        column.setOrder(Config.getValue(configTable, columnName, "order", column.getOrder()));
    }

    private static String getTypeClass(Column column) {
        int dataType = column.getDataType();
        switch (dataType) {
            case Types.DECIMAL://3, Number
                return column.getDecimalDigits() == 0 ?
                        column.getColumnSize() > 10 ? Long.class.getName() : Integer.class.getName()
                        : Float.class.getName();
            case Types.CHAR:
            case Types.CLOB:
            case Types.VARCHAR://12, varchar, varchar2
                return String.class.getName();
            case Types.DATE://91, date
            case Types.TIMESTAMP: //93, date
                return Date.class.getName();
            case Types.BLOB:
                return Byte.class.getName()+"[]";
        }
        if (column.getDataTypeName().startsWith("NVARCHAR")) {
            return String.class.getName();
        }
        throw new RuntimeException("不支持的数据类型:" + column.getDataTypeName() + "[" + column.getDataType() + "]");
    }

    private static String getInputType(Column column) {
        int dataType = column.getDataType();
        switch (dataType) {
            case Types.DECIMAL://3, Number
                return column.getDecimalDigits() == 0 ? "int" : "float";
            case Types.CHAR:
            case Types.CLOB:
            case Types.VARCHAR://12, varchar, varchar2
                return column.getColumnSize() >= 100 ? "textarea" : "text";
            case Types.DATE://91, date
            case Types.TIMESTAMP: //93, date
                return "date";
            case Types.BLOB:
                return "file";
        }
        if (column.getDataTypeName().startsWith("NVARCHAR")) {
            return column.getColumnSize() >= 100 ? "textarea" : "text";
        }
        throw new RuntimeException("不支持的数据类型:" + column.getTable().getTableName() + "." + column.getColumnName() + ":" + column.getDataTypeName() + "[" + column.getDataType() + "]");
    }

    public static void main(String[] args) {
        /*List<String> list = new ArrayList<String>();
        list.addAll(Arrays.asList("a", "b", "c", "d"));
        list.removeAll(Arrays.asList("d", "b"));
        System.out.println(list);*/
        System.out.println(Util.buildName("1_2_abc_a1_"));
        List<Column> columns = new ArrayList<Column>();
        for (int i = 10; i > 0; i--) {
            Column column = new Column();
            column.setOrder(i);
            columns.add(column);
        }
        for (Column column : columns) {
            System.out.println(column.getOrder());
        }
        Collections.sort(columns, new ColumnComparator());
        for (Column column : columns) {
            System.out.println(column.getOrder());
        }
    }
}
