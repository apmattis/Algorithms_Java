package StringMatching;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.Scanner;

public class P02 {

    private static StringBuilder text, pattern;
    private static String inText, inPattern;
    private static NaiveSearch ns;
    private static RKSearch rk;
    private static KMPSearch kmp;
    private static long totalTime, totalTime2, totalTime3;
    private int lineCount = 0;

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }

    public int getlineCount() {
        return lineCount;
    }

    private static StringBuilder fileReader(String fin)  {
        String line;
        int count = 0;
        StringBuilder sb = new StringBuilder();
        try (FileReader fReader = new FileReader(fin);
             BufferedReader bReader = new BufferedReader(fReader)) {

            while ((line = bReader.readLine()) != null) {
                sb.append(line);
                count++;
            }

            bReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '"
                    + fin + "'");
            System.exit(0);
        } catch (IOException ex) {
            System.out.println("Error reading file '"
                    + fin + "'");
            System.exit(0);
        }

        return sb;
    }

    private static void fileWriter(String fout) {

        try (FileWriter fWriter = new FileWriter(fout);
             BufferedWriter bWriter = new BufferedWriter(fWriter))
        {
            bWriter.write("Naive time: " + String.valueOf(totalTime/1000/1000.0) + " ms");
            bWriter.newLine();
            bWriter.write("RK time: " + String.valueOf(totalTime2/1000/1000.0) + " ms");
            bWriter.newLine();
            bWriter.write("KMP time: " + String.valueOf(totalTime3/1000/1000.0) + " ms");
            bWriter.newLine();

            bWriter.newLine();

            bWriter.write("pattern: " + ns.getP());
            bWriter.newLine();
            bWriter.write("Occurrences: " + ns.getCount());
            bWriter.newLine();
            bWriter.write("At indices: " + ns.getIdxFound().toString());
            bWriter.newLine();

            bWriter.newLine();

            bWriter.write("pattern: " + rk.getP());
            bWriter.newLine();
            bWriter.write("Occurrences: " + rk.getCount());
            bWriter.newLine();
            bWriter.write("At indices: " + rk.getIdxFound().toString());
            bWriter.newLine();

            bWriter.newLine();

            bWriter.write("pattern: " + kmp.getP());
            bWriter.newLine();
            bWriter.write("Occurrences: " + kmp.getCount());
            bWriter.newLine();
            bWriter.write("At indices: " + kmp.getIdxFound().toString());
            bWriter.newLine();

            bWriter.flush();
            bWriter.close();
        } catch (FileAlreadyExistsException e) {
            System.out.println("File '" + fout + "' already exists.");
            System.exit(0);
        } catch (IOException ex) {
            System.out.println("Error writing file '"
                    + fout + "'");
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        String fin, fin2, fout;
        int choice;
        Scanner in = new Scanner(System.in);
        BufferedReader br;

        System.out.print("Enter 1 to search file, 2 to search input stream, or -1 to quit: ");
        choice = in.nextInt();

        while(choice != -1) {
            switch(choice) {
                case 1:
                    System.out.println("Enter the file to search: ");
                    fin = "test/" + in.next();                          //user input
                    //fin = "test/sampleInput3.txt";
                    text = fileReader(fin);

                    System.out.println("Enter the pattern file: ");
                    fin2 = "test/" + in.next();
                    //fin2 = "test/samplePattern.txt";
                    pattern = fileReader(fin2);


                    ns = new NaiveSearch(text.toString(), pattern.toString());
                    long time1 = System.nanoTime();
                    ns.run();
                    totalTime = System.nanoTime() - time1;

                    rk = new RKSearch(text.toString(), pattern.toString());
                    long time2 = System.nanoTime();
                    rk.run();
                    totalTime2 = System.nanoTime() - time2;

                    kmp = new KMPSearch(text.toString(), pattern.toString());
                    long time3 = System.nanoTime();
                    kmp.run();
                    totalTime3 = System.nanoTime() - time3;

                    fout = fin.replaceAll(".txt", "Out.txt");
                    fileWriter(fout);
                    break;
                case 2:
                    try {
                        br = new BufferedReader(new InputStreamReader(System.in));

                        System.out.println("Enter some text to search: ");
                        inText = br.readLine();
                        System.out.println("Enter a pattern to find: ");
                        inPattern = br.readLine();

                    }catch (IOException e) {
                        e.printStackTrace();
                    }

                    ns = new NaiveSearch(inText, inPattern);
                    time1 = System.nanoTime();
                    ns.run();
                    totalTime = System.nanoTime() - time1;

                    rk = new RKSearch(inText, inPattern);
                    time2 = System.nanoTime();
                    rk.run();
                    totalTime2 = System.nanoTime() - time2;

                    kmp = new KMPSearch(inText, inPattern);
                    time3 = System.nanoTime();
                    kmp.run();
                    totalTime3 = System.nanoTime() - time3;

                    fout = "test/inputStreamOut.txt";
                    fileWriter(fout);
                    break;
            }
            System.out.print("Enter 1 to search file, 2 to search input stream, or -1 to quit: ");
            choice = in.nextInt();
        }
    }
}
