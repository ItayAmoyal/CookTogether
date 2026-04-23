package com.example.cooktogether;

import static com.example.cooktogether.FBRef.refAllRecipes;
import static com.example.cooktogether.FBRef.refAuth;
import static com.example.cooktogether.FBRef.refImages;
import static com.example.cooktogether.FBRef.refUsers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class FavoriteRecipes extends AppCompatActivity {
    FirebaseUser user=refAuth.getCurrentUser();
    User userCurrent=new User();
    RecyclerView rvFavoriteRecipes;
    RecipeAdapter favoritesAdapter;
    ImageButton btnBack;
    ArrayList<Recipe> favoriteRecipes=new ArrayList<>();
    ArrayList<RecipeShow> favoriteRecipesShow=new ArrayList<>();
    ArrayList<String> favRecipes=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_recipes);
        btnBack=findViewById(R.id.backButton2);
        rvFavoriteRecipes = findViewById(R.id.rvFavoriteRecipes);
        rvFavoriteRecipes.setLayoutManager(new LinearLayoutManager(FavoriteRecipes.this));
        favoritesAdapter = new RecipeAdapter(FavoriteRecipes.this,favoriteRecipesShow);
        rvFavoriteRecipes.setAdapter(favoritesAdapter);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FavoriteRecipes.this, HomePageActivity.class);
                startActivity(intent);
            }
        });
        refUsers.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userCurrent = snapshot.getValue(User.class);
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

    private void getRecipes() {
        int[]LoadedRecipes={0};
        for (String recipeId : userCurrent.getFavRecipesId()) {
            refAllRecipes.child(recipeId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    favoriteRecipes.add(snapshot.getValue(Recipe.class));
                    LoadedRecipes[0]++;
                    if(LoadedRecipes[0]==userCurrent.getFavRecipesId().size()){
                        getRecipesPic();
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
public void getRecipesPic(){
    int[] LoadedCount = {0};
    for (Recipe recipe : favoriteRecipes) {
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
                            favoriteRecipesShow.add(new RecipeShow(recipe, bitmap));
                            LoadedCount[0]++;
                            if (LoadedCount[0] == favoriteRecipes.size()) {
                                favoritesAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            });

        }
        else LoadedCount[0]++;
    }
    favoritesAdapter.setOnRecipeClickListener(position -> {
        Recipe clicked;
        clicked = favoriteRecipes.get(position);
        Intent intent = new Intent(FavoriteRecipes.this, RecipeActivity.class);
        intent.putExtra("Rid", clicked.getRecipeID());
        startActivity(intent);
    });
}
}

