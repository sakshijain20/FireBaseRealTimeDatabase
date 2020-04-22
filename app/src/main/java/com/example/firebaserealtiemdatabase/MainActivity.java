package com.example.firebaserealtiemdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

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

    private void insertData() {
        firstname= firstName.getText().toString().trim();
        lastname= lastName.getText().toString().trim();
        Address = address.getText().toString().trim();

        if(!TextUtils.isEmpty(firstname) && !TextUtils.isEmpty(lastname) && !TextUtils.isEmpty(Address)){
            database=FirebaseDatabase.getInstance();
            dbReference=database.getReference("Users").push();
            User user=new User(firstname,lastname,Address);
            dbReference.child(phoneNumber.getText().toString().trim()).setValue(user);
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(this, "All filed are required!!", Toast.LENGTH_SHORT).show();
        }


    }

    public void retrieve_data(View view) {
    }
}
