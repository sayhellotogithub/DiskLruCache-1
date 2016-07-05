package com.disklrucache.android.activity;

import android.annotation.TargetApi;
import android.disklrucache.com.disklrucache.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.disklrucache.android.diskcache.cachemanager.DiskCacheManager;

import java.io.File;
import java.io.InputStream;

;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private int mPage = 0;
    private StringBuilder mBuilder = new StringBuilder();

    private static final String mUrl = "http://59.173.241.186:8042/LUPDPTEST/LEAP/Download/default/2016/2/25/22c75dd0d434484997ead062adfc4078.png";

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DiskCacheManager.getInstance().open(this);
//        DiskCacheManager.getInstance().open(this, "");
//        DiskCacheManager.getInstance().open(this, 10000000);
//        DiskCacheManager.getInstance().open(this, new File(""), 10000000);
//        DiskCacheManager.getInstance().open(this, "package_name", 1000000);

        //存储一张Drawable
        BitmapDrawable d = (BitmapDrawable) getDrawable(R.mipmap.ic_launcher);
        DiskCacheManager.getInstance().put("KEY", d);
        DiskCacheManager.getInstance().flush();

        final TextView textSize = (TextView) findViewById(R.id.text_size);
        final ImageView mImageView = (ImageView) findViewById(R.id.image_diskcache);
        final TextView textView = (TextView) findViewById(R.id.text_view);
        final TextView textList = (TextView) findViewById(R.id.text_list);

        Button buttonShow = (Button) findViewById(R.id.button_show);
        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputStream bitmap = DiskCacheManager.getInstance().get(mUrl);
                Bitmap bitmap1 = BitmapFactory.decodeStream(bitmap);
                mImageView.setImageBitmap(bitmap1);
                if (bitmap1 == null) {
                    Toast.makeText(MainActivity.this, "please press down agin", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button buttonRemove = (Button) findViewById(R.id.button_remove);
        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                DiskCacheManager.getInstance().remove(mUrl);
                mPage = 0;
                textList.setText("");
                mBuilder.delete(0, mBuilder.length() - 1);
            }
        });

        Button buttonDown = (Button) findViewById(R.id.button_down);
        buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiskCacheManager.getInstance().download(mUrl);
            }
        });

        /**
         * 点击clear之后将会DisklruCache处于close  再次操作会crash
         * */
        Button buttonclear = (Button) findViewById(R.id.button_clear);
        buttonclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiskCacheManager.getInstance().clearCache();
            }
        });

        Button buttonSize = (Button) findViewById(R.id.button_size);
        buttonSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textSize.setText("Cache size : " + DiskCacheManager.getInstance().size());
            }
        });

        Button buttonDir = (Button) findViewById(R.id.button_dir);
        buttonDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isClose = DiskCacheManager.getInstance().isClosed();
                File f = DiskCacheManager.getInstance().getDirectory();
                textView.setText("isClosed：" + isClose + "   cache dir:" + f);
            }
        });

        Button buttonList = (Button) findViewById(R.id.button_list);
        buttonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = DiskCacheManager.getInstance().getAsString(mPage + TAG);
                if (TextUtils.isEmpty(value)) {
                    Toast.makeText(MainActivity.this, "已经是最后一条", Toast.LENGTH_SHORT).show();
                    return;
                }
                mBuilder.append(value + "--");
                textList.setText(mBuilder.toString());
                mPage++;
            }
        });
        add();
    }

    /**
     * 简单模仿list加载过程
     */
    private void add() {
        for (int i = 0; i < 5; i++) {
            DiskCacheManager.getInstance().put(i + TAG, i + "");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DiskCacheManager.getInstance().close();
    }
}
