import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

public class Circus_12 {

	public static String infile = "input.txt";
	public static String outfile = "output.txt";

	public static boolean validateFile(String inputFile) {
		try {
			File f = new File(inputFile);
			if (f.length() > 10 * 1024) {
				return false;
			}

			BufferedReader reader = new BufferedReader(
					new FileReader(inputFile));
			String tempString = null;
			Pattern pattern = Pattern.compile("^[\\d\\(\\),]*$");
			while ((tempString = reader.readLine()) != null) {
				// System.out.println(tempString);
				if (!pattern.matcher(tempString).matches()) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			infile = args[0];
			outfile = args[1];

			if (!validateFile(infile)) {
				throw new Exception();
			}

			BufferedReader reader = new BufferedReader(new FileReader(infile));
			FileWriter writer = new FileWriter(outfile, false);
			StringBuffer sb = new StringBuffer("");
			String tempString = null;
			tempString = reader.readLine();

			if (!(tempString.startsWith("(") && tempString.endsWith(")"))) {
				throw new Exception();
			}

			if (tempString != null) {
				tempString = tempString.substring(1, tempString.length() - 1);

				String[] inputs = tempString.split("(\\)\\()");
				int total = inputs.length;
				if (total < 1 || total > 100) {
					throw new Exception();
				}
				int[] heights = new int[total];
				int[] weights = new int[total];
				int[] flag = new int[total];

				for (int i = 0; i < total; i++) {
					String person = inputs[i];
					if (person.split(",").length != 2) {
						throw new Exception();
					}
					heights[i] = Integer.valueOf(person.split(",")[0]);
					weights[i] = Integer.valueOf(person.split(",")[1]);
					if (!(heights[i] >= 0 && heights[i] <= 10000
							&& weights[i] >= 0 && weights[i] <= 10000)) {
						throw new Exception();
					}
					System.out.print(heights[i] + " ");
					System.out.print(weights[i] + " ");
				}

				for (int i = 0; i < total; i++) {
					for (int j = i + 1; j < total; j++) {
						if (heights[j] < heights[i]) {
							int tmpH = heights[j];
							int tmpW = weights[j];
							heights[j] = heights[i];
							weights[j] = weights[i];
							heights[i] = tmpH;
							weights[i] = tmpW;
						}
					}
				}

				flag[0] = 1;
				int max = 1;
				for (int i = 1; i < total; i++) {
					flag[i] = 1;
					for (int j = 0; j < i; j++) {
						if (heights[i] > heights[j] && weights[i] > weights[j]) {
							if (flag[i] < flag[j] + 1) {
								flag[i] = flag[j] + 1;
							}
						}
					}
					if (max < flag[i]) {
						max = flag[i];
					}
				}

				sb.append(max);

			} else {
				throw new Exception();
			}

			// sb.append(System.getProperty("line.separator"));
			writer.write(sb.toString());

			reader.close();
			writer.close();
		} catch (Exception e) {
			FileWriter writer;
			try {
				writer = new FileWriter(outfile, false);

				writer.write("Invalid Input");
				writer.close();
			} catch (IOException e1) {
				System.out.println("Write to result file error:"
						+ e1.getMessage());
			}
		}

		// System.out.print("Done.");
	}

}
