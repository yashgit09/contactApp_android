package com.example.contactlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class AddContact extends AppCompatActivity {
    TextView title;
    EditText addName, addNumber;
    Button doneButton;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        title = (TextView)findViewById(R.id.addcontact);
        addName = (EditText)findViewById(R.id.addName);
        addNumber = (EditText)findViewById(R.id.addNumber);
        doneButton = (Button)findViewById(R.id.doneButton);
        DatabaseHandler db = new DatabaseHandler(this);
        intent = getIntent();

        // If a contact has to be edited
        if(intent.getIntExtra("EDIT_CONTACT", 0) == 1){
            title.setText("Edit Contact");
            Contact contact = db.getContact(intent.getIntExtra("CONTACT_DB_ID", 0));
            addName.setText(contact.getName());
            addNumber.setText(contact.getNumber());
         }

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = addName.getText().toString().trim();
                String number = addNumber.getText().toString().trim();

                if(name.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please enter a valid name.",Toast.LENGTH_SHORT).show();
                }
                else if(!isNumeric(number)) {
                    Toast.makeText(getApplicationContext(), "Please add a valid number.", Toast.LENGTH_SHORT).show();
                }
                else if(intent.getIntExtra("EDIT_CONTACT", 0) == 0){
                    boolean added = db.addContact(new Contact(name, number));
                    if(added){
                        Toast.makeText(getApplicationContext(),"New Contact Added.",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"A contact with the same name already exists.",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    db.updateContact(name,number,intent.getIntExtra("CONTACT_DB_ID", 0));
                    Toast.makeText(getApplicationContext(),"Contact edited.",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
            }
        });
    }

    public static boolean isNumeric(String s) {
        return !s.isEmpty() && s.matches("^[0-9]*$");
    }
}