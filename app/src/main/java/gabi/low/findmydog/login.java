package gabi.low.findmydog;

import static gabi.low.findmydog.FBref.refAuth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
private EditText eTEmail;
private EditText eTPass;
private TextView tVMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }
    void init()
    {
        eTEmail = findViewById(R.id.eTEmail);
        eTPass = findViewById(R.id.eTpass);
        tVMsg = findViewById(R.id.tVMsg);
    }

    public void loginUser(View view) {
        init();
        Log.d("c", "init: eTEmail = " + findViewById(R.id.eTEmail));
        String email = eTEmail.getText().toString();
        String pass = eTPass.getText().toString();
        Log.d("debug", "loginUser: email = " + email);
        if (email.isEmpty() || pass.isEmpty()) {
            tVMsg.setText("Please fill all fields");
        } else {
            ProgressDialog pd = new ProgressDialog(this);
            pd.setTitle("Connecting");
            pd.setMessage("Logging in user...");
            pd.show();
            refAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            pd.dismiss();

                            if (task.isSuccessful()) {

                                Log.i("MainActivity", "createUserWithEmailAndPassword:success");
                                FirebaseUser user = refAuth.getCurrentUser();
                                tVMsg.setText("User logged in successfully");
                                Intent intent = new Intent(login.this, DogsActivity.class);
                                startActivity(intent);
                            }
                            else
                                {
                                    Exception exp = task.getException();
                                    if (exp instanceof FirebaseAuthInvalidUserException){
                                        tVMsg.setText("Invalid email address.");
                                    } else if (exp instanceof FirebaseAuthWeakPasswordException) {
                                        tVMsg.setText("Password too weak.");
                                    } else if (exp instanceof FirebaseAuthUserCollisionException) {
                                        tVMsg.setText("User already exists.");
                                    } else if (exp instanceof FirebaseAuthInvalidCredentialsException) {
                                        tVMsg.setText("General authentication failure.");
                                    } else if (exp instanceof FirebaseNetworkException) {
                                        tVMsg.setText("Network error. Please check your connection.");
                                    } else {
                                        tVMsg.setText("An error occurred. Please try again later.");
                                    }
                                }
                            }
                    });
        }
    }











}