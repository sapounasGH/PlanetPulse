package com.example.planyerpulse;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class dataanalyzation {

    public ArrayList dataAnalysation2(Context context ) {
        ArrayList<drasiCLASS> arrayList = new ArrayList<>();
        List<List<String[]>> allFilesData = new ArrayList<>();
        List<List<String[]>> allFilesData1 = new ArrayList<>();
        List<List<String[]>> allFilesData2 = new ArrayList<>();
        List<List<String[]>> allFilesData3 = new ArrayList<>();
        try {
            String[] files = context.getAssets().list("");
            //
            for (String filename : files) {
                if (filename.startsWith("all_2024_no_excel_file__NOx")) {
                    List<String[]> rows = new ArrayList<>();
                    InputStream is = context.getAssets().open(filename);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] cols = line.split(",");
                        rows.add(cols);
                    }
                    reader.close();
                    allFilesData.add(rows);
                }

                if (filename.startsWith("all_2024_so_excel_file__SO2")) {
                    List<String[]> rows = new ArrayList<>();
                    InputStream is = context.getAssets().open(filename);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] cols = line.split(",");
                        rows.add(cols);
                    }
                    reader.close();
                    allFilesData1.add(rows);
                }

                if (filename.startsWith("all_data_2024_co__CO")) {
                    List<String[]> rows = new ArrayList<>();
                    InputStream is = context.getAssets().open(filename);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] cols = line.split(",");
                        rows.add(cols);
                    }
                    reader.close();
                    allFilesData2.add(rows);
                }
                if (filename.startsWith("o3_ag_merged__O3#AGS")) {
                    List<String[]> rows = new ArrayList<>();
                    InputStream is = context.getAssets().open(filename);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] cols = line.split(",");
                        rows.add(cols);
                    }
                    reader.close();
                    allFilesData3.add(rows);
                }

            }
        } catch (IOException e) {
        }

        // εδω θα μπούνε τα analysation των data
        double totalNo2 = 0;
        int countNo2 = 0;

        for (List<String[]> fileData : allFilesData) {
            for (int i = 1; i < fileData.size(); i++) {
                String[] row = fileData.get(i);
                try {
                    double no2 = Double.parseDouble(row[4]);
                    totalNo2 += no2;
                    countNo2++;
                } catch (Exception e) {
                    continue;
                }
            }
        }
        double avgNo2 = totalNo2 / countNo2;
        if (avgNo2 > 10) {
            arrayList.add(new drasiCLASS(LocalDate.now().plusDays(7), "Λευκός Πύργος", LocalTime.of(9, 0), "<<Περπατάμε Μαζί>> "));
        }
/// /////////////////////
        double totalSo2 = 0;
        int countSo2 = 0;

        for (List<String[]> fileData : allFilesData1) {
            for (int i = 1; i < fileData.size(); i++) {
                String[] row = fileData.get(i);
                try {
                    double So2 = Double.parseDouble(row[2]);
                    totalSo2 += So2;
                    countSo2++;
                } catch (Exception e) {
                    continue;
                }
            }
        }
        double avgSo2 = totalSo2 / countSo2;

        if (avgSo2 > 0.350) {
            arrayList.add(new drasiCLASS(LocalDate.now().plusDays(7), "Λευκός Πύργος", LocalTime.of(9, 0), "<<Εργαστήρι Καθαρού Αέρα>>"));
        }
/// ////////////////////////
        double totalCo = 0;
        int countCo = 0;

        for (List<String[]> fileData : allFilesData2) {
            for (int i = 1; i < fileData.size(); i++) {
                String[] row = fileData.get(i);
                try {
                    double Co = Double.parseDouble(row[4]);
                    totalCo += Co;
                    countCo++;
                } catch (Exception e) {
                    continue;
                }
            }
        }
        double avgCo = totalCo / countCo;
        if (avgCo > 1) {
            arrayList.add(new drasiCLASS(LocalDate.now().plusDays(7), "Λευκός Πύργος", LocalTime.of(9, 0), "Ζεσταίνουμε αλλιώς"));
        }
/// //////////////////////////
        double totalO3AGS = 0;
        int countO3AGS = 0;

        for (List<String[]> fileData : allFilesData2) {
            for (int i = 1; i < fileData.size(); i++) {
                String[] row = fileData.get(i);
                try {
                    double Co = Double.parseDouble(row[4]);
                    totalO3AGS += Co;
                    countO3AGS++;
                } catch (Exception e) {
                    continue;
                }
            }
        }
        double avgO3AGS = totalO3AGS / countO3AGS;
        if (avgO3AGS > 1) {
            arrayList.add(new drasiCLASS(LocalDate.now().plusDays(14), "Λευκός Πύργος", LocalTime.of(9, 0), "Ζεσταίνουμε αλλιώς"));
        }
        return arrayList;
    }
}
