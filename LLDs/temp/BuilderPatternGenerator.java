import java.util.Map;

public class BuilderPatternGenerator {

    public static String generateBuilderPattern(String className, Map<String, String> attributes) {
        StringBuilder builder = new StringBuilder();

        // Class definition
        builder.append("public class ").append(className).append(" {\n\n");

        // Attributes
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            builder.append("    private ").append(entry.getValue()).append(" ").append(entry.getKey()).append(";\n");
        }
        builder.append("\n");

        // Private constructor
        builder.append("    private ").append(className).append("(" + className + "Builder builder) {\n");
        for (String attr : attributes.keySet()) {
            builder.append("        this.").append(attr).append(" = builder.").append(attr).append(";\n");
        }
        builder.append("    }\n\n");

        // Getter methods
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            builder.append("    public ").append(entry.getValue()).append(" get")
                    .append(capitalize(entry.getKey())).append("() {\n")
                    .append("        return ").append(entry.getKey()).append(";\n")
                    .append("    }\n\n");
        }

        // Builder class
        builder.append("    public static class " + className + "Builder {\n\n");

        // Builder attributes
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            builder.append("        private ").append(entry.getValue()).append(" ").append(entry.getKey())
                    .append(";\n");
        }
        builder.append("\n");

        // Builder methods
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            builder.append("        public " + className + "Builder ").append(entry.getKey()).append("(")
                    .append(entry.getValue()).append(" ").append(entry.getKey()).append(") {\n")
                    .append("            this.").append(entry.getKey()).append(" = ")
                    .append(entry.getKey()).append(";\n")
                    .append("            return this;\n")
                    .append("        }\n\n");
        }

        // Build method
        builder.append("        public ").append(className).append(" build() {\n")
                .append("            return new ").append(className).append("(this);\n")
                .append("        }\n");

        builder.append("    }\n");

        builder.append("}");

        return builder.toString();
    }

    private static String capitalize(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static void main(String[] args) {
        // Example usage
        Map<String, String> attributes = Map.of(
                "transactionId", "String",
                "merchantId", "String",
                "timestamp", "String",
                "paymentMethod", "String",
                "paymentMethodType", "String",
                "Bank", "Bank",
                "transactionAmount", "String");
        String className = "Transaction";
        String result = generateBuilderPattern(className, attributes);
        System.out.println(result);
    }
}
