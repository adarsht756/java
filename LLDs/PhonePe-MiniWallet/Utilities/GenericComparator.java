package Utilities;

import java.lang.reflect.Field;
import java.util.Comparator;

public class GenericComparator<T> implements Comparator<T> {
    private final String fieldName;

    public GenericComparator(String fieldName) {
        this.fieldName = fieldName;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public int compare(T o1, T o2) {
        try {
            Field field = o1.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            Object value1 = field.get(o1);
            Object value2 = field.get(o2);

            if (value1 instanceof Comparable && value2 instanceof Comparable) {
                return ((Comparable) value1).compareTo(value2);
            } else {
                throw new IllegalArgumentException("Field values are not comparable");
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Error accessing field", e);
        }
    }
}
