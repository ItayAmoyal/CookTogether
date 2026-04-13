package com.example.cooktogether;

import static com.example.cooktogether.FBRef.refAllRecipes;
import static com.example.cooktogether.FBRef.refAuth;
import static com.example.cooktogether.FBRef.refUsers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavoriteRecipes extends AppCompatActivity {
    FirebaseUser user=refAuth.getCurrentUser();
    User userCurrent=new User();
    RecyclerView rvFavoriteRecipes;
    RecipeAdapter favoritesAdapter;
    ImageButton btnBack;
    ArrayList<Recipe> favoriteRecipes=new ArrayList<>();
    ArrayList<String> favRecipes=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_recipes);
        btnBack=findViewById(R.id.backButton2);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FavoriteRecipes.this, HomePageActivity.class);
                startActivity(intent);
            }
        });
        rvFavoriteRecipes = findViewById(R.id.rvFavoriteRecipes);
        refUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    String str = data.getKey();
                    if (user.getUid().equals(str)) {
                        userCurrent = data.getValue(User.class);
                    }
                }
                favRecipes = userCurrent.getFavRecipesId();
                if (favRecipes!=null&&!favRecipes.isEmpty()) {
                    getRecipes();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getRecipes(){
        refAllRecipes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren()){
                    for(String rid:favRecipes){
                        if(rid.equals(data.getKey())){
                            favoriteRecipes.add(data.getValue(Recipe.class));
                        }
                    }
                }
                rvFavoriteRecipes.setLayoutManager(new LinearLayoutManager(FavoriteRecipes.this));
                favoritesAdapter = new RecipeAdapter(FavoriteRecipes.this,favoriteRecipes);
                rvFavoriteRecipes.setAdapter(favoritesAdapter);
                favoritesAdapter.setOnRecipeClickListener(position -> {
                    Recipe clicked;
                    clicked = favoriteRecipes.get(position);
                    Intent intent=new Intent(FavoriteRecipes.this, RecipeActivity.class);
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