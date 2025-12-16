package com.example.cooktogether;

import static com.example.cooktogether.FBRef.refImages;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.util.IOUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.Blob;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CreateRecipe extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private static final int REQUEST_PICK_IMAGE=301;
    Spinner spinnerDiff,spinnerING;
    EditText ingrediantName,ingredientAmount,instructionText;
    String ingredientUnit="";
    Button btnAddIngredient,btnAddImageIns,btnAddInstruction;
    ArrayList<Ingredient>allIngredients=new ArrayList<>();
    ArrayList<Uri>allImages=new ArrayList<>();
    ArrayList<String>allImagesID=new ArrayList<>();
    ArrayList<String>instructionsText=new ArrayList<>();
    ArrayList<ArrayList<Uri>> instructionsimages=new ArrayList<>();
    ArrayList<ArrayList<String>> instructionsimagesID=new ArrayList<>();
    IngrediantCreateAdapter ingredientsAdapter;
    ImageAdapter imageAdapter;
    InstructionCreateAdapter instructionCreateAdapter;
    String[] difficulty,unitMeasure;
    RecyclerView rvIngrediant,rvImages,rvInstructions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        spinnerDiff = findViewById(R.id.spinnerDifficulty);
        spinnerING=findViewById(R.id.spinnerMeasure);
        rvInstructions=findViewById(R.id.rvInstructions);
        btnAddInstruction=findViewById(R.id.btnAddInstruction);
        instructionText=findViewById(R.id.etInstructions);
        rvIngrediant = findViewById(R.id.rvCreateIngredients);
        ingrediantName = findViewById(R.id.IngredientsName);
        btnAddImageIns =findViewById(R.id.btnsubmitImageinstructions);
        ingredientAmount = findViewById(R.id.IngredientsAmount);
        btnAddIngredient = findViewById(R.id.btnAddIngredient);
        rvImages= findViewById(R.id.rvImagesinstructions);
        imageAdapter=new ImageAdapter(this, allImages,allImagesID, new ImageAdapter.OnDeleteListener(){
            @Override
            public void onDelete(int position, Uri uri) {

            }
        });
        rvImages.setLayoutManager(new LinearLayoutManager(this));
        rvImages.setAdapter(imageAdapter);
        rvIngrediant.setLayoutManager(new LinearLayoutManager(this));
        ingredientsAdapter = new IngrediantCreateAdapter(this,allIngredients);
        rvIngrediant.setAdapter(ingredientsAdapter);
        difficulty = getResources().getStringArray(R.array.difficulty_levels);
        ArrayAdapter<String> adpDifficulty = new ArrayAdapter<>(CreateRecipe.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, difficulty);
        spinnerDiff.setAdapter(adpDifficulty);
        spinnerING = findViewById(R.id.spinnerMeasure);
        unitMeasure = getResources().getStringArray(R.array.unit_measure);
        ArrayAdapter<String> adpMeasure = new ArrayAdapter<>(CreateRecipe.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, unitMeasure);
        spinnerING.setAdapter(adpMeasure);
        spinnerING.setOnItemSelectedListener(this);
        rvInstructions.setLayoutManager(new LinearLayoutManager(this));
        instructionCreateAdapter=new InstructionCreateAdapter(this, instructionsText, instructionsimages, new InstructionCreateAdapter.OnInstructionDeleteListener() {
            @Override
            public void onInstructionDelete(int position) {

            }
        });
        rvInstructions.setAdapter(instructionCreateAdapter);

        //הוסף מצרך
        btnAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddIngrediant();
            }
        });
        //הוסף תמונה להוראות
        btnAddImageIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gallery(v);
            }
        });

        //הוספת הוראה
        btnAddInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddInstruction();
            }
        });
    }







    //הוספת מצרך
    private void AddIngrediant(){
        String name=ingrediantName.getText().toString();
        String amount=ingredientAmount.getText().toString()+" ";
        if (name.isEmpty() || amount.isEmpty() ||
                ingredientUnit.isEmpty() || ingredientUnit.equals(unitMeasure[0])) {
            Toast.makeText(this, "Fill name, amount and choose unit", Toast.LENGTH_SHORT).show();
            return;
        }
        Ingredient ingredient=new Ingredient(amount,ingredientUnit,name);
        allIngredients.add(ingredient);
        ingredientsAdapter.notifyItemInserted(allIngredients.size() - 1);
        ingrediantName.setText("");
        ingredientAmount.setText("");
        spinnerING.setSelection(0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ingredientUnit = unitMeasure[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        ingredientUnit = "";
    }

    //הוספת הוראה
    private void AddInstruction(){
        String text=instructionText.getText().toString();
        if((!text.equals(""))) {
            instructionsText.add(text);
            instructionsimages.add(new ArrayList<>(allImages));
            instructionCreateAdapter = new InstructionCreateAdapter(CreateRecipe.this, instructionsText, instructionsimages, new InstructionCreateAdapter.OnInstructionDeleteListener() {
                @Override
                public void onInstructionDelete(int position) {

                }
            });
            rvInstructions.setAdapter(instructionCreateAdapter);
            int size = allImages.size();
            allImages.clear();
            allImagesID.clear();
            imageAdapter.notifyItemRangeRemoved(0, size);
            instructionText.setText("");
            Toast.makeText(CreateRecipe.this," Instruction Added",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(CreateRecipe.this,"You must type an Instruction",Toast.LENGTH_SHORT).show();
        }
    }



    //העלאת תמונה מהגלריה
    public void gallery(View view){
        Intent si=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(si,REQUEST_PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==REQUEST_PICK_IMAGE&&resultCode == Activity.RESULT_OK&&data!=null){
            Uri imageUri =data.getData();
            uploadImage(imageUri);
        }
    }
    private void uploadImage(Uri imageUri){
        if (imageUri != null) {
            String fileName= UUID.randomUUID().toString()+".jpg";
            ProgressDialog pd=new ProgressDialog(this);
            pd.setTitle("Uploading...");
            pd.show();
            try{
                InputStream stream =getContentResolver().openInputStream(imageUri);
                byte[]imageBytes= IOUtils.toByteArray(stream);
                Map<String,Object> imageMap=new HashMap<>();
                imageMap.put("ImageName",fileName);
                imageMap.put("ImageData", Blob.fromBytes(imageBytes));
                refImages.document(fileName).set(imageMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        pd.dismiss();
                        Toast.makeText(CreateRecipe.this,"Upload successful",Toast.LENGTH_SHORT).show();
                        AddImageInstructions(imageUri,fileName);
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                pd.dismiss();
                                Toast.makeText(CreateRecipe.this,"Upload failed",Toast.LENGTH_SHORT).show();

                            }
                        });
            } catch (IOException e){
                e.printStackTrace();
                pd.dismiss();
                Toast.makeText(CreateRecipe.this,"Error processing image",Toast.LENGTH_SHORT).show();

            }
        }
    }
    //הוספת תמונה להוראה
    private void AddImageInstructions(Uri imageUri,String imageID){
        imageAdapter.addImage(imageUri,imageID);
    }

}
