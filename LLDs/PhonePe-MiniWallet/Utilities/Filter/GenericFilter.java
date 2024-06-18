package Utilities.Filter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class GenericFilter<T> {
    private final String fieldName;
    private final List<FilterType> filterTypes;
    private final Object value;

    public GenericFilter(String fieldName, List<FilterType> filterTypes, Object value) {
        this.fieldName = fieldName;
        this.filterTypes = filterTypes;
        this.value = value;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<T> filter(List<T> list) {
        List<T> filteredList = new ArrayList<>();
        for (T item : list) {
            try {
                Field field = item.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);

                Object fieldValue = field.get(item);

                if (fieldValue instanceof Comparable) {
                    Comparable comparableFieldValue = (Comparable) fieldValue;
                    Comparable comparableValue = (Comparable) value;

                    boolean match = false;
                    for (FilterType comparisonType : this.filterTypes) {
                        switch (comparisonType) {
                            case EQUAL_TO:
                                match = match || comparableFieldValue.compareTo(comparableValue) == 0;
                                break;
                            case GREATER_THAN:
                                match = match || comparableFieldValue.compareTo(comparableValue) > 0;
                                break;
                            case LESS_THAN:
                                match = match || comparableFieldValue.compareTo(comparableValue) < 0;
                                break;
                        }
                    }

                    if (match) {
                        filteredList.add(item);
                    }
                } else {
                    throw new IllegalArgumentException("Field values are not comparable");
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Error accessing field", e);
            }
        }
        return filteredList;
    }
}
