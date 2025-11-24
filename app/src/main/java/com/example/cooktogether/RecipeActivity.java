package com.example.cooktogether;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipeActivity extends AppCompatActivity {
    RecyclerView rvingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        rvingredients = findViewById(R.id.rvIngredients);
        String[] names = {"oil", "eggs", "butter"};
        String[] amount = {"2", "4", "5"};
        rvingredients.setLayoutManager(new LinearLayoutManager(this));
        IngredientsAdapter adapterIngridents = new IngredientsAdapter(this, names, amount);
        rvingredients.setAdapter(adapterIngridents);
    }
}

