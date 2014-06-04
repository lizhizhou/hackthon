#include <iostream>
#include <vector>
#include <algorithm>
#include <fstream> 
#include <string>
#include <sstream>

using namespace std;

struct Node
{
	int height;
	int weight;

	Node(){}
	Node(int h, int w):height(h), weight(w){}
};

bool comp(const Node &lhs, const Node &rhs)
{
	return lhs.height < rhs.height;
}

int solve(vector<Node> &a)
{
	sort(a.begin(), a.end(), comp);

	vector<int> f(a.size());

	f[0] = 1;

	for(int i = 1; i < a.size(); i++)
	{
		f[i] = 1;
		for(int j = 0; j < i; j++)
			if (a[i].height > a[j].height && a[i].weight > a[j].weight)
				f[i] = max(f[i], f[j] + 1);
	}

	int maxF = 0;
	for(int i = 0; i < f.size(); i++)
		maxF = max(maxF, f[i]);

	return maxF;
}

bool isNum(const std::string &s)
{
	for (int i = 0; i < s.length(); i++)
	{
		if(!isdigit(s[i]))
			return false;
	}
	return true;
}

bool split(const std::string &s, std::vector<Node> &elems) {
	std::stringstream ss(s);
	std::string item;
	while (std::getline(ss, item, ')')) 
	{
		int n = item.find(',');
		if(item[0] == '(' && n != -1)
		{
			int h = -1;
			int w = -1;
			string strh = item.substr(1, n-1);
			string strw = item.substr(n+1, item.length());
			if(!isNum(strh) || !isNum(strw))
				return false;
			h = strtol(strh.c_str(), NULL, 10);
			w = strtol(strw.c_str(), NULL, 10);
			if((h<0 || h>10000)||(w<0 || w>10000))
				return false;
			else
			{
				elems.push_back(Node(h,w));
			}

		}
		else
		{
			return false;
		}
	}

	if(elems.size() == 0 || elems.size() > 100)
		return false;
	else
		return true;
}

int main(int argc, char* argv[]) 
{ 
	if(argc != 3){
		printf("Invalid command line parameters.");
		return 1;
	}

	ifstream cin(argv[1]);
	ofstream cout(argv[2]);

	char input[2001] = {0};
	if(cin.getline(input, 2000))
	{
		char temp[101] = {0};
		if(cin.getline(temp, 100))
		{
			cout<<"Invalid Input";
			return 1;
		}

		string strInput(input);
		std::vector<Node> elems;
		bool ret = split(strInput, elems);
		if(ret)
		    cout << solve(elems);
		else
			cout<<"Invalid Input";
	}
	else
	{
		cout<<"Invalid Input";
		return 1;
	}

	return 0;
}