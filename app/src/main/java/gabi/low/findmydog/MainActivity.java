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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    public EditText eTEmail;
    public  EditText eTPass;
    public TextView tVMsg;
    public  EditText eTName;
    public  EditText eTPhone;
    public EditText eTAddress;

    //public FirebaseAuth refAuth = FirebaseAuth.getInstance();
    // FirebaseAuth refAuth = FBRef.refAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();


    }
    public  void init()
    {
        eTEmail = findViewById(R.id.eTEmail);
        eTPass = findViewById(R.id.eTPass);
        tVMsg = findViewById(R.id.tVMsg);
        eTName = findViewById(R.id.eTName);
        eTPhone = findViewById(R.id.etPhone);
        eTAddress = findViewById(R.id.eTaddress);

    }

    public void createUser(View view) {
        String email = eTEmail.getText().toString();
        String pass = eTPass.getText().toString();
        if (email.isEmpty() || pass.isEmpty()) {
            tVMsg.setText("Please fill all fields");
        } else {
            ProgressDialog pd = new ProgressDialog(this);
            pd.setTitle("Connecting");
            pd.setMessage("Creating user...");
            pd.show();
            refAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            pd.dismiss();
                            if (task.isSuccessful()) {
                                Log.i("MainActivity", "createUserWithEmailAndPassword:success");
                                FirebaseUser user = refAuth.getCurrentUser();
                                tVMsg.setText("User created successfully\nUid: " + user.getUid());
                                addToRT();
                                Intent intent =new Intent(MainActivity.this, DogsActivity.class);
                                startActivity(intent);
                            } else {
                                Exception exp = task.getException();
                                if (exp instanceof FirebaseAuthInvalidUserException) {
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
    void addToRT()
    {
        String email = eTEmail.getText().toString();
        String name = eTName.getText().toString();
        String phone = eTPhone.getText().toString();
        String address = eTAddress.getText().toString();

        if (email.isEmpty() || name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            tVMsg.setText("Please fill all fields");
            return;
        }

        // Assuming FBref.refStudents is a DatabaseReference to your Firebase Realtime Database
        Subscriber sub = new Subscriber(email,name,phone,address);

        FBref.refSubscibe.child(FBref.refAuth.getCurrentUser().getUid()).setValue(sub)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        tVMsg.setText("User details added successfully to real time.");
                    } else {
                        tVMsg.setText("Failed to write to database: " + task.getException().getMessage());
                        Log.e("DB_ERROR", "Database write failed", task.getException());
                    }
                });


        tVMsg.setText("User details added successfully to real time.");
    }
}