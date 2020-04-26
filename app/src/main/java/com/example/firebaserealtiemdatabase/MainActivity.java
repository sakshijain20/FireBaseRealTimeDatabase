package com.example.firebaserealtiemdatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText phoneNumber,firstName,lastName,address;
    Button add;
    FirebaseDatabase database;
    DatabaseReference dbReference;
    public String firstname,lastname,Address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneNumber= (EditText) findViewById(R.id.phoneNumber);
        firstName= (EditText) findViewById(R.id.firstName);
        lastName= (EditText) findViewById(R.id.lastName);
        address= (EditText) findViewById(R.id.address);
        add=(Button) findViewById (R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    insertData();
            }
        });


    }




    //push data in firebase
    private void insertData() {
        firstname= firstName.getText().toString().trim();
        lastname= lastName.getText().toString().trim();
        Address = address.getText().toString().trim();

        if(!TextUtils.isEmpty(firstname) && !TextUtils.isEmpty(lastname) && !TextUtils.isEmpty(Address)){
            database=FirebaseDatabase.getInstance();
            dbReference=database.getReference("Users");
            User user=new User(firstname,lastname,Address);
            dbReference.child(phoneNumber.getText().toString().trim()).setValue(user);
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(this, "All field are required!!", Toast.LENGTH_SHORT).show();
        }


    }

    //retrieve data from firebase
    public void retrieve_data(View view) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");

//listener for the node

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //here we loop through all possible ids under Users maybe one or maybe more
                ArrayList arrayList=new ArrayList();
                for(DataSnapshot ds:dataSnapshot.getChildren() ){

                    //this loop will run for every child under Users

                    //grab the user id or user ids

                    String userID = ds.getKey();
                    System.out.println(userID);
                    Log.d("retrieve",userID);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("retieve", "loadPost:onCancelled", databaseError.toException());

            }
        };
        mDatabase.addValueEventListener(listener);
    }




}
