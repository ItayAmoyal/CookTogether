package com.example.cooktogether;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class RecipeActivity extends AppCompatActivity {
    RecyclerView rvingredients,rvInstructions,rvComments;
    ImageButton rating1,rating2,rating3,rating4,rating5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        rvingredients = findViewById(R.id.rvIngredients);
        rvInstructions=findViewById(R.id.rvInstructions);
        rating1=findViewById(R.id.rating1);
        rating2=findViewById(R.id.rating2);
        rating3=findViewById(R.id.rating3);
        rating4=findViewById(R.id.rating4);
        rating5=findViewById(R.id.rating5);
        ImageButton[]ratingsSet={rating1,rating2,rating3,rating4,rating5};
        String[] names = {"oil", "eggs", "butter"};
        String[] amount = {"2", "4", "5"};
        rvingredients.setLayoutManager(new LinearLayoutManager(this));
        IngredientsAdapter adapterIngridents = new IngredientsAdapter(this, names, amount);
        rvingredients.setAdapter(adapterIngridents);


        //instructions
        ArrayList<InstructionItem>instructionItems=new ArrayList<>();
        instructionItems.add(new InstructionItem("Bake the cookie",null));
        InstructionsAdapter instructionsAdapter=new InstructionsAdapter(this,instructionItems);
        rvInstructions.setLayoutManager(new LinearLayoutManager(this));
        rvInstructions.setAdapter(instructionsAdapter);
        for (int i = 0; i < ratingsSet.length; i++) {
            int index=i;
            ratingsSet[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRating(index+1,ratingsSet);
                }
            });
        }

        //comments
        rvComments = findViewById(R.id.AllComments);
        rvComments.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Comments>comments=new ArrayList<>();
        comments.add(new Comments("user123", "Looks delicious!"));
        comments.add(new Comments("user456", "I tried this and it was amazing."));
        comments.add(new Comments("user789", "Can I replace butter with oil?"));

        CommentsAdapter commentsAdapter = new CommentsAdapter(this, comments);
        rvComments.setAdapter(commentsAdapter);
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



