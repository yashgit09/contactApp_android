package com.example.contactlist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView contactList;
    List<Contact> contacts;
    DatabaseHandler db;
    ArrayAdapter arrayAdapter;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList= findViewById(R.id.contactList);
        fab = findViewById(R.id.fab);
        db = new DatabaseHandler(this);
        contacts = db.getAllContacts();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts);
        contactList.setAdapter(arrayAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddContact.class);
                startActivity(intent);
            }
        });

        // Handle click event in contactList ListView
        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Launch Contact Page Activity
                Contact contact = (Contact)parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this,ContactPage.class);
                intent.putExtra("CONTACT_DB_ID", contact.getID());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        contacts = db.getAllContacts();

        arrayAdapter.clear();
        arrayAdapter.addAll(contacts);
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.add:
                Intent intent = new Intent(MainActivity.this,AddContact.class);
                startActivity(intent);
                return true;
            case R.id.deleteAll:
                alert();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void alert() {
        // Create the object of AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        // Set the message show for the Alert time
        builder.setMessage("Do you want to delete all contacts?");

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
                // When the user click yes button
                // then app will close
                db.deleteAllContacts();
                arrayAdapter.clear();
                contacts.clear();
                Toast.makeText(getApplicationContext(),"All contacts deleted.",Toast.LENGTH_SHORT).show();
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