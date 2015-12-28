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

public class MainActivity extends Activity {

    private DataHandler dataHandler;
    public List<Contact> myContact = new ArrayList<Contact>();
    private MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataHandler = new DataHandler(this);
        dataHandler.open();


        ListView myListView = (ListView) findViewById(R.id.listView);

        adapter = new MyAdapter(this, myContact);

        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(adapterListener);


        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
                                adapter.setContacts(dataHandler.getMainFist());
                            }
                        })
                        .setCancelable(false);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });

    }



    private AdapterView.OnItemClickListener adapterListener = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Contact contact = (Contact) adapter.getItem(position);
            Bundle bundle = new Bundle();
            bundle.putString(ContactList.CONTACT_ID, contact.getContactID());
            Intent intent = new Intent();
            intent.putExtras(bundle);
            intent.setClass(getBaseContext(), ContactList.class);
            startActivity(intent);
        }
    };


    public void onClickAddContact(View v) {
        Intent intent = new Intent(MainActivity.this, ContactList.class);
        startActivity(intent);
    }


    protected void onDestroy() {
        super.onDestroy();
        dataHandler.close();
    }

}
