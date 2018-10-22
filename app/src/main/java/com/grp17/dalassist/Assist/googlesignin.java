package com.grp17.dalassist.Assist;

/* Referred following cites
*   https://www.simplifiedcoding.net/google-login-android
*   http://www.androiddeft.com/2018/01/28/android-login-with-google-account
*   Stack Over Flow and Android development documentation */

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.grp17.dalassist.Catalog;
import com.grp17.dalassist.MainActivity;
import com.grp17.dalassist.MyAccount.LoginFrontActivity;
import com.grp17.dalassist.R;

public class googlesignin extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = googlesignin.class.getSimpleName();
    GoogleSignInClient mGoogleSignInClient;
    SignInButton signInButton;
    ProgressDialog progressDialog;
    FirebaseUser firebaseUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googlesignin);
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton = findViewById(R.id.sign_in_button);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener( ) {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId( )) {
                            case R.id.nav_assist:
                                Intent intent=new Intent(getApplicationContext(),googlesignin.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_catalog:
                                Intent second = new Intent(getApplicationContext(), Catalog.class);
                                startActivity(second);
                                break;
                            case R.id.nav_myaccount:
                                Intent third = new Intent(getApplicationContext(), LoginFrontActivity.class);
                                startActivity(third);
                                break;
                        }
                        return true;
                    }
                });
    }
    //[18][17]

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }



    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            @SuppressLint("RestrictedApi") Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {


                Toast.makeText(this, "Google Sign In Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.e(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            if (user != null) {
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(acct.getDisplayName())
                                        .build();
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

                                DatabaseReference reference = databaseReference.child("Assists").child("users").child(user.getUid()).child("userinfo");


                                reference.child("username").setValue(acct.getDisplayName());
                                reference.child("email").setValue(acct.getEmail());

                                startActivity(new Intent(googlesignin.this, Assists.class));
                            }

                        } else {
                            Toast.makeText(googlesignin.this, "Firebase Authentication Failed"
                                    , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}