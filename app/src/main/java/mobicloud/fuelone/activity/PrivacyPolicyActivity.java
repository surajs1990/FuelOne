package mobicloud.fuelone.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mobicloud.fuelone.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

public class PrivacyPolicyActivity extends AppCompatActivity implements  OnPageChangeListener, OnLoadCompleteListener {

    private Context context;
    private LinearLayout backLayout;
    private RelativeLayout parentlayout;
    private PDFView pdfView;
    private String pdfFileName;
    private Integer pageNumber = 0;
    public static final String SAMPLE_FILE = "school.pdf";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy_policy);
        getSupportActionBar().hide();
        initWidgets();
        addEventListeners();
    }

    /*Declare widgtes*/
    public void initWidgets(){

        context                 = this;
        backLayout              = (LinearLayout) findViewById(R.id.backLayout);
        parentlayout            = (RelativeLayout) findViewById(R.id.parentlayout);

        pdfView       = (PDFView) findViewById(R.id.pdfView);
        displayFromAsset(SAMPLE_FILE);

    }

    private void displayFromAsset(String assetFileName) {
        pdfFileName = assetFileName;

        pdfView.fromAsset(SAMPLE_FILE)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }


    /*Click Listener function*/
    private void addEventListeners(){

        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void loadComplete(int nbPages) {

    }

    @Override
    public void onPageChanged(int page, int pageCount) {

    }
}
