#include <iostream>
#include <algorithm>
#include <fstream>
#include <vector>
#include <string>
#include <cstring>
#include <cstdlib>
#include <cstdio>
#include <list>
#include <queue>
#include <stack>
#include <map>
#include <set>
#include <cctype>
using namespace std;
#define INVALID_INPUT "input invalid"
#define SIZE10K 1024*10

bool filesize_exceed(const char* filename)
{
    long save_pos;
    long size_of_file = 0;

    FILE *file = fopen(filename, "rb");
    if (file)
    {
        save_pos = ftell(file);
        fseek(file, 0L, SEEK_END);
        size_of_file = ftell(file);
        fseek(file, save_pos, SEEK_SET);
        fclose(file);
    } else
        return true;

    //printf("size:%d\n", size_of_file);
    return size_of_file > SIZE10K;
}

bool bindInout(const char* inFile, const char* outFile,
    ifstream& ifs, ofstream& ofs) {
    ofs.open(outFile);
    if (!ofs.is_open()) {
        cout<<"output file open error"<<endl;
        return false;
    }
    if (filesize_exceed(inFile)) {
        ofs<<INVALID_INPUT;
    }
    ifs.open(inFile);
    if (!ifs.is_open()) {
        cout<<"input file open error"<<endl;
        return false;
    }
    return true;
}

void stopInout(ifstream& ifs, ofstream& ofs) {
    ifs.close();
    ofs.close();
}
void inputErrorHandle(ifstream& ifs, ofstream& ofs,
    const char* outFile) {
    ifs.close();
    ofs.close();
    //re-open
    ofs.open(outFile);
    ofs<<INVALID_INPUT;
    ofs.close();
}
#define INPUTSUCCESS    0
#define INPUTFAIL       1
#define INPUTEND        2

#define FLAG_ALPHA      0
#define FLAG_UPPER_ONLY 1
#define FLAG_LOWER_ONLY 2
#define FLAG_FIRSTUPPER 3

int getInt(ifstream& ifs, int& val, char sep) {//till newline or eof
    if (ifs.eof()) {
        return INPUTFAIL;
    }
   
    char c=ifs.peek();
    if (!isdigit(c) && c!='-') {
        return INPUTFAIL;
    }
    ifs>>val;
    ifs.get(c);
    if (c=='\n' || ifs.eof()) {
        return INPUTEND;
    }
    if (c==sep) {
        return INPUTSUCCESS;
    }
    cout<<"input error"<<endl;
    return INPUTFAIL;
}

int getInt(ifstream& ifs, int& val, char sep,
    int low, int high) {//till newline or eof
    if (ifs.eof()) {
        return INPUTFAIL;
    }

    char c=ifs.peek();
    if (!isdigit(c) && c!='-') {
        return INPUTFAIL;
    }
    ifs>>val;
    
    if (val<low || val>=high) return INPUTFAIL;

    ifs.get(c);
    if (c=='\n' || ifs.eof()) {
        return INPUTEND;
    }
    if (c==sep) {
        return INPUTSUCCESS;
    }
    cout<<"input error"<<endl;
    return INPUTFAIL;
}

int getWord(ifstream& ifs, char* str, int n, char sep, int flag) {//till newline or eof
    if (ifs.eof()) {
        return INPUTFAIL;
    }
    char c;
    int i=0;
    bool first;
    switch (flag) {
    case FLAG_UPPER_ONLY:
        while(true) {
            ifs.get(c);
            if (!isupper(c)) break;
            str[i++]=c;
            if (i==n) break;
        }
        str[i]=0;
        if (i==0) return INPUTFAIL;
        break;
    case FLAG_LOWER_ONLY:
        while(true) {
            ifs.get(c);
            if (!islower(c)) break;
            str[i++]=c;
            if (i==n) break;
        }
        str[i]=0;
        if (i==0) return INPUTFAIL;
        break;
    case FLAG_FIRSTUPPER:
        first=true;
        while(true) {
            ifs.get(c);
            if (first && isupper(c)) {
                str[i++]=c;
                if (i==n) break;
                first=false;
            } else if (!first && islower(c)){
                str[i++]=c;
                if (i==n) break;
            } else {
                break;
            }
        }
        str[i]=0;
        if (i==0) return INPUTFAIL;
        break;
    default:
        while(true) {
            ifs.get(c);
            if (!isalpha(c)) break;
            str[i++]=c;
            if (i==n) break;
        }
        str[i]=0;
        if (i==0) return INPUTFAIL;
        break;
    }
    if (c=='\n' || ifs.eof()) {
        return INPUTEND;
    }
    if (c==sep) {
        return INPUTSUCCESS;
    }
    return INPUTFAIL;
}
int count_x(int* s, int cur) {
    int cnt;
    for (int i=0;i<cur;++i) {
        if (s[i]==1) cnt++;
    }
    return cnt;
}
int count_y(int* s, int cur) {
    int cnt;
    for (int i=0;i<cur;++i) {
        if (s[i]==2) cnt++;
    }
    return cnt;
}
long long int pNum(int n, int m) {
    int cnt=0;
    long long int ret=1;
    for (int i=n;cnt<m;i--) {
        ret*=i;
        cnt++;
    }
    return ret;
}
void solve(int m, int n, ofstream& ofs) {
    int total=m+n-2;
    long long int COUNT=pNum(m+n-2,m-1)/pNum(m-1,m-1);
    
    int count=0;
    int cur=0;
    int * s=new int[total];
    for (int i=0;i<total;++i) {
        s[i]=0;
    }
    
    while (cur>=0) {
        cout<<"cur="<<cur<<endl;
        //cur=cur;
        if (cur==total) {
            for (int i=0;i<total;++i) {
                if (s[i]==1) {
                    ofs<<"X";
                } else {
                    ofs<<"Y";
                }
            }
            count++;
            if (count<COUNT) {
                ofs<<endl;
            }
            cur--;
            continue;
        }
        s[cur]+=1;
        if (s[cur]==1) {
            if (count_x(s, cur)>=m-1) {
                s[cur]+=1;
            }
        } else if (s[cur]==2) {
            if (count_y(s,cur)>=n-1) {
                s[cur]+=1;
            }
        }
        if (s[cur]>=3) {
            s[cur]=0;
            cur--;
            continue;
        } 
        cur++;
    }
    delete s;
}


int main(int argc, char* argv[]) {
    if (argc!=3) {
        cout<<INVALID_INPUT<<endl;
        return 1;
    }
    char* inFilePath=argv[1];
    char* outFilePath=argv[2];
    long long int tt=0;
    //bind in/out
    ifstream ifs;
    ofstream ofs;
    if (!bindInout(inFilePath, outFilePath, ifs,ofs)){
        return 0;
    }
    /////////////////////////input begin//////////////////
    char ch;
    ifs.get(ch);
    if (ch!='(') {
        goto inputerror;
    }
    int m,n;
    cout<<"get m"<<endl;
    if (getInt(ifs, m, ',',
        2, 11)==INPUTFAIL) {
        goto inputerror;
    }
    cout<<"get n"<<endl;
    if (getInt(ifs, n, ')',
        2, 11)==INPUTFAIL) {
        goto inputerror;
    }
    cout<<"m="<<m<<" n="<<n<<endl;
    //tt=pNum(m+n-2,m-1)/pNum(m-1,m-1);
    //cout<<"total count: "<<tt<<endl;
    solve(m,n,ofs);
    //cout<<"solve done"<<endl;
    /////////////////////////input end//////////////////
    char tmpc;
    ifs.get(tmpc);//get \n
    if (tmpc=='\r') {
        ifs.get(tmpc);//get \n
        if (tmpc!='\n') goto inputerror;
    } else if (tmpc!='\n') {
        goto inputerror;
    }
    ifs.get(tmpc);//trigger EOF
    if (!ifs.eof()) {
        goto inputerror;
    }
    //clear in/out
    stopInout(ifs,ofs);
    return 0;

inputerror:
    inputErrorHandle(ifs,ofs,outFilePath);
    return 0;
}
