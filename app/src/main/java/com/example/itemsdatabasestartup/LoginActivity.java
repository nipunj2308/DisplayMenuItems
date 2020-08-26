package com.example.itemsdatabasestartup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import static com.example.itemsdatabasestartup.BillSettings.Shared_Prefs;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText userIdText;
    private TextInputEditText Password;
    private String[] citynames = {"BHO","IND", "PUN" };
    private DatabaseReference adminIDReference, subOutletIDReference;
    private FirebaseAuth firebaseAuth;
    private ArrayList<ModelClassForAdminIDDetailsFirebase> abc;
    private Button loginButton;
    String password;
    //public static final Hashtable<String,String> ADMIN_ID_INFORMATION_IN_FIREBASE =  new Hashtable<>();

    // all the interractions with realtime database are asyncronous;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        abc = new ArrayList<>();

        userIdText = findViewById(R.id.userNameField);
        Password = findViewById(R.id.passwordField);
        loginButton = findViewById(R.id.LoginButton);





        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String enteredID = userIdText.getText().toString();
                final String enteredPassword = Password.getText().toString();
                final String AdminID = AdminID(enteredID);
                Login(enteredID,AdminID,enteredPassword);
            }
        });




    }



    public String AdminID(String enteredID)
    {
        String substring="";

        for (String city:citynames)
        {
            int n = enteredID.indexOf(city);
            //if enteredID contains any of the city names abbreviations then it will return the index of its first occurence else
            // a negetive value
            // if n>0 it means the first index of city name is not 0th position or starting and there is some text before it.
            if (n>=0)
            {
                substring = enteredID.substring(n);
            }
            break;
        }
        return substring;
    }

    public void Login(final String enteredID,final String AdminID, final String Password)
    {
        // if the entered ID is the admin ID
        if (enteredID.equals(AdminID))
        {
            adminIDReference = FirebaseDatabase.getInstance().getReference("AdminID").child(AdminID);

            adminIDReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                     // if the entered adminID exists
                    if (dataSnapshot.exists())
                    {
                        adminIDReference.child("General Information").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                            {
                                if (dataSnapshot.exists())
                                {
                                    SharedPreferences sharedPreferences = (LoginActivity.this).getSharedPreferences(Shared_Prefs, Context.MODE_PRIVATE);

                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                                    {

                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(String.valueOf(dataSnapshot1.getKey()), String.valueOf(dataSnapshot1.getValue()));
                                        editor.commit();

                                    }

                                    String password = sharedPreferences.getString("password","");
                                    // Toast.makeText(LoginActivity.this,"entered password: "+password,Toast.LENGTH_SHORT).show();
                                    if (Password.equals(password))
                                    {
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("currentAdminID",enteredID);
                                        editor.commit();
                                       // Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this, SubletBaseActivity.class);
                                        startActivity(intent);

                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(LoginActivity.this,"Invalid password",Toast.LENGTH_SHORT).show();

                                    }

                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                    //if the entered AdminID does not exist
                    else
                    {
                        Toast.makeText(LoginActivity.this,"User does not exist",Toast.LENGTH_SHORT).show();

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
        //if entered ID is not an adminID
        else
        {
            subOutletIDReference = FirebaseDatabase.getInstance().getReference("AdminID").child(AdminID).child("Counters").child(enteredID);
            subOutletIDReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists())
                    {
                        SharedPreferences sharedPreferences = (LoginActivity.this).getSharedPreferences(Shared_Prefs, Context.MODE_PRIVATE);

                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                        {

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(String.valueOf(dataSnapshot1.getKey()), String.valueOf(dataSnapshot1.getValue()));
                            editor.commit();

                        }

                        String password = sharedPreferences.getString("password","");

                        if (Password.equals(password))
                        {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("current Sublet ID",enteredID);
                            editor.putString("current Admin ID",AdminID);
                            editor.commit();
                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, SubletBaseActivity.class);
                            startActivity(intent);

                            finish();
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this,"Invalid password",Toast.LENGTH_SHORT).show();

                        }

                    }
                    else {
                        Toast.makeText(LoginActivity.this,"Sublet User does not exist",Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });




        }
    }
}
