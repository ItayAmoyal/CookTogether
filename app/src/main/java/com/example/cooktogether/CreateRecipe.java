package com.example.cooktogether;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CreateRecipe extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Spinner spinnerDiff,spinnerING;
    EditText ingrediantName,ingredientAmount;
    String ingredientUnit="";
    Button btnAddIngredient;
    ArrayList<Ingredient>allIngredients=new ArrayList<>();
    IngrediantCreateAdapter ingredientsAdapter;

    String[] difficulty,unitMeasure;
    RecyclerView rvIngrediant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        spinnerDiff = findViewById(R.id.spinnerDifficulty);
        spinnerING=findViewById(R.id.spinnerMeasure);
        rvIngrediant = findViewById(R.id.rvCreateIngredients);
        ingrediantName = findViewById(R.id.IngredientsName);
        ingredientAmount = findViewById(R.id.IngredientsAmount);
        btnAddIngredient = findViewById(R.id.btnAddIngredient);
        rvIngrediant.setLayoutManager(new LinearLayoutManager(this));
        ingredientsAdapter = new IngrediantCreateAdapter(this,allIngredients);
        rvIngrediant.setAdapter(ingredientsAdapter);
        difficulty = getResources().getStringArray(R.array.difficulty_levels);
        ArrayAdapter<String> adpDifficulty = new ArrayAdapter<>(CreateRecipe.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, difficulty);
        spinnerDiff.setAdapter(adpDifficulty);
        spinnerING = findViewById(R.id.spinnerMeasure);
        unitMeasure = getResources().getStringArray(R.array.unit_measure);
        ArrayAdapter<String> adpMeasure = new ArrayAdapter<>(CreateRecipe.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, unitMeasure);
        spinnerING.setAdapter(adpMeasure);
        spinnerING.setOnItemSelectedListener(this);
        //הוסף מצרך
        btnAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddIngrediant();
            }
        });
    }

    //הוספת מצרך
    private void AddIngrediant(){
        String name=ingrediantName.getText().toString();
        String amount=ingredientAmount.getText().toString()+" ";
        if (name.isEmpty() || amount.isEmpty() ||
                ingredientUnit.isEmpty() || ingredientUnit.equals(unitMeasure[0])) {
            Toast.makeText(this, "Fill name, amount and choose unit", Toast.LENGTH_SHORT).show();
            return;
        }
        Ingredient ingredient=new Ingredient(amount,ingredientUnit,name);
        allIngredients.add(ingredient);
        ingredientsAdapter.notifyItemInserted(allIngredients.size() - 1);
        ingrediantName.setText("");
        ingredientAmount.setText("");
        spinnerING.setSelection(0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ingredientUnit = unitMeasure[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        ingredientUnit = "";
    }
}
