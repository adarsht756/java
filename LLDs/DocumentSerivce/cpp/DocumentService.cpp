#include <bits/stdc++.h>
#include <iostream>

#include "model/AccessLevel.cpp"
#include "model/User.cpp"
#include "model/Document.cpp"

using namespace std;

class DocumentService {
private:
    unordered_map<string, User*> users;
    unordered_map<string, Document*> documents;
    unordered_map<string, unordered_map<int, AccessLevel>> usersDocumentStore;
    int docCount = 0;

public:
    DocumentService() {
        cout << "Starting doc service\n";
    }

    void createUser(string firstName, string lastName, string username) {
        if (users.find(username) != users.end()) {
            cout << "Username already exist, choose another one\n";
            return;
        }
        User* user = new User(firstName, lastName, username);
        users[username] = user;
        cout << "User " << username << " created successfully\n";
    }

    void updateOrAddDocumentForUser(string username, int documentId, AccessLevel access) {
        unordered_map<int, AccessLevel> userDocStore = usersDocumentStore[username];
        userDocStore[documentId] = access;
        usersDocumentStore[username] = userDocStore;
    }

    void readDocument(string username, string documentName) {
        if (users.find(username) != users.end() && documents.find(documentName) != documents.end()) {
            cout << "document data is: " << documents[documentName]->getDocData() << endl;
        } else cout << "User " << username << " not found or document doesn't exist\n";
    }

    void createDocument(string username, Document* document) {
        document->setDocId(docCount);
        documents[document->getDocName()] = document;
        if (users.find(username) != users.end()) {
            updateOrAddDocumentForUser(username, document->id, AccessLevel::OWNER);
            this->docCount++;
            cout << "Document created successfully\n";
        } else cout << "User " + username + " not found\n";
    }

};