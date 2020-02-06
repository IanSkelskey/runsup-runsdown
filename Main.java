//******************************************************************************
//CLASS: CSE205 (CSE205.java)
//
//DESCRIPTION
//This program will read a list of integers from a text file and perform a
//statistical runs up and runs down test. The program will output a text file
//which includes the individual runs(k) as well as the runs(total)
//
//COURSE AND PROJECT INFO
//CSE205 Object Oriented Programming and Data Structures, Fall 2010
//Project Number: 01
//
//AUTHOR: Ian Skelskey, iskelske, ianskelskey@gmail.com
//******************************************************************************
package p01;

import java.io.*;
import java.util.*;

public class Main {

    public static int RUNS_UP = 1;
    public static int RUNS_DN = 0;

    public static void main(String args[]) {
        new Main().run();
    }

    private void run() {
        ArrayList<Integer> list = readInputFile("p01-in.txt");
        if (list == null || list.size() <= 0) {
            return;
        }
        ArrayList<Integer> listRunsUpCount = findRuns(list, RUNS_UP);
        ArrayList<Integer> listRunsDnCount = findRuns(list, RUNS_DN);
        ArrayList<Integer> listRunsCount = mergeLists(listRunsUpCount, listRunsDnCount);
        writeOutputFile("p01-runs.txt", listRunsCount);
    }

    private ArrayList<Integer> findRuns(ArrayList<Integer> pList, int pDir) {
        ArrayList<Integer> listRunsCount = arrayListCreate(pList.size(), 0);
        if (pDir == RUNS_UP) {
            int i = 0;
            int k = 0;
            while (i < pList.size() - 1) {
                if (pList.get(i) <= pList.get(i + 1)) {
                    k++;
                } else {
                    listRunsCount.set(k, listRunsCount.get(k) + 1);
                    k = 0;
                }
                i++;
            }
            if (k > 0) {
                listRunsCount.set(k, listRunsCount.get(k) + 1);
            }
        }
        if (pDir == RUNS_DN) {
            int i = 0;
            int k = 0;
            while (i < pList.size() - 1) {
                if (pList.get(i) >= pList.get(i + 1)) {
                    k++;
                } else {
                    listRunsCount.set(k, listRunsCount.get(k) + 1);
                    k = 0;
                }
                i++;
            }
            if (k > 0) {
                listRunsCount.set(k, listRunsCount.get(k) + 1);
            }
        }
        return listRunsCount;
    }

    private ArrayList<Integer> mergeLists(ArrayList<Integer> pListRunsUpCount, ArrayList<Integer> pListRunsDnCount) {
        ArrayList<Integer> mergedList = arrayListCreate(pListRunsDnCount.size(), 0);
        for (int i = 0; i < pListRunsUpCount.size(); i++) {
            mergedList.set(i, pListRunsUpCount.get(i) + pListRunsDnCount.get(i));
        }
        return mergedList;
    }

    private ArrayList<Integer> arrayListCreate(int pSize, int pInitValue) {
        ArrayList<Integer> listRunsCount = new ArrayList<>();
        for (int i = 0; i < pSize; i++) {
            listRunsCount.add(pInitValue);
        }
        return listRunsCount;
    }

    private void writeOutputFile(String pFilename, ArrayList<Integer> pListRuns) {
        File file = new File(pFilename);
        FileWriter fw = null;
        int totalRun = 0;
        for (int i = 1; i < pListRuns.size(); i++) {
            if (pListRuns.get(i) > 0) {
                totalRun += pListRuns.get(i);
            }
        }
        try {
            fw = new FileWriter(file);
            String totalRuns = "runs_total: " + totalRun + "\n";
            fw.write(totalRuns);
            for (int i = 1; i < pListRuns.size(); i++) {
                String toWrite = "run_" + i + ": " + pListRuns.get(i) + "\n";
                fw.write(toWrite);
            }
        } catch (FileNotFoundException pExcept) {
            System.out.println("Oops, could not open 'p01-runs.txt' for writing. The program is ending.");
            pExcept.printStackTrace();
            System.exit(-200);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fw.close();
            } catch (IOException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private ArrayList<Integer> readInputFile(String pFilename) {
        ArrayList<Integer> list = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File(pFilename));
            while (sc.hasNext()) {
                String line = sc.nextLine();
                String[] parts = line.split(" ");
                for (String str : parts) {
                    list.add(Integer.parseInt(str));
                }
            }
        } catch (FileNotFoundException pExcept) {
            System.out.println("Oops, could not open 'p01-in.txt' for reading. The program is ending.");
            pExcept.printStackTrace();
            System.exit(-100);
        }
        return list;
    }

}
