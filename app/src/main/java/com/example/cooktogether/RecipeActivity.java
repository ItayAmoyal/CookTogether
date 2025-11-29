package com.example.cooktogether;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {
    RecyclerView rvingredients,rvInstructions,rvComments;
    ImageButton rating1,rating2,rating3,rating4,rating5;
    TextView recipeTitle,cooktime;
    ImageView recipePicture;
    EditText writeComment;
    Button submitComment,submitRating;
    ArrayList<String> ridFirebase =new ArrayList<>();
    ArrayList<Recipe> recipeFireBase=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        rvingredients = findViewById(R.id.rvIngredients);
        rvInstructions=findViewById(R.id.rvInstructions);
        rvComments = findViewById(R.id.AllComments);
        recipeTitle=findViewById(R.id.textRecipeTitle);
        recipePicture=findViewById(R.id.imageRecipe);
        writeComment=findViewById(R.id.editTextComment);
        cooktime=findViewById(R.id.cooktime);
        submitRating=findViewById(R.id.submitRatingButton);
        submitComment=findViewById(R.id.submitCommentButton);
        rating1=findViewById(R.id.rating1);
        rating2=findViewById(R.id.rating2);
        rating3=findViewById(R.id.rating3);
        rating4=findViewById(R.id.rating4);
        rating5=findViewById(R.id.rating5);
        ImageButton[]ratingsSet={rating1,rating2,rating3,rating4,rating5};
        //get the RecipeId
        String Rid="Itay2202";
      //Example for data
        Recipe recipe1=new Recipe("Chicken Fried","Itay");
        recipe1.setRecipeID("Itay2202");
        recipe1.setCookTime("2 min");
        recipe1.setAverageRating(3.0);
        String imageUri = "android.resource://" + getPackageName() + "/" + R.drawable.favrecipes;
        recipe1.setPicture(imageUri);
        String[]names = {"oil", "eggs", "butter"};
        String[] amount = {"2", "4", "5"};
        ArrayList<Ingridiants> ingridiants=new ArrayList<>();
        ingridiants.add(new Ingridiants(32,"grams","Oil"));
        ingridiants.add(new Ingridiants(40,"Ounces","Chicken"));
        recipe1.setIngridiantsArrayList(ingridiants);
        ArrayList<InstructionItem>instructionItems=new ArrayList<>();
        instructionItems.add(new InstructionItem("Bake the cookie",null));
        recipe1.setInstructions(instructionItems);
        recipe1.setAverageRating(3.0);
        ArrayList<Comments>comments=new ArrayList<>();
        comments.add(new Comments("user123", "Looks delicious!"));
        comments.add(new Comments("user456", "I tried this and it was amazing."));
        comments.add(new Comments("user789", "Can I replace butter with oil?"));
        //הכנסת נתונים(דוגמא)
        FBRef.refAllRecipes.child(recipe1.getRecipeID()).setValue(recipe1);
        FBRef.refComments.child(recipe1.getRecipeID()).setValue(comments);
        //קריאת נתונים
        GetRecipeIdFromFireBase( "fall");
        }

        //פעולות


    //תצוגת המתכון על המסך
    private Recipe GetRecipeIdFromFireBase(String correctRid){
        FBRef.refAllRecipes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                recipeFireBase.clear();
                ridFirebase.clear();
                for (DataSnapshot data : snapshot.getChildren()){
                    String str1=(String) data.getKey();
                    Recipe recipeData=data.getValue(Recipe.class);
                    recipeFireBase.add(recipeData);
                    ridFirebase.add(str1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        for(Recipe recipe:recipeFireBase){
            if((recipe.getRecipeID().equals(correctRid))){
                return recipe;
            }
        }
        Toast.makeText(this, "Not possible", Toast.LENGTH_SHORT).show();
        return null;
    }

    private void setRecipeInscreen(){

    }
    private void setRating(int num,ImageButton[]ratingsSet){
        for (int i = 0; i < 5; i++) {
            ratingsSet[i].setImageResource(R.drawable.emptystar);
        }
        for (int i = 0; i < num; i++) {
            ratingsSet[i].setImageResource(R.drawable.fullstar);
        }
    }
}



