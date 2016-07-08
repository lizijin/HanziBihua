package com.peter.hanzibihua;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    private Button mQueryButton;
    private EditText mQueryEditText;
    private HanzibihuaView mHanzibihuaView;
    private HanzipinyinAnimateView mHanziAnimateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQueryButton = (Button) findViewById(R.id.button_query);
        mQueryEditText = (EditText) findViewById(R.id.edit_text);
        mHanzibihuaView = (HanzibihuaView) findViewById(R.id.bihuaView);
        mHanziAnimateView = (HanzipinyinAnimateView) findViewById(R.id.bihuaAnimateView);
        mQueryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String queryText = mQueryEditText.getText().toString();
                if (!TextUtils.isEmpty(queryText)) {
                    query(queryText);
                }
            }
        });
    }

    private void query(String queryText) {
        new QueryTask().execute(queryText);
        new QueryAnimationTask().execute(queryText);
    }

    class QueryTask extends AsyncTask<String, Void, BihuaParser.Bihua> {
        @Override
        protected BihuaParser.Bihua doInBackground(String... params) {
            try {
                String query = params[0];
                String encodeQuery = URLEncoder.encode(query).replace("%", "");
                String urlStr = "http://bihua.51240.com/web_system/51240_com_www/system/file/bihua/get_0/?font=" + encodeQuery + "&shi_fou_zi_dong=1&cache_sjs1=15123011";

                URL url = new URL(urlStr);
                // 得到URLConnection对象
                URLConnection connection = url.openConnection();
                InputStream is = connection.getInputStream();
                byte[] bs = new byte[1024];
                int len = 0;
                StringBuffer sb = new StringBuffer();
                while ((len = is.read(bs)) != -1) {
                    String str = new String(bs, 0, len);
                    sb.append(str);
                }
                return BihuaParser.parse(sb.toString());
            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(BihuaParser.Bihua bihua) {
            super.onPostExecute(bihua);
            mHanzibihuaView.setBihua(bihua);
        }
    }

    class QueryAnimationTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                String query = params[0];
                String encodeQuery = URLEncoder.encode(query).replace("%", "");
                String urlStr = "http://www.chiguome.com/bishun/bishun.aspx?functionid=10002&word=" + encodeQuery;

                URL url = new URL(urlStr);
                // 得到URLConnection对象
                URLConnection connection = url.openConnection();
                InputStream is = connection.getInputStream();
                byte[] bs = new byte[1024];
                int len = 0;
                StringBuffer sb = new StringBuffer();
                while ((len = is.read(bs)) != -1) {
                    String str = new String(bs, 0, len);
                    sb.append(str);
                }
                return sb.toString();
            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);
            Log.d("jiangbin",data);
            mHanziAnimateView.setData(data);
        }
    }
}
