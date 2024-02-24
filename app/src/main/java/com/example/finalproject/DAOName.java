package com.example.finalproject;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class DAOName {

    private DatabaseReference databaseReference;

    public DAOName() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(FullName.class.getSimpleName());
    }

    public Task<Void> add(FullName name){
        return databaseReference.push().setValue(name);
    }
}
