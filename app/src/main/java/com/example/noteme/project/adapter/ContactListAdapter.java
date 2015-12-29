package com.example.noteme.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.noteme.R;
import com.example.noteme.project.database.Contact;

import java.util.List;


public class ContactListAdapter extends BaseAdapter {
    private Context context;
    private List<Contact> contacts ;
    private LayoutInflater inflater;

    public ContactListAdapter(Context context, List<Contact> contacts) {
        this.contacts = contacts;
        this.context = context;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = convertView;

        if (view == null){
            view = inflater.inflate(R.layout.item_contact_list, viewGroup, false);
        }

        TextView contactName = (TextView) view.findViewById(R.id.tv_item_contact_name);
        contactName.setText(contacts.get(position).getName());

        ImageView contactAvatar = (ImageView) view.findViewById(R.id.iv_item_contact_avatar);

        if (contacts.get(position).getFilePath() != null) {
            contactAvatar.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_android));
        }
        return view;
    }

    public void setContacts (List<Contact> contacts){
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    public Contact getContact(int position){
        return (Contact) getItem(position);
    }
}
