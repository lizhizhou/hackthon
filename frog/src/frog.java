import java.util.ArrayList;
public class frog {
	ArrayList<Integer> p;
	void print()
	{
		int i;
		System.out.print("[");
		for(i = 0; i < p.size(); i++)
		{
			System.out.print(p.get(i).toString()+" ");
		}
		System.out.print("]");
	}
	void move()
	{
		
	}
	void jump()
	{
		
	}
	public frog(int n)
	{
		int i;
		p = new ArrayList<Integer>();
		for(i = 1; i <= n; i++)
		{
			p.add(new Integer(i -1 - n/2));
		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		frog f = new frog(7);
		f.print();
	}

}
