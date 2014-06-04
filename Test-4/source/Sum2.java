import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 *
 * Copyright 2014 Cisco Inc. All rights reserved.
 * Sum2.java
 *
 */

/**
 *
 * @Title: Sum2.java
 * @Package: 
 * @Description: 
 * 
 * @Author: aaron
 * @Date: 2014-3-10
 *
 */

public class Sum2 {
    private static String INVLID_INPUT = "Invalid Input";
    static class People {
        public float W;
        public float H;
        public People(float w, float h) throws Exception {
            // TODO Auto-generated constructor stub
            if(w < 0 || h < 0) {
                throw new Exception();
            }
            W = w;
            H = h;
        }
    }
    
    public static int max=0;
    public static void main(String[] args) {
        BufferedReader reader = readFileContent(args[0]);
        if(reader != null) {
            try {
                String lineString = reader.readLine();
                if(lineString.charAt(0) != '(') {
                    throw new Exception();
                }
                ArrayList<People> list = new ArrayList<Sum2.People>();
                int startindex = 0;
                int endindex = 0;
                while(endindex < lineString.length()) {
                    startindex = lineString.indexOf('(');
                    endindex = lineString.indexOf(')');
                    String pairString = lineString.substring(startindex+1, endindex);
                    String[] pair = pairString.split(",");
                    if(pair[0].contains(" ") || pair[1].contains(" ")) {
                        throw new Exception();
                    }
                    People people = new People(Float.parseFloat(pair[0]), Float.parseFloat(pair[1]));
                    list.add(people);
                    lineString = lineString.substring(endindex+1);
                }
                if(list.size() > 100) {
                    throw new Exception();
                }
                Collections.sort(list, new MyCompator());
                int flag = 0;
                while(flag != -1) {
                    flag = getMax(list, flag);
                }
                
                writeResult(args[1], String.valueOf(max));
               
                
            } catch (Exception e) {
                // TODO Auto-generated catch block
//                e.printStackTrace();
                writeResult(args[1], INVLID_INPUT);
            }
            
        }

        
    }
    
    
    private static int getMax(ArrayList<People> list, int pos) {
        People p, n;
        int currentmax = 1;
        int flag = -1;
        p = list.get(pos);
        for(int i=pos; i<list.size()-1; i++) {
            n = list.get(i+1);
            if(p.H < n.H) {
                currentmax++;
                p = n;
                continue;
            } else {
                if(flag == -1) {
                    flag = pos+1;
                }
            }
        }
        if(currentmax > max) {
            max = currentmax;
        }
        
        return flag;
    }
    
    static class MyCompator implements java.util.Comparator<People> {

        @Override
        public int compare(People arg0, People arg1) {
            float num =  arg0.W - arg1.W;
            if(num != 0 ) {
                if(num > 0) {
                    return 1;
                } else {
                    return -1;
                }
            }
            
            num =  arg0.H-arg1.H;
            if(num != 0 ) {
                if(num > 0) {
                    return 1;
                } else {
                    return -1;
                }
            }
            return 0;
        }

       
        
    }
    
    private static BufferedReader readFileContent(String filename) {
        File file = new File(filename);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            return reader;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    
    private static void writeResult(String filename, String result) {
        File file = new File(filename);
        FileOutputStream strem = null;
        try {
            file.createNewFile();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            strem = new FileOutputStream(file);
            strem.write(result.getBytes());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(strem != null) {
                try {
                    strem.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        
    }
}
