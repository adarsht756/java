import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.Column;
import Model.RecordEntity;

public class Database {
    private String dbName;
    private Map<String, Table> tables;

    public Database(String dbName) {
        this.dbName = dbName;
        this.tables = new HashMap<String, Table>();
        System.out.println(dbName + " created successfully");
    }

    public void addRecordInTable(String tableName, List<RecordEntity> record) {
        if (tables.containsKey(tableName)) {
            for (Map.Entry<String, Table> entry: tables.entrySet()) {
                if (entry.getKey() == tableName) {
                    Table table = entry.getValue();
                    if (table.addRecord(record)) {
                        System.out.println("Record entered successfully");
                        tables.put(tableName, table);
                    } else
                        System.out.println("hence error occured while creating record");
                    break;
                }
            }
        } else System.out.println("Table " + tableName + " not found");
    }

    public void createTable(String tableName, List<Column> columns) {
        Table table = new Table(tableName, columns);
        this.tables.put(tableName, table);
    }

    public void addColumnInTable(String tableName, Column column) {
        if (tables.containsKey(tableName)) {
            for (Map.Entry<String, Table> entry: tables.entrySet()) {
                if (entry.getKey() == tableName) {
                    Table table = entry.getValue();
                    table.addColumn(column);
                    tables.put(tableName, table);
                    System.out.println("Column " + column.getColumnName() + " added successfully");
                    break;
                }
            }
        } else {
                System.out.println("Table " + tableName + " not found");
        }
    }

    public void deleteColumnInTable(String tableName, String columnName) {
        if (tables.containsKey(tableName)) {
            for (Map.Entry<String, Table> entry: tables.entrySet()) {
                if (entry.getKey() == tableName) {
                    Table table = entry.getValue();
                    if (table.deleteColumn(columnName)) {
                        tables.put(tableName, table);
                        System.out.println("Column " + columnName + " deleted successfully");
                    } else {
                        System.out.println("Can't find column in table " + tableName);
                    }
                    break;
                }
            }
        } else
            System.out.println("Table " + tableName + " not found");
    }

    public void printData(String tableName) {
        if (tables.containsKey(tableName)) {
            tables.get(tableName).printTableData();
        } else
            System.out.println("Table " + tableName + " not found");
        System.out.println();
    }

    public void printDataWithFilter(String tableName, RecordEntity recordEntity) {
        if (tables.containsKey(tableName)) {
            tables.get(tableName).printTableDataWithFilter(recordEntity);
        } else
            System.out.println("Table " + tableName + " not found");
    }

    public String getDbName() {
        return this.dbName;
    }
}