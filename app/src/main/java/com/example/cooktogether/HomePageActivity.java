package com.example.cooktogether;

import static com.example.cooktogether.FBRef.refImages;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class HomePageActivity extends AppCompatActivity {
    RecyclerView rvAllrecipes;
    ImageButton btnCreateRecipe, btnFilters, btnAi, settingsBtn, btnFavRecipes;
    EditText etSerchBar;
    int flagFilter = 0, flagfilter2;
    RecipeAdapter recipeAdapter;
    ImageButton btnChats;
    String currentkashroot="all",  currentType="all";
    String filterType = "", filterKashroot = "";
    ArrayList<RecipeShow> recipesAfterFilter = new ArrayList<>();
    ArrayList<RecipeShow> recipesAfterTextChange = new ArrayList<>();
    ArrayList<RecipeShow> allShowRecipes = new ArrayList<>();
    ArrayList<RecipeShow> displayedRecipes = new ArrayList<>();
    ArrayList<Recipe> allRecipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        rvAllrecipes = findViewById(R.id.rvAllRecipes);
        btnAi = findViewById(R.id.recipeButton3);
        btnFilters = findViewById(R.id.FilterButton);
        btnChats = findViewById(R.id.recipeButton5);
        btnFavRecipes = findViewById(R.id.recipeButton4);
        settingsBtn = findViewById(R.id.buttonSettings);
        etSerchBar = findViewById(R.id.editTextSearch);
        btnCreateRecipe = findViewById(R.id.recipeButton2);

        rvAllrecipes.setLayoutManager(new LinearLayoutManager(HomePageActivity.this));
        recipeAdapter = new RecipeAdapter(HomePageActivity.this, displayedRecipes);
        rvAllrecipes.setAdapter(recipeAdapter);

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
        btnAi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, AiActivity.class);
                startActivity(intent);
            }
        });
        btnCreateRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, CreateRecipe.class);
                startActivity(intent);
            }
        });
        btnChats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, AllChatsActivity.class);
                startActivity(intent);
            }
        });
        btnFavRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, FavoriteRecipes.class);
                startActivity(intent);
            }
        });
        ChooseFilters();
        //קריאת נתונים
        ShowAllRecipes();
    }

    private void ShowAllRecipes() {
        FBRef.refAllRecipes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    Recipe currentRecipe = data.getValue(Recipe.class);
                    allRecipes.add(currentRecipe);
                }
                int[] LoadedCount = {0};
                for (Recipe recipe : allRecipes) {
                    String pictureString = recipe.getPicture();
                    if (pictureString != null) {
                        DocumentReference docRef = refImages.document(pictureString);
                        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    Blob blob = documentSnapshot.getBlob("ImageData");
                                    if (blob != null) {
                                        byte[] bytes = blob.toBytes();
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                        displayedRecipes.add(new RecipeShow(recipe, bitmap));
                                        allShowRecipes.add(new RecipeShow(recipe, bitmap));
                                        LoadedCount[0]++;
                                        if (LoadedCount[0] == allRecipes.size()) {
                                            recipeAdapter.notifyDataSetChanged();
                                        }
                                    }
                                }
                            }
                        });

                    }
                }
                btnFilters.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle args = new Bundle();
                        args.putInt("place", 1);


                        FiltersFragment fragment = new FiltersFragment();
                        fragment.setArguments(args);

                        fragment.show(getSupportFragmentManager(), "FILTERS_DIALOG");
                    }
                });
                etSerchBar.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void afterTextChanged(Editable s) {
                        FilterRecipe();
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                });

                recipeAdapter.setOnRecipeClickListener(position -> {
                    Recipe clicked;
                    clicked = displayedRecipes.get(position).getRecipe();
                    Intent intent = new Intent(HomePageActivity.this, RecipeActivity.class);
                    intent.putExtra("Rid", clicked.getRecipeID());
                    startActivity(intent);
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomePageActivity.this, "שגיאה בטעינת הנתונים", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void ChooseFilters() {
        getSupportFragmentManager().setFragmentResultListener(
                "filters_result",
                HomePageActivity.this,
                (requestKey, bundle) -> {
                    currentType = bundle.getString("type");
                    currentkashroot = bundle.getString("kashroot");
                    FilterRecipe();

                }
        );
    }

    public void FilterRecipe() {
        displayedRecipes.clear();
        String currentText = etSerchBar.getText().toString();
        Boolean isTextTrue=false;
        for(RecipeShow recipe:allShowRecipes){
            isTextTrue=false;
            int size = currentText.length();
            if (currentText.length() == 0) {
                isTextTrue=true;
            }
            if (recipe.getRecipe().getName().length() >= size) {
                String recipeName = recipe.getRecipe().getName().toLowerCase();
                String searchText = currentText.toLowerCase();
                if(recipeName.startsWith(searchText)){
                    isTextTrue=true;
                }
                if (isTextTrue&&
                        (recipe.getRecipe().getFilterKashroot().equals(currentkashroot)||currentkashroot.equals("all"))&&
                        (recipe.getRecipe().getFilterType().equals(currentType)||currentType.equals("all"))){
                   displayedRecipes.add(recipe);
                }
            }
        }
        recipeAdapter.notifyDataSetChanged();
    }
}
