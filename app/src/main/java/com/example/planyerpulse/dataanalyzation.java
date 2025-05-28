package com.example.planyerpulse;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dataanalyzation {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<String, Object> data = new HashMap<>();

    public void checkΤΟupdate(Context  context){
        db.collection("datesUpdates")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                        Timestamp lastUpdate = documentSnapshot.getTimestamp("lastUpdate");
                        Timestamp nextUpdate = documentSnapshot.getTimestamp("nextUpdate");
                        if (lastUpdate != null && nextUpdate != null) {
                            Calendar calendar = Calendar.getInstance();
                            Date currentDate = calendar.getTime();
                            Timestamp currentTimestamp = new Timestamp(currentDate);
                            if (currentTimestamp.compareTo(nextUpdate)>0) {
                                dataAnalysation2(context);
                                Calendar newCalendar = Calendar.getInstance();
                                newCalendar.add(Calendar.DAY_OF_YEAR, 30);
                                Timestamp newNextUpdate = new Timestamp(newCalendar.getTime());
                                Map<String, Object> updates = new HashMap<>();
                                updates.put("lastUpdate", nextUpdate);
                                updates.put("nextUpdate", newNextUpdate);
                                db.collection("datesUpdates")
                                        .document(documentSnapshot.getId())
                                        .update(updates)
                                        .addOnSuccessListener(aVoid -> {
                                            Log.d("Firestore", "Ημερομηνίες ενημερώθηκαν με επιτυχία.");
                                        })
                                        .addOnFailureListener(e -> {
                                            Log.e("Firestore", "Σφάλμα στην ενημέρωση ημερομηνιών: ", e);
                                        });
                            } else {
                                Log.d("Firestore", "Η τωρινή ημερομηνία δεν είναι μεγαλύτερη από την επόμενη.");
                            }
                        }
                    } else {
                        Log.d("Firestore", "Δεν βρέθηκαν έγγραφα.");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Σφάλμα στην ανάκτηση: ", e);
                });
    }
    public void dataAnalysation2(Context context ) {
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
            //arrayList.add(new drasiCLASS(LocalDate.now().plusDays(7), "Λευκός Πύργος", LocalTime.of(9, 0), "<<Περπατάμε Μαζί>> "));
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 14);
            calendar.set(Calendar.HOUR_OF_DAY, 10);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date newDate = calendar.getTime();
            Timestamp newTimestamp = new Timestamp(newDate);
            GeoPoint geoPoint = new GeoPoint(40.62626280172782, 22.947939295840637);
            data.put("dateTime", newTimestamp);
            data.put("mapsPlace", geoPoint);
            data.put("name", "Περπατάμε Μαζί");
            data.put("place", "Λευκός Πύργος");
            db.collection("drasi").add(data)
                    .addOnSuccessListener(aVoid -> {
                        Log.d("Firestore", "DocumentSnapshot successfully written!");
                    })
                    .addOnFailureListener(e -> {
                        Log.w("Firestore", "Error writing document", e);
                    });
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
            //arrayList.add(new drasiCLASS(LocalDate.now().plusDays(7), "Λευκός Πύργος", LocalTime.of(9, 0), "<<Εργαστήρι Καθαρού Αέρα>>"));
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 10);
            calendar.set(Calendar.HOUR_OF_DAY, 10);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date newDate = calendar.getTime();
            Timestamp newTimestamp = new Timestamp(newDate);
            GeoPoint geoPoint = new GeoPoint(40.62626280172782, 22.947939295840637);
            data.put("dateTime", newTimestamp);
            data.put("mapsPlace", geoPoint);
            data.put("name", "Εργαστήρι Καθαρού Αέρα");
            data.put("place", "Λευκός Πύργος");
            db.collection("drasi").add(data)
                    .addOnSuccessListener(aVoid -> {
                        Log.d("Firestore", "DocumentSnapshot successfully written!");
                    })
                    .addOnFailureListener(e -> {
                        Log.w("Firestore", "Error writing document", e);
                    });
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
            //arrayList.add(new drasiCLASS(LocalDate.now().plusDays(7), "Λευκός Πύργος", LocalTime.of(9, 0), "Ζεσταίνουμε αλλιώς"));
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 10);
            calendar.set(Calendar.HOUR_OF_DAY, 20);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date newDate = calendar.getTime();
            Timestamp newTimestamp = new Timestamp(newDate);
            GeoPoint geoPoint = new GeoPoint(40.62626280172782, 22.947939295840637);
            data.put("dateTime", newTimestamp);
            data.put("mapsPlace", geoPoint);
            data.put("name", "Ζεσταίνουμε αλλιώς");
            data.put("place", "Λευκός Πύργος");
            db.collection("drasi").add(data)
                    .addOnSuccessListener(aVoid -> {
                        Log.d("Firestore", "DocumentSnapshot successfully written!");
                    })
                    .addOnFailureListener(e -> {
                        Log.w("Firestore", "Error writing document", e);
                    });
        }
/////////////////////////////
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
            //arrayList.add(new drasiCLASS(LocalDate.now().plusDays(14), "Λευκός Πύργος", LocalTime.of(9, 0), "Ζεσταίνουμε αλλιώς"));
        }
    }
}
