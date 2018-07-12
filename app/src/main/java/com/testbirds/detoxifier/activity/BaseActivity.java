package com.testbirds.detoxifier.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.testbirds.detoxifier.R;

/**
 * Base Activity that implements common functionality for the rest of the activities
 */
public class BaseActivity extends AppCompatActivity {

    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Sets up the action bar
     * @param titleId
     * @param enableHomeAsUp
     */
    protected void setUpActionBar(int titleId, boolean enableHomeAsUp) {
        setUpActionBar(getString(titleId), enableHomeAsUp);
    }

    /**
     * Sets up the action bar
     * @param title
     * @param enableHomeAsUp
     */
    private void setUpActionBar(String title, boolean enableHomeAsUp) {
        setToolbar();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(enableHomeAsUp);
            getSupportActionBar().setTitle(title);
        }
    }

    /**
     * Sets up the toolbar
     */
    private void setToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

}
