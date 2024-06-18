import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.AccessLevel;
import Model.Document;
import Model.User;

public class DocumentService {
    private HashMap<String, User> users;
    private HashMap<String, Document> documents;
    private HashMap<String, HashMap<Integer, AccessLevel>> userDocumentStore;
    public Integer docCount = 0;

    public DocumentService() {
        this.users = new HashMap<String, User>();
        this.documents = new HashMap<String, Document>();
        this.userDocumentStore = new HashMap<String, HashMap<Integer, AccessLevel>>();
    }

    public void createUser(String firstName, String lastName, String username) {
        if (users.containsKey(username)) {
            System.out.println("Username already exist, choose another one!");
            return;
        }
        User user = new User(firstName, lastName, username);
        users.put(username, user);
    }

    public void updateOrAddDocumentForUser(String username, Integer documentId, AccessLevel access) {
        HashMap<Integer, AccessLevel> userDocStore = userDocumentStore.get(username);
        userDocStore.put(documentId, access);
        userDocumentStore.put(username, userDocStore);
    }

    public void readDocument(String username, String documentName) {
        if (users.containsKey(username) && documents.containsKey(documentName)) {
            if (userDocumentStore.containsKey(username)) {
                Document document = documents.get(documentName);
                if (userDocumentStore.get(username).containsKey(document.id)) {
                    System.out.println("Document data is: " + document.getDocData());
                } else System.out.println("User " + username + " doesn't have access to document " + documentName);
            } else System.out.println("User " + username + " doesn't have any document access");
        } else System.out.println("User " + username + " not found or document doesn't exist");
    }

    public void updateDocument(String username, String documentName, String updatedDocumentData) {
        if (users.containsKey(username) && documents.containsKey(documentName)) {
            Document document = documents.get(documentName);
            if (userDocumentStore.containsKey(username) && userDocumentStore.get(username).containsKey(document.id)) {
                AccessLevel accessLevel = userDocumentStore.get(username).get(document.id);
                if (accessLevel == AccessLevel.OWNER || accessLevel == AccessLevel.WRITE) {
                    document.updateDocData(updatedDocumentData);
                    documents.put(documentName, document);
                } else System.out.println("User " + username + " doesn't have update access");
            }
        } else System.out.println("User " + username + " not found or document doesn't exist");
    }

    public void deleteDocument(String username, String documentName) {
        if (users.containsKey(username) && documents.containsKey(documentName)) {
            Document document = documents.get(documentName);
            if (document.getOwner() == username) {
                List<String> usersHavingDocument = new ArrayList<String>();
                for (Map.Entry<String, HashMap<Integer, AccessLevel>> userDocuments : userDocumentStore.entrySet()) {
                    for(Map.Entry<Integer, AccessLevel> userDocument : userDocuments.getValue().entrySet()) {
                        if (userDocument.getKey() == document.id) {
                            usersHavingDocument.add(userDocuments.getKey());
                            break;
                        }
                    }
                }
                for (String user : usersHavingDocument)
                    userDocumentStore.get(user).remove(document.id);
                documents.remove(documentName);
            } else System.out.println("User " + username + " doesn't have OWNER rights");
        } else System.out.println("User " + username + " not found or document doesn't exist");
    }

    public void grantAccess(String username, String documentName, AccessLevel access, boolean provideAccess) {
        if (provideAccess && access != AccessLevel.OWNER) {
            if (documents.containsKey(documentName)) {
                Document document = documents.get(documentName);
                if (userDocumentStore.get(username) == null)
                    userDocumentStore.put(username, new HashMap<Integer, AccessLevel>());
                updateOrAddDocumentForUser(username, document.id, access);
                System.out.println("User " + username + " have now " + access + " access");
            } else System.out.println("Document doesn't exists.");
        } else {
            System.out.println("Owner denied to grant access.");
        }
    }

    public void createDocument(String username, Document document) {
        document.setDocumentId(docCount);
        documents.put(document.getDocName(), document);
        if (users.containsKey(username)) {
            if (userDocumentStore.get(username) == null)
                userDocumentStore.put(username, new HashMap<Integer, AccessLevel>());
            updateOrAddDocumentForUser(username, document.id, AccessLevel.OWNER);
            docCount++;
            System.out.println("Document created successfully");
        } else {
            System.out.println("User " + username + " not found");
        }
    }
}