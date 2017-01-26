package com.theironyard.androidcontacts2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AndroidContactsActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editedName;
    EditText editedPhone;

    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_contacts);

        Intent intent = getIntent();
        String contact = intent.getStringExtra(MainActivity.NAMES_INFO);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setTextSize(18);
        textView.setText(contact);

        editedName = (EditText) findViewById(R.id.editedName);
        editedPhone = (EditText) findViewById(R.id.editedPhone);

        position = getIntent().getExtras().getInt("position", 0);
        //pull out person

        Button button = (Button) findViewById(R.id.editSaveButton);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("name", editedName.getText().toString());
        returnIntent.putExtra("phone", editedPhone.getText().toString());
        returnIntent.putExtra("position", position);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
