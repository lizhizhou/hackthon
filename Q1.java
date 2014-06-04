import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Q1 {
	public static void main(String args[]) {
		String input = args[0];
		String output =args[1];
		File inFile = new File(input);
		File outFile = new File(output);
		StringBuffer outputtext = new StringBuffer();
		try {
			if(sizeOf(inFile)>170*1024){		
				throw new Exception("File size too large");
			}
			List<String> list = readLines(inFile, "UTF-8");
			int line;
			int col;
			if(list.size()==1){
				String line_s = list.get(0);
				if(line_s.charAt(0)!='('){
					throw new Exception("The input data need start with (");
				}

				if(line_s.charAt(line_s.length()-1)!=')'){
					throw new Exception("The input data need start with )");
				}
				
				String s = line_s.substring(1,line_s.length()-1);
				String line_str = s.split(",")[0];
				String col_str = s.split(",")[1];
				
				if(!isNumeric(line_str) ||!isNumeric(col_str)){
					throw new Exception("The input data need be number!");
				}
				line = Integer.parseInt(String.valueOf(line_str))-1;
				col = Integer.parseInt(String.valueOf(col_str))-1;

				if(line>9 || col>9 ||line<1||col<1){
					throw new Exception("The num of Line or col too big or small");
				}			
			}else{
				throw new Exception("Total input line count is invalid");
			}
			

			Stack<Integer> lineStack = new Stack<Integer>();
			lineStack.add(0);
			
			
			
			Stack<Pos> pos = new Stack<Pos>();
			int i = 0;
			int j = 0;
			
			Pos posO =new Pos();
			posO.line = i;
			posO.col =j;
			posO.visited =0;
			
			pos.add(posO);
			
			boolean outputed =false;
			
			while(pos.size()>0){
				j=pos.peek().col;
				i=pos.peek().line;
				
				boolean found = false;
				
				if(pos.peek().visited == 2){				
					pos.pop();
					continue;
				}
				
				if(pos.peek().visited == 0){
					pos.peek().visited =1;	
					pos.peek().next="X";
					while(j<col){					
						Pos posX =new Pos();	
						posX.line = i;
						posX.col = ++j;
						pos.add(posX);
						//outputtext.append("X");
						found =true;
						break;
					}
				}
				
				
				if(pos.peek().visited == 1){
					pos.peek().visited =2;
					pos.peek().next="Y";
						while(i<line){
							

							Pos posX =new Pos();
							posX.line = ++i;
							posX.col = j;
							pos.add(posX);
							
							//outputtext.append("Y");
							found =true;
							break;
						}
					
				}
				

				if(!found){
					pos.pop();
				}
				if(i==line&&j==col){
					pos.pop();			
					
					if(outputed == false){
						outputed = true;					
					}else{
						outputtext.append("\n");
					}
					
					StringBuffer temp = new StringBuffer();
					for(Pos o:pos){
						temp.append(o.next);
					}
					
					outputtext.append(temp);
				}				
			}
			
			writeStringToFile(outFile,outputtext.toString(),"UTF-8",false);
			
			
			
//			int NO =0;
//			for(int i =0;i<line;i++){
//
//				outputtext.append("Case #"+(NO+++1)+": "+NO +"\n");
//			}
//			writeStringToFile(outFile,outputtext.toString(),"UTF-8",false);
			System.out.println(outputtext.toString());

		} catch (Exception e) {
			System.out.print(e.getMessage());
			try{
				writeStringToFile(outFile,"INVALID INPUT","UTF-8",false);
			}catch(Exception ex){
				//NOOP
			}
		}
		
	}
	
	
	public static class Pos{
		public int line = 0;
		public int col = 0;
		public String next = "";
		public int visited =0;
	}
	
	public static List<Integer> splitCellByReleasedSeq(int num, List<Integer> seq){
		List<Integer> splitedSeq = new ArrayList<Integer>();
		for(int index=0;index<=seq.size();index++){
			int lastValue = index>0?seq.get(index-1):0;
			int currentValue = index<seq.size()?seq.get(index):num+1;
			splitedSeq.add(currentValue-lastValue-1);
		}
		return splitedSeq;
	}
	
	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
	private static final int EOF = -1;
	// Empty checks
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Checks if a CharSequence is empty ("") or null.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isEmpty(null)      = true
	 * StringUtils.isEmpty("")        = true
	 * StringUtils.isEmpty(" ")       = false
	 * StringUtils.isEmpty("bob")     = false
	 * StringUtils.isEmpty("  bob  ") = false
	 * </pre>
	 * 
	 * <p>
	 * NOTE: This method changed in Lang version 2.0. It no longer trims the
	 * CharSequence. That functionality is available in isBlank().
	 * </p>
	 * 
	 * @param cs
	 *            the CharSequence to check, may be null
	 * @return {@code true} if the CharSequence is empty or null
	 * @since 3.0 Changed signature from isEmpty(String) to
	 *        isEmpty(CharSequence)
	 */
	public static boolean isEmpty(final CharSequence cs) {
		return cs == null || cs.length() == 0;
	}

	/**
	 * <p>
	 * Checks if the CharSequence contains only Unicode digits. A decimal point
	 * is not a Unicode digit and returns false.
	 * </p>
	 * 
	 * <p>
	 * {@code null} will return {@code false}. An empty CharSequence
	 * (length()=0) will return {@code false}.
	 * </p>
	 * 
	 * <p>
	 * Note that the method does not allow for a leading sign, either positive
	 * or negative. Also, if a String passes the numeric test, it may still
	 * generate a NumberFormatException when parsed by Integer.parseInt or
	 * Long.parseLong, e.g. if the value is outside the range for int or long
	 * respectively.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isNumeric(null)   = false
	 * StringUtils.isNumeric("")     = false
	 * StringUtils.isNumeric("  ")   = false
	 * StringUtils.isNumeric("123")  = true
	 * StringUtils.isNumeric("12 3") = false
	 * StringUtils.isNumeric("ab2c") = false
	 * StringUtils.isNumeric("12-3") = false
	 * StringUtils.isNumeric("12.3") = false
	 * StringUtils.isNumeric("-123") = false
	 * StringUtils.isNumeric("+123") = false
	 * </pre>
	 * 
	 * @param cs
	 *            the CharSequence to check, may be null
	 * @return {@code true} if only contains digits, and is non-null
	 * @since 3.0 Changed signature from isNumeric(String) to
	 *        isNumeric(CharSequence)
	 * @since 3.0 Changed "" to return false and not true
	 */
	public static boolean isNumeric(final CharSequence cs) {
		if (isEmpty(cs)) {
			return false;
		}
		final int sz = cs.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isDigit(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if the CharSequence contains only Unicode digits or space (
	 * {@code ' '}). A decimal point is not a Unicode digit and returns false.
	 * </p>
	 * 
	 * <p>
	 * {@code null} will return {@code false}. An empty CharSequence
	 * (length()=0) will return {@code true}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isNumericSpace(null)   = false
	 * StringUtils.isNumericSpace("")     = true
	 * StringUtils.isNumericSpace("  ")   = true
	 * StringUtils.isNumericSpace("123")  = true
	 * StringUtils.isNumericSpace("12 3") = true
	 * StringUtils.isNumericSpace("ab2c") = false
	 * StringUtils.isNumericSpace("12-3") = false
	 * StringUtils.isNumericSpace("12.3") = false
	 * </pre>
	 * 
	 * @param cs
	 *            the CharSequence to check, may be null
	 * @return {@code true} if only contains digits or space, and is non-null
	 * @since 3.0 Changed signature from isNumericSpace(String) to
	 *        isNumericSpace(CharSequence)
	 */
	public static boolean isNumericSpace(final CharSequence cs) {
		if (cs == null) {
			return false;
		}
		final int sz = cs.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isDigit(cs.charAt(i)) == false && cs.charAt(i) != ' ') {
				return false;
			}
		}
		return true;
	}


	// -----------------------------------------------------------------------
	/**
	 * Returns the size of the specified file or directory. If the provided
	 * {@link File} is a regular file, then the file's length is returned. If
	 * the argument is a directory, then the size of the directory is calculated
	 * recursively. If a directory or subdirectory is security restricted, its
	 * size will not be included.
	 * 
	 * @param file
	 *            the regular file or directory to return the size of (must not
	 *            be <code>null</code>).
	 * 
	 * @return the length of the file, or recursive size of the directory,
	 *         provided (in bytes).
	 * 
	 * @throws NullPointerException
	 *             if the file is <code>null</code>
	 * @throws IllegalArgumentException
	 *             if the file does not exist.
	 * 
	 * @since 2.0
	 */
	public static long sizeOf(File file) {
		if (!file.exists()) {
			String message = file + " does not exist";
			throw new IllegalArgumentException(message);
		}
		if (!file.isDirectory()) {
			return file.length();
		}
		return 0;
	}

	// -----------------------------------------------------------------------
	/**
	 * Reads the contents of a file into a String. The file is always closed.
	 * 
	 * @param file
	 *            the file to read, must not be <code>null</code>
	 * @param encoding
	 *            the encoding to use, <code>null</code> means platform default
	 * @return the file contents, never <code>null</code>
	 * @throws IOException
	 *             in case of an I/O error
	 * @throws java.io.UnsupportedEncodingException
	 *             if the encoding is not supported by the VM
	 */
	public static String readFileToString(File file, String encoding)
			throws IOException {
		InputStream in = null;
		try {
			in = openInputStream(file);
			return toString(in, encoding);
		} finally {
			closeQuietly(in);
		}
	}

	/**
	 * Reads the contents of a file line by line to a List of Strings. The file
	 * is always closed.
	 * 
	 * @param file
	 *            the file to read, must not be <code>null</code>
	 * @param encoding
	 *            the encoding to use, <code>null</code> means platform default
	 * @return the list of Strings representing each line in the file, never
	 *         <code>null</code>
	 * @throws IOException
	 *             in case of an I/O error
	 * @throws java.io.UnsupportedEncodingException
	 *             if the encoding is not supported by the VM
	 * @since 1.1
	 */
	public static List<String> readLines(File file, String encoding)
			throws IOException {
		InputStream in = null;
		try {
			in = openInputStream(file);
			return readLines(in, encoding);
		} finally {
			closeQuietly(in);
		}
	}

	/**
	 * Writes a String to a file creating the file if it does not exist.
	 * 
	 * @param file
	 *            the file to write
	 * @param data
	 *            the content to write to the file
	 * @param encoding
	 *            the encoding to use, <code>null</code> means platform default
	 * @param append
	 *            if <code>true</code>, then the String will be added to the end
	 *            of the file rather than overwriting
	 * @throws IOException
	 *             in case of an I/O error
	 * @throws java.io.UnsupportedEncodingException
	 *             if the encoding is not supported by the VM
	 * @since 2.1
	 */
	public static void writeStringToFile(File file, String data,
			String encoding, boolean append) throws IOException {
		OutputStream out = null;
		try {
			out = openOutputStream(file, append);
			write(data, out, encoding);
			out.close(); // don't swallow close Exception if copy completes
							// normally
		} finally {
			closeQuietly(out);
		}
	}

	/**
	 * Opens a {@link FileOutputStream} for the specified file, checking and
	 * creating the parent directory if it does not exist.
	 * <p>
	 * At the end of the method either the stream will be successfully opened,
	 * or an exception will have been thrown.
	 * <p>
	 * The parent directory will be created if it does not exist. The file will
	 * be created if it does not exist. An exception is thrown if the file
	 * object exists but is a directory. An exception is thrown if the file
	 * exists but cannot be written to. An exception is thrown if the parent
	 * directory cannot be created.
	 * 
	 * @param file
	 *            the file to open for output, must not be <code>null</code>
	 * @param append
	 *            if <code>true</code>, then bytes will be added to the end of
	 *            the file rather than overwriting
	 * @return a new {@link FileOutputStream} for the specified file
	 * @throws IOException
	 *             if the file object is a directory
	 * @throws IOException
	 *             if the file cannot be written to
	 * @throws IOException
	 *             if a parent directory needs creating but that fails
	 * @since 2.1
	 */
	public static FileOutputStream openOutputStream(File file, boolean append)
			throws IOException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException("File '" + file
						+ "' exists but is a directory");
			}
			if (file.canWrite() == false) {
				throw new IOException("File '" + file
						+ "' cannot be written to");
			}
		} else {
			File parent = file.getParentFile();
			if (parent != null) {
				if (!parent.mkdirs() && !parent.isDirectory()) {
					throw new IOException("Directory '" + parent
							+ "' could not be created");
				}
			}
		}
		return new FileOutputStream(file, append);
	}

	/**
	 * Writes chars from a <code>String</code> to bytes on an
	 * <code>OutputStream</code> using the specified character encoding.
	 * <p>
	 * Character encoding names can be found at <a
	 * href="http://www.iana.org/assignments/character-sets">IANA</a>.
	 * <p>
	 * This method uses {@link String#getBytes(String)}.
	 * 
	 * @param data
	 *            the <code>String</code> to write, null ignored
	 * @param output
	 *            the <code>OutputStream</code> to write to
	 * @param encoding
	 *            the encoding to use, null means platform default
	 * @throws NullPointerException
	 *             if output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since 1.1
	 */
	public static void write(String data, OutputStream output, String encoding)
			throws IOException {
		if (data != null) {
			if (encoding == null) {
				write(data, output);
			} else {
				output.write(data.getBytes(encoding));
			}
		}
	}

	/**
	 * Writes chars from a <code>String</code> to bytes on an
	 * <code>OutputStream</code> using the default character encoding of the
	 * platform.
	 * <p>
	 * This method uses {@link String#getBytes()}.
	 * 
	 * @param data
	 *            the <code>String</code> to write, null ignored
	 * @param output
	 *            the <code>OutputStream</code> to write to
	 * @throws NullPointerException
	 *             if output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since 1.1
	 */
	public static void write(String data, OutputStream output)
			throws IOException {
		if (data != null) {
			output.write(data.getBytes());
		}
	}

	/**
	 * Get the contents of an <code>InputStream</code> as a list of Strings, one
	 * entry per line, using the specified character encoding.
	 * <p>
	 * Character encoding names can be found at <a
	 * href="http://www.iana.org/assignments/character-sets">IANA</a>.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedInputStream</code>.
	 * 
	 * @param input
	 *            the <code>InputStream</code> to read from, not null
	 * @param encoding
	 *            the encoding to use, null means platform default
	 * @return the list of Strings, never null
	 * @throws NullPointerException
	 *             if the input is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since 1.1
	 */
	public static List<String> readLines(InputStream input, String encoding)
			throws IOException {
		if (encoding == null) {
			return readLines(input);
		} else {
			InputStreamReader reader = new InputStreamReader(input, encoding);
			return readLines(reader);
		}
	}

	/**
	 * Get the contents of a <code>Reader</code> as a list of Strings, one entry
	 * per line.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedReader</code>.
	 * 
	 * @param input
	 *            the <code>Reader</code> to read from, not null
	 * @return the list of Strings, never null
	 * @throws NullPointerException
	 *             if the input is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since 1.1
	 */
	public static List<String> readLines(Reader input) throws IOException {
		BufferedReader reader = toBufferedReader(input);
		List<String> list = new ArrayList<String>();
		String line = reader.readLine();
		while (line != null) {
			list.add(line);
			line = reader.readLine();
		}
		return list;
	}

	// readLines
	// -----------------------------------------------------------------------
	/**
	 * Get the contents of an <code>InputStream</code> as a list of Strings, one
	 * entry per line, using the default character encoding of the platform.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedInputStream</code>.
	 * 
	 * @param input
	 *            the <code>InputStream</code> to read from, not null
	 * @return the list of Strings, never null
	 * @throws NullPointerException
	 *             if the input is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since 1.1
	 */
	public static List<String> readLines(InputStream input) throws IOException {
		InputStreamReader reader = new InputStreamReader(input);
		return readLines(reader);
	}

	/**
	 * Returns the given reader if it is a {@link BufferedReader}, otherwise
	 * creates a toBufferedReader for the given reader.
	 * 
	 * @param reader
	 *            the reader to wrap or return
	 * @return the given reader or a new {@link BufferedReader} for the given
	 *         reader
	 * @since 2.2
	 */
	public static BufferedReader toBufferedReader(Reader reader) {
		return reader instanceof BufferedReader ? (BufferedReader) reader
				: new BufferedReader(reader);
	}

	// -----------------------------------------------------------------------
	/**
	 * Opens a {@link FileInputStream} for the specified file, providing better
	 * error messages than simply calling <code>new FileInputStream(file)</code>
	 * .
	 * <p>
	 * At the end of the method either the stream will be successfully opened,
	 * or an exception will have been thrown.
	 * <p>
	 * An exception is thrown if the file does not exist. An exception is thrown
	 * if the file object exists but is a directory. An exception is thrown if
	 * the file exists but cannot be read.
	 * 
	 * @param file
	 *            the file to open for input, must not be <code>null</code>
	 * @return a new {@link FileInputStream} for the specified file
	 * @throws FileNotFoundException
	 *             if the file does not exist
	 * @throws IOException
	 *             if the file object is a directory
	 * @throws IOException
	 *             if the file cannot be read
	 * @since 1.3
	 */
	public static FileInputStream openInputStream(File file) throws IOException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException("File '" + file
						+ "' exists but is a directory");
			}
			if (file.canRead() == false) {
				throw new IOException("File '" + file + "' cannot be read");
			}
		} else {
			throw new FileNotFoundException("File '" + file
					+ "' does not exist");
		}
		return new FileInputStream(file);
	}

	/**
	 * Get the contents of an <code>InputStream</code> as a String using the
	 * specified character encoding.
	 * <p>
	 * Character encoding names can be found at <a
	 * href="http://www.iana.org/assignments/character-sets">IANA</a>.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedInputStream</code>.
	 * 
	 * @param input
	 *            the <code>InputStream</code> to read from
	 * @param encoding
	 *            the encoding to use, null means platform default
	 * @return the requested String
	 * @throws NullPointerException
	 *             if the input is null
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public static String toString(InputStream input, String encoding)
			throws IOException {
		StringBuilderWriter sw = new StringBuilderWriter();
		copy(input, sw, encoding);
		return sw.toString();
	}

	/**
	 * Copy bytes from an <code>InputStream</code> to chars on a
	 * <code>Writer</code> using the specified character encoding.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedInputStream</code>.
	 * <p>
	 * Character encoding names can be found at <a
	 * href="http://www.iana.org/assignments/character-sets">IANA</a>.
	 * <p>
	 * This method uses {@link InputStreamReader}.
	 * 
	 * @param input
	 *            the <code>InputStream</code> to read from
	 * @param output
	 *            the <code>Writer</code> to write to
	 * @param encoding
	 *            the encoding to use, null means platform default
	 * @throws NullPointerException
	 *             if the input or output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since 1.1
	 */
	public static void copy(InputStream input, Writer output, String encoding)
			throws IOException {
		if (encoding == null) {
			copy(input, output);
		} else {
			InputStreamReader in = new InputStreamReader(input, encoding);
			copy(in, output);
		}
	}

	/**
	 * Copy bytes from an <code>InputStream</code> to chars on a
	 * <code>Writer</code> using the default character encoding of the platform.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedInputStream</code>.
	 * <p>
	 * This method uses {@link InputStreamReader}.
	 * 
	 * @param input
	 *            the <code>InputStream</code> to read from
	 * @param output
	 *            the <code>Writer</code> to write to
	 * @throws NullPointerException
	 *             if the input or output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since 1.1
	 */
	public static void copy(InputStream input, Writer output)
			throws IOException {
		InputStreamReader in = new InputStreamReader(input);
		copy(in, output);
	}

	/**
	 * Copy chars from a <code>Reader</code> to a <code>Writer</code>.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedReader</code>.
	 * <p>
	 * Large streams (over 2GB) will return a chars copied value of
	 * <code>-1</code> after the copy has completed since the correct number of
	 * chars cannot be returned as an int. For large streams use the
	 * <code>copyLarge(Reader, Writer)</code> method.
	 * 
	 * @param input
	 *            the <code>Reader</code> to read from
	 * @param output
	 *            the <code>Writer</code> to write to
	 * @return the number of characters copied, or -1 if &gt; Integer.MAX_VALUE
	 * @throws NullPointerException
	 *             if the input or output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since 1.1
	 */
	public static int copy(Reader input, Writer output) throws IOException {
		long count = copyLarge(input, output);
		if (count > Integer.MAX_VALUE) {
			return -1;
		}
		return (int) count;
	}

	/**
	 * Copy chars from a large (over 2GB) <code>Reader</code> to a
	 * <code>Writer</code>.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedReader</code>.
	 * <p>
	 * The buffer size is given by {@link #DEFAULT_BUFFER_SIZE}.
	 * 
	 * @param input
	 *            the <code>Reader</code> to read from
	 * @param output
	 *            the <code>Writer</code> to write to
	 * @return the number of characters copied
	 * @throws NullPointerException
	 *             if the input or output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since 1.3
	 */
	public static long copyLarge(Reader input, Writer output)
			throws IOException {
		return copyLarge(input, output, new char[DEFAULT_BUFFER_SIZE]);
	}

	/**
	 * Copy chars from a large (over 2GB) <code>Reader</code> to a
	 * <code>Writer</code>.
	 * <p>
	 * This method uses the provided buffer, so there is no need to use a
	 * <code>BufferedReader</code>.
	 * <p>
	 * 
	 * @param input
	 *            the <code>Reader</code> to read from
	 * @param output
	 *            the <code>Writer</code> to write to
	 * @param buffer
	 *            the buffer to be used for the copy
	 * @return the number of characters copied
	 * @throws NullPointerException
	 *             if the input or output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since 2.2
	 */
	public static long copyLarge(Reader input, Writer output, char[] buffer)
			throws IOException {
		long count = 0;
		int n = 0;
		while (EOF != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

	/**
	 * Unconditionally close an <code>InputStream</code>.
	 * <p>
	 * Equivalent to {@link InputStream#close()}, except any exceptions will be
	 * ignored. This is typically used in finally blocks.
	 * <p>
	 * Example code:
	 * 
	 * <pre>
	 * byte[] data = new byte[1024];
	 * InputStream in = null;
	 * try {
	 * 	in = new FileInputStream(&quot;foo.txt&quot;);
	 * 	in.read(data);
	 * 	in.close(); // close errors are handled
	 * } catch (Exception e) {
	 * 	// error handling
	 * } finally {
	 * 	IOUtils.closeQuietly(in);
	 * }
	 * </pre>
	 * 
	 * @param input
	 *            the InputStream to close, may be null or already closed
	 */
	public static void closeQuietly(InputStream input) {
		closeQuietly((Closeable) input);
	}

	/**
	 * Unconditionally close a <code>Closeable</code>.
	 * <p>
	 * Equivalent to {@link Closeable#close()}, except any exceptions will be
	 * ignored. This is typically used in finally blocks.
	 * <p>
	 * Example code:
	 * 
	 * <pre>
	 * Closeable closeable = null;
	 * try {
	 * 	closeable = new FileReader(&quot;foo.txt&quot;);
	 * 	// process closeable
	 * 	closeable.close();
	 * } catch (Exception e) {
	 * 	// error handling
	 * } finally {
	 * 	IOUtils.closeQuietly(closeable);
	 * }
	 * </pre>
	 * 
	 * @param closeable
	 *            the object to close, may be null or already closed
	 * @since 2.0
	 */
	public static void closeQuietly(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (IOException ioe) {
			// ignore
		}
	}

	/**
	 * {@link Writer} implementation that outputs to a {@link StringBuilder}.
	 * <p>
	 * <strong>NOTE:</strong> This implementation, as an alternative to
	 * <code>java.io.StringWriter</code>, provides an <i>un-synchronized</i>
	 * (i.e. for use in a single thread) implementation for better performance.
	 * For safe usage with multiple {@link Thread}s then
	 * <code>java.io.StringWriter</code> should be used.
	 * 
	 * @version $Id: StringBuilderWriter.java 1304052 2012-03-22 20:55:29Z
	 *          ggregory $
	 * @since 2.0
	 */
	public static class StringBuilderWriter extends Writer implements
			Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 4257946458803494800L;
		private final StringBuilder builder;

		/**
		 * Construct a new {@link StringBuilder} instance with default capacity.
		 */
		public StringBuilderWriter() {
			this.builder = new StringBuilder();
		}

		/**
		 * Construct a new {@link StringBuilder} instance with the specified
		 * capacity.
		 * 
		 * @param capacity
		 *            The initial capacity of the underlying
		 *            {@link StringBuilder}
		 */
		public StringBuilderWriter(int capacity) {
			this.builder = new StringBuilder(capacity);
		}

		/**
		 * Construct a new instance with the specified {@link StringBuilder}.
		 * 
		 * @param builder
		 *            The String builder
		 */
		public StringBuilderWriter(StringBuilder builder) {
			this.builder = builder != null ? builder : new StringBuilder();
		}

		/**
		 * Append a single character to this Writer.
		 * 
		 * @param value
		 *            The character to append
		 * @return This writer instance
		 */
		@Override
		public Writer append(char value) {
			builder.append(value);
			return this;
		}

		/**
		 * Append a character sequence to this Writer.
		 * 
		 * @param value
		 *            The character to append
		 * @return This writer instance
		 */
		@Override
		public Writer append(CharSequence value) {
			builder.append(value);
			return this;
		}

		/**
		 * Append a portion of a character sequence to the {@link StringBuilder}
		 * .
		 * 
		 * @param value
		 *            The character to append
		 * @param start
		 *            The index of the first character
		 * @param end
		 *            The index of the last character + 1
		 * @return This writer instance
		 */
		@Override
		public Writer append(CharSequence value, int start, int end) {
			builder.append(value, start, end);
			return this;
		}

		/**
		 * Closing this writer has no effect.
		 */
		@Override
		public void close() {
		}

		/**
		 * Flushing this writer has no effect.
		 */
		@Override
		public void flush() {
		}

		/**
		 * Write a String to the {@link StringBuilder}.
		 * 
		 * @param value
		 *            The value to write
		 */
		@Override
		public void write(String value) {
			if (value != null) {
				builder.append(value);
			}
		}

		/**
		 * Write a portion of a character array to the {@link StringBuilder}.
		 * 
		 * @param value
		 *            The value to write
		 * @param offset
		 *            The index of the first character
		 * @param length
		 *            The number of characters to write
		 */
		@Override
		public void write(char[] value, int offset, int length) {
			if (value != null) {
				builder.append(value, offset, length);
			}
		}

		/**
		 * Return the underlying builder.
		 * 
		 * @return The underlying builder
		 */
		public StringBuilder getBuilder() {
			return builder;
		}

		/**
		 * Returns {@link StringBuilder#toString()}.
		 * 
		 * @return The contents of the String builder.
		 */
		@Override
		public String toString() {
			return builder.toString();
		}
	}
}