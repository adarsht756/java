#include <bits/stdc++.h>

#include "DocumentService.cpp"
// #include "model/Document.cpp"

using namespace std;

int main() {
    DocumentService* documentService = new DocumentService();
    documentService->createUser("Adarsh", "Tiwari", "adarsht756");
    documentService->createUser("Adarsh", "Tiwari", "adarsht756");

    documentService->readDocument("adarsht756", "doc");
    documentService->createDocument("adarsht756", new Document("doc", "doc ka data", "adarsht756"));
    documentService->readDocument("adarsht756", "doc");
}