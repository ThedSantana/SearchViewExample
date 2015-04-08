package com.github.vrcca.searchlistactivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends FragmentActivity implements SearchItem1Fragment.OnItem1SelectedListener {

    private EditText edtItem1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        edtItem1 = (EditText) findViewById(R.id.edtItem1);
        edtItem1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    loadSearchItem1();
                }

                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        Fragment searchFragment = fm.findFragmentByTag("SEARCH");
        if (searchFragment != null ) {
            searchFragment.getFragmentManager().popBackStack();
        }
    }

    private void loadSearchItem1() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.search_fragment, SearchItem1Fragment.newInstance(), "SEARCH");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void OnItem1SelectedListener(String item) {
        edtItem1.setText(item);
    }
}
