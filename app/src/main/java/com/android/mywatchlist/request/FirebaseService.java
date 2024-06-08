package com.android.mywatchlist.request;

import android.annotation.SuppressLint;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseService {
    @SuppressLint("StaticFieldLeak")
    public static FirebaseFirestore firebaseDatabase = FirebaseFirestore.getInstance();
    public static CollectionReference reference_users = firebaseDatabase
            .collection("users");

    public static CollectionReference reference_mwl_movies(String user_id) {
        return  reference_users
                .document(user_id)
                .collection("mwl_movies");
    }
    public static CollectionReference reference_mwl_tv_shows(String user_id) {
        return  reference_users
                .document(user_id)
                .collection("mwl_tv_shows");
    }

    public static CollectionReference reference_mwl_anime(String user_id) {
        return  reference_users
                .document(user_id)
                .collection("mwl_anime");
    }
}
