import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class MainFinal {
	static String invalidInputStr = "Invalid Input";
	
	static Scanner inputStream = null;
	static OutputStreamWriter outputStream = null;
	
	static int kerexn = 0;
	static int mkorx = 0;

	static String[] nmyx = null;
	
	static boolean sxy = false;
	
	public static void backtrack(int yurxxx, int uixcee, int  nmxy ) {
		if ((yurxxx == mkorx - 1) && (uixcee == kerexn - 1)) {
			writeOutput(nmyx, nmxy);
			return;
		} 
		
		if (uixcee < kerexn - 1) {
			nmyx[nmxy] = "X";
			backtrack(yurxxx, uixcee+1, nmxy+1);
		}
		
		if (yurxxx < mkorx - 1) {
			nmyx[nmxy] = "Y";
			backtrack(yurxxx+1, uixcee, nmxy+1);
		}
	}
	
	public static void main(String[] args) {
		try {
			if (readInput(args)) {
				backtrack(0, 0, 0);
			}  else {
				writeOutput(invalidInputStr);
			}
		} finally {
			if (inputStream != null) {
				try { inputStream.close(); 	} catch (Exception e) {}
			}
			if (outputStream != null) {
				try { outputStream.close(); 	} catch (Exception e) {}
			}
		}
	}

	public static void writeOutput(String result) {
		if (outputStream != null) {
			try {
				outputStream.write(result);
				outputStream.flush();
			} catch (Exception e) {
			}
		}
	}
	
	public static void writeOutput(String[] results, int step) {
		if (outputStream != null) {
			try {
				if (sxy) {
					outputStream.write("\n");
				} else {
					sxy = true;
				}
				for (int i=0; i< step; i++) {
					outputStream.write(results[i]);
				}
				outputStream.flush();
			} catch (Exception e) {
			}
		}
	}
	public static boolean readInput(String[] args) {
		boolean valid = true;
		
		if (args == null || args.length < 2) {
			valid = false;
		}
		
		try {
			outputStream =new OutputStreamWriter(new FileOutputStream(args[1]), "UTF-8");
		} catch (Exception e1) {
			valid = false;
		}
		
		File inputFile = null;
		if (args[0] == null || (inputFile = new File(args[0])) == null || !inputFile.exists() || inputFile.length() >(10 * 1024)) {
			valid = false;
		} else  {
			try {
				inputStream = new Scanner(new FileInputStream(args[0]), "UTF-8");
			} catch (Exception e) {
				valid = false;
			}
		}
		
		if (inputStream.hasNextLine()) {
			String line = inputStream.nextLine();
			if (line.length() < 5 || line.indexOf(",") == -1 || !line.startsWith("(") || !line.endsWith(")")) {
				valid = false;
			} else {
				String[] nums = line.substring(1, line.length()-1).split(",");
				if (nums.length < 2) {
					valid = false;
				} else {
					try {
						mkorx = Integer.parseInt(nums[0]);
						if (mkorx < 2 ||  mkorx > 10) {
							valid = false;
						}
					} catch (Exception e) {
						valid = false;
					}
					
					try {
						kerexn = Integer.parseInt(nums[1]);
						if (kerexn < 2 ||  kerexn > 10) {
							valid = false;
						}
					} catch (Exception e) {
						valid = false;
					}
					
					nmyx = new String[kerexn + mkorx +10];
				}
			}
		} else {
			valid = false;
		}
		return valid;
	}
}
