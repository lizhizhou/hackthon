import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Circus_13 {
	
	private People[] people;
	private static PrintWriter out=null;
	private static File inFile=null;
	private static File outFile=null;
	
	private static Scanner in=null;
	
	private static LinkedList<People> peopleList = new LinkedList<People>();

	public void setPeopel(People[] people) {
		this.people = people;
	}

	public Circus() {
	}

	private static void printErr(String err, Exception ex){
		System.err.println(err);
		if(out!=null){
			out.print("Invalid Input");
			
		}
		if(ex!=null){
			ex.printStackTrace();
		}
		out.close();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(null==args||args.length<=0){
			printErr("No command arguments supplied!", null);
		}
		if(args.length!=2){
			printErr("Wrong number of arguments: "+args.length+"!", null);
		}
		
		Circus p = new Circus();
		
		try{
			inFile=new File(args[0]);
			outFile=new File(args[1]);
			in=new Scanner(new FileInputStream(inFile));
			out=new PrintWriter(new FileOutputStream(outFile));
		}catch(IOException ex){
			printErr("Failed to open input/output file. Are your file path correct: "+args[0]+", "+args[1]+"?"+"or malformed data!", ex);
			return;
		}
		
		try{
			
			String input = in.nextLine();
			StringTokenizer tokenizer = new StringTokenizer(input, "()", true);
			
			String regEx = "^\\s*(\\s*\\(\\s*[0-9]+\\s*,\\s*[0-9]+\\s*\\)\\s*)+\\s*$";
			Pattern pat = Pattern. compile(regEx);
			Matcher mat = pat.matcher(input);
			if(!mat.find())
				throw new Exception();
			
			while (tokenizer.hasMoreTokens()) {
				String current = tokenizer.nextToken().trim();
				if(current.contains(",")) {
					String param[] = current.split(",");
					if(param.length==2) {
						int Height = Integer.parseInt(param[0].trim());
						int Weight = Integer.parseInt(param[1].trim());
						peopleList.add(p.new People(Height, Weight));
						if(Height<=0||Weight<=0) {
							throw new Exception();
						}
					}
					
				}
			}
		}
		catch(Exception ex){
			printErr("malformed data!", ex);
			return;
		}
		// TODO Auto-generated method stub
		try{
			int length = peopleList.size();
			People[] people = new People[length];
			
			for(int i=0; i< length; i++) {
				people[i] = (People) peopleList.get(i);
			}
			
			p.setPeopel(people);


			out.print(p.solve());			
			out.close();
		}
		catch(Exception ex){
			printErr("Failed to open input/output file. Are your file path correct: "+args[0]+", "+args[1]+"?"+"malformed data!", ex);
		}
		
	}
	
	private void sortByWeight(People[] people) {
		int size = people.length;
		for(int i=0; i<size; i++) {
			for(int j=0; j<size-1; j++) {
				if(people[j].getWeight()>people[j+1].getWeight()) {
					People tmp = people[j];
					people[j] = people[j+1];
					people[j+1] = tmp;
				}
			}
		}
	}
	
	public int solve() {
		this.sortByWeight(people);
		int [] byWeight = new int[people.length];
		for(int i=0; i<people.length; i++) {
			byWeight[i] = people[i].getHeight();
		}
		LISP lis2 = new LISP(byWeight);
		int num2 = lis2.lis();
		
		return num2;
		
	}
	
	class LISP
	{
		private int[] input;
		private int[] recorder;

		public LISP(int[] input)
		{
			this.input = input;
			recorder = new int[input.length];
		}

		public int lis()
		{
			recorder[0] = 1;

			for (int i = 0; i < input.length; i++)
			{
				int k = 0;
				for (int j = 0; j < i; j++)
				{
					if (input[j] <= input[i] && k < recorder[j])
					{
						k = recorder[j];
					}
				}
				recorder[i] = k + 1;
			}
			return max(recorder);
		}

		private int max(int[] arrary)
		{
			int max = arrary[0];
			for (int i = 0; i < arrary.length; i++)
			{
				if (max < arrary[i])
				{
					max = arrary[i];
				}
			}
			return max;
		}
		
	}
	
	class People {

		private int height;
		
		private int weight;
		
		public People(int weight, int height) {
			this.height = height;
			this.weight = weight;
		}
		
		public int getHeight() {
			return height;
		}
		
		public int getWeight() {
			return weight;
		}

		public String toString() {
			return "height: " + height + "weight: " + weight;
		}

	}

}
 
