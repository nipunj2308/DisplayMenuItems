package com.example.itemsdatabasestartup;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategorySettingsFragment extends Fragment {

    private EditText CategoryName;
    private Button AddCategoryToDatabase;
    private TextView DefineCategories, DefinedCategories;
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;
    private ArrayList<String> categoryNames;
    private RecyclerAdapterToDisplayDefinedCategories recyclerAdapterToDisplayDefinedCategories;
    private RecyclerView recyclerView;



    public CategorySettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_settings, container, false);

        categoryNames = new ArrayList<>();
        sqLiteHelper = new SQLiteHelper(getContext());

        DefineCategories = view.findViewById(R.id.DefineCategoriesTextView);
        DefinedCategories = view.findViewById(R.id.DefinedCategoriesTextView);
        CategoryName = view.findViewById(R.id.defineCategoryName);
        recyclerView = view.findViewById(R.id.DisplayCategoriesRecyclerView);
        AddCategoryToDatabase = view.findViewById(R.id.AddCategory);



        return  view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //((SettingsActivity)getActivity()).setToolbarTitle("Item Categories Settings");


    }


    @Override
    public void onStart() {
        super.onStart();
        getActivity().findViewById(R.id.bottom_navigationView).setVisibility(View.GONE);

        DisplayCategory();
        recyclerAdapterToDisplayDefinedCategories = new RecyclerAdapterToDisplayDefinedCategories(categoryNames);
        recyclerView.setAdapter(recyclerAdapterToDisplayDefinedCategories);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);


        AddCategoryToDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!TextUtils.isEmpty(CategoryName.getText().toString())) {
                    boolean DataInserted = sqLiteHelper.AddCategory(CategoryName.getText().toString().trim());
                    if (DataInserted == true) {
                        Toast.makeText(getActivity(), "data added successfully", Toast.LENGTH_SHORT).show();
                        categoryNames.add(CategoryName.getText().toString());
                        recyclerAdapterToDisplayDefinedCategories.notifyDataSetChanged();
                        CategoryName.setText("");
                        DefinedCategories.setText("Defined Categories");

                    } else {
                        Toast.makeText(getActivity(), "unsuccessful...try again", Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    Toast.makeText(getContext(), "Please enter a name", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //DisplayCategory();

    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    public void DisplayCategory()
    {
        Cursor cursor = sqLiteHelper.DisplayCategoryNames();
        if(cursor.getCount() == 0)
        {
            DefinedCategories.setText(" No Categories Defined");
        }
        else {
            while (cursor.moveToNext()) {
                categoryNames.add(cursor.getString(0));
            }
        }
    }
}
