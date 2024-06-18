#include <bits/stdc++.h>
#include <iostream>

using namespace std;

class Parent {
private:
    virtual string getParentName() = 0;
};

class Child : public Parent {
public:
    string getParentName() {
        return "Parent";
    }
    string temp() {
        return "temp";
    }
};

int main() {
    Child* child = new Child();
    cout << (*child).temp() << endl << (*child).getParentName();
}