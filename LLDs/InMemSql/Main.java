import java.util.ArrayList;
import java.util.List;

import Model.Column;
import Model.IntColumn;
import Model.LengthConstraint;
import Model.MandatoryConstraint;
import Model.MinValConstraint;
import Model.RecordEntity;
import Model.StringColumn;

public class Main {
    public static void main(String[] args) {
        Database DB = new Database("Test DB");
        List<Column> columns = new ArrayList<Column>();
        columns.add(new StringColumn("first_name", new LengthConstraint(4)));
        columns.add(new StringColumn("last_name", new LengthConstraint(4)));
        columns.add(new IntColumn("age", new MinValConstraint(20)));
        columns.add(new StringColumn("phone_number", new LengthConstraint(10, 10)));
        columns.add(new StringColumn("address", new MandatoryConstraint()));

        DB.createTable("Person", columns);
        DB.printData("Person");
        DB.printData("Persons");

        DB.addRecordInTable("Person", List.of(new RecordEntity("first_name", "Adarsh"),
                new RecordEntity("last_name", "Tiwari"),
                new RecordEntity("age", 23),
                new RecordEntity("phone_number", "9760851121"),
                new RecordEntity("address", "Ejipura BLR")));

        DB.addRecordInTable("Person", List.of(new RecordEntity("first_name", "Komal"),
                new RecordEntity("last_name", "Tiwari"),
                new RecordEntity("age", 24),
                new RecordEntity("phone_number", "9760851121"),
                new RecordEntity("address", "Pantnagar")));

                DB.addRecordInTable("Person", List.of(new RecordEntity("first_name", "Sanskruti"),
                new RecordEntity("last_name", "Tiwari"),
                new RecordEntity("age", 24),
                new RecordEntity("phone_number", "9760851121"),
                new RecordEntity("address", "Pune, MH")));

        DB.addRecordInTable("Person", List.of(new RecordEntity("first_name", "Sanskruti"),
                new RecordEntity("last_name", "Tiwari"),
                new RecordEntity("age", 24),
                new RecordEntity("phone_number", "9760851121"),
                new RecordEntity("address", null)));

        DB.addRecordInTable("Person", List.of(new RecordEntity("first_name", "Temp"),
                new RecordEntity("last_name", "Tiwari"),
                new RecordEntity("age", 19),
                new RecordEntity("phone_number", "9760851121"),
                new RecordEntity("address", "Pune, MH")));

        DB.printData("Person");

        DB.addColumnInTable("Person", new StringColumn("adhaar", null));

        DB.addRecordInTable("Person", List.of(new RecordEntity("first_name", "Temp2"),
                new RecordEntity("last_name", "Tiwari"),
                new RecordEntity("age", 24),
                new RecordEntity("phone_number", "9760851121"),
                new RecordEntity("address", "Pune, MH")));

        DB.addRecordInTable("Person", List.of(new RecordEntity("first_name", "Temp2"),
                new RecordEntity("last_name", "Tiwari"),
                new RecordEntity("age", 24),
                new RecordEntity("phone_number", "9760851121"),
                new RecordEntity("adhaar", "827689982341"),
                new RecordEntity("address", "Pune, MH")));

        DB.printData("Person");

        DB.deleteColumnInTable("Person", "adhaar");
        DB.printData("Person");
        DB.printDataWithFilter("Person", new RecordEntity("last_name", "tiwari"));
        DB.printDataWithFilter("Person", new RecordEntity("first_name", "Adarsh"));
    }
}