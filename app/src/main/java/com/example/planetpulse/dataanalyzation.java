package com.example.planetpulse;

import android.content.Context;
import android.util.Log;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        List<List<String[]>> allFilesData4 = new ArrayList<>();
        try {
            String[] files = context.getAssets().list("");
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

                if (filename.startsWith("pm10_merged_2__PM10#NEO")) {
                    List<String[]> rows = new ArrayList<>();
                    InputStream is = context.getAssets().open(filename);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] cols = line.split(",");
                        rows.add(cols);
                    }
                    reader.close();
                    allFilesData4.add(rows);
                }

            }
        } catch (IOException e) {
        }

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

        for (List<String[]> fileData : allFilesData3) {
            for (int i = 0; i < fileData.size(); i++) {
                String[] row = fileData.get(i);
                for (int j = 1; j < row.length; j++) {
                    try {
                        double O3 = Double.parseDouble(row[j]);
                        totalO3AGS += O3;
                        countO3AGS++;
                    } catch (Exception e) {
                    }
                }
            }
        }
        double avgO3AGS = totalO3AGS / countO3AGS;
        if (avgO3AGS > 60) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 6);
            calendar.set(Calendar.HOUR_OF_DAY, 18);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date newDate = calendar.getTime();
            Timestamp newTimestamp = new Timestamp(newDate);
            GeoPoint geoPoint = new GeoPoint(40.633455, 22.945909);
            data.put("dateTime", newTimestamp);
            data.put("mapsPlace", geoPoint);
            data.put("name", "Ενημερωτική Εκστρατεία για Υψηλό Οζον");
            data.put("place", "Αγίας Σοφίας");
            db.collection("drasi").add(data)
                    .addOnSuccessListener(aVoid -> {
                        Log.d("Firestore", "DocumentSnapshot successfully written!");
                    })
                    .addOnFailureListener(e -> {
                        Log.w("Firestore", "Error writing document", e);
                    });
        }
        /// ////////////////////////
        double totalPM10 = 0;
        int countPM10 = 0;

        for (List<String[]> fileData : allFilesData4) {
            for (int i = 0; i < fileData.size(); i++) {
                String[] row = fileData.get(i);
                for (int j = 1; j < row.length; j++) {
                    try {
                        double PM10 = Double.parseDouble(row[j]);
                        if (PM10 > -100 && PM10 < 1000) {
                            totalPM10 += PM10;
                            countPM10++;
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }

        double avgPM10 = totalPM10 / countPM10;
        if (avgPM10 > 50) {
            Map<String, Object> data = new HashMap<>();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 3);
            calendar.set(Calendar.HOUR_OF_DAY, 19);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date newDate = calendar.getTime();
            Timestamp newTimestamp = new Timestamp(newDate);
            GeoPoint geoPoint = new GeoPoint(40.629269, 22.948545); // π.χ. Νέο Δημαρχείο

            data.put("dateTime", newTimestamp);
            data.put("mapsPlace", geoPoint);
            data.put("name", "Δράση Ενημέρωσης για PM10");
            data.put("place", "Νέο Δημαρχείο");
            data.put("avgPM10", avgPM10);

            db.collection("drasi").add(data)
                    .addOnSuccessListener(aVoid -> Log.d("Firestore", "DocumentSnapshot successfully written!"))
                    .addOnFailureListener(e -> Log.w("Firestore", "Error writing document", e));
        }
   }
}
