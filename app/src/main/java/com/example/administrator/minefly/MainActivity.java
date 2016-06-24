package com.example.administrator.minefly;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private ImageView mImgeView;
    private static final int REQUEST = 0;
    private static final int REQUEST1 = 1;
    private String filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImgeView = (ImageView) findViewById(R.id.img_photo);
        filePath = Environment.getExternalStorageDirectory().getPath();
        filePath = filePath +"/"+"temp.png";
        Log.d("xin",""+filePath);
    }
    public void doWebView(View view){
        startActivity(new Intent(MainActivity.this,MiniBrowserActivity.class));
        MainActivity.this.finish();
    }
    /**
     * 跳转
     */
    public void doQC(View view){
        startActivity(new Intent(MainActivity.this,QCActivity.class));
        MainActivity.this.finish();
    }
    /**
     * 调用系统的Camera
     */
    public void doMakeOhoto(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST);
    }
    public void doMakeOhoto2(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri photoUri = Uri.fromFile(new File(filePath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
        startActivityForResult(intent,REQUEST1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST){
            if (resultCode == RESULT_OK){
                //获取图片的缩略图
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                mImgeView.setImageBitmap(bitmap);
            }
        }else if(requestCode == REQUEST1){
            if (resultCode == RESULT_OK){
                //获取原图
                FileInputStream inputStream = null;
                try {
                    inputStream = new FileInputStream(filePath);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    mImgeView.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }finally {
                    if (inputStream !=null){
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
