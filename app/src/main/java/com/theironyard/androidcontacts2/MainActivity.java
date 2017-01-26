package com.theironyard.androidcontacts2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {

    ArrayAdapter<String> contacts;

    ListView list;
    EditText nameField;
    EditText phoneField;
    Button addContact;

    static final int NAMES_REQUEST = 1;
    static final String NAMES_INFO = "com.theironyard.androidcontacts.contact";

    final String FILENAME = "contacts.csv";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.listView);
        nameField = (EditText) findViewById(R.id.nameField);
        phoneField = (EditText) findViewById(R.id.phoneField);
        addContact = (Button) findViewById(R.id.addContact);

        contacts = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        loadContacts();
        list.setAdapter(contacts);

        addContact.setOnClickListener(this);
        list.setOnItemLongClickListener(this);
        list.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String contact = nameField.getText().toString();
        String phone = phoneField.getText().toString();
        contacts.add(contact + " " + phone);
        nameField.setText("");
        phoneField.setText("");

        saveContacts();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        String contact = contacts.getItem(position);
        contacts.remove(contact);
        saveContacts();
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, AndroidContactsActivity.class);

        String contact = contacts.getItem(position);
        //intent.putExtra("person", contacts.getItem(position));
        intent.putExtra(NAMES_INFO, contact);
        intent.putExtra("position", position);

        //goes here
        startActivityForResult(intent, NAMES_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == NAMES_REQUEST) {
            if(resultCode == RESULT_OK) {
                int position = data.getIntExtra("position", 0);
                String contact = contacts.getItem(position);

                String name = data.getStringExtra("name");
                String phone = data.getStringExtra("phone");
                contacts.remove(contact);
                contacts.add(name + " " + phone);

                saveContacts();
            }
        }
    }

    private void saveContacts() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);

            StringBuilder sb = new StringBuilder();
            for(int i = 0;i<contacts.getCount();i++) {
                String contact = contacts.getItem(i);

                sb.append(contact + "\n");
            }
            fos.write(sb.toString().getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadContacts() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            int position = 0;
            while(br.ready()) {
                String contactLine = br.readLine();

                contacts.add(contactLine);

                position++;
            }

        } catch(IOException e) {
            e.printStackTrace();
        }
    }


}

