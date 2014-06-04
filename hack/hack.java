import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
class Robot
{
	private int N;
	private int M;
	private int x;
	private int y;
	LinkedList<String> path = new LinkedList<String>();
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
public class hack {

	public static void main(String[] args) {

		String inputFile = args[0];
		String outputFile = args[1];
		String inputFileContent = readContentFromFile(inputFile);
		String data = inputFileContent;
		if(data.length() > 1024*10)
		{
			writeContentToFile("Invalid Input", outputFile);
        	return;
		}
        String reg = "\\((\\d+),(\\d+)\\)";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(data);
        int M,N;
        if (matcher.find()) {  
        	try{
	            M = Integer.parseInt(matcher.group(1));  
	            N = Integer.parseInt(matcher.group(2));  
        	} catch (NumberFormatException e) 
        	{
    			writeContentToFile("Invalid Input", outputFile);
            	return;	
        	}
            if (M > 10 || N > 10 || M < 2 || N < 2)
            {
    			writeContentToFile("Invalid Input", outputFile);
            	return;
            }
			//Do the program
			Robot ro = new Robot(M,N);
			ro.next();
			writeContentToFile(ro.path_string.substring(0,ro.path_string.length()-1), outputFile);
        }  
        else
			writeContentToFile("Invalid Input", outputFile);
	}
	
	/**
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public static String readContentFromFile(String fileName){
		InputStream is = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			is = new FileInputStream(fileName);
			byte[] buffer = new byte[1024*15];
			int len = -1;
			while ((len = is.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}
			byte[] resByte = baos.toByteArray();
			String resStr = new String(resByte);
			return resStr;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				baos.close();
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	public static void writeContentToFile(String content,String file){
		BufferedOutputStream os = null;
		try {
			os = new BufferedOutputStream(new FileOutputStream(file));
			byte[] tmpByte = content.getBytes();
			os.write(tmpByte);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				os.flush();
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
}
