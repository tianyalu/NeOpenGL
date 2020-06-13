package com.sty.ne.opengl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.widget.TextView;

import com.sty.ne.opengl.util.PermissionUtils;

public class MainActivity extends AppCompatActivity {
    private String[] needPermissions = {Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!PermissionUtils.checkPermissions(this, needPermissions)) {
            PermissionUtils.requestPermissions(this, needPermissions);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if(requestCode == PermissionUtils.REQUEST_PERMISSIONS_CODE){
            if(!PermissionUtils.verifyPermissions(grantResults)) {
                PermissionUtils.showMissingPermissionDialog(this);
            }else {

            }
        }
    }

}
