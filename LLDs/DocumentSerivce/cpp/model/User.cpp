#include <bits/stdc++.h>

using namespace std;

class User {
private:
    string firstName, lastName, username;

public:
    User(string firstName, string lastName, string username) {
        this->firstName = firstName;
        this->lastName = lastName;
        this->username = username;
    }

    string getFirstName() {
        return this->firstName;
    }
    string getLastName() {
        return this->lastName;
    }

    string getUsername() {
        return this->username;
    }
};