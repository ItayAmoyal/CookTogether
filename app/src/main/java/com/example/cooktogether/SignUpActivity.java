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
import com.google.firebase.auth.AuthResult;
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
                    if (task.isSuccessful()) {
                        pd.dismiss();
                        Toast.makeText(SignUpActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                        refAuth.getCurrentUser().updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(name).build()).
                                addOnCompleteListener(profileTask -> {
                                    if (profileTask.isSuccessful()) {
                                        Intent intentLogin=new Intent(SignUpActivity.this, LoginActivity.class);
                                        startActivity(intentLogin);
                                    }
                                    else {
                                        Toast.makeText(SignUpActivity.this, "User not created", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            });
        }
    }
}