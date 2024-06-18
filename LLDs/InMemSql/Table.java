import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import Model.Column;
import Model.IntColumn;
import Model.MandatoryConstraint;
import Model.RecordEntity;
import Model.StringColumn;

public class Table {
    private String name;
    private LinkedHashMap<String, Column> columns; // used LinkedHashMap to preserve order of elements inserted
    private List<LinkedHashMap<String, Object>> data;

    public Table(String name, List<Column> columns) {
        this.data = new ArrayList<LinkedHashMap<String, Object>>();
        this.name = name;
        if (this.columns == null)
            this.columns = new LinkedHashMap<String, Column>();
        for (Column column : columns)
            this.columns.put(column.getColumnName(), column);
    }

    public boolean addRecord(List<RecordEntity> record) {
        if (record.size() != columns.size()) {
            System.out.print("Incomplete argument, ");
            return false;
        }
        boolean allValuesSatisfy = true;
        LinkedHashMap<String, Object> newRecord = new LinkedHashMap<String, Object>();
        for (RecordEntity entity : record) {
            if (entity.entityValue == null) {
                Column column = columns.get(entity.entityName);
                if (column.hasConstraint && !(column.constraint instanceof MandatoryConstraint)) {
                    continue;
                } else {
                    System.out.print(entity.entityName + " cannot be Null, ");
                    return false;
                }
            }
            else if (entity.entityValue instanceof String) {
                String value = (String) entity.entityValue;
                StringColumn column = (StringColumn) columns.get(entity.entityName);
                if (column == null)
                    return false;
                if (column.hasConstraint == true) {
                    if (!column.constraint.validate(value.length())) {
                        allValuesSatisfy = false;
                        System.out.print("Entered " + entity.entityName + " is invalid, ");
                        break;
                    }
                }
            } else if (entity.entityValue instanceof Integer) {
                Integer value = (Integer) entity.entityValue;
                IntColumn column = (IntColumn) columns.get(entity.entityName);
                if (column == null)
                    return false;
                if (column.hasConstraint == true) {
                    if (!column.constraint.validate(value)) {
                        allValuesSatisfy = false;
                        System.out.print("Entered " + entity.entityName + " is invalid, ");
                        break;
                    }
                }
            }
            newRecord.put(entity.entityName, entity.entityValue);
        }
        if (allValuesSatisfy) {
            data.add(newRecord);
            return true;
        }
        return false;
    }

    public String tableName() {
        return this.name;
    }

    public void addColumn(Column column) {
        this.columns.put(column.getColumnName(), column);
        for (LinkedHashMap<String, Object> record : data) {
            if (column instanceof StringColumn) {
                record.put(column.getColumnName(), "");
            } else if (column instanceof IntColumn) {
                record.put(column.getColumnName(), 0);
            }
        }
    }

    public boolean deleteColumn(String columnName) {
        if (columns.containsKey(columnName)) {
            for (LinkedHashMap<String, Object> record : data) {
                for (Map.Entry<String, Object> rec : record.entrySet()) {
                    if (rec.getKey() == columnName) {
                        record.remove(columnName);
                        break;
                    }
                }
            }
            columns.remove(columnName);
            return true;
        }
        return false;
    }

    public void deleteAllColumns() {
        this.columns.clear();
        this.data.clear();
        System.out.println("All columns removed");
    }

    public void printRecord(LinkedHashMap<String, Object> record) {
        for (Map.Entry<String, Object> entry : record.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        System.out.println();
    }

    public void printTableData() {
        if (data.size() == 0) {
            System.out.println("Table is empty");
            return;
        }
        for (LinkedHashMap<String, Object> record : data)
            printRecord(record);
    }

    public void printTableDataWithFilter(RecordEntity filter) {
        if (data.size() == 0) {
            System.out.println("Table is empty");
            return;
        }
        System.out.println("Results where " + filter.entityName + " equals to " + filter.entityValue + ":");
        boolean dataFound = false;
        for (LinkedHashMap<String, Object> record : data) {
            for (Map.Entry<String, Object> entry : record.entrySet()) {
                if (entry.getKey() == filter.entityName && entry.getValue() == filter.entityValue) {
                    printRecord(record);
                    dataFound = true;
                }
            }
        }
        if (!dataFound)
            System.out.println("Empty results where " + filter.entityName + " equals to " + filter.entityValue + "\n");
    }
}