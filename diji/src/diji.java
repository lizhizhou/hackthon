import java.util.LinkedList;

class path
{
	int source;
	int end;
	int weight;
}
class SPT{
	int point;
	LinkedList<path> path_set = new LinkedList<path>();
	LinkedList<path> path_tree = new LinkedList<path>();
	void add_path(int source, int end, int weight)
	{
		path p = new path();
		p.source = source;
		p.end    = end;
		p.weight = weight;
		path_set.add(p);
	}
	
	void SPT_RUN()
	{
		int i;
		int mini_path, t;
		path mini_p, p;
		for (i = 2; i< point;i++)
		{
			t  = SPT_path(i-1);
			p = find_path_end(i);
			
		}
	}
	
	path find_path_end(int end)
	{
		for(path p: path_tree)
		{
			if(p.end == end)
				return p;
		}
		return null;
	}
	
	int SPT_path(int end)
	{
		int path_weight = 0;
		path p;
		if (end ==1)
			return 0;
		do{
			p = find_path_end(end);
			if (p == null)
				break;
			path_weight += p.weight;
			end = p.source;
		}while(true);
		return path_weight;
	}
	SPT(int p)
	{
		point = p;
	}
}

public class diji {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int w;
		SPT spt = new SPT(5);
		spt.add_path(1, 2, 3);
		spt.add_path(1, 3, 2);
		spt.add_path(2, 3, 4);
		spt.add_path(3, 4, 3);
		spt.add_path(2, 4, 5);
		spt.add_path(3, 5, 2);
		spt.add_path(4, 5, 1);
		
		 w = spt.SPT_path(1, 5);
	}

}
