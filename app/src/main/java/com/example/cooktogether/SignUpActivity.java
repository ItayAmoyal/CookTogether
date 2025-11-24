package com.example.cooktogether;

import static com.example.cooktogether.FBRef.refAuth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpActivity extends AppCompatActivity {
    Button SignUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        SignUpButton=findViewById(R.id.button2SignUp);
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser(v);
            }
        });
    }
    public void createUser(View view){
        EditText email1 =findViewById(R.id.editTextEmailSignUp);
        EditText name1=findViewById(R.id.editTextNameSignUp);
        String name = name1.getText().toString();
        EditText pass=findViewById(R.id.editTextPasswordSignUp);
        String email=email1.getText().toString();
        String password=pass.getText().toString();
        if(email.isEmpty()||password.isEmpty()|| name.isEmpty()){
            Toast.makeText(this,"Please fill all fields",Toast.LENGTH_SHORT).show();
        }
        else{
            ProgressDialog pd=new ProgressDialog(this);
            pd.setTitle("Connecting");
            pd.setMessage("Creating user...");
            pd.show();
            refAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    pd.dismiss();
                    if (task.isSuccessful()) {
                        FirebaseUser user=refAuth.getCurrentUser();
                        User user1=new User(name,user.getUid(),email,password);
                        //הכנסה לפיירבייס


                        Toast.makeText(SignUpActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                        refAuth.getCurrentUser().updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(name).build()).
                                addOnCompleteListener(profileTask -> {
                                    if (profileTask.isSuccessful()) {
                                        Intent intentLogin=new Intent(SignUpActivity.this, LoginActivity.class);
                                        startActivity(intentLogin);
                                    }
                                    else {
                                        Exception exp =task.getException();
                                        if (exp instanceof FirebaseAuthInvalidUserException){
                                            Toast.makeText(SignUpActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                                        }
                                        else if (exp instanceof FirebaseAuthWeakPasswordException){
                                            Toast.makeText(SignUpActivity.this, "Password is too week", Toast.LENGTH_SHORT).show();
                                        }
                                        else if (exp instanceof FirebaseAuthUserCollisionException){
                                            Toast.makeText(SignUpActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                                        }
                                        else if (exp instanceof FirebaseAuthInvalidCredentialsException){
                                            Toast.makeText(SignUpActivity.this, "General authentication faliure", Toast.LENGTH_SHORT).show();
                                        }
                                        else if (exp instanceof FirebaseNetworkException){
                                            Toast.makeText(SignUpActivity.this, "Network error. Check your connection", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            Toast.makeText(SignUpActivity.this, "An error occurred, Please try again later", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            });
        }
    }
}