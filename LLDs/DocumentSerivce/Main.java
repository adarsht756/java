import Model.AccessLevel;
import Model.Document;

public class Main {
    public static void main(String[] args) {
        DocumentService documentService = new DocumentService();
        documentService.createUser("Adarsh", "Tiwari", "adarsht756");
        documentService.createUser("Komal", "Tiwari", "komalt756");
        documentService.createUser("Sanskruti", "Tiwari", "sanst756");
        documentService.createUser("Adarsh", "Tiwari", "adarsht756");

        documentService.createDocument("adarsht756", new Document("Notes", "adarsh document", "adarsht756"));
        documentService.createDocument("komalt756", new Document("Hello", "komal document", "komalt756"));
        documentService.readDocument("adarsht756", "Notes");
        documentService.readDocument("sanst756", "Notes");
        documentService.grantAccess("sanst756", "Notes", AccessLevel.READ, true);
        documentService.readDocument("sanst756", "Notes");

        documentService.deleteDocument("adarsht756", "Hello");
        documentService.readDocument("komalt756", "Hello");
        documentService.deleteDocument("komalt756", "Hello");
        documentService.readDocument("komalt756", "Hello");
    }
}