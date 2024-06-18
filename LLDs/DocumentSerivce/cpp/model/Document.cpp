#include <bits/stdc++.h>

using namespace std;

class Document
{
private:
    string name, data, owner;

public:
    int id;

    Document(string docName, string docData, string owner) {
        this->name = docName;
        this->data = docData;
        this->owner = owner;
    }
    string getDocData() {
        return this->data;
    }

    string getDocName() {
        return this->name;
    }

    string getDocOwner() {
        return this->owner;
    }

    void setDocId(int id) {
        this->id = id;
    }
    // void setDocId(int id);
    // string getDocName();
    // string getDocData();
    // string getDocOwner();
};
