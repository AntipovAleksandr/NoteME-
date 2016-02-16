package com.example.noteme.project.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.noteme.R;
import com.example.noteme.project.database.DataHandler;
import com.example.noteme.project.listeners.OnContactListListener;
import com.example.noteme.project.model.Contact;
import com.squareup.picasso.Picasso;


public class ContactInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private long contactsId;
    private DataHandler dataHandler;
    private ImageView btnDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        Bundle bundle = getIntent().getExtras();
        contactsId = bundle.getLong("ContactsId");
        dataHandler = DataHandler.getInstance(this);
        ;

        Contact contact = dataHandler.getContact(String.valueOf(contactsId));

        TextView userName = (TextView) findViewById(R.id.tv_userinfo_user_name);
        TextView userEmail = (TextView) findViewById(R.id.tv_userinfo_user_email);
        TextView userNumber = (TextView) findViewById(R.id.tv_userinfo_user_number);
        TextView userDescription = (TextView) findViewById(R.id.tv_userinfo_user_description);


        ImageView contactAvatar = (ImageView) findViewById(R.id.contact_avatar);

        Picasso.with(this)
                .load("file://" + contact.getFilePath())
                .resize(500, 500)
                .centerCrop()
                .placeholder(R.drawable.ninja_avatar)
                .into(contactAvatar);

        userName.setText(contact.getName());
        userEmail.setText(contact.getEmail());
        userNumber.setText(contact.getNumber());
        userDescription.setText(contact.getDescription());

        btnDelete = (ImageView) findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_delete:
                dataHandler.removeContact(String.valueOf(contactsId));
                finish();
                break;
            default:
                break;
        }

    }
}

