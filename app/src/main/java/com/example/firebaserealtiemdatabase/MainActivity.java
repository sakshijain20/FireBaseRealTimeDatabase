package com.example.firebaserealtiemdatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    EditText phoneNumber,firstName,lastName,address;
    Button add;
    FirebaseDatabase database;
    DatabaseReference dbReference;
    public String firstname,lastname,Address,phone;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneNumber= (EditText) findViewById(R.id.phoneNumber);
        firstName= (EditText) findViewById(R.id.firstName);
        lastName= (EditText) findViewById(R.id.lastName);
        address= (EditText) findViewById(R.id.address);
        add=(Button) findViewById (R.id.add);
        progressBar=(ProgressBar) findViewById (R.id.progressbar);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                    if(phoneNumber.getText().toString().length()!=10){
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "Invalid phone number", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        progressBar.setVisibility(View.GONE);
                        insertData();
                    }

            }
        });
    }

    private void insertData() {
        firstname= firstName.getText().toString().trim();
        lastname= lastName.getText().toString().trim();
        Address = address.getText().toString().trim();
    phone=phoneNumber.getText().toString().trim();

        if(!TextUtils.isEmpty(firstname) && !TextUtils.isEmpty(lastname) && !TextUtils.isEmpty(Address)){
            database=FirebaseDatabase.getInstance();
            dbReference=database.getReference("Users");

            progressBar.setVisibility(View.VISIBLE);

            User user=new User(firstname,lastname,Address);
            dbReference.child(phoneNumber.getText().toString().trim()).setValue(user, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    progressBar.setVisibility(View.GONE);
                    if(databaseError!=null){
                        Toast.makeText(MainActivity.this, "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Data inserted successfully!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else{

            if(TextUtils.isEmpty(firstname)){
                firstName.setError("First name required");
                firstName.requestFocus();
            }
            if(TextUtils.isEmpty(lastname)){
                lastName.setError("Last name required");
                lastName.requestFocus();
            }
            if(TextUtils.isEmpty(Address)){
                address.setError("Address required");
                address.requestFocus();
            }
            else{
                Toast.makeText(this, "All field are required!!", Toast.LENGTH_SHORT).show();
                firstName.requestFocus();
                lastName.requestFocus();
                address.requestFocus();
            }

        }


    }

    public void retrieve_data(View view) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = (DatabaseReference) database.getReference("Users").orderByKey();

        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String user = dataSnapshot.getKey();
                System.out.println(String.valueOf(user));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}
