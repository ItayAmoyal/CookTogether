package com.example.cooktogether;

import static com.example.cooktogether.FBRef.refAuth;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomePageActivity extends AppCompatActivity {
    RecyclerView rvAllrecipes;
    ImageButton btnCreateRecipe, btnFilters,btnAi,settingsBtn,btnFavRecipes;
    EditText etSerchBar;
    int flagFilter=0,flagfilter2;
    RecipeAdapter recipeAdapter;
    String filterType="",filterKashroot="";
    ArrayList<Recipe>filteredRecipes1=new ArrayList<>();
    ArrayList<Recipe>filteredRecipes2=new ArrayList<>();
    ArrayList<Recipe>activefilteredRecipes=new ArrayList<>();
    ArrayList<Recipe>displayedREcipes=new ArrayList<>();
    ArrayList<Recipe> allRecipes=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        rvAllrecipes=findViewById(R.id.rvAllRecipes);
        btnAi=findViewById(R.id.recipeButton3);
        btnFilters=findViewById(R.id.FilterButton);
        btnFavRecipes=findViewById(R.id.recipeButton4);
        settingsBtn=findViewById(R.id.buttonSettings);
        etSerchBar=findViewById(R.id.editTextSearch);
        btnCreateRecipe=findViewById(R.id.recipeButton2);
        ImageButton btnMyRecipes=findViewById(R.id.recipeButton1);
        btnMyRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomePageActivity.this,RecipeActivity.class);
                startActivity(intent);
            }
        });
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomePageActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
        btnAi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomePageActivity.this,AiActivity.class);
                startActivity(intent);
            }
        });
        btnCreateRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomePageActivity.this,CreateRecipe.class);
                startActivity(intent);
            }
        });
        btnFavRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomePageActivity.this,FavoriteRecipes.class);
                startActivity(intent);
            }
        });
        //קריאת נתונים
        ShowAllRecipes();
    }
    private void ShowAllRecipes(){
        FBRef.refAllRecipes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    Recipe currentRecipe= data.getValue(Recipe.class);
                   allRecipes.add(currentRecipe);
                   filteredRecipes2.add(currentRecipe);
                   filteredRecipes1.add(currentRecipe);
                }
                rvAllrecipes.setLayoutManager(new LinearLayoutManager(HomePageActivity.this));
               displayedREcipes = new ArrayList<>();
                displayedREcipes.addAll(allRecipes);

                recipeAdapter = new RecipeAdapter(HomePageActivity.this, displayedREcipes);
                rvAllrecipes.setAdapter(recipeAdapter);

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
                getSupportFragmentManager().setFragmentResultListener(
                        "filters_result",
                        HomePageActivity.this,
                        (requestKey, bundle) -> {
                            filterType = bundle.getString("type");
                            filterKashroot = bundle.getString("kashroot");
                            flagFilter++;
                            filteredRecipes1.clear();
                            activefilteredRecipes.clear();
                            for(Recipe recipe:allRecipes){
                                if(((filterKashroot.equals(recipe.getFilterKashroot())||filterKashroot.equals("all"))&&(filterType.equals(recipe.getFilterType())||filterType.equals("all")))){
                                    filteredRecipes1.add(recipe);
                                }
                            }
                            for(Recipe recipe:allRecipes){
                                if(filteredRecipes1.contains(recipe)&&filteredRecipes2.contains(recipe)){
                                    activefilteredRecipes.add(recipe);
                                }
                            }
                            displayedREcipes.clear();
                            displayedREcipes.addAll(activefilteredRecipes);
                            recipeAdapter.notifyDataSetChanged();
                        }
                );
                etSerchBar.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void afterTextChanged(Editable s) {
                        String currentText = etSerchBar.getText().toString();
                        flagFilter++;
                        flagfilter2++;
                        filteredRecipes2.clear();
                        activefilteredRecipes.clear();
                        for (Recipe recipe : allRecipes) {
                            int size = currentText.length();
                            String recipeName = recipe.getName().substring(0, size);
                            if (recipeName.equals(currentText)) {
                                filteredRecipes2.add(recipe);
                            }
                        }
                        for (Recipe recipe : allRecipes) {
                            if (filteredRecipes1.contains(recipe) && filteredRecipes2.contains(recipe)) {
                                activefilteredRecipes.add(recipe);
                            }
                        }
                        if(flagfilter2>0) {
                            displayedREcipes.clear();
                            displayedREcipes.addAll(activefilteredRecipes);
                            recipeAdapter.notifyDataSetChanged();
                        }
                        else{
                            displayedREcipes.clear();
                            displayedREcipes.addAll(filteredRecipes2);
                            recipeAdapter.notifyDataSetChanged();
                        }
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
                        clicked = displayedREcipes.get(position);
                    Intent intent=new Intent(HomePageActivity.this, RecipeActivity.class);
                    intent.putExtra("Rid",clicked.getRecipeID());
                    startActivity(intent);
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}