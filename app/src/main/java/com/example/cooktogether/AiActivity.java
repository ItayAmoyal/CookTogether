package com.example.cooktogether;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.common.util.IOUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.Blob;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AiActivity extends AppCompatActivity {
Button btnPick,btnEst;
int REQUEST_PICK_IMAGE=303;
Bitmap picBitmap;
int flag=0;
ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai);
        btnPick=findViewById(R.id.btnPickFromGallery);
        btnEst=findViewById(R.id.btnEstimateMacros);
        picture=findViewById(R.id.imgPreview);
        btnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gallery(v);
            }
        });
        btnEst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==1){
                    promptSend();
                    flag = 0;
                }
            }
        });
    }
    public void gallery(View view){
        Intent si=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(si,REQUEST_PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==REQUEST_PICK_IMAGE&&resultCode == Activity.RESULT_OK&&data!=null){
            Uri imageUri=data.getData();
            btnEst.setEnabled(true);
            if(imageUri==null)
                return;
            try {
                Bitmap bitmap;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                    ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), imageUri);
                    bitmap = ImageDecoder.decodeBitmap(source);
                } else {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                }

                picBitmap = bitmap;
                picture.setImageBitmap(picBitmap);
                picture.setVisibility(View.VISIBLE);
                flag = 1;
            }
         catch (IOException e) {
            e.printStackTrace();
        }
        }
    }
    private void promptSend(){
        String prompt=
                "If not clearly food output exactly: 1\n" +
                        "Else output exactly:\n" +
                        "Calories:(type number)\nProtein:(type number) grams\nCarbs:(type number) grams\nFat:(type number) grams";
        GeminiManager geminiManager=new GeminiManager();
        geminiManager.sendTextWithPrompt(prompt, picBitmap, new GeminiCallBack() {
            @Override
            public void onSuccess(String result) {
                runOnUiThread(() -> {
                    if (!result.trim().equals("1")) {
                       flag=1;

                        Bundle args = new Bundle();
                        args.putString("answer", result);

                        AiFragment fragment = new AiFragment();
                        fragment.setArguments(args);

                        fragment.show(getSupportFragmentManager(), "AiFragment");
                    } else {
                        Toast.makeText(AiActivity.this, "This picture doesn't contain a food", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFaliure(Throwable error) {
                Toast.makeText(AiActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}