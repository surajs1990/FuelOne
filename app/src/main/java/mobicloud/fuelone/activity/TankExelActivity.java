package mobicloud.fuelone.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jeevandeshmukh.glidetoastlib.GlideToast;
import com.mobicloud.fuelone.R;

import java.io.File;
import java.util.ArrayList;

import mobicloud.fuelone.adapter.MapTankAdapter;
import mobicloud.fuelone.adapter.MappingAdapter;
import mobicloud.fuelone.model.FileModel;
import mobicloud.fuelone.model.MapingModel;
import mobicloud.fuelone.model.NozzelModel;
import mobicloud.fuelone.model.TankModel;
import mobicloud.fuelone.utils.ManageSession;

public class TankExelActivity extends AppCompatActivity implements View.OnClickListener {

    final static int PICK_PDF_CODE = 2342;
    private Context context;
    private RelativeLayout parentlayout;
    private ArrayList<FileModel> fileList;
    private ProgressBar progressBar;
    private DatabaseReference mappingData;
    private RecyclerView exelListData;
    private StorageReference mStorageReference;
    private DatabaseReference mDatabaseReference;
    private MapTankAdapter adapter;
    StorageReference storageRef = FirebaseStorage.getInstance().getReference("Uploads");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exel_config);
        setTitle("Tank Config");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initWidgets();

    }

    /*Declare widgtes*/
    public void initWidgets(){

        context                 = this;
        mStorageReference       = FirebaseStorage.getInstance().getReference();
        mappingData             = FirebaseDatabase.getInstance().getReference(ManageSession.getPreference(context,"id")+"mapping_");

        mDatabaseReference      = FirebaseDatabase.getInstance().getReference("_tank_config");
        progressBar             = (ProgressBar) findViewById(R.id.progressBar);
        parentlayout            = (RelativeLayout) findViewById(R.id.parentlayout);
        exelListData            = (RecyclerView) findViewById(R.id.exelListData);

        getAllList();

    }


    public void getAllList(){
        progressBar.setVisibility(View.VISIBLE);
        mDatabaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fileList = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    FileModel model = postSnapshot.getValue(FileModel.class);
                    fileList.add(model);
                }
                ShowDATA(fileList);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    /*
    * Set Data Adapter
    * */
    private void ShowDATA(ArrayList<FileModel> file){
        adapter = new MapTankAdapter(context,TankExelActivity.this,file);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        exelListData.setLayoutManager(mLayoutManager);
        exelListData.setItemAnimator(new DefaultItemAnimator());
        exelListData.setAdapter(adapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //when the user choses the file
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                //uploading the file
//                Uploadfile(data.getData());
//                Toast.makeText(context,data.getData().getPath().toString(),Toast.LENGTH_SHORT).show();
                ShowDialog(context,data.getData());
            }else{
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
    }




    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    //this function will get the pdf from the storage
    private void getPDF() {
        //for greater than lolipop versions we need the permissions asked on runtime
        //so if the permission is not available user will go to the screen to allow storage permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            startActivity(intent);
            return;
        }

        //creating an intent for file chooser
        Intent intent = new Intent();
        intent.setType("application/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select file"), PICK_PDF_CODE);

    }

    private void Uploadfile(Uri data, final String Name){
        progressBar.setVisibility(View.VISIBLE);
        StorageReference sRef = mStorageReference.child(""+Name+".xlsx");
        sRef.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressBar.setVisibility(View.GONE);
                        FileModel model = new FileModel();
                        model.setFileName(Name);
                        model.setFileUrl( taskSnapshot.getStorage().getRoot().getDownloadUrl().toString());
                        Toast.makeText(context,taskSnapshot.getStorage().getDownloadUrl().getResult().toString(),Toast.LENGTH_SHORT).show();
                        Log.e("",""+taskSnapshot.getStorage().getPath().toString());
                        mDatabaseReference.child(mDatabaseReference.push().getKey()).setValue(model);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//                        textViewStatus.setText((int) progress + "% Uploading...");
                    }
                });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }if (id == R.id.addFileMenu) {
//            getPDF();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.add_file_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    public void ShowDialog(Context context, final Uri datauri){
        final Dialog dialog = new Dialog(context);
        dialog.setTitle("Alert");
        dialog.setContentView(R.layout.file_dialog);

        final EditText file_Edit = (EditText) dialog.findViewById(R.id.file_Edit);
        TextView OkayTxt = (TextView) dialog.findViewById(R.id.OkayTxt);

        OkayTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(file_Edit.getText().toString().length()!=0){
                    Uploadfile(datauri, file_Edit.getText().toString());
                    dialog.dismiss();
                }else {
                    Snackbar.make(parentlayout,"Please enter file name",Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();

    }

    @Override
    public void onClick(View v) {

    }
}
