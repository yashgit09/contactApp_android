package com.example.contactlist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ContactPage extends AppCompatActivity {

    TextView contactNameTextView;
    TextView contactNumberTextView;
    Button editButton, deleteButton;
    DatabaseHandler db;
    Intent inboundIntent, outboundIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_page);

        contactNameTextView = (TextView)findViewById(R.id.contactName);
        contactNumberTextView = (TextView)findViewById(R.id.contactNumber);
        editButton = (Button)findViewById(R.id.editButton);
        deleteButton = (Button)findViewById(R.id.deleteButton);
        db = new DatabaseHandler(this);
        inboundIntent = getIntent();
        outboundIntent = new Intent(this, AddContact.class);

        Contact contact = db.getContact(inboundIntent.getIntExtra("CONTACT_DB_ID", 0));
        contactNameTextView.setText(contact.getName());
        contactNumberTextView.setText(contact.getNumber());

        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                outboundIntent.putExtra("EDIT_CONTACT", 1);
                outboundIntent.putExtra("CONTACT_DB_ID", inboundIntent.getIntExtra("CONTACT_DB_ID", 0));
                startActivity(outboundIntent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alert();
            }
        });
    }

    public void alert() {
        // Create the object of AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(ContactPage.this);

        // Set the message show for the Alert time
        builder.setMessage("Do you want to delete this contact?");

        // Set Alert Title
        builder.setTitle("Alert !");

        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deleteContact(db.getContact(inboundIntent.getIntExtra("CONTACT_DB_ID", 0)));
                Toast.makeText(getApplicationContext(),"Contact deleted.",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // Set the Negative button with No name
        // OnClickListener method is use
        // of DialogInterface interface.
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // If user click no
                // then dialog box is canceled.
                dialog.cancel();
            }
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }
}