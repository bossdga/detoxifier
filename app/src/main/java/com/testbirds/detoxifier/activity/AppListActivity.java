package com.testbirds.detoxifier.activity;

import android.os.Bundle;

import com.testbirds.detoxifier.R;

/**
 * Activity that will show a list of installed apps
 */
public class AppListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);

        setUpActionBar(R.string.title_activity_app_list, true);
    }

}
