package com.example.kk.buildpart.activity;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.example.kk.buildpart.R;
import com.example.kk.buildpart.base.BaseActivity;
import com.example.kk.buildpart.tools.NativeObject;
import com.example.kk.buildpart.tools.ToastUtils;
import com.example.kk.buildpart.view.XWebView;
import com.githang.statusbar.StatusBarCompat;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import junit.framework.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import okhttp3.OkHttpClient;


public class MainActivity extends BaseActivity  {
    @Bind(R.id.xWebView)
    XWebView xWebView;
    @Bind(R.id.button)
    Button button;
    private ValueCallback<Uri> mUploadMessage;// 表单的数据信息
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private final static int FILECHOOSER_RESULTCODE = 1;// 表单的结果回调
    private Uri imageUri;
    private static final int REQUEST_CODE_PERMISSION_CAMERA_SD = 100;
    private boolean mShouldOverrideUrlLoading;
    private NativeObject nativeobject;
    private long exitTime;
    private Bundle savedInstanceState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState=savedInstanceState;
        //Log.i("savedInstanceState=======",savedInstanceState.toString());
        onCreateView(R.layout.activity_main);
        StatusBarCompat.setStatusBarColor(this,222222,true);



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



        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               // nativeobject.joinMeeting("1",);
            }
        });
    }

    @Override
    protected void initComponent() {
        WebViewClient client = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                Intent intent = null;
                //根据拦截的url来判断是否拦截跳转
                if (url.contains("test")) {
                    intent = new Intent(MainActivity.this, Test.class);
                } else if (url.contains("returnBackController")) {
                    //...
                }else if (url.contains("....")) {
                    xWebView.loadUrl("javascript:reload()");
                }else {
                    xWebView.loadUrl(url);
                }
                if (intent != null){
                    startActivity(intent);
                }
                return true;

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mShouldOverrideUrlLoading = true;

                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //此方法是为了处理在5.0以上Htts的问题，必须加上
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (mShouldOverrideUrlLoading) {

                    return;
                }

            }
        };
        xWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onShowFileChooser(WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             FileChooserParams fileChooserParams) {
                mUploadCallbackAboveL = filePathCallback;
                takePhoto();
                return true;
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                takePhoto();
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                mUploadMessage = uploadMsg;
                takePhoto();
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                mUploadMessage = uploadMsg;
                takePhoto();
            }



            @Override
            public boolean onJsAlert(WebView webView, String s, String s1, JsResult jsResult) {
                super.onJsAlert(webView, s, s1, jsResult);
                return false;
            }

            @Override
            public boolean onJsConfirm(WebView webView, String s, String s1, JsResult jsResult) {
                super.onJsConfirm(webView,s,s1,jsResult);
                return false;
            }

            @Override
            public boolean onJsPrompt(WebView webView, String s, String s1, String s2, JsPromptResult jsPromptResult) {
                super.onJsPrompt(webView,s,s1,s2,jsPromptResult);
                return false;
            }
        });



        nativeobject=   new NativeObject(this,new OkHttpClient(),savedInstanceState);
        xWebView.addJavascriptInterface(nativeobject,"NativeObject");
        xWebView.setWebViewClient(client);

        xWebView.loadUrl("http://dev-2.meiliangzi.cn:8080/smdj/app/login");

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (mUploadMessage != null) {
                Log.e("Mr.Kang", "onActivityResult: "+result);
                if (result == null) {
                    mUploadMessage.onReceiveValue(imageUri);
                    mUploadMessage = null;

                    Log.e("Mr.Kang", "onActivityResult: "+imageUri);
                } else {
                    mUploadMessage.onReceiveValue(result);
                    mUploadMessage = null;
                }


            }
        }
    }


    @SuppressWarnings("null")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILECHOOSER_RESULTCODE
                || mUploadCallbackAboveL == null) {
            return;
        }

        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                results = new Uri[]{imageUri};
            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();

                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }

                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        if (results != null) {
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        } else {
            results = new Uri[]{imageUri};
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        }

        return;
    }

    private void takePhoto() {
        File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyApp");
        // Create the storage directory if it does not exist
        if (!imageStorageDir.exists()) {
            imageStorageDir.mkdirs();
        }
        File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        imageUri = Uri.fromFile(file);

        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent i = new Intent(captureIntent);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            i.setPackage(packageName);
            i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            cameraIntents.add(i);

        }
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        Intent chooserIntent = Intent.createChooser(i, "Image Chooser");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
        MainActivity.this.startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
    }


        @Override
    protected void onDestroy() {
//        ZoomSDK zoomSDK = ZoomSDK.getInstance();
//
//        if(zoomSDK.isInitialized()) {
//            MeetingService meetingService = zoomSDK.getMeetingService();
//            meetingService.removeListener(this);
//        }
            nativeobject.Destroy();

        super.onDestroy();
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK ) {
            //do something.
            //exitApp();
            return true;
        }else {
            return super.dispatchKeyEvent(event);
        }
    }
    private void exitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.custom("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            getApplication().onTerminate();
        }
    }



}
