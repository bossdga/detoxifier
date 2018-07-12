package com.testbirds.detoxifier.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.testbirds.detoxifier.ProgressDialogHandler;
import com.testbirds.detoxifier.R;
import com.testbirds.detoxifier.ui.AppDataViewModel;

/**
 * Base Fragment that implements common functionality for all the fragments
 */
public class BaseFragment extends Fragment implements ProgressDialogHandler {

    private ProgressDialog mProgressDialog;
    private boolean showDialog = false;
    protected AppDataViewModel appDataViewModel;

    public BaseFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appDataViewModel = new AppDataViewModel(getActivity().getApplication());
    }

    @Override
    public void onPause() {
        super.onPause();

        showDialog = false;
    }

    /**
     * Creates and shows a ProgressDialog
     */
    @Override
    public void showProgressDialog() {
        showDialog = true;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (showDialog) {
                    mProgressDialog = new ProgressDialog(getActivity());
                    mProgressDialog.setTitle(R.string.please_wait);
                    mProgressDialog.setMessage(getResources().getString(R.string.loading));
                    mProgressDialog.show();
                }
            }
        }, 1);
    }

    /**
     * Hides the ProgressDialog
     */
    @Override
    public void hideProgressDialog() {
        showDialog = false;

        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

}
