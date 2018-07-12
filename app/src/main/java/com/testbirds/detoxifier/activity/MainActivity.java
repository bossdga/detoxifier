package com.testbirds.detoxifier.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.testbirds.detoxifier.R;

/**
 * Activity that will show a list of blacklisted apps, add new apps to the black list and start/stop
 * a background service to check which app is on the foreground
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpActionBar(R.string.title_activity_main, false);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AppListActivity.class));
            }
        });
    }

}
