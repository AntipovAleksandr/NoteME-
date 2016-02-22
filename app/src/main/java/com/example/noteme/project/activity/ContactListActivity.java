package com.example.noteme.project.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.noteme.R;
import com.example.noteme.project.adapter.ContactListAdapter;
import com.example.noteme.project.database.DataHandler;
import com.example.noteme.project.listeners.OnContactListListener;
import com.example.noteme.project.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends Activity implements AdapterView.OnItemClickListener,
        View.OnClickListener, AdapterView.OnItemLongClickListener, OnContactListListener {

    private DataHandler dataHandler;
    public List<Contact> myContacts = new ArrayList<Contact>();
    private ContactListAdapter adapter;
    private TextView textToOneContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        dataHandler = DataHandler.getInstance(this);
        dataHandler.getInstance(this).setListener(this);
        dataHandler.open();

        (findViewById(R.id.btn_contact_list_add)).setOnClickListener(this);
        ListView myListView = (ListView) findViewById(R.id.lv_contact_list);
        adapter = new ContactListAdapter(this);

        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(this);
        myListView.setOnItemLongClickListener(this);

        textToOneContact = (TextView) findViewById(R.id.text_to_one_contact);

        hideContactsIndicator();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myContacts = dataHandler.getInstance(this).getContacts();
        hideContactsIndicator();
        adapter.setContacts(myContacts);

    }


    protected void onDestroy() {
        super.onDestroy();
        dataHandler.getInstance(this).close();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, AddContactActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ContactInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("ContactsId", parent.getItemIdAtPosition(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ContactListActivity.this);
        builder.setMessage("Точно хотите удалить этот контакт?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataHandler.removeContact(((Contact) adapter.getItem(position)).getContactID());
                    }
                })
                .setCancelable(false);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        openQuitDialog();
    }

    private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(this);
        quitDialog.setTitle(R.string.NoteMe);
        quitDialog.setIcon(R.mipmap.ic_noteme);
        quitDialog.setCancelable(false);
        quitDialog.setMessage(R.string.title_back_press);
        quitDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        quitDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        quitDialog.show();
    }

    @Override
    public void onContactListChanged() {
        myContacts = DataHandler.getInstance(this).getContacts();
        adapter.setContacts(myContacts);
        hideContactsIndicator();
    }

    private void hideContactsIndicator() {
        if (myContacts.isEmpty()) {
            textToOneContact.setVisibility(View.VISIBLE);
        } else {
            textToOneContact.setVisibility(View.GONE);
        }
    }
}
