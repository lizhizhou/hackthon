#include <iostream>
#include <cstdio>
#include <cmath>
#include <vector>
#include <cstring>
#include <algorithm>
#include <string>
#include <set>
#include <functional>
#include <numeric>
#include <sstream>
#include <stack>
#include <map>
#include <queue>

#define CL(arr, val) memset(arr, val, sizeof(arr))

#define lc l,m,rt<<1
#define rc m + 1,r,rt<<1|1
#define pi acos(-1.0)
#define L(x)    (x) << 1
#define R(x)    (x) << 1 | 1
#define MID(l, r)   (l + r) >> 1
#define Min(x, y)   (x) < (y) ? (x) : (y)
#define Max(x, y)   (x) < (y) ? (y) : (x)
#define E(x)        (1 << (x))
#define iabs(x)     (x) < 0 ? -(x) : (x)
#define lowbit(x)   (x)&(-x)
#define Read(x)  freopen(x, "r", stdin)
#define Writex(x) freopen(x, "w", stdout)

void swap(int &a,int &b)
{
        int tmp = a;
        a = b;
        b = tmp;
        return;
}

int lis(int a[], int b[], int n)
{
	int *d = new int[n];
	int len = 1;
	for (int i=0; i<n; i++) {
		d[i] = 1;
		for(int j=0; j<i; ++j) {
			if(a[j] < a[i] && b[j] < b[i] && d[j] + 1 > d[i])
				d[i] = d[j] + 1;
			if(d[i] > len) len = d[i];
		}
	}
	delete []d;
	return len;
}

int main(int argc, char *argv[])
{
	const char* input = argv[1];
	const char* output = argv[2];
	Read(input);
	Writex(output);
	int he[101];
	int we[101];
	int total=0;
    while(~scanf("(%d,%d)", &he[total], &we[total]))
    	++total;

	if(total > 100) {
		return -1;
	}
	for(int i=0; i<total; i++) {
		if(!(he[i] >= 0 && he[i] <= 10000))
			return -1;
		if(!(we[i] >= 0 && we[i] <= 10000))
			return -1;
	}

    for(int i=1; i<total; i++) {
    	int tmp = he[i];
    	int j=i-1;

    	while(j>=0 && he[j] > tmp) {
    		he[j+1] = he[j];
    		--j;
    	}
    	he[j+1] = tmp;
    	swap(we[i], we[j+1]);
    }

    printf("%d", lis(we, he, total));
    return 0;

}
