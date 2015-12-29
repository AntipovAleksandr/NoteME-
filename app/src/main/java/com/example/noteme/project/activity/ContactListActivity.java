package com.example.noteme.project.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.noteme.R;
import com.example.noteme.project.adapter.ContactListAdapter;
import com.example.noteme.project.database.Contact;
import com.example.noteme.project.database.DataHandler;

import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private DataHandler dataHandler;
    public List<Contact> myContacts = new ArrayList<Contact>();
    private ContactListAdapter adapter;
    private ListView myListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        dataHandler = new DataHandler(this);
        dataHandler.open();

        myContacts = dataHandler.getContacts();

        myListView = (ListView) findViewById(R.id.lv_contact_list);
        adapter = new ContactListAdapter(this, myContacts);

        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(this);

        (findViewById(R.id.btn_contact_list_add)).setOnClickListener(this);

        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ContactListActivity.this);
                builder.setMessage("Точно хотите удалить")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Contact contact = (Contact) adapter.getItem(position);
                                dataHandler.removeContact(contact.getContactID());
                                adapter.setContacts(dataHandler.getContacts());
                            }
                        })
                        .setCancelable(false);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.setContacts(dataHandler.getContacts());
    }

    protected void onDestroy() {
        super.onDestroy();
        dataHandler.close();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ContactListActivity.this, AddContactActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}