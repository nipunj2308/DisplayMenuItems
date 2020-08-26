package com.example.itemsdatabasestartup;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceFragmentCompat;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BillSettings extends Fragment implements AdapterView.OnItemSelectedListener {


    private Spinner GSTSpinner;
    private Switch EnableGST, EnableGreetingText;
    private EditText GreetingsText;
    private Button SaveInvoiceSettingsChanges;
    private LinearLayout GSTvaluelayout , GreetingsTextlayout;
    private ArrayList<String> GSTValues;
    public String GSTValueSelected ,GreetingsTextValue;
    public ArrayAdapter<String> GSTValuesSpinner;
    //private SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener;
    public static final String Shared_Prefs = "Shared_Prefs";
    public static final String GST_switch= "GST_switch";
    public static final String GST_value ="GST_value";
    public static final String Greetings_Text_Switch="Greetings_Text_Switch";
    public static final String Greetings_Text = "Greetings_Text";

    private String greetingsText, GstValue;
    private boolean GreetingsTextOnOff  , GSTSwitchOnOff;



    public BillSettings() {
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
        View view = inflater.inflate(R.layout.fragment_bill_settings, container, false);
        GSTSpinner = view.findViewById(R.id.GSTValueSpinner);
        EnableGST = view.findViewById(R.id.EnableGSTSwitch);
        EnableGreetingText = view.findViewById(R.id.GreetingsTextSwitch);
        GreetingsText = view.findViewById(R.id.GreetingsEditText);
        SaveInvoiceSettingsChanges = view.findViewById(R.id.SaveInvoiceSettingChanges);
        GSTvaluelayout = view.findViewById(R.id.GSTspinnerLayout);
        GreetingsTextlayout = view.findViewById(R.id.GreetingsEditTextLayout);
        GSTValues = new ArrayList<>();
        GSTValues.add(0, "Select GST %");
        GSTValues.add(1, "5%");
        GSTValues.add(2, "10%");
        GSTValues.add(3, "15%");
        GSTValues.add(4, "18%");

        //https://stackoverflow.com/questions/22883599/how-to-access-parent-activity-view-in-fragment



        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(" Customize bill SMS");
        //((SettingsActivity)getActivity()).setToolbarTitle("Customize bill Settings");
    }

    @Override
    public void onStart() {
        super.onStart();

        getActivity().findViewById(R.id.bottom_navigationView).setVisibility(View.GONE);

        GSTValuesSpinner =  new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,GSTValues);
        GSTValuesSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        GSTSpinner.setAdapter(GSTValuesSpinner);
        GSTSpinner.setOnItemSelectedListener(this);

        // load data after an adapter has been attached to the spinner otherwise it will just reset the set Position to
        //first position in the array always
        LoadData();
        //SaveInvoiceSettingsChanges.setEnabled(false);
        // check if Enable gst switch is checked or not
        GSTvaluelayout.setVisibility(EnableGST.isChecked()? View.VISIBLE : View.GONE);


        // check if Enable greetings text switch is checked or not
        GreetingsTextlayout.setVisibility(EnableGreetingText.isChecked()? View.VISIBLE : View.GONE);
/*
        if (EnableGreetingText.isChecked() == GreetingsTextOnOff && EnableGST.isChecked()== GSTSwitchOnOff  && GreetingsText.getText().toString().equals(greetingsText))
        {
            //(GSTValueSelected.equals(GstValue)
            SaveInvoiceSettingsChanges.setEnabled(false);
        }
        else
        {
            SaveInvoiceSettingsChanges.setEnabled(true);
        }*/


        //https://stackoverflow.com/questions/10576307/android-how-do-i-correctly-get-the-value-from-a-switch
        EnableGST.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                GSTvaluelayout.setVisibility(isChecked? View.VISIBLE : View.GONE);


                if (GSTvaluelayout.getVisibility() == View.VISIBLE)
                {

                    if (EnableGreetingText.isChecked() == GreetingsTextOnOff && EnableGST.isChecked() == GSTSwitchOnOff && GSTValueSelected.equals(GstValue))
                    {
                        SaveInvoiceSettingsChanges.setEnabled(false);
                    } else {
                        SaveInvoiceSettingsChanges.setEnabled(true);
                    }
                }
                if (GreetingsTextlayout.getVisibility() == View.VISIBLE)
                {
                    GreetingsTextValue = GreetingsText.getText().toString();

                    if (EnableGreetingText.isChecked() == GreetingsTextOnOff && EnableGST.isChecked() == GSTSwitchOnOff && GreetingsTextValue.equals(greetingsText))
                    {
                        SaveInvoiceSettingsChanges.setEnabled(false);
                    } else {
                        SaveInvoiceSettingsChanges.setEnabled(true);
                    }
                }

                if (GSTvaluelayout.getVisibility() == View.VISIBLE && GreetingsTextlayout.getVisibility() == View.VISIBLE)

                {
                    GreetingsTextValue = GreetingsText.getText().toString();

                    if (EnableGreetingText.isChecked() == GreetingsTextOnOff && EnableGST.isChecked() == GSTSwitchOnOff && GreetingsTextValue.equals(greetingsText) &&  GSTValueSelected.equals(GstValue))
                    {
                        SaveInvoiceSettingsChanges.setEnabled(false);
                    } else {
                        SaveInvoiceSettingsChanges.setEnabled(true);
                    }
                }
                else
                {

                    if (EnableGreetingText.isChecked() == GreetingsTextOnOff && EnableGST.isChecked() == GSTSwitchOnOff )
                    {
                        SaveInvoiceSettingsChanges.setEnabled(false);
                    } else {
                        SaveInvoiceSettingsChanges.setEnabled(true);
                    }


                }
            }
        });






        EnableGreetingText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                GreetingsTextlayout.setVisibility(isChecked ? View.VISIBLE : View.GONE);

                //  https://stackoverflow.com/questions/3791607/how-can-i-check-if-a-view-is-visible-or-not-in-android

                if (GSTvaluelayout.getVisibility() == View.VISIBLE) {

                    if (EnableGreetingText.isChecked() == GreetingsTextOnOff && EnableGST.isChecked() == GSTSwitchOnOff && GSTValueSelected.equals(GstValue)) {
                        SaveInvoiceSettingsChanges.setEnabled(false);
                    } else {
                        SaveInvoiceSettingsChanges.setEnabled(true);
                    }
                }
                if (GreetingsTextlayout.getVisibility() == View.VISIBLE) {
                    GreetingsTextValue = GreetingsText.getText().toString();

                    if (EnableGreetingText.isChecked() == GreetingsTextOnOff && EnableGST.isChecked() == GSTSwitchOnOff && GreetingsTextValue.equals(greetingsText)) {
                        SaveInvoiceSettingsChanges.setEnabled(false);
                    } else {
                        SaveInvoiceSettingsChanges.setEnabled(true);
                    }
                }

                if (GSTvaluelayout.getVisibility() == View.VISIBLE && GreetingsTextlayout.getVisibility() == View.VISIBLE) {
                    GreetingsTextValue = GreetingsText.getText().toString();

                    if (EnableGreetingText.isChecked() == GreetingsTextOnOff && EnableGST.isChecked() == GSTSwitchOnOff && GreetingsTextValue.equals(greetingsText) && GSTValueSelected.equals(GstValue)) {
                        SaveInvoiceSettingsChanges.setEnabled(false);
                    } else {
                        SaveInvoiceSettingsChanges.setEnabled(true);
                    }
                } else {

                    if (EnableGreetingText.isChecked() == GreetingsTextOnOff && EnableGST.isChecked() == GSTSwitchOnOff) {
                        SaveInvoiceSettingsChanges.setEnabled(false);
                    } else {
                        SaveInvoiceSettingsChanges.setEnabled(true);
                    }


                }
            }
        });



        GreetingsText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

               // SaveInvoiceSettingsChanges.setEnabled(editable.toString().equals(greetingsText)? false: true);
                /*if (EnableGreetingText.isChecked() ==  GreetingsTextOnOff && EnableGST.isChecked() == GSTSwitchOnOff  && GreetingsText.equals(greetingsText))
                {
                    SaveInvoiceSettingsChanges.setEnabled(false);
                }
                else
                {
                    SaveInvoiceSettingsChanges.setEnabled(true);
                }*/

                if (GSTvaluelayout.getVisibility() == View.VISIBLE)
                {

                    if (EnableGreetingText.isChecked() == GreetingsTextOnOff && EnableGST.isChecked() == GSTSwitchOnOff && GSTValueSelected.equals(GstValue))
                    {
                        SaveInvoiceSettingsChanges.setEnabled(false);
                    } else {
                        SaveInvoiceSettingsChanges.setEnabled(true);
                    }
                }
                if (GreetingsTextlayout.getVisibility() == View.VISIBLE)
                {
                    GreetingsTextValue = GreetingsText.getText().toString();

                    if (EnableGreetingText.isChecked() == GreetingsTextOnOff && EnableGST.isChecked() == GSTSwitchOnOff && GreetingsTextValue.equals(greetingsText))
                    {
                        SaveInvoiceSettingsChanges.setEnabled(false);
                    } else {
                        SaveInvoiceSettingsChanges.setEnabled(true);
                    }
                }

                if (GSTvaluelayout.getVisibility() == View.VISIBLE && GreetingsTextlayout.getVisibility() == View.VISIBLE)

                {
                    GreetingsTextValue = GreetingsText.getText().toString();

                    if (EnableGreetingText.isChecked() == GreetingsTextOnOff && EnableGST.isChecked() == GSTSwitchOnOff && GreetingsTextValue.equals(greetingsText) &&  GSTValueSelected.equals(GstValue))
                    {
                        SaveInvoiceSettingsChanges.setEnabled(false);
                    } else {
                        SaveInvoiceSettingsChanges.setEnabled(true);
                    }
                }
                else
                {

                    if (EnableGreetingText.isChecked() == GreetingsTextOnOff && EnableGST.isChecked() == GSTSwitchOnOff )
                    {
                        SaveInvoiceSettingsChanges.setEnabled(false);
                    } else {
                        SaveInvoiceSettingsChanges.setEnabled(true);
                    }


                }
            }

        });


        SaveInvoiceSettingsChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveInvoiceSettings();
                Toast.makeText(getContext(), "Changes Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        //getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);

    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onPause() {
        super.onPause();
        //getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        GSTValueSelected = adapterView.getItemAtPosition(i).toString();
        //SharedPreferences sharedPreferences = getContext().getSharedPreferences(Shared_Prefs,Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.putInt("SpinnerPositionSelected", i);
        //editor.commit();
        /*if (EnableGreetingText.isChecked() ==  GreetingsTextOnOff && EnableGST.isChecked() == GSTSwitchOnOff && GSTValueSelected.equals(GstValue) && GreetingsText.equals(greetingsText))
        {
            SaveInvoiceSettingsChanges.setEnabled(false);
        }
        else
        {
            SaveInvoiceSettingsChanges.setEnabled(true);
        }*/

        if (GSTvaluelayout.getVisibility() == View.VISIBLE)
        {

            if (EnableGreetingText.isChecked() == GreetingsTextOnOff && EnableGST.isChecked() == GSTSwitchOnOff && GSTValueSelected.equals(GstValue))
            {
                SaveInvoiceSettingsChanges.setEnabled(false);
            }
            else {
                SaveInvoiceSettingsChanges.setEnabled(true);
            }
        }
        if (GreetingsTextlayout.getVisibility() == View.VISIBLE)
        {
            GreetingsTextValue = GreetingsText.getText().toString();

            if (EnableGreetingText.isChecked() == GreetingsTextOnOff && EnableGST.isChecked() == GSTSwitchOnOff && GreetingsTextValue.equals(greetingsText))
            {
                SaveInvoiceSettingsChanges.setEnabled(false);
            } else {
                SaveInvoiceSettingsChanges.setEnabled(true);
            }
        }

        if (GSTvaluelayout.getVisibility() == View.VISIBLE && GreetingsTextlayout.getVisibility() == View.VISIBLE)

        {
            GreetingsTextValue = GreetingsText.getText().toString();

            if (EnableGreetingText.isChecked() == GreetingsTextOnOff && EnableGST.isChecked() == GSTSwitchOnOff && GreetingsTextValue.equals(greetingsText) &&  GSTValueSelected.equals(GstValue))
            {
                SaveInvoiceSettingsChanges.setEnabled(false);
            } else {
                SaveInvoiceSettingsChanges.setEnabled(true);
            }
        }
        else
        {

            if (EnableGreetingText.isChecked() == GreetingsTextOnOff && EnableGST.isChecked() == GSTSwitchOnOff )
            {
                SaveInvoiceSettingsChanges.setEnabled(false);
            } else {
                SaveInvoiceSettingsChanges.setEnabled(true);
            }


        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void SaveInvoiceSettings()
    {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Shared_Prefs,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(GST_switch, EnableGST.isChecked());
        editor.putString(GST_value, GSTValueSelected);
        editor.putBoolean(Greetings_Text_Switch, EnableGreetingText.isChecked());
        editor.putString(Greetings_Text, GreetingsText.getText().toString());
        editor.commit();


    }


    public void LoadData()
    {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Shared_Prefs, Context.MODE_PRIVATE);

       //set the GST switch;
        GSTSwitchOnOff = sharedPreferences.getBoolean(GST_switch, false);
        EnableGST.setChecked(GSTSwitchOnOff);

        //set GST value Spinner
        GstValue = sharedPreferences.getString(GST_value, "");
        Toast.makeText(getContext(), "the last spinner value was"+GstValue,Toast.LENGTH_SHORT).show();
        int SpinnerPosition = GSTValuesSpinner.getPosition(GstValue);
        GSTSpinner.setSelection(SpinnerPosition);

        /*if(GSTSpinner.getVisibility()== View.VISIBLE)
        {*/
            /*if (GstValue.equals("")) {
                GSTSpinner.setSelection(0);
            }
            else if(!GstValue.equals(""))
                {
                int SpinnerPosition = GSTValuesSpinner.getPosition(GstValue);
                GSTSpinner.setSelection(SpinnerPosition);

            }*/
           // Toast.makeText(getContext(), "SpinnerPosition is" +SpinnerPosition, Toast.LENGTH_SHORT).show();
            //GSTSpinner.setSelection(SpinnerPosition);
        //}

        //int SpinnerPosition = sharedPreferences.getInt("SpinnerPositionSelected", 0);


        //set the greetings Text Switch
        GreetingsTextOnOff = sharedPreferences.getBoolean(Greetings_Text_Switch, false);
        EnableGreetingText.setChecked(GreetingsTextOnOff);

        //set text in Greetings text EditText
        greetingsText = sharedPreferences.getString(Greetings_Text, "");
        GreetingsText.setText(greetingsText);


    }

}
