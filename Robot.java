import java.io.*;

public class Robot {
    private static final String INVALID_INPUT = "Invalid Input";
    private static final long TenK = 10 * 1024l;
    private static BufferedWriter writer = null;
    private static BufferedReader reader = null;

    public static void main(String[] args) {
        if (!generateInputOutput(args)) {
            return;
        }
        int X = 0, Y = 0;
        try {
            String tempString = reader.readLine();
            if (tempString == null || tempString.trim().length() == 0) outInvalidInput();
            if (!tempString.startsWith("(") || !tempString.endsWith(")") || !tempString.contains(","))
                outInvalidInput();
            tempString = tempString.substring(1, tempString.length() - 1);
            String[] s = tempString.split(",");

            try {
                X = Integer.valueOf(s[0]);
                Y = Integer.valueOf(s[1]);
            } catch (NumberFormatException e) {
                outInvalidInput();
            }

            if (X < 2 || X > 20) outInvalidInput();
            if (Y < 2 || Y > 20) outInvalidInput();

            X -= 1;
            Y -= 1;
            StringBuilder sb = new StringBuilder();
            generateResult(X, Y, "", sb);
            String sss = sb.toString();
            sss = sss.substring(0, sss.length() - 1);
            writer.write(sss);
            writer.flush();
        } catch (IOException e) {
            outInvalidInput();
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    private static void generateResult(int x, int y, String result, StringBuilder sb) {
        if (x == 0 && y == 0) {
            sb.append(result);
            sb.append("\n");
        }
        if (x != 0) {
            generateResult(x - 1, y, result + "X", sb);
        }
        if (y != 0) {
            generateResult(x, y - 1, result + "Y", sb);
        }
    }

    private static void outInvalidInput() {
        try {
            writer.write("Invalid Input");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e1) {
                }
            }
        }
        System.exit(0);
    }


    private static boolean generateInputOutput(String args[]) {
        try {
            File input = new File(args[0]);
            File output = new File(args[1]);
            reader = new BufferedReader(new FileReader(input));
            writer = new BufferedWriter(new FileWriter(output));
            if (!input.exists() || !input.canRead() || input.length() > TenK) {
                writer.write(INVALID_INPUT);
                writer.flush();
                return false;
            }
        } catch (Exception e) {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e1) {
                }
            }
            return false;
        }
        return true;
    }
}
