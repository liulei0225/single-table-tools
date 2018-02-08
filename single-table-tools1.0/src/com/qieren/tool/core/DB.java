package com.qieren.tool.core;

import com.qieren.tool.bo.Column;
import com.qieren.tool.bo.Table;
import com.qieren.tool.util.Util;

import java.sql.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ThinkPad
 * Date: 13-10-22
 * Time: 下午3:06
 * To change this template use File | Settings | File Templates.
 */
public class DB {
    private static Connection connection;
    private static String schema;

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        List<Table> tables = listTable();
        for (Table table : tables) {
            System.out.println(table);
            listTableColumn(table.getTableName());
            getFK(table.getTableName());
            getPK(table.getTableName());
        }
    }

    public static List<Table> listTable() {
        try {
            DatabaseMetaData databaseMetaData = getConnection().getMetaData();
            ResultSet rs = databaseMetaData.getTables(null, schema, null, new String[]{"TABLE"});
            String tableName;
            String tableType;
            List<Table> tables = new ArrayList<Table>();
            Table table;
            Config.DB db = Config.instance().getDb();
            List<String> includeTables = db.getIncludeTables();
            boolean hasInc = !includeTables.isEmpty();
            List<String> excludeTables = db.getExcludeTables();
            boolean hasExc = !excludeTables.isEmpty();
            while (rs.next()) {
                tableName = rs.getString("TABLE_NAME").toUpperCase();
                tableType = rs.getString("TABLE_TYPE");
                if (tableType.equalsIgnoreCase("table") && !tableName.contains("$")) {
                    if (hasInc && !includeTables.contains(tableName)) {//不在要的里面
                        continue;
                    }
                    if (hasExc && excludeTables.contains(tableName)) {//在不要的里面
                        continue;
                    }
                    table = new Table();
                    table.setTableName(tableName);
                    table.setColumns(listTableColumn(tableName));
                    tables.add(table);
                }
            }
            return build(tables);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Table> build(List<Table> tables) {
        if (tables.isEmpty()) {
            return tables;
        }
        //TODO qieren 外键集
        Map<String, Column> columnMap = new HashMap<String, Column>();
        /*for (Table table : tables) {
            for (Column column : table.getColumns()) {
                columnMap.put(table.getTableName() + "." + column.getColumnName(), column);
            }
        }*/
        List<Column> ids;
        List<Table> noIdT = new ArrayList<Table>();
        for (Table table : tables) {
            ids = new ArrayList<Column>();
            for (Column column : table.getColumns()) {
                //主键
                if (column.getPk()) {
                    ids.add(column);
                }
                //外键
                column.setFkColumn(columnMap.get(table.getTableName() + "." + column.getColumnName()));
                //与表关联
                column.setTable(table);
            }
            if (ids.isEmpty()) {
                noIdT.add(table);
                System.out.println("Warning: table "+table.getTableName()+" no id, can't generate!");
            } else {
                table.setIds(ids);
                table.getColumns().removeAll(ids);
            }
        }
        tables.removeAll(noIdT);
        return tables;
    }

    private static List<Column> listTableColumn(String tableName) throws SQLException {
        DatabaseMetaData databaseMetaData = getConnection().getMetaData();
        ResultSet rs = databaseMetaData.getColumns(null, schema, tableName, null);
        Map<String, Integer> pks = getPK(tableName);
        Map<String, Map.Entry<String, String>> fks = getFK(tableName);
        List<Column> list = new ArrayList<Column>();
        String columnName;
        String columnTypeName;
        int columnType;
        int columnSize;
        int decimalDigits;
        int nullAble;
        Column column;
        String remark;
        while (rs.next()) {
            columnName = rs.getString("COLUMN_NAME").toUpperCase();
            columnTypeName = rs.getString("TYPE_NAME");
            columnType = rs.getInt("DATA_TYPE");
            columnSize = rs.getInt("COLUMN_SIZE");
            decimalDigits = rs.getInt("DECIMAL_DIGITS");
            nullAble = rs.getInt("NULLABLE");
            remark = rs.getString("REMARKS");
            column = new Column();
            column.setColumnName(columnName);
            column.setDataTypeName(columnTypeName);
            column.setDataType(columnType);
            column.setColumnSize(columnSize);
            column.setDecimalDigits(decimalDigits);
            column.setNullAble(nullAble != 0);
            column.setPk(pks.get(columnName) != null);
            column.setFkTableColumn(fks.get(columnName));
            if (Util.isNotBlank(remark)){
                column.setRemark(remark);
            }
            list.add(column);
            //System.out.println("\t" + columnName + "\t" + columnTypeName + "("+columnType+")" + "\t" + columnSize + "\t" + decimalDigits + "\t" + nullAble);
        }
        return list;
    }

    private static Map<String, Map.Entry<String, String>> getFK(String tableName) throws SQLException {
        DatabaseMetaData databaseMetaData = getConnection().getMetaData();
        ResultSet rs = databaseMetaData.getImportedKeys(null, schema, tableName);
        String fkColumn;
        String pkTable;
        String pkColumn;
        Map<String, Map.Entry<String, String>> fks = new HashMap<String, Map.Entry<String, String>>();
        while (rs.next()) {
            fkColumn = rs.getString("FKCOLUMN_NAME").toUpperCase();
            pkTable = rs.getString("PKTABLE_NAME").toUpperCase();
            pkColumn = rs.getString("PKCOLUMN_NAME").toUpperCase();
            fks.put(fkColumn, new MyEntry(pkTable, pkColumn));
            //System.out.println("\t" + fkColumn + "\t" + pkTable + "\t" + pkColumn);
        }
        return fks;
    }

    static class MyEntry implements Map.Entry<String, String>{
        private String key;
        private String value;

        MyEntry(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public String setValue(String value) {
            this.value = value;
            return value;
        }
    }

    private static Map<String, Integer> getPK(String tableName) throws SQLException {
        DatabaseMetaData databaseMetaData = getConnection().getMetaData();
        ResultSet rs = databaseMetaData.getPrimaryKeys(null, schema, tableName);
        String pkColumn;
        int keySeq;
        Map<String, Integer> pks = new HashMap<String, Integer>();
        while (rs.next()) {
            pkColumn = rs.getString("COLUMN_NAME").toUpperCase();
            keySeq = rs.getInt("KEY_SEQ");
            pks.put(pkColumn, keySeq);
            //System.out.println("\t" + pkColumn + "\t" + keySeq);
        }
        return pks;
    }

    private static int connNum = 0;
    private static Connection getConnection() {
        if (connNum == 0 || connNum == 10) {
            connection = newConnection();
            if (connNum == 10) {
                connNum = 0;
            }
        }
        connNum++;
        return connection;
    }

    private static Connection newConnection() {
        Connection connection;
        try {
            Config.DB db = Config.instance().getDb();
            Class.forName(db.getDbDriver());
            Properties props = new Properties();
            props.put("remarksReporting", "true");
            props.put("user", db.getUserName());
            props.put("password", db.getPassword());
            connection = DriverManager.getConnection(db.getDbUrl(), props);
            schema = db.getUserName().toUpperCase();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
