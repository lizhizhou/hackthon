#include <iostream>
#include <fstream>
#include <string.h>
#include <math.h>

using namespace std;


class Robot
{
public:
	Robot()
	{
		M=0;
		N=0;
		memset(a,0,sizeof(char)*512);
		nPos=0;
		bNeedChangeLine=false;
	}

	virtual ~Robot()
	{
		Uninit();
	}

	int Main(int argc, char *argv[])
	{
		if(!Init(argc,argv))
		{
			return -1;
		}

		if(!CheckInput())
		{
			return -1;
		}

		if(!Resolve())
		{
			return -1;
		}


		Uninit();

		return 0;
	}

protected:
	virtual bool Init(int argc, char *argv[])
	{
		if(argc!=3)
		{
			cout<<"Invalid parameter!"<<endl;
			return false;
		}

		char* strFileIn=argv[1];
		char* strFileOut=argv[2];
		if(strFileIn== NULL || strFileOut==NULL)
		{
			cout<<"Invalid parameter!"<<endl;
			return false;
		}

		m_ifStream.open(strFileIn,  ios_base::in);
		if(!m_ifStream.is_open())
		{
			cout<<"Invalid input file"<<endl;
			return false;

		}

		m_ofStream.open(strFileOut,  ios_base::out);
		if( !m_ofStream.is_open())
		{
			cout<<"Invalid output file!"<<endl;
			return false;
		}

		return true;
	}


	virtual void Uninit()
	{
		if(m_ifStream.is_open())
		{
			m_ifStream.close();
		}
		if(m_ofStream.is_open())
		{
			m_ofStream.close();
		}
	}


	virtual bool CheckInput()
	{
		m_ifStream.seekg(0, std::ios::beg);
		int fsize = m_ifStream.tellg();
		m_ifStream.seekg(0, std::ios::end);
		fsize = (int)m_ifStream.tellg() - fsize;
		m_ifStream.seekg(0, std::ios::beg);


		if(fsize>10*1024)
		{
			OutputInvlidInputError();
			return false;
		}

		char szLine[64]={0};
		m_ifStream.getline(szLine, 64);
		sscanf(szLine, " ( %d , %d ) ", &M, &N);

		if(M<2 || M>10 || N<2 || N>10)
		{
			OutputInvlidInputError();
			return false;
		}

		return true;
	}

	virtual bool Resolve()
	{
		MoveNext(1,1,0);
		return false;
	}

	void MoveNext(int i, int j, int pos)
	{
		if(j<N)
		{
			a[pos]='X';
			MoveNext(i, j+1,pos+1);
		}
		if(i<M)
		{
			a[pos]='Y';
			MoveNext(i+1, j,pos+1);
		}
		if(i>=M && j>=N)
		{
			if(bNeedChangeLine)
			{
				m_ofStream<<endl;
			}
			bNeedChangeLine=true;
			m_ofStream<<a;
		}
	}


	void OutputInvlidInputError()
	{
		if( m_ofStream.is_open())
		{
			m_ofStream<<"Invalid Input"<<endl;
		}
	}


private:
	ifstream m_ifStream;
	ofstream m_ofStream;

	int M;
	int N;
	char a[512];
	int nPos;
	bool bNeedChangeLine;
};

int main(int argc, char *argv[])
{
	Robot hackthon;
	return hackthon.Main(argc, argv);
}
