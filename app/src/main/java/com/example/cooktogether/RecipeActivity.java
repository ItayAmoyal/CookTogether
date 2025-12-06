package com.example.cooktogether;

import static com.example.cooktogether.FBRef.refAuth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;
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
    ImageButton rating1, rating2, rating3, rating4, rating5,backButton;
    ImageView ratingRecipe1, ratingRecipe2, ratingRecipe3, ratingRecipe5, ratingRecipe4;
    TextView recipeTitle, cooktime;
    ImageView recipePicture;
    Boolean[]ratingsOn={false,false,false,false,false};
     Boolean[]ratingsRecipeOn={false,false,false,false,false};
    EditText writeComment;
    Recipe activeRecipe;
    FirebaseUser user =refAuth.getCurrentUser();
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
        backButton=findViewById(R.id.backButton);
        rating1 = findViewById(R.id.rating1);
        rating2 = findViewById(R.id.rating2);
        rating3 = findViewById(R.id.rating3);
        rating4 = findViewById(R.id.rating4);
        rating5 = findViewById(R.id.rating5);
        ratingRecipe1 = findViewById(R.id.Recipeating1);
        ratingRecipe2 = findViewById(R.id.Reciperating2);
        ratingRecipe3 = findViewById(R.id.Reciperating3);
        ratingRecipe4 = findViewById(R.id.Reciperating4);
        ratingRecipe5 = findViewById(R.id.Reciperating5);
        ImageView[] recipeRatings={ratingRecipe1,ratingRecipe2,ratingRecipe3,ratingRecipe4,ratingRecipe5};
        ImageButton[] ratings={rating1,rating2,rating3,rating4,rating5};
        for (int i = 0; i < 5; i++) {
            int index=i;
            ratings[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRating(index+1);
                }
            });
        }
        for (int i = 0; i < 5; i++) {
            recipeRatings[i].setImageResource(R.drawable.emptystar);
        }
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RecipeActivity.this, HomePageActivity.class);
                startActivity(intent);
            }
        });
        //get the RecipeId
        Intent intent=getIntent();
        String correctRid = intent.getStringExtra("Rid");
        //Example for data
        Recipe recipe1 = new Recipe("Chicken Fried", "Itay");
        recipe1.setRecipeID(correctRid);
        recipe1.setCookTime("2 min");
        recipe1.setAverageRating(3);
        String imageUri = "android.resource://" + getPackageName() + "/" + R.drawable.favrecipes;
        recipe1.setPicture(imageUri);
        String[] names = {"oil", "eggs", "butter"};
        String[] amount = {"2", "4", "5"};
        ArrayList<Ingredient> ingridiants = new ArrayList<>();
        ingridiants.add(new Ingredient("32", "grams", "Oil"));
        ingridiants.add(new Ingredient("40", "Ounces", "Chicken"));
        recipe1.setIngridiantsArrayList(ingridiants);
        ArrayList<InstructionItem> instructionItems = new ArrayList<>();
        instructionItems.add(new InstructionItem("Bake the cookie", null));
        recipe1.setInstructions(instructionItems);
        recipe1.setAverageRating(3);

        Comments comments1=new Comments("user123", "Looks delicious!", correctRid);
        Comments comments2=new Comments("user456", "I tried this and it was amazing.", correctRid);
        Comments comments3=new Comments("user789", "Can I replace butter with oil?", correctRid);
        Recipe recipe2=new Recipe(recipe1);
        recipe2.setRecipeID("taim");
        String keyId1=FBRef.refComments.push().getKey();
        comments1.setKeyId(keyId1);
        String keyId2=FBRef.refComments.push().getKey();
        comments2.setKeyId(keyId2);
        String keyId3=FBRef.refComments.push().getKey();
        comments3.setKeyId(keyId3);
        //הכנסת נתונים(דוגמא)
        FBRef.refAllRecipes.child(("yami")).setValue(recipe1);
        FBRef.refAllRecipes.child(("taim")).setValue(recipe2);
        //קריאת נתונים
        GetRecipeFromFireBase(correctRid);
        submitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCommment(correctRid);
            }
        });
        submitRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddRating(GetRating());
            }
        });
    }

    //פעולות

    //תצוגת המתכון על המסך
    private void GetRecipeFromFireBase(String correctRid) {
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

        Query query=FBRef.refComments.orderByChild("rid").equalTo(correctRid);

      query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commentsFireBase.clear();
                ridFirebase.clear();
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
        ImageView[] recipeRatings={ratingRecipe1,ratingRecipe2,ratingRecipe3,ratingRecipe4,ratingRecipe5};
        if (activeRecipe == null) {
            Toast.makeText(this, "No recipe to show", Toast.LENGTH_SHORT).show();
            return;
        }
        cooktime.setText("CookTime: "+activeRecipe.getRecipeID());
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
        for (int i = 0; i < activeRecipe.getAverageRating(); i++) {
            recipeRatings[i].setImageResource(R.drawable.fullstar);
        }
    }
    //הוספת תגובה
    private void AddCommment(String correctID){
        String comment=writeComment.getText().toString();
        String userName="Itay";
        Comments newComment=new Comments(userName,comment,correctID);
        String keyId1=FBRef.refComments.push().getKey();
        newComment.setKeyId(keyId1);
        FBRef.refComments.child(keyId1).setValue(newComment);
        commentsFireBase.add(newComment);
        CommentsAdapter commentsAdapter=new CommentsAdapter(this,commentsFireBase);
        rvComments.setAdapter(commentsAdapter);
        Toast.makeText(this, "Comment Added", Toast.LENGTH_SHORT).show();
    }
    private void AddRating(int num){
        if( activeRecipe.AddRating(num,"Itay")==true) {
            setRecipeRating(activeRecipe.getAverageRating());
            FBRef.refAllRecipes.child(activeRecipe.getRecipeID()).setValue(activeRecipe);
            Toast.makeText(this, "Rating Added", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "You Can't Rate more than once", Toast.LENGTH_SHORT).show();
        }
    }

    private int GetRating(){
        ImageButton[] ratingsSet = {rating1, rating2, rating3, rating4, rating5};
        int count=0;
        for (int i = 0; i < 5; i++) {
            if(ratingsOn[i]==true)
                count++;
        }
        if (count==5)
            return 5;
        for (int i = 0; i < 5; i++) {
            count=0;
            if(ratingsOn[i]==true){
                for (int j = i+1; j < 5; j++) {
                    if(ratingsOn[j]==true){
                        count++;
                    }
                }
                if (count==0)
                    return i+1;
            }
        }
        return 0;
    }
    private void setRating(int num){
        ImageButton[] ratingsSet = {rating1, rating2, rating3, rating4, rating5};
        for (int i = 0; i < 5; i++) {
            ratingsSet[i].setImageResource(R.drawable.emptystar);
            ratingsOn[i]=false;
        }
        for (int i = 0; i < num; i++) {
            ratingsOn[i]=true;
            ratingsSet[i].setImageResource(R.drawable.fullstar);
        }
    }
    private void setRecipeRating(int num){
        ImageView[] ratingsSet = {ratingRecipe1, ratingRecipe2, ratingRecipe3, ratingRecipe4, ratingRecipe5};
        for (int i = 0; i < 5; i++) {
            ratingsSet[i].setImageResource(R.drawable.emptystar);
            ratingsRecipeOn[i]=false;
        }
        for (int i = 0; i < num; i++) {
            ratingsSet[i].setImageResource(R.drawable.fullstar);
            ratingsRecipeOn[i]=true;
        }
    }
}



