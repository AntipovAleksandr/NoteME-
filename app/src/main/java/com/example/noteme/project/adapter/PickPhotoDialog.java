package com.example.noteme.project.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;

import com.example.noteme.R;
import com.example.noteme.project.activity.AddContactActivity;

public class PickPhotoDialog extends android.support.v4.app.DialogFragment {

    public PickPhotoDialog() {
        // Empty constructor required for DialogFragment
    }

    public static PickPhotoDialog newInstance(String title) {
        PickPhotoDialog frag = new PickPhotoDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(R.array.resource, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, AddContactActivity.REQUEST_IMAGE_CAPTURE);
                        break;
                    case 1:
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto, AddContactActivity.REQUEST_GALLERY);
                        break;
                    case 2:
                        dismiss();
                        break;
                }
            }
        });
        return builder.create();
    }

}
