package com.yaya.books;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.client.android.BuildConfig;
import com.google.zxing.client.android.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BookDetailActivity extends Activity {
    private static String TAG = "BookDetailActivity";
    private static boolean DBG = BuildConfig.DEBUG;

    private JSONObject mBookJson;

    // http://api.douban.com/v2/book/isbn/978-7-115-21687-8
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.ybk_detail_activity);

        try {
            mBookJson = new JSONObject(getIntent().getStringExtra("YBKD"));

            initView();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            finish();
        }

    }

    private ImageView mBookImg;
    private TextView mTitle;
    private TextView mAuthor;
    private TextView mSubtitle;
    private TextView mTranslator;

    private ImageLoader mImgLoader = ImageLoader.getInstance();

    private void initView() {

        mBookImg = (ImageView) findViewById(R.id.ybk_detail_bigimg);
        mImgLoader.displayImage(mBookJson.optString("image"), mBookImg);

        mAuthor = (TextView) findViewById(R.id.ybk_detail_author);
        mSubtitle = (TextView) findViewById(R.id.ybk_detail_subtitle);
        String subtitle = mBookJson.optString("subtitle");
        if(TextUtils.isEmpty(subtitle)){
            mSubtitle.setVisibility(View.GONE);
        }else{
            mSubtitle.setVisibility(View.VISIBLE);
            mSubtitle.setText("sub: " + subtitle);
        }
        mTranslator = (TextView) findViewById(R.id.ybk_detail_subtitle);
        JSONArray translators = mBookJson.optJSONArray("translator");
        if (translators != null) {
            String translator = "译者：";
            for (int i = 0; i < translators.length(); i++) {
                translator += translators.optString(i) + ",";
            }

            mTranslator.setVisibility(View.VISIBLE);
            mTranslator.setText(translator);
        } else {
            mTranslator.setVisibility(View.GONE);
        }
    }
}
