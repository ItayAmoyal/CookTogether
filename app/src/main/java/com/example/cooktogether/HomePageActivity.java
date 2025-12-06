package com.example.cooktogether;

import static com.example.cooktogether.FBRef.refAuth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    RecipeAdapter recipeAdapter;
    ArrayList<Recipe> allRecipes=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        rvAllrecipes=findViewById(R.id.rvAllRecipes);
        ImageButton recipebutton=findViewById(R.id.recipeButton1);
        recipebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomePageActivity.this,RecipeActivity.class);
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
                }
                rvAllrecipes.setLayoutManager(new LinearLayoutManager(HomePageActivity.this));
                recipeAdapter=new RecipeAdapter(HomePageActivity.this,allRecipes);
                rvAllrecipes.setAdapter(recipeAdapter);
                recipeAdapter.setOnRecipeClickListener(position -> {
                    Recipe clicked = allRecipes.get(position);
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