package com.sj.yinjiaoyun.shituwang.activity;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.sj.yinjiaoyun.shituwang.R;
import com.sj.yinjiaoyun.shituwang.adapter.FolderAdapter;
import com.sj.yinjiaoyun.shituwang.adapter.SinglePhotoAdapter;
import com.sj.yinjiaoyun.shituwang.app.MyApplication;
import com.sj.yinjiaoyun.shituwang.bean.Photo;
import com.sj.yinjiaoyun.shituwang.bean.PhotoFolder;
import com.sj.yinjiaoyun.shituwang.bean.PictureReturnBean;
import com.sj.yinjiaoyun.shituwang.http.Api;
import com.sj.yinjiaoyun.shituwang.http.CallBack;
import com.sj.yinjiaoyun.shituwang.http.HttpClient;
import com.sj.yinjiaoyun.shituwang.utils.Const;
import com.sj.yinjiaoyun.shituwang.utils.DensityUtils;
import com.sj.yinjiaoyun.shituwang.utils.NetToastUtils;
import com.sj.yinjiaoyun.shituwang.utils.OtherUtils;
import com.sj.yinjiaoyun.shituwang.utils.PhotoUtils;
import com.sj.yinjiaoyun.shituwang.utils.PhotoZoomUtil;
import com.sj.yinjiaoyun.shituwang.utils.PreferencesUtils;
import com.sj.yinjiaoyun.shituwang.utils.ToastUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.xiaopan.android.net.NetworkUtils;
import permissions.dispatcher.NeedsPermission;

/**
 * 上传头像
 */
public class ChangeHeadPictureActivity extends AppCompatActivity {
    public final static String TAG = "ChangeHeadPictureActivity";
    //启动相册的标示
    public final static int REQUEST_CAMERA = 1;

    private final static String ALL_PHOTO = "所有图片";
    //启动相册剪裁的标示
    private static final int RESULT_CAMERA_CROP_PATH_RESULT =0 ;


    private GridView mGridView;
    private Map<String, PhotoFolder> mFolderMap;
    private List<Photo> mPhotoLists = new ArrayList<>();
    private SinglePhotoAdapter mPhotoAdapter;
    private ProgressDialog mProgressDialog;
    private ListView mFolderListView;

    private TextView mPhotoNumTV;
    private TextView mPhotoNameTV;
    private Button mCommitBtn;
    /** 文件夹列表是否处于显示状态 */
    boolean mIsFolderViewShow = false;
    /** 文件夹列表是否被初始化，确保只被初始化一次 */
    boolean mIsFolderViewInit = false;


    private Bitmap bitmap;
    private static final int READ_EXTERNAL_STORAGE=1;
    //获取打开相机的权限
    private static final int OPEN_CAMERA=2;
    //剪切之后的图片uri
    private Uri uritempFile;
    //存储照片的路径
    private File mTmpFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_head_picture);
        initView();
        if (!OtherUtils.isExternalStorageAvailable()) {
            Toast.makeText(this, "No SD card!", Toast.LENGTH_SHORT).show();
            return;
        }

        //在App中需要请求权限才能执行用户的操作
        getPermissionToReadExternalStorage();

    }


    /**
     * 申请读取内存的操作
     */
    private void getPermissionToReadExternalStorage() {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //发起请求获得用户许可,可以在此请求多个权限
                ActivityCompat.requestPermissions(this,new String[] { Manifest.permission.READ_EXTERNAL_STORAGE},
                        READ_EXTERNAL_STORAGE);
            }else {
                getPhotosTask.execute();
            }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
          switch (requestCode){
              case READ_EXTERNAL_STORAGE:
                  if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                      getPhotosTask.execute();
                  }
                  break;
              case OPEN_CAMERA:
                  if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                      showCamera();
                  }
                  break;
          }

            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initView() {
        mCommitBtn = (Button) findViewById(R.id.commit);
        mCommitBtn.setVisibility(View.GONE);
        mGridView = (GridView) findViewById(R.id.photo_gridview);
        mPhotoNumTV = (TextView) findViewById(R.id.photo_num);
        mPhotoNameTV = (TextView) findViewById(R.id.floder_name);
        findViewById(R.id.bottom_tab_bar).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //消费触摸事件，防止触摸底部tab栏也会选中图片
                return true;
            }
        });
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 获取照片的异步任务
     */

    private AsyncTask getPhotosTask = new AsyncTask() {
        @Override
        protected void onPreExecute() {
            mProgressDialog = ProgressDialog.show(ChangeHeadPictureActivity.this, null, "loading...");
        }

        @Override
        protected Object doInBackground(Object[] params) {
            mFolderMap = PhotoUtils.getPhotos(
                    ChangeHeadPictureActivity.this.getApplicationContext());
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            getPhotosSuccess();
        }
    };
    private void getPhotosSuccess() {
        mProgressDialog.dismiss();
        mPhotoLists.addAll(mFolderMap.get(ALL_PHOTO).getPhotoList());

        mPhotoNumTV.setText(OtherUtils.formatResourceString(getApplicationContext(),
                R.string.photos_num, mPhotoLists.size()));

        mPhotoAdapter = new SinglePhotoAdapter(this.getApplicationContext(), mPhotoLists);
        mPhotoAdapter.setIsShowCamera(true);
        mGridView.setAdapter(mPhotoAdapter);
        Set<String> keys = mFolderMap.keySet();
        final List<PhotoFolder> folders = new ArrayList<>();
        for (String key : keys) {
            if (ALL_PHOTO.equals(key)) {
                PhotoFolder folder = mFolderMap.get(key);
                folder.setIsSelected(true);
                folders.add(0, folder);
            } else {
                folders.add(mFolderMap.get(key));
            }
        }
        mPhotoNameTV.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                toggleFolderList(folders);
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if ( position == 0) {
                    getPermissionToOpenCamera();
                    return;
                }else{
                    /**
                     * 跳转剪切界面
                     */
                    Photo p=mPhotoLists.get(position);
                    File file=new File(p.getPath());
                     Uri uri=PhotoZoomUtil.getFileUri(ChangeHeadPictureActivity.this,file);
                    cropImg(uri);
                    return;
                }

            }
        });
    }

    /**
     * 申请相机的权限
     */
    private void getPermissionToOpenCamera(){
            //发起请求获得用户许可,可以在此请求多个权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        OPEN_CAMERA  );
            }else {
                //同意该权限则直接启动相机
                showCamera();
            }

    }
    /**
     * 跳转相册剪切界面
     　＊picPath
     */
    public void cropImg(Uri uri ) {
        try {
            //直接裁剪
            Intent intent = new Intent("com.android.camera.action.CROP");
            //设置裁剪之后的图片路径文件
            File cutfile = new File(Environment.getExternalStorageDirectory().getPath(),
                    "cutcamera.png"); //随便命名一个
            if (cutfile.exists()){ //如果已经存在，则先删除,这里应该是上传到服务器，然后再删除本地的，没服务器，只能这样了
                cutfile.delete();
            }
            cutfile.createNewFile();
            //初始化 uri
            Uri imageUri = uri; //返回来的 uri
            Uri outputUri = null; //真实的 uri

            Logger.d("CutForPhoto: "+cutfile);
            outputUri = Uri.fromFile(cutfile);
            uritempFile = outputUri;
            Logger.d("mCameraUri: "+uritempFile);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            // crop为true是设置在开启的intent中设置显示的view可以剪裁
            intent.putExtra("crop",true);
            // aspectX,aspectY 是宽高的比例，这里设置正方形
            intent.putExtra("aspectX",1);
            intent.putExtra("aspectY",1);
            //设置要裁剪的宽高
            intent.putExtra("outputX", DensityUtils.dp2px(ChangeHeadPictureActivity.this, 200)); //200dp
            intent.putExtra("outputY",DensityUtils.dp2px(ChangeHeadPictureActivity.this, 200));
            intent.putExtra("scale",true);
            //如果图片过大，会导致oom，这里设置为false
            intent.putExtra("return-data",false);
            if (imageUri != null) {
                intent.setDataAndType(imageUri, "image/*");
            }
            if (outputUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
            }
            intent.putExtra("noFaceDetection", true);
            //压缩图片
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            startActivityForResult(intent, RESULT_CAMERA_CROP_PATH_RESULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * 选择相机
     */
    @NeedsPermission(Manifest.permission.CAMERA)
    protected void showCamera() {
        //创建一个file，用来存储拍照后的照片
       mTmpFile = new File(getExternalCacheDir(),"output.png");
        try {
            if (mTmpFile.exists()){
                mTmpFile.delete();//删除
            }
            mTmpFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        Uri imageuri=PhotoZoomUtil.getFileUri(ChangeHeadPictureActivity.this,mTmpFile) ;
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageuri);
        startActivityForResult(intent,REQUEST_CAMERA);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 相机拍照完成后，返回图片路径
        if(requestCode == REQUEST_CAMERA){
            if(resultCode == Activity.RESULT_OK) {
                //设置文件保存路径这里放在跟目录下
                Uri uri=PhotoZoomUtil.getFileUri(ChangeHeadPictureActivity.this,mTmpFile);
                cropImg(uri);

            }
            //相机拍完后或者相册选择后剪裁返回的数据
        }else  if(requestCode==RESULT_CAMERA_CROP_PATH_RESULT && resultCode==Activity.RESULT_OK){
            //获取裁剪后的图片，并显示出来
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                bitmap = bundle.getParcelable("data");
                Logger.d("裁剪之后回去的照片："+bitmap);
                if(bitmap!=null){
                    UploadPhoto(bitmap);
                }else {
                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile));
                        if(bitmap!=null){
                            UploadPhoto(bitmap);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }else {
                try {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile));
                    if(bitmap!=null){
                        UploadPhoto(bitmap);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }


        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    private void UploadPhoto(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //将bitmap一字节流输出 Bitmap.CompressFormat.PNG 压缩格式，100：压缩率，baos：字节流
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] buffer = baos.toByteArray();
        System.out.println("图片的大小："+buffer.length);
        //将图片的字节流数据加密成base64字符输出
        final String photo = Base64.encodeToString(buffer, 0, buffer.length,Base64.DEFAULT).replace("+", "%2B");
        //网络状态判断
        if (!NetworkUtils.isConnectedByState(MyApplication.getContext())) {
            NetToastUtils.showShortToast(MyApplication.getContext(),"网络异常，请稍后再试吧。");
            return;
        }
        HttpClient.postArray1(Api.COMMON_UPLOAD_PHOTO,
                "image=" + photo + "&fileName=" + new Date().getTime() + ".png" + "&mimeType=png&groupType="+String.valueOf(1), new CallBack<StringBuffer>() {
                    @Override
                    public void onSuccess(StringBuffer result) {
                        Logger.d("上传图片返回的URL:"+result.toString());
                        Gson gson=new Gson();
                        PictureReturnBean bean=gson.fromJson(result.toString(),PictureReturnBean.class);
                        if(bean.isSuccess()){
                            Logger.d("上传图片返回的URL："+bean.getData().getUrl());
                            Intent intent=new Intent();
                            intent.putExtra(Const.USERiMG,bean.getData().getUrl());
                            //将图片存放到本地
                            PreferencesUtils.putSharePre(ChangeHeadPictureActivity.this,Const.USERiMG,bean.getData().getUrl());
                           //Activity的回调
                            ChangeHeadPictureActivity.this.setResult(EditPersonInfoActivity.requestCode,intent);
                            ChangeHeadPictureActivity.this.finish();
                        }else{
                            ToastUtil.showShortToast(ChangeHeadPictureActivity.this,bean.getMessage());
                        }
                    }

                });
    }
    /**
     * 显示或者隐藏文件夹列表
     * @param folders
     */
    private void toggleFolderList(final List<PhotoFolder> folders) {
        //初始化文件夹列表
        if(!mIsFolderViewInit) {
            ViewStub folderStub = (ViewStub) findViewById(R.id.floder_stub);
            folderStub.inflate();
            View dimLayout = findViewById(R.id.dim_layout);
            mFolderListView = (ListView) findViewById(R.id.listview_floder);
            final FolderAdapter adapter = new FolderAdapter(this, folders);
            mFolderListView.setAdapter(adapter);
            mFolderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    for (PhotoFolder folder : folders) {
                        folder.setIsSelected(false);
                    }
                    PhotoFolder folder = folders.get(position);
                    folder.setIsSelected(true);
                    adapter.notifyDataSetChanged();

                    mPhotoLists.clear();
                    mPhotoLists.addAll(folder.getPhotoList());
                    //这里重新设置adapter而不是直接notifyDataSetChanged，是让GridView返回顶部
                    mGridView.setAdapter(mPhotoAdapter);
                    mPhotoNumTV.setText(OtherUtils.formatResourceString(getApplicationContext(),
                            R.string.photos_num, mPhotoLists.size()));
                    mPhotoNameTV.setText(folder.getName());
                    toggle();
                }
            });
            dimLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (mIsFolderViewShow) {
                        toggle();
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            initAnimation(dimLayout);
            mIsFolderViewInit = true;
        }
        toggle();
    }
    /**
     * 弹出或者收起文件夹列表
     */
    private void toggle() {
        if(mIsFolderViewShow) {
            outAnimatorSet.start();
            mIsFolderViewShow = false;
        } else {
            inAnimatorSet.start();
            mIsFolderViewShow = true;
        }
    }
    /**
     * 初始化文件夹列表的显示隐藏动画
     */
    AnimatorSet inAnimatorSet = new AnimatorSet();
    AnimatorSet outAnimatorSet = new AnimatorSet();
    private void initAnimation(View dimLayout) {
        ObjectAnimator alphaInAnimator, alphaOutAnimator, transInAnimator, transOutAnimator;
        //获取actionBar的高
        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        /**
         * 这里的高度是，屏幕高度减去上、下tab栏，并且上面留有一个tab栏的高度
         * 所以这里减去3个actionBarHeight的高度
         */
        int height = OtherUtils.getHeightInPx(this) - 3*actionBarHeight;
        alphaInAnimator = ObjectAnimator.ofFloat(dimLayout, "alpha", 0f, 0.7f);
        alphaOutAnimator = ObjectAnimator.ofFloat(dimLayout, "alpha", 0.7f, 0f);
        transInAnimator = ObjectAnimator.ofFloat(mFolderListView, "translationY", height , 0);
        transOutAnimator = ObjectAnimator.ofFloat(mFolderListView, "translationY", 0, height);

        LinearInterpolator linearInterpolator = new LinearInterpolator();

        inAnimatorSet.play(transInAnimator).with(alphaInAnimator);
        inAnimatorSet.setDuration(300);
        inAnimatorSet.setInterpolator(linearInterpolator);
        outAnimatorSet.play(transOutAnimator).with(alphaOutAnimator);
        outAnimatorSet.setDuration(300);
        outAnimatorSet.setInterpolator(linearInterpolator);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //内存泄露检测
        MyApplication.getRefWatcher(this);
    }
}
