import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
class Robot
{
	int N;
	int M;
	private int x;
	private int y;
	ArrayList<String> path = new ArrayList<String>();
	String path_string = new String();
	public Robot(int n, int m)
	{
		N = n;
		M = m;
		x = 1;
		y = 1;
	}
	public void next()
	{
		if(x > N || y > M)
			return;
		if(x == N && y == M)
		{
			int i;
			for(i=0;i<path.size();i++)
				path_string += path.get(i);
			path_string+="\n";
			return;
		}
		x++;
		path.add("X");
		next();
		x--;
		path.remove(path.size()-1);
	
		y++;
		path.add("Y");
		next();
		y--;
		path.remove(path.size()-1);
	}
};
public class Test {


	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		try {
			BufferedReader input = new BufferedReader(new FileReader("input.txt"));
			BufferedWriter output = new BufferedWriter(new FileWriter("output.txt"));
			String data = input.readLine();
            String reg = "(\\d+),(\\d+)";
            Pattern pattern = Pattern.compile(reg);
            Matcher matcher = pattern.matcher(data);
            int M,N;
            if (matcher.find()) {  
                M = Integer.parseInt(matcher.group(1));  
                N = Integer.parseInt(matcher.group(2));  
                if (M > 20 || N > 20)
                {
                	System.out.println("Data out of range");
        			input.close();
        			output.close();
                	return;
                }
    			//Do the program
    			Robot ro = new Robot(M,N);
    			ro.next();
    			output.write(ro.path_string);
    			System.out.println("Finish");
            }  
            else{
            	System.out.println("input file format error");
            }
			input.close();
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IO error");
		}  

	}
}
