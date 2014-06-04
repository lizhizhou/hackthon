import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Solution4 {

	private static String LINE_PATTERN = "(\\([0-9]{1,5},[0-9]{1,5}\\))+";
	private static int MAX_FILE_LINES = 1;
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		try
		{
			String[][] dataStr = readFile(args[0]);
			int len = dataStr.length;
			String[] sortX = new String[len];
			String[] sortY = new String[len];
			for(int i=0; i<len; i++)
			{
				sortX[i] = "1";
				sortY[i] = "1";
				if(Integer.parseInt(dataStr[i][0]) > 10000 || Integer.parseInt(dataStr[i][1]) > 10000)
				{
					throw new Exception("Invalid Input");
				}
			}
			
			for(int i=0; i<len; i++)
			{
				for(int j=i+1;j<=len-1;j++)
				{
					if(Integer.parseInt(dataStr[i][0]) > Integer.parseInt(dataStr[j][0]))
					{
						String temp[] = dataStr[i];
						dataStr[i] = dataStr[j];
						dataStr[j] = temp;
					}
				}
			}
			
			int maxX = 1;
			for(int i=1; i<len; i++)
			{
				for(int j=0; j<i; j++)
				{
					if(Integer.parseInt(dataStr[j][0]) < Integer.parseInt(dataStr[i][0]) && Integer.parseInt(dataStr[j][1]) < Integer.parseInt(dataStr[i][1]))
					{
						sortX[i] =Integer.valueOf((Integer.parseInt(sortX[j]) + 1)).toString();
						maxX = Math.max(Integer.parseInt(sortX[i]), maxX);
					}
				}
				
				if(Integer.parseInt(dataStr[i][0]) > 10000 || Integer.parseInt(dataStr[i][1]) > 10000)
				{
					throw new Exception("Invalid Input");
				}
			}
			
			for(int i=0; i<len; i++)
			{
				for(int j=i+1;j<=len-1;j++)
				{
					if(Integer.parseInt(dataStr[i][1]) > Integer.parseInt(dataStr[j][1]))
					{
						String temp[] = dataStr[i];
						dataStr[i] = dataStr[j];
						dataStr[j] = temp;
					}
				}
			}
	
			int maxY = 1;
			for(int i=1; i<len; i++)
			{
				for(int j=0; j<=i; j++)
				{
					if(Integer.parseInt(dataStr[j][0]) < Integer.parseInt(dataStr[i][0]) && Integer.parseInt(dataStr[j][1]) < Integer.parseInt(dataStr[i][1]))
					{
						sortY[i] = Integer.valueOf((Integer.parseInt(sortY[j]) + 1)).toString();
						maxY = Math.max(Integer.parseInt(sortY[i]), maxY);
					}
				}
				
				if(Integer.parseInt(dataStr[i][0]) > 10000 || Integer.parseInt(dataStr[i][1]) > 10000)
				{
					throw new Exception("Invalid Input");
				}
			}
			
			int max = Math.max(maxX, maxY);
			writeFile(args[1], String.valueOf(max),false);
		}
		catch(Exception e)
		{
			System.out.println("Invalid Input");
			writeFile(args[1], "Invalid Input", false);
		}
	}
	
	public static String[][] readFile(String fileName)throws Exception
	{
		String[][] dataStr = null;
		File file = new File(fileName);
        BufferedReader reader = null;
		try
		{	
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                if(!validateLine(tempString))
                {
                	throw new Exception("Invalid line :" + line + "\n" + tempString);
                }
                else
                {
                	 dataStr = intoData(tempString);
                }
                
                if(line > MAX_FILE_LINES)
                {
                	throw new Exception("Input.txt is too long...");
                }
                line++;
            }
            reader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("Invalid Input");
		}
		finally
		{
			if (reader != null) 
			{
                try 
                {
                    reader.close();
                } 
                catch (IOException e1) 
                {
                	e1.printStackTrace();
                }
            }
		}
		return dataStr;
	}
	
	public static void writeFile(String fileName, String content, boolean isAppend)
	{
		try {
            FileWriter writer = new FileWriter(fileName, isAppend);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
        	System.out.println("Fail to write output.txt");
            e.printStackTrace();
        }
	}
	
	public static boolean validateLine(String lineStr)throws Exception
	{
		Pattern p = Pattern.compile(LINE_PATTERN);
		Matcher m = p.matcher(lineStr);
		return m.matches();
	}
	
	public static String[][] intoData(String temp)throws Exception
	{
		String tempStr = temp.replace(")(", "@").replace("(", "").replace(")", "");
		String[] arr = tempStr.split("@");
		if(arr.length > 100)
		{
			throw new Exception("Invalid Exception");
		}
		
		String[][] dataStr = new String[arr.length][2];
		for(int i=0; i<arr.length; i++)
		{
			dataStr[i] = arr[i].split(",");
		}
		return dataStr;
	}
}