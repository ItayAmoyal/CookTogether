package com.example.cooktogether;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FBRef {
    public static FirebaseAuth refAuth= FirebaseAuth.getInstance();
    public static FirebaseFirestore FBFS =FirebaseFirestore.getInstance();
    public static CollectionReference refImages=FBFS.collection("Images");
    public static FirebaseDatabase FBDB=FirebaseDatabase.getInstance();
    public static DatabaseReference refAllRecipes=FBDB.getReference("AllRecipes");
    public static DatabaseReference refComments=FBDB.getReference("Comments");
    public static DatabaseReference refRecipesByUser=FBDB.getReference("RecipesByUser");
    public static DatabaseReference refFilters=FBDB.getReference("Filters");
    public static DatabaseReference refUsers=FBDB.getReference("Users");
    public static DatabaseReference refFavRecipes=FBDB.getReference("FavRecipes");
}
