import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Circus {
	private static final int MIN_NUMBER = 0;
	private static final int MAX_NUMBER = 10000;
	private static final int MAX_PEOPLE_COUNT = 100;
	private String file;

	public Circus(String string) {
		this.file = string;

	}

	public static void main(String[] args) throws IOException {
		String inputFile = args[0];
		String outFile = args[1];

		Circus hack = new Circus(inputFile);
		try {
			hack.hack();
			output(outFile, "" + hack.getResult());
		} catch (Exception e) {
			output(outFile, "invalid input");
		}

	}

	private static void output(String outFile, String result)
			throws UnsupportedEncodingException, FileNotFoundException,
			IOException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(outFile), "UTF-8"));
		writer.write(result);
		writer.flush();
		writer.close();
	}

	private int getResult() {
		return max;
	}

	List<People> peoples = new ArrayList<People>();

	private void hack() throws FileNotFoundException {
		fillArray();
		sort();
		calc();
	}

	List<HeightInRow> inArray = new ArrayList<HeightInRow>();
	int max = 0;

	private void calc() {
		int length = peoples.size();
		for (int i = 0; i < length; i++) {
			build(i);
		}

	}

	private void build(int index) {
		int length = inArray.size();
		People people = peoples.get(index);

		HeightInRow newRow = new HeightInRow(index, 1);
		for (int i = length - 1; i >= 0; i--) {
			HeightInRow r = inArray.get(i);
			People inPeople = peoples.get(r.index);
			if (canTop(people, inPeople)) {
				if (r.height + 1 > newRow.height) {
					newRow.height = r.height + 1;

					if (newRow.height > max) {
						break;
					}
				}
			}
		}
		
		inArray.add(newRow);
		if (newRow.height > max)
			max = newRow.height;

	}

	private boolean canTop(People people, People inPeople) {
		if (people.weight > inPeople.weight && people.height > inPeople.height)
			return true;
		return false;
	}

	private void sort() {
		Collections.sort(peoples, new Comparator<People>() {

			@Override
			public int compare(People o1, People o2) {
				int compareTo = o1.height.compareTo(o2.height);
				if (compareTo != 0)
					return compareTo;
				else
					return o1.weight.compareTo(o2.weight);
			}
		});
	}

	private void fillArray() throws FileNotFoundException {
		Scanner s = new Scanner(new File(this.file));

		String line = "";
		while (s.hasNext()) {
			line += s.nextLine();
		}
		if(!(line.startsWith("(") && line.endsWith(")"))){
			throw new RuntimeException();
		}

		String[] split = line.split("\\)\\(");
		for (int i = 0; i < split.length; i++) {
			String[] array = split[i].split(",");
			if(array.length != 2)
				throw new RuntimeException();
			
			String first = array[0];
			if (first.startsWith("("))
				first = first.substring(1);

			String last = array[1];
			if (last.endsWith(")"))
				last = last.substring(0, last.length() - 1);

			int height = Integer.parseInt(first);
			if (height < MIN_NUMBER || height > MAX_NUMBER)
				throw new RuntimeException();
			
			int weight = Integer.parseInt(last);
			if (weight < MIN_NUMBER || height > MAX_NUMBER)
				throw new RuntimeException();

			peoples.add(new People(height, weight));
		}

		if(peoples.size() > MAX_PEOPLE_COUNT){
			throw new RuntimeException();
		}
		/**
		 * while(s.hasNext()){ byte nextByte = s.nextByte();//( int height =
		 * s.nextInt(); byte comma=s.nextByte();//, int weight=s.nextInt(); byte
		 * rBrace=s.nextByte();//); peoples.add(new People(height, weight)); }
		 **/
	}

	public class People implements java.util.Comparator<People> {

		public Integer height;
		public Integer weight;

		public People(int height2, int weight2) {
			this.height = height2;
			this.weight = weight2;
		}

		@Override
		public int compare(People o1, People o2) {
			return o1.height.compareTo(o2.height);
		}

	}

	public class HeightInRow {
		public HeightInRow(int index2, int i) {
			this.index = index2;
			this.height = i;
		}

		public int index;
		public int height;
	}
}
