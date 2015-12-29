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
import com.example.noteme.project.adapter.MyAdapter;
import com.example.noteme.project.database.DataHandler;
import com.example.noteme.project.database.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends Activity {

    private DataHandler dataHandler;
    public List<Contact> myContacts = new ArrayList<Contact>();
    private MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        dataHandler = new DataHandler(this);
        dataHandler.open();

        myContacts = dataHandler.getContacts();

        ListView myListView = (ListView) findViewById(R.id.lv_contact_list);
        adapter = new MyAdapter(this, myContacts);

        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(adapterListener);


        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ContactListActivity.this);
                builder.setMessage("Точно хотите удалить")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
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
        adapter.notifyDataSetChanged();
    }

    private AdapterView.OnItemClickListener adapterListener = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Contact contact = (Contact) adapter.getItem(position);
            Bundle bundle = new Bundle();
            bundle.putString(AddContactActivity.CONTACT_ID, contact.getContactID());
            Intent intent = new Intent();
            intent.putExtras(bundle);
            intent.setClass(getBaseContext(), AddContactActivity.class);
            startActivity(intent);
        }
    };


    public void onClickAddContact(View v) {
        Intent intent = new Intent(ContactListActivity.this, AddContactActivity.class);
        startActivity(intent);
    }


    protected void onDestroy() {
        super.onDestroy();
        dataHandler.close();
    }

}
