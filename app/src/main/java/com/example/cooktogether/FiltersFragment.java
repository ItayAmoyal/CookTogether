package com.example.cooktogether;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;


public class FiltersFragment extends DialogFragment {

 private SwitchMaterial switchMilk, switchMeat, switchParve,switchOther;
    private SwitchMaterial switchKosher, switchNotKosher;
    private Button confirm, back;
    private int place=0;

 public FiltersFragment() {
 // Required empty public constructor
 }

 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container,
 Bundle savedInstanceState) {
 return inflater.inflate(R.layout.fragment_filters, container, false);
 }

 @Override
 public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
 super.onViewCreated(view, savedInstanceState);
     Bundle args = getArguments();
     if (args != null) {
         place = args.getInt("place");

     }
 switchMilk = view.findViewById(R.id.switchMilk);
 switchMeat = view.findViewById(R.id.switchMeat);
 switchParve = view.findViewById(R.id.switchParve);
 switchKosher = view.findViewById(R.id.switchKosher);
 switchOther=view.findViewById(R.id.switchother);
 switchNotKosher = view.findViewById(R.id.switchNotKosher);
 confirm=view.findViewById(R.id.enter);
 back=view.findViewById(R.id.back);

 CompoundButton.OnCheckedChangeListener singleSelectListener = (buttonView, isChecked) -> {
 if (!isChecked) return;

 if (buttonView.getId() != R.id.switchMilk) switchMilk.setChecked(false);
 if (buttonView.getId() != R.id.switchMeat) switchMeat.setChecked(false);
 if (buttonView.getId() != R.id.switchParve) switchParve.setChecked(false);
 if (buttonView.getId() != R.id.switchother) switchOther.setChecked(false);
 };

 switchMilk.setOnCheckedChangeListener(singleSelectListener);
 switchMeat.setOnCheckedChangeListener(singleSelectListener);
 switchParve.setOnCheckedChangeListener(singleSelectListener);
 switchOther.setOnCheckedChangeListener(singleSelectListener);
     back.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Bundle result = new Bundle();
             result.putString("type", "all");
             result.putString("kashroot", "all");
             getParentFragmentManager().setFragmentResult("filters_result", result);
             dismiss();
         }
     });
     confirm.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             String type = "", kashroot = "";
             int flag1 = 0, flag2 = 0;
             if (switchMilk.isChecked()) {
                 type = "milk";
             } else if (switchMeat.isChecked()) {
                 type = "meat";
             } else if (switchParve.isChecked()) {
                 type = "parve";
             }else if (switchOther.isChecked()) {
                     type = "other";
             } else {
                 if(place==2) {
                     Toast.makeText(requireContext(), "חייב לבחור אחד", Toast.LENGTH_SHORT).show();
                     flag2++;
                 }
                 else{
                     type="all";
                 }
             }
             if (switchKosher.isChecked()) {
                 kashroot = "kosher";
             } else if (switchNotKosher.isChecked()) {
                 kashroot = "notKosher";
             } else {
                 if(place==2) {
                     Toast.makeText(requireContext(), "חייב לבחור אחד", Toast.LENGTH_SHORT).show();
                     flag1++;
                 }
                 else{
                     kashroot="all";
                 }
             }
             if (flag1 == 0 && flag2 == 0) {
                 Bundle result = new Bundle();
                 result.putString("type", type);
                 result.putString("kashroot", kashroot);
                 getParentFragmentManager().setFragmentResult("filters_result", result);
                 dismiss();

             }
         }
     });
 View.OnClickListener debugClick = v -> {
 String chosen = switchMilk.isChecked() ? "חלבי"
 : switchMeat.isChecked() ? "בשרי"
 : switchParve.isChecked() ? "פרווה"
 : "לא נבחר";
 };

 view.setOnClickListener(debugClick);
     CompoundButton.OnCheckedChangeListener kashrutListener = (buttonView, isChecked) -> {
         if (!isChecked) return;

         if (buttonView.getId() != R.id.switchKosher) switchKosher.setChecked(false);
         if (buttonView.getId() != R.id.switchNotKosher) switchNotKosher.setChecked(false);
     };

     switchKosher.setOnCheckedChangeListener(kashrutListener);
     switchNotKosher.setOnCheckedChangeListener(kashrutListener);
 }
    public void onStart() {
        super.onStart();

        //קובע גודל: 90% מהמסך
        if (getDialog() != null && getDialog().getWindow() != null) {
            DisplayMetrics dm = new DisplayMetrics();
            requireActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

            int width = (int) (dm.widthPixels * 0.90);
            int height = (int) (dm.heightPixels * 0.75); // רוב המסך (אפשר 0.85)

            getDialog().getWindow().setLayout(width, height);
        }
    }
 }