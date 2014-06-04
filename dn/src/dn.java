import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
class number
{
	String input;
	String r="";
	LinkedList<Integer> buf = new LinkedList<Integer>();
	LinkedList<Integer> resualt = new LinkedList<Integer>();	
	number()
	{
//		buf.add(5);
//		buf.add(6);
//		buf.add(5);
//		buf.add(1);
//		buf.add(6);
//		buf.add(5);
	}
	void add(int i)
	{
		buf.add(i);
	}
	int search(int i)
	{
		int j = 0;
		if( resualt.size() == 0)
			return -1;
		else {
			for (Integer I:resualt)
			{
				if(I.intValue() == i)
					return j;
				j++;
			}
		}
		return -1;
	}
	void process()
	{
		int i;
		for (Integer I:buf)
		{
			i = search(I.intValue());
			if( i >= 0)
			{
				if(I.intValue() > resualt.get(i+1).intValue())
				{
					resualt.remove(i);
					resualt.add(I);
				} else
				{
					
				}
			} else
			{
				resualt.add(I);
			}
		}
		for (Integer I:resualt)
		{
			r += I.toString() + ","; 
		}
	}
};
public class dn {

	public static void main(String[] args) {


		
		String inputFile = args[0];
		String outputFile = args[1];
		String inputFileContent = readContentFromFile(inputFile);
		String data = inputFileContent;
		number n = new number();
		data = data.substring(1,data.length()-1);
		String dd[] = data.split(",");
		int i;
        	for(i = 0; i < dd.length ;i++)
        	{
	            int d = Integer.parseInt(dd[i]);  
        		n.add(d);
        	}
            n.process();        
			writeContentToFile("("+n.r.substring(0,n.r.length()-1)+")", outputFile);

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
			byte[] buffer = new byte[1024];
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
