package com.example.noteme.project.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.noteme.R;
import com.example.noteme.project.database.Contact;
import com.example.noteme.project.database.DataHandler;


public class ContactInfoActivity extends AppCompatActivity {

    private long contacsId;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        Bundle bundle =  getIntent().getExtras();
        contacsId = bundle.getLong("ContacsId");
        DataHandler dataHandler = new DataHandler(this);
        Contact contact = dataHandler.getMain(String.valueOf(contacsId));


        TextView userName = (TextView) findViewById(R.id.tv_userinfo_user_name);
        TextView userEmail = (TextView) findViewById(R.id.tv_userinfo_user_email);
        TextView userNumber = (TextView) findViewById(R.id.tv_userinfo_user_number);
        TextView userDescription = (TextView) findViewById(R.id.tv_userinfo_user_description);


        userName.setText(contact.getName());
        userEmail.setText(contact.getEmail());
        userNumber.setText(contact.getNumber());
        userDescription.setText(contact.getDescription());


    }
}
