#include <stdio.h>
#include <stdlib.h>
#include <algorithm>

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
#define INVALID_INPUT "invalid input"
#define SIZE10K 1024*10
using namespace std;

struct person_t
{
	int h,w;
	const bool operator < (const person_t &o) const {
		if (h > o.h)
			return true;
		else if (h == o.h && w > o.w)
			return true;
		return false;
	}
};

person_t person[101];
int n;
int opt[101];

int solve()
{
	memset(opt, 0, sizeof(opt));
	opt[0] = 1;
	for (int i = 1; i < n; i++) {
		for (int j = 0; j < i; j++) {
			if (person[j].h > person[i].h
				&& person[j].w > person[i].w) {
					if (opt[j] + 1 > opt[i])
						opt[i] = opt[j]+1;
			}

		}
	}

	int ans = 0;

	for (int i = 0; i < n; i++) {
		if (opt[i] > ans)
			ans = opt[i];
	}

	return ans;
}

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


int main(int argc, char* argv[])
{
	//freopen("input.txt", "r", stdin);
	//freopen("output.txt", "w", stdout);
	int answ;

	if (argc!=3) {
        cout<<INVALID_INPUT<<endl;
        return 1;
    }
    char* inFilePath=argv[1];
    char* outFilePath=argv[2];
	//char* inFilePath="input.txt";
	//char* outFilePath="output.txt";

    //bind in/out
    ifstream ifs;
    ofstream ofs;
    if (!bindInout(inFilePath, outFilePath, ifs,ofs)){
        return 0;
    }


	int h, w;
	n = 0;
	/*while(scanf("(%d,%d)", &h, &w) != EOF) {
		person[n].h = h;
		person[n].w = w;
		n++;
	}*/

	char tmpc;
	int ret, flag = 0;;
	while (1) {
		ifs.get(tmpc);
		if (flag == 0 && ifs.eof())
			goto inputerror;
		else if (flag == 1 && ifs.eof())
			break;

		if (tmpc == '\n' || tmpc == '\r')
			goto inputerror;

		if (tmpc != '(')
			goto inputerror;
		ret = getInt(ifs, h, ',', 0, 1001);
		if (ret == INPUTFAIL)
			goto inputerror;
		ret = getInt(ifs, w, ')', 0, 1001);
		if (ret == INPUTFAIL)
			goto inputerror;
		person[n].h = h;
		person[n].w = w;
		n++;
		flag = 1;
	}

	if (n > 100)
		goto inputerror;

	sort(person, person+n);
	//printf("%d", solve());
	answ = solve();
	ofs<<answ;
	/////////////////////////input end//////////////////
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