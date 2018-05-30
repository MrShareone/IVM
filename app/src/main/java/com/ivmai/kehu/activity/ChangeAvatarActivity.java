package com.ivmai.kehu.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ivmai.kehu.R;
import com.ivmai.kehu.fragment.MySettingFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChangeAvatarActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PHOTO = 0x1;
    private static final int REQUEST_CODE_CAMERA = 0x2;

    private static final int MY_PERMISSION_REQUEST_CODE = 10000;

    Bitmap mBitmap;
    private Uri fileUrl;


    private  CircleImageView user_avatar;
    private  TextView title_text;
    private  Button  change_but;

    public void binview(){
        title_text=(TextView)findViewById(R.id.title_name);
        change_but=(Button)findViewById(R.id.change_avatar_button);
        user_avatar=(CircleImageView)findViewById(R.id.user_avatar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_avatar);
        binview();
        title_text.setText("修改头像");
//        String path=Environment.getExternalStorageDirectory()+"/w65/avatar_bitmap"+ "my_avatar.jpg";
//        if (getDiskBitmap(path)!=null){
//            user_avatar.setImageBitmap(getDiskBitmap(path));
//        }else {
//            Toast.makeText(ChangeAvatarActivity.this, "暂时没有照片", Toast.LENGTH_LONG).show();
//        }

        path=Environment.getExternalStorageDirectory().getPath();
        //showToast(path);
        mBitmap = BitmapFactory.decodeFile(path + "/user_head.jpg");// 从sdcard中获取本地图片,通过BitmapFactory解码,转成bitmap
        if (mBitmap != null) {
            @SuppressWarnings("deprecation")
            RoundedBitmapDrawable circularBitmapDrawable =
                    RoundedBitmapDrawableFactory.create(getResources(), mBitmap);
            circularBitmapDrawable.setCornerRadius(35);
            user_avatar.setImageDrawable(circularBitmapDrawable);

        } else {
            /** 从服务器取,同时保存在本地 ,后续的工作 */
        }

        change_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(intent, 1);
                checkPessmission();
            }
        });
    }

//Activity回调


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case REQUEST_CODE_PHOTO:
                if (resultCode == RESULT_OK) {
                    shearPhoto(data.getData());// 剪切图片
                }
                break;
            case REQUEST_CODE_CAMERA:
                if (resultCode == RESULT_OK) {
                    File fileTemp = new File(
                            Environment.getExternalStorageDirectory() + "/user_head.jpg");
                    shearPhoto(Uri.fromFile(fileTemp));// 剪切图片
                }
                break;
            case 3:
                if (data != null) {
                    try{
                        mBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(fileUrl));

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if (mBitmap != null) {
                        /** 上传到服务器 待--- */
                        setPictureToSD(mBitmap);// 保存本地
                        user_avatar.setImageBitmap(mBitmap);// 显示
                        Message msg = new Message();
                        msg.what=999;
                        MySettingFragment.handler.sendMessage(msg);
                    }
                }
                break;
        }

    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (requestCode == 1 && data != null){
//            if (resultCode == Activity.RESULT_OK) {
//                Uri uri = data.getData();
//                Bitmap bitmap=creatbitmap(uri);
//                log.d( "onActivityResult:","true");
//                try {
//                    saveFile(bitmap);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                user_avatar.setImageBitmap(bitmap);
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 30,bos);

//                try {
//                    String[] proj = {MediaStore.Images.Media.DATA};
//                    Cursor cur = getContentResolver().query(uri, proj, null, null, null);
////                    int count = cur.getCount();
////                    int cls= cur.getColumnCount();
////                    System.out.println("Hello" + cur.getColumnName(0));
////                    log.d("xxxxx" , cur.getColumnName(0));
////                    int kk = cur.getType(0);
//                    cur.moveToFirst();
//                    int actual_image_column_index = cur.getColumnIndexOrThrow(proj[0]);
//                    img_path = cur.getString(actual_image_column_index);
//                    System.out.println("XXXXXXXXXXXXX:"  + img_path );
////                    try {
////                        saveFile(bitmap,"111.jpg");
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
////                     img_path = cur.getString(0);
//
//                    File file = new File(img_path);
//                    Toast.makeText(MainActivity.this, file.toString(), Toast.LENGTH_SHORT).show();
//                }catch (Exception e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

 //图片裁剪
    protected Bitmap creatbitmap(Uri uri){
        ContentResolver cr=this.getContentResolver();
        try{
            Bitmap bitmap1= BitmapFactory.decodeStream(cr.openInputStream(uri));
            if(bitmap1.getHeight()<=400&&bitmap1.getWidth()<=400){
                return bitmap1;
            }
            if(bitmap1.getWidth()>=bitmap1.getHeight()){
                int x=bitmap1.getWidth()-bitmap1.getHeight();
                Bitmap bitmap2 = Bitmap.createBitmap(bitmap1, x/2, 0, bitmap1.getHeight(),
                        bitmap1.getHeight());
                Bitmap bitmap=Bitmap.createScaledBitmap(bitmap2, 400,400, true);
                return bitmap;
            }else if (true){
                int y=bitmap1.getHeight()-bitmap1.getWidth();
                Bitmap bitmap2 = Bitmap.createBitmap(bitmap1, 0, y/2, bitmap1.getWidth(),
                        bitmap1.getWidth());
                Bitmap bitmap=Bitmap.createScaledBitmap(bitmap2, 400, 400, true);
                return  bitmap;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public File saveFile(Bitmap bm) throws IOException {
//        String path = Environment.getExternalStorageDirectory().toString()+"/w65/avatar_bitmap/";
//        log.d("path====", path);
//        File dirFile = new File(path);
//        if(!dirFile.exists()){
//            dirFile.mkdirs();
//        }
//        File myAvatarFile= new File(path + "my_avatar.jpg");
//        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myAvatarFile));
//        bm.compress(Bitmap.CompressFormat.JPEG, 30, bos);
//        bos.flush();
//        bos.close();
//        return myAvatarFile;
//    }

//    public Bitmap getDiskBitmap(String pathString)
//    {
//        Bitmap bitmap = null;
//        try
//        {
//            File file = new File(pathString);
//            if(file.exists())
//            {
//                bitmap = BitmapFactory.decodeFile(pathString);
//            }
//        } catch (Exception e)
//        {
//
//        }
//        return bitmap;
//    }



    private void selectImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择图片");
        builder.setItems(new String[] {"拍照","相册"},new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    openCamra();//调用具体方法
                } else if (item == 1) {

                    Intent intent_photo = new Intent(Intent.ACTION_PICK, null);
                    intent_photo.setDataAndType(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            "image/*");
                    startActivityForResult(intent_photo, REQUEST_CODE_PHOTO);
                }
            }
        });
        builder.show();
    }


    private void openCamra(){
        Intent intent_pat = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent_pat.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                .fromFile(new File(Environment
                        .getExternalStorageDirectory(), "user_head.jpg")));
        startActivityForResult(intent_pat, REQUEST_CODE_CAMERA);
    }




    private String path =  "/sdcard/headPhoto";// 存放本地的头像照片

    private void setPictureToSD(Bitmap bitmap) {

        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {// 检测SD卡的可用性
            return;
        }
        FileOutputStream fos = null;

        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + "/user_head.jpg";// 图片名字
        try {
            fos = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);// 压缩后写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭输出流
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void shearPhoto(Uri uri) {

        Bitmap bmp = null;
        String str = "";
        try{
            bmp = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            str = MediaStore.Images.Media.insertImage(getContentResolver(),bmp,"","");
        }catch (Exception e){
            e.printStackTrace();
        }
        fileUrl = Uri.parse(str);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(fileUrl, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX",0);
        intent.putExtra("aspectY", 0);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX",200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", false);
        intent.putExtra("output",fileUrl);
        intent.putExtra("outputFormat",Bitmap.CompressFormat.JPEG.toString());
        //剪切后直接上传图片
        startActivityForResult(intent, 3);
    }

    private void checkPessmission(){
        boolean isAllGranted = checkPermissionAllGranted(
                new String[] {
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }
        );

        // 如果这3个权限全都拥有, 则直接执行备份代码
        if (isAllGranted) {
            selectImage();
            return;
        }
        ActivityCompat.requestPermissions(
                ChangeAvatarActivity.this,
                new String[] {
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                MY_PERMISSION_REQUEST_CODE
        );
    }


    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(ChangeAvatarActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }

    /**
     * 第 3 步: 申请权限结果返回处理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            boolean isAllGranted = true;

            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if (isAllGranted) {
                // 如果所有的权限都授予了, 则执行备份代码
                selectImage();

            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                openAppDetails();
            }
        }
    }

    /**
     * 打开 APP 的详情设置
     */
    private void openAppDetails() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("上传图片需要访问 “摄像头” 和 “外部存储器”，请到 “应用信息 -> 权限” 中授予！");
        builder.setPositiveButton("去手动授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }



}
