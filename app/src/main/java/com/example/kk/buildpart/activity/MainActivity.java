package com.example.kk.buildpart.activity;


import android.os.Bundle;

import com.example.kk.buildpart.R;
import com.example.kk.buildpart.base.BaseActivity;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

public class MainActivity extends BaseActivity {
    private static final int REQUEST_CODE_PERMISSION_CAMERA_SD = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_main);
    }

    @Override
    protected void findWidgets() {
        {
            AndPermission.with(MainActivity.this)
                    .requestCode(REQUEST_CODE_PERMISSION_CAMERA_SD)
                    .permission(new String[]{"android.permission.READ_PHONE_STATE", "android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"})
                    // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                    .rationale(new RationaleListener() {
                        @Override
                        public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                            AndPermission.rationaleDialog(MainActivity.this, rationale).show();
                        }
                    })
                    .send();

        }
    }

    @Override
    protected void initComponent() {

    }
}
