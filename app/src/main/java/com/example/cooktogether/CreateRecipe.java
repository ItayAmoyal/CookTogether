package com.example.cooktogether;

import static com.example.cooktogether.FBRef.FBDB;
import static com.example.cooktogether.FBRef.refAllRecipes;
import static com.example.cooktogether.FBRef.refAuth;
import static com.example.cooktogether.FBRef.refImages;
import static com.example.cooktogether.FBRef.refUsers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.util.IOUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.Blob;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CreateRecipe extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private static final int REQUEST_PICK_IMAGE=301;
    Spinner spinnerDiff,spinnerING;
    ImageButton btnBack;
    Button btnFilters;
    EditText ingrediantName,ingredientAmount,instructionText,recipeTitle,cookTime;
    String ingredientUnit="",difficulty="",stringRecipeImage;
    Boolean isImage=false;
    ArrayList<InstructionItem>allInstructions;
    Button btnAddIngredient,btnAddImageIns,btnAddInstruction,btnimageRecipe,btnCreateRecipe;
    ArrayList<Ingredient>allIngredients=new ArrayList<>();
    ArrayList<Uri>allImages=new ArrayList<>();
    ArrayList<String>allImagesID=new ArrayList<>();
    ArrayList<String>instructionsText=new ArrayList<>();
    ArrayList<ArrayList<Uri>> instructionsimages=new ArrayList<>();

    IngrediantCreateAdapter ingredientsAdapter;
    ImageAdapter imageAdapter;
    InstructionCreateAdapter instructionCreateAdapter;
    String[] arraydifficulty,unitMeasure;
    ImageView recipeImage;
    User userCurrent;
    FirebaseUser user;
    Boolean flagContinue=false;
    String filterKashroot="",filterType="";
    ArrayAdapter<String> adpMeasure,adpDifficulty;
    int flagImage =0;
    static int numOfInstructions=0,numOfIngridiants=0;
    RecyclerView rvIngrediant,rvImages,rvInstructions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        user=refAuth.getCurrentUser();
        btnBack=findViewById(R.id.backButton);
        recipeTitle=findViewById(R.id.etRecipeTitle1);
        spinnerDiff = findViewById(R.id.spinnerDifficulty);
        cookTime=findViewById(R.id.CookTime);
        btnFilters=findViewById(R.id.filters1);
        spinnerING=findViewById(R.id.spinnerMeasure);
        rvInstructions=findViewById(R.id.rvInstructions);
        btnimageRecipe=findViewById(R.id.btnSelectImage);
        btnAddInstruction=findViewById(R.id.btnAddInstruction);
        instructionText=findViewById(R.id.etInstructions);
        recipeImage=findViewById(R.id.imageRecipe);
        btnCreateRecipe=findViewById(R.id.btnCreateRecipe);
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
        arraydifficulty = getResources().getStringArray(R.array.difficulty_levels);
         adpDifficulty = new ArrayAdapter<>(CreateRecipe.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arraydifficulty);
        spinnerDiff.setAdapter(adpDifficulty);
        spinnerDiff.setOnItemSelectedListener(this);
        spinnerING = findViewById(R.id.spinnerMeasure);
        unitMeasure = getResources().getStringArray(R.array.unit_measure);
        adpMeasure = new ArrayAdapter<>(CreateRecipe.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, unitMeasure);
        spinnerING.setAdapter(adpMeasure);
        spinnerING.setOnItemSelectedListener(this);
        rvInstructions.setLayoutManager(new LinearLayoutManager(this));
        instructionCreateAdapter=new InstructionCreateAdapter(this, instructionsText, instructionsimages, new InstructionCreateAdapter.OnInstructionDeleteListener() {
            @Override
            public void onInstructionDelete(int position) {

            }
        });
        rvInstructions.setAdapter(instructionCreateAdapter);
        allInstructions=new ArrayList<>();
        //חזרה למסך בית
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CreateRecipe.this, HomePageActivity.class);
                startActivity(intent);
            }
        });
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
                flagImage =0;
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

        //בחירת פילטרים
        btnFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putInt("place", 2);


                FiltersFragment fragment = new FiltersFragment();
                fragment.setArguments(args);

                fragment.show(getSupportFragmentManager(), "FILTERS_DIALOG");
            }
        });
        getSupportFragmentManager().setFragmentResultListener(
                "filters_result",
                this,
                (requestKey, bundle) -> {
                    filterType = bundle.getString("type");
                    filterKashroot = bundle.getString("kashroot");
                }
        );

        //תמונת המתכון
        btnimageRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagImage =1;
                gallery(v);
            }
        });

        //העלאת מתכון
        btnCreateRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb=new AlertDialog.Builder(CreateRecipe.this);
                adb.setTitle("היי איתי");
                adb.setMessage("האם אתה בטוח שברצונך להעלות את המתכון הזה?");
                adb.setIcon(R.drawable.create_recipe_logo);
                adb.setPositiveButton("כן", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AddRecipe();
                    }
                });
                adb.setNegativeButton("לא", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog alertDialog=adb.create();
                alertDialog.show();
            }
        });
    }












    //פעולות------------------------------------------------------------------------------------------------------------------


    //העלאת מתכון
    private void AddRecipe() {
        if ((!recipeTitle.getText().toString().equals("")) && !filterType.equals("") && !filterKashroot.equals("") && isImage == true
                && difficulty != arraydifficulty[0] && difficulty != ""
                && (!cookTime.getText().toString().equals("")) && numOfInstructions > 0 && numOfIngridiants > 0)
        {
            refUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        String str = data.getKey();
                        if (user.getUid().equals(str)) {
                            userCurrent = data.getValue(User.class);
                        }
                    }
                    Recipe recipe = new Recipe(recipeTitle.getText().toString(), user.getUid());
                    recipe.setCookTime(cookTime.getText().toString());
                    recipe.setUid(user.getUid());
                    recipe.setDifficulty(difficulty);
                    recipe.setPicture(stringRecipeImage);
                    recipe.setInstructions(allInstructions);
                    recipe.setFilterKashroot(filterKashroot);
                    recipe.setFilterType(filterType);
                    recipe.setIngridiantsArrayList(allIngredients);
                    recipe.setRecipeID(refAllRecipes.push().getKey());
                    userCurrent.addNumOfRecipes();
                    refUsers.child(user.getUid()).setValue(userCurrent);
                    refAllRecipes.child(recipe.getRecipeID()).setValue(recipe);
                    Toast.makeText(CreateRecipe.this, "המתכוו הועלה בהצלחה!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateRecipe.this, HomePageActivity.class);
                    startActivity(intent);
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            Toast.makeText(this, "חובה למלא את כל השדות", Toast.LENGTH_SHORT).show();
        }
    }
    //הוספת מצרך
    private void AddIngrediant(){
        String name=ingrediantName.getText().toString();
        String amount=ingredientAmount.getText().toString()+" ";
        if (name.isEmpty() || amount.isEmpty() ||
                ingredientUnit.isEmpty() || ingredientUnit.equals(unitMeasure[0])) {
            Toast.makeText(this, "נא למלא שם, כמות ויחידת מידה", Toast.LENGTH_SHORT).show();
            return;
        }
        Ingredient ingredient=new Ingredient(amount,ingredientUnit,name);
        allIngredients.add(ingredient);
        ingredientsAdapter.notifyItemInserted(allIngredients.size() - 1);
        ingrediantName.setText("");
        ingredientAmount.setText("");
        spinnerING.setSelection(0);
        numOfIngridiants++;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.spinnerMeasure) {
            ingredientUnit = unitMeasure[position];

        } else if (parent.getId() == R.id.spinnerDifficulty) {
            difficulty = arraydifficulty[position];
        }
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
            ArrayList<String>allImagesString=new ArrayList<>();
            for(Uri item:allImages){
                String newItem=item.toString();
                allImagesString.add(newItem);
            }
            InstructionItem instructionItem=new InstructionItem(text,allImagesString);
            allInstructions.add(instructionItem);
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
            numOfInstructions++;
            Toast.makeText(CreateRecipe.this,"הוראה נוספה",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(CreateRecipe.this,"חובה להקליד הוראה",Toast.LENGTH_SHORT).show();
        }
    }



    //העלאת תמונה מהגלריה
    public void gallery(View view){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, 100);
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            getContentResolver().takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
            );
            uploadImage(uri);
        }
    }
    private void uploadImage(Uri imageUri){
        if (imageUri != null) {
            String fileName= UUID.randomUUID().toString()+".jpg";
            ProgressDialog pd=new ProgressDialog(this);
            pd.setTitle("מעלה...");
            pd.show();
            try{
                InputStream stream =getContentResolver().openInputStream(imageUri);
                byte[]imageBytes= IOUtils.toByteArray(stream);
                Map<String,Object> imageMap=new HashMap<>();
                imageMap.put("ImageName",fileName);
                imageMap.put("ImageData", Blob.fromBytes(imageBytes));
                Uri localUri = copyToInternalStorage(imageUri);
                refImages.document(fileName).set(imageMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                pd.dismiss();
                                Toast.makeText(CreateRecipe.this,"ההעלאה בוצעה בהצלחה",Toast.LENGTH_SHORT).show();
                                if(flagImage ==0){
                                    AddImageInstructions(localUri,fileName);
                                }
                                else{
                                    AddRecipeImage(localUri,fileName);
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                pd.dismiss();
                                Toast.makeText(CreateRecipe.this,"ההעלאה נכשלה",Toast.LENGTH_SHORT).show();

                            }
                        });
            } catch (IOException e){
                e.printStackTrace();
                pd.dismiss();
                Toast.makeText(CreateRecipe.this,"שגיאה בעיבוד התמונה",Toast.LENGTH_SHORT).show();

            }
        }
    }
    //הוספת תמונה להוראה
    private void AddImageInstructions(Uri imageUri,String imageID){
        imageAdapter.addImage(imageUri,imageID);
    }

    //העלאת תמונת התמכון
    private void AddRecipeImage(Uri imageUri,String imageID){
        isImage=true;
        stringRecipeImage= imageUri.toString();
        recipeImage.setImageURI(imageUri);

    }
    private Uri copyToInternalStorage(Uri originalUri) throws IOException {

        String fileName = UUID.randomUUID().toString() + ".jpg";
        File file = new File(getFilesDir(), fileName);

        InputStream inputStream = getContentResolver().openInputStream(originalUri);
        OutputStream outputStream = new FileOutputStream(file);

        byte[] buffer = new byte[4096];
        int length;

        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        inputStream.close();
        outputStream.close();

        return Uri.fromFile(file);
    }
}
