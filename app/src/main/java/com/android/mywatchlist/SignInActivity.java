package com.android.mywatchlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.android.mywatchlist.databinding.ActivitySignInBinding;
import com.android.mywatchlist.models.UserModel;
import com.android.mywatchlist.request.FirebaseAuthService;
import com.android.mywatchlist.request.FirebaseService;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

public class SignInActivity extends AppCompatActivity {
    private ActivitySignInBinding activitySignInBinding;
    final String TAG = "SignInActivity();";
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignInBinding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(activitySignInBinding.getRoot());

        activitySignInBinding.signInActivityBackButton.setOnClickListener(v -> onBackPressed());

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            GoogleSignIn.getClient(this, googleSignInOptions).signOut();
            mGoogleSignInClient.signOut();
        }
        GoogleSignIn.getClient(this, googleSignInOptions).revokeAccess();
        Log.d(TAG, "onCreate: " + mGoogleSignInClient.getSignInIntent().getData());
        Log.d(TAG, "onCreate: " + GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this));
        activitySignInBinding.signInActivityGoogleSignInButton.setOnClickListener(v -> {
            Log.d(TAG, "ON CLICK LISTENER");
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                Intent intent = mGoogleSignInClient.getSignInIntent();
                setResult(Activity.RESULT_OK);
                getAccountData.launch(intent);
                Log.d(TAG, "onCreate: " + intent.getData());
            }
        });
    }

    ActivityResultLauncher<Intent> getAccountData = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            (result -> {

                if (result.getResultCode() == Activity.RESULT_OK) {
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                    try {
                        Log.d(TAG, "getAccountData();");
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        firebaseAuth(account.getIdToken());
                    } catch (ApiException e) {
                        throw new RuntimeException(e);
                    }
                } else
                    Log.d(TAG, "RESULT_CANCELED");
            }));

    private void firebaseAuth(String idToken) {
        Log.d("sign_in_activity","okay");
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();

                        assert user != null;
                        UserModel userModel = new UserModel();
                        userModel.setUserId(user.getUid());
                        userModel.setName(user.getDisplayName());
                        userModel.setProfile(Objects.requireNonNull(user.getPhotoUrl()).toString());

                        Log.d("sign_in_activity",userModel.toString());

                        FirebaseService
                                .reference_users
                                .document(Objects.requireNonNull(FirebaseAuthService.getFirebaseAuth().getCurrentUser()).getUid())
                                .set(userModel)
                                .addOnCompleteListener(task1 -> {
                                    Toast.makeText(this, "Successful Login", Toast.LENGTH_SHORT).show();
                                    SignInActivity.this.finish();
                                });

                    } else {
                        Log.d("sign_in_activity", String.valueOf(task.getException()));
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
