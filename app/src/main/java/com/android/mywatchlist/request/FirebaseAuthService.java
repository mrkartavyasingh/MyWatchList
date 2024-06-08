package com.android.mywatchlist.request;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseAuthService {
    static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public static FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }


}
