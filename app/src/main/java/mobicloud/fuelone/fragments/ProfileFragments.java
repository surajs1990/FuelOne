package mobicloud.fuelone.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobicloud.fuelone.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import mobicloud.fuelone.model.ProfileModel;
import mobicloud.fuelone.model.TankModel;
import mobicloud.fuelone.utils.ManageSession;
import okhttp3.MultipartBody;
import mobicloud.fuelone.utils.Constant;


/**
 * Created by Suraj Shakya on 18/05/18.
 */

public class ProfileFragments extends Fragment {




    public static FragmentManager fragmentManager;
    public static Fragment fragment;
    public static View view;
    public static Context context;
    public static RelativeLayout editLayout;
    private RelativeLayout parentlayout;
    private ImageView editProfileImg, profile_image;
    private EditText name_Edit, lastname_Edit, email_Edit, phone_Edit;
    private TextView updateTxt;
    private DatabaseReference databaseprofile, retriveprofile;

    MultipartBody.Part fileToUpload;
    /*For Camera and attechment*/
    String filepath = android.os.Environment.getExternalStorageDirectory()
    + File.separator + "AI LOGISTIC"+ File.separator;
    public static String fileActualPath = null;
    public static String fileName;

    public static Fragment getInstance(Context fragmentcontext, FragmentManager manager) {
        context = fragmentcontext;
        fragmentManager = manager;
        fragment = new ProfileFragments();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.profile_fragment, container, false);
        initWidgets(view);
        addEventListeners();
        retriveProfileFromFireBase();
        return view;
    }

    private void initWidgets(View view){

        databaseprofile = FirebaseDatabase.getInstance().getReference(ManageSession.getPreference(context,"id")+"_profile");
        parentlayout    = (RelativeLayout) view.findViewById(R.id.parentlayout);
        editProfileImg  = (ImageView) view.findViewById(R.id.editProfileImg);
        profile_image   = (ImageView) view.findViewById(R.id.profile_image);
        editLayout      = (RelativeLayout) view.findViewById(R.id.editLayout);
        name_Edit       = (EditText) view.findViewById(R.id.name_Edit);
        lastname_Edit   = (EditText) view.findViewById(R.id.lastname_Edit);
        email_Edit      = (EditText) view.findViewById(R.id.email_Edit);
        phone_Edit      = (EditText) view.findViewById(R.id.phone_Edit);
        updateTxt       = (TextView) view.findViewById(R.id.updateTxt);

        email_Edit.setText(ManageSession.getPreference(context,"email"));

    }

    private void addEventListeners(){

        editLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view = getLayoutInflater().inflate(R.layout.camera_layout, null);
                LinearLayout cameraLayout, galleryLayout;
                TextView cancelTxt;
                final Dialog mBottomSheetDialog = new Dialog(context, R.style.CameraDialog);
                cameraLayout    = (LinearLayout) view.findViewById(R.id.cameraLayout);
                galleryLayout   = (LinearLayout) view.findViewById(R.id.galleryLayout);
                cancelTxt       = (TextView) view.findViewById(R.id.cancelTxt);
                cameraLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBottomSheetDialog.dismiss();
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        fileName = System.currentTimeMillis()+".jpg";
                        File style = new File(filepath);
                        if(!style.exists()){style.mkdir();}
                        File f = new File(filepath,fileName);
                        fileActualPath= f.getAbsolutePath();
                        startActivityForResult(intent,1);
                    }
                });

                galleryLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBottomSheetDialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);
                    }
                });

                cancelTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBottomSheetDialog.dismiss();
                    }
                });

                mBottomSheetDialog.setContentView(view);
                mBottomSheetDialog.setCancelable(true);
                mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
                mBottomSheetDialog.show();
            }
        });

        editProfileImg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                view = getLayoutInflater().inflate(R.layout.camera_layout, null);
                LinearLayout cameraLayout, galleryLayout;
                TextView cancelTxt;
                final Dialog mBottomSheetDialog = new Dialog(context, R.style.CameraDialog);
                cameraLayout    = (LinearLayout) view.findViewById(R.id.cameraLayout);
                galleryLayout   = (LinearLayout) view.findViewById(R.id.galleryLayout);
                cancelTxt       = (TextView) view.findViewById(R.id.cancelTxt);
                cameraLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBottomSheetDialog.dismiss();
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        fileName = System.currentTimeMillis()+".jpg";
                        File style = new File(filepath);
                        if(!style.exists()){style.mkdir();}
                        File f = new File(filepath,fileName);
                        fileActualPath= f.getAbsolutePath();
                        startActivityForResult(intent,1);
                    }
                });

                galleryLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBottomSheetDialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);
                    }
                });

                cancelTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBottomSheetDialog.dismiss();
                    }
                });

                mBottomSheetDialog.setContentView(view);
                mBottomSheetDialog.setCancelable(true);
                mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
                mBottomSheetDialog.show();
            }
        });

        updateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constant.isNetworkConnected(getContext())){
                    if(Validation()){
                        String id = databaseprofile.push().getKey();
                        ProfileModel model = new ProfileModel();
                        model.setUserId(ManageSession.getPreference(context,"id"));
                        model.setFirstName(name_Edit.getText().toString());
                        model.setLastName(lastname_Edit.getText().toString());
                        model.setEmail(email_Edit.getText().toString());
                        model.setContactNumber(phone_Edit.getText().toString());
                        databaseprofile.child(id).setValue(model);
                    }
                }else {
                    Snackbar.make(parentlayout,"Please check your internet",Snackbar.LENGTH_SHORT).show();
                }

            }
        });

    }

    /*
    * Retrive Data From Firebase
    * */
    public void retriveProfileFromFireBase(){

        databaseprofile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ProfileModel model = postSnapshot.getValue(ProfileModel.class);
                    if(!model.getEmail().equalsIgnoreCase("")){
                        SetProfileData(model);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    /*
    * Set Profile Data
    * */
    public void SetProfileData(ProfileModel profileModel){
        name_Edit.setText(profileModel.getFirstName());
        lastname_Edit.setText(profileModel.getLastName());
        email_Edit.setText(profileModel.getEmail());
        phone_Edit.setText(profileModel.getContactNumber());
    }

    private boolean Validation(){
        if(name_Edit.getText().length()==0){
            Snackbar.make(parentlayout,context.getResources().getString(R.string.enter_name),Snackbar.LENGTH_SHORT).show();
            return false;
        }else if(lastname_Edit.getText().length()==0){
            Snackbar.make(parentlayout,context.getResources().getString(R.string.enter_last_name),Snackbar.LENGTH_SHORT).show();
            return false;
        }else if(email_Edit.getText().length()==0){
            Snackbar.make(parentlayout,context.getResources().getString(R.string.eater_email),Snackbar.LENGTH_SHORT).show();
            return false;
        }else if(!Constant.isEmailValid(email_Edit.getText().toString())){
            Snackbar.make(parentlayout,context.getResources().getString(R.string.valid_email),Snackbar.LENGTH_SHORT).show();
            return false;
        }else if(phone_Edit.getText().length()==0){
            Snackbar.make(parentlayout,context.getResources().getString(R.string.enter_mobile),Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /*Image and attechment Result*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            if (requestCode == 1) {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                fileName = getResources().getString(R.string.app_name)+System.currentTimeMillis()+".jpg";
                File style = new File(filepath);
                if(!style.exists()){style.mkdir();}
                File f = new File(filepath,fileName);
                fileActualPath= f.getAbsolutePath();
                FileOutputStream fo;
                profile_image.setImageBitmap(image);
                try {
                    fo = new FileOutputStream(f);
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = context.getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                fileActualPath = cursor.getString(columnIndex);
                cursor.close();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                Bitmap bitmap = BitmapFactory.decodeFile(fileActualPath, options);
                profile_image.setImageBitmap(bitmap);
            }
        }
    }


}