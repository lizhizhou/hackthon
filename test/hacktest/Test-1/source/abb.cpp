#include<iostream>
#include<string>
#include <stdio.h>
using namespace std;

const string SAME = "SAME";
const string DIFFERENT = "DIFFERENT";

string getAbbreviations(int stirngCount)
{
    string result = "";
    string name;
    while(stirngCount--)
    {
        cin>>name;
        result += name[0];
    }
    return result;
}
int main(int argc, char **argv)
{
    freopen(argv[1], "r",stdin);
	freopen(argv[2], "w",stdout);
    int cases;cin>>cases;
    while(cases--)
    {
        int numInFirstName; cin >> numInFirstName;
        string fAbbreviations = getAbbreviations(numInFirstName);

        int numInLastName; cin >> numInLastName;
        string lAbbreviations = getAbbreviations(numInLastName);

        if(fAbbreviations.compare(lAbbreviations) == 0)
        {
            cout<<SAME;
        }
        else
        {
            cout<<DIFFERENT;
        }
        cout<<endl;
    }
    return 0;
}
