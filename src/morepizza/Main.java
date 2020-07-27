package morepizza;

import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;


public class Main {

    static ArrayList<String> inputFiles;

    static int M, N;
    static ArrayList<Integer> typesOfPizza;

    static int bestScore;
    static int bestNumberOfPizzasOrdered;
    static ArrayList<Integer> bestTypesOfPizzaOrdered;


    static void getInputFiles(String folder) {

        inputFiles = new ArrayList<String>();

        File inputFolder = new File(folder);
        File [] listOfFiles = inputFolder.listFiles();

        if (listOfFiles != null)
            for (File inputFile : listOfFiles)
                if (inputFile.isFile())
                    inputFiles.add(inputFile.getName());

    }


    static void read(String file) throws FileNotFoundException {

        M = 0;
        N = 0;
        typesOfPizza = new ArrayList<Integer>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            for (int i = 0; i < 2; i++) {
                String line = br.readLine();

                if (i == 0) {
                    M = Integer.parseInt(line.split(" ")[0]);
                    N = Integer.parseInt(line.split(" ")[1]);
                } else {
                    String [] auxTypesOfPizza = line.split(" ");
                    for (String atop : auxTypesOfPizza)
                        typesOfPizza.add(Integer.parseInt(atop));
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println(e);
        }

    }


    static void solve() {

        bestScore = -1;
        bestNumberOfPizzasOrdered = 0;
        bestTypesOfPizzaOrdered = new ArrayList<Integer>();

        int it = 0;

        while (it < N) {
            int numberOfPizzasOrdered = 0;
            ArrayList<Integer> typesOfPizzaOrdered = new ArrayList<Integer>();
            int totalNumberOfSlices = 0;

            for (int i = N-1-it; i >= 0; i--) {
                totalNumberOfSlices += typesOfPizza.get(i);

                if (totalNumberOfSlices <= M) {
                    numberOfPizzasOrdered += 1;
                    typesOfPizzaOrdered.add(i);

                    if (bestScore < totalNumberOfSlices) {
                        bestScore = totalNumberOfSlices;
                        bestNumberOfPizzasOrdered = numberOfPizzasOrdered;
                        bestTypesOfPizzaOrdered = typesOfPizzaOrdered;
                    }
                } else
                    totalNumberOfSlices -= typesOfPizza.get(i);
            }

            it++;
        }

    }


    static void write(String file) throws FileNotFoundException {

        try {
            PrintWriter outputFile = new PrintWriter(file.replace(".in", ".out"));

            outputFile.println(bestNumberOfPizzasOrdered);

            for (int i = bestNumberOfPizzasOrdered - 1; i >= 0; i--)
                outputFile.print(bestTypesOfPizzaOrdered.get(i) + " ");

            outputFile.close();
        } catch (IOException e) {
            System.out.println(e);
        }

    }



    public static void main(String[] args) throws FileNotFoundException {

        getInputFiles("input");

        for (String inputFile : inputFiles) {
            read("input/" + inputFile);
            solve();
            write("output/" + inputFile);
            System.out.println(inputFile + " ------> " + Integer.toString(bestScore) + " points");
        }

    }

}
