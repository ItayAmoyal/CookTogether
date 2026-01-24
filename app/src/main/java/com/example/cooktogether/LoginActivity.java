package com.example.cooktogether;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.UserProfileChangeRequest;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth refAuth=FBRef.refAuth;
    Button SignUpButton,LoginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SignUpButton=findViewById(R.id.ButtonSignUp);
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSignUp=new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intentSignUp);
            }
        });
        LoginButton=findViewById(R.id.buttonLogin);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(v);
            }
        });
    }

    @Override
    protected void onDestroy() {
        refAuth.signOut();
        super.onDestroy();
    }

    public void loginUser(View view){
        EditText email1 =findViewById(R.id.editTextEmail);
        EditText pass=findViewById(R.id.editTextPassword);
        String email=email1.getText().toString();
        String password=pass.getText().toString();
        if(email.isEmpty()||password.isEmpty()){
            Toast.makeText(this,"Please fill all fields",Toast.LENGTH_SHORT).show();
        }
        else{
            ProgressDialog pd=new ProgressDialog(this);
            pd.setTitle("Connecting");
            pd.setMessage("Login in user...");
            pd.show();
            refAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    pd.dismiss();
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "User Logged in successfully", Toast.LENGTH_SHORT).show();
                        FirebaseUser user=refAuth.getCurrentUser();
                        Intent intent=new Intent(LoginActivity.this, HomePageActivity.class);
                        intent.putExtra("Uid",user.getUid());
                        startActivity(intent);
                    }
                    else{
                            Exception exp = task.getException();
                            if (exp instanceof FirebaseAuthInvalidUserException) {
                                Toast.makeText(LoginActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                            } else if (exp instanceof FirebaseAuthWeakPasswordException) {
                                Toast.makeText(LoginActivity.this, "Password is too week", Toast.LENGTH_SHORT).show();
                            } else if (exp instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(LoginActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                            } else if (exp instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(LoginActivity.this, "General authentication faliure", Toast.LENGTH_SHORT).show();
                            } else if (exp instanceof FirebaseNetworkException) {
                                Toast.makeText(LoginActivity.this, "Network error. Check your connection", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "An error occurred, Please try again later", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
            }
        }
    }
