package com.example.cooktogether;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {
    RecyclerView rvingredients, rvInstructions, rvComments;
    ImageButton rating1, rating2, rating3, rating4, rating5;
    TextView recipeTitle, cooktime;
    ImageView recipePicture;
    EditText writeComment;
    Recipe activeRecipe;
    Button submitComment, submitRating;
    ArrayList<String> ridFirebase = new ArrayList<>();
    ArrayList<Comments> commentsFireBase = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        rvingredients = findViewById(R.id.rvIngredients);
        rvInstructions = findViewById(R.id.rvInstructions);
        rvComments = findViewById(R.id.AllComments);
        recipeTitle = findViewById(R.id.textRecipeTitle);
        recipePicture = findViewById(R.id.imageRecipe);
        writeComment = findViewById(R.id.editTextComment);
        cooktime = findViewById(R.id.cooktime);
        submitRating = findViewById(R.id.submitRatingButton);
        submitComment = findViewById(R.id.submitCommentButton);
        //get the RecipeId

        String Rid = "Itay2202";
        //Example for data

        Recipe recipe1 = new Recipe("Chicken Fried", "Itay");
        recipe1.setRecipeID("Itay2202");
        recipe1.setCookTime("2 min");
        recipe1.setAverageRating(3.0);
        String imageUri = "android.resource://" + getPackageName() + "/" + R.drawable.favrecipes;
        recipe1.setPicture(imageUri);
        String[] names = {"oil", "eggs", "butter"};
        String[] amount = {"2", "4", "5"};
        ArrayList<Ingridiants> ingridiants = new ArrayList<>();
        ingridiants.add(new Ingridiants(32, "grams", "Oil"));
        ingridiants.add(new Ingridiants(40, "Ounces", "Chicken"));
        recipe1.setIngridiantsArrayList(ingridiants);
        ArrayList<InstructionItem> instructionItems = new ArrayList<>();
        instructionItems.add(new InstructionItem("Bake the cookie", null));
        recipe1.setInstructions(instructionItems);
        recipe1.setAverageRating(3.0);
        ArrayList<Comments> comments = new ArrayList<>();
        comments.add(new Comments("user123", "Looks delicious!",Rid));
        comments.add(new Comments("user456", "I tried this and it was amazing.",Rid));
        comments.add(new Comments("user789", "Can I replace butter with oil?",Rid));
        //הכנסת נתונים(דוגמא)

        FBRef.refAllRecipes.child(recipe1.getRecipeID()).setValue(recipe1);
        FBRef.refComments.child(recipe1.getRecipeID()).setValue(comments);
        //קריאת נתונים

        GetRecipeIdFromFireBase(Rid);
    }

    //פעולות

    //תצוגת המתכון על המסך
    private void GetRecipeIdFromFireBase(String correctRid) {
        Query query=FBRef.refAllRecipes.orderByChild("recipeID").equalTo(correctRid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    Toast.makeText(RecipeActivity.this, "Recipe not found", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (DataSnapshot data : snapshot.getChildren()) {
                    Recipe recipeData = data.getValue(Recipe.class);
                    activeRecipe = new Recipe(recipeData);
                }
                GetComments(correctRid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void GetComments(String correctRid) {


        FBRef.refComments.child(correctRid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commentsFireBase.clear();
                ridFirebase.clear();
                if (!snapshot.exists()) {
                    Toast.makeText(RecipeActivity.this, "Recipe not found", Toast.LENGTH_SHORT).show();
                    return; //
                }
                for (DataSnapshot data : snapshot.getChildren()) {
                    Comments commentsData = data.getValue(Comments.class);
                    commentsFireBase.add(commentsData);
                }
                setRecipeInscreen();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void setRecipeInscreen(){
        if (activeRecipe == null) {
            Toast.makeText(this, "No recipe to show", Toast.LENGTH_SHORT).show();
            return;
        }
        recipeTitle.setText(activeRecipe.getName().toString());
        Glide.with(this)
                .load(activeRecipe.getPicture())
                .into(recipePicture);
        rvingredients.setLayoutManager(new LinearLayoutManager(this));
        IngredientsAdapter ingredientsAdapter=new IngredientsAdapter(this,activeRecipe.getIngridiantsArrayList());
        rvingredients.setAdapter(ingredientsAdapter);
        rvInstructions.setLayoutManager(new LinearLayoutManager(this));
       InstructionsAdapter instructionsAdapter=new InstructionsAdapter(this,activeRecipe.getInstructions());
        rvInstructions.setAdapter(instructionsAdapter);
        rvComments.setLayoutManager(new LinearLayoutManager(this));
       CommentsAdapter commentsAdapter=new CommentsAdapter(this,commentsFireBase);
        rvComments.setAdapter(commentsAdapter);
        setRating((int) activeRecipe.getAverageRating());
    }
    private void setRating(int num){
        rating1 = findViewById(R.id.rating1);
        rating2 = findViewById(R.id.rating2);
        rating3 = findViewById(R.id.rating3);
        rating4 = findViewById(R.id.rating4);
        rating5 = findViewById(R.id.rating5);
        ImageButton[] ratingsSet = {rating1, rating2, rating3, rating4, rating5};
        for (int i = 0; i < 5; i++) {
            ratingsSet[i].setImageResource(R.drawable.emptystar);
        }
        for (int i = 0; i < num; i++) {
            ratingsSet[i].setImageResource(R.drawable.fullstar);
        }
    }
}



