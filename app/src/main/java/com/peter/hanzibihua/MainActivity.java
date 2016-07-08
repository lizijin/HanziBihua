package com.peter.hanzibihua;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.j256.ormlite.dao.Dao;
import com.peter.hanzibihua.bean.Chinese;
import com.peter.hanzibihua.bean.ChineseAnimation;
import com.peter.hanzibihua.database.ChineseAnimationDatabaseHelper;
import com.peter.hanzibihua.database.ChineseDatabaseHelper;

import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button mQueryButton;
    private EditText mQueryEditText;
    private HanzibihuaView mHanzibihuaView;
    private HanzipinyinAnimateView mHanziAnimateView;
    private ChineseDatabaseHelper mChineseDatabaseHelper;
    private ChineseAnimationDatabaseHelper mChineseAnimationDatabaseHelper;

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
        mChineseDatabaseHelper = ChineseDatabaseHelper.getHelper(this);
        mChineseAnimationDatabaseHelper = ChineseAnimationDatabaseHelper.getHelper(this);
//        new ChineseAnimationDatabaseHelper(this);
//        new ChineseDatabaseHelper(this);

    }

    private void query(String queryText) {
//        new QueryTask().execute(queryText);
        new QueryAnimationDbTask().execute(queryText);
        new QueryDbTask().execute(queryText);
    }


    class QueryAnimationDbTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                String query = params[0];
                String encodeQuery = URLEncoder.encode(query).replace("%", "");
                List<ChineseAnimation> list = mChineseAnimationDatabaseHelper.getChineseAnimationDao().queryForEq("ENCODE", encodeQuery);
                if (list != null && list.size() != 0) {
                    Log.d("jiangbin", list.get(0).animation);
                    return list.get(0).animation;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);
            Log.d("jiangbin", data);
            mHanziAnimateView.setData(data);
        }
    }


    class QueryDbTask extends AsyncTask<String, Void, BihuaParser.Bihua> {
        @Override
        protected BihuaParser.Bihua doInBackground(String... params) {
            String query = params[0];
            String encodeQuery = URLEncoder.encode(query).replace("%", "");
            Dao dao = mChineseDatabaseHelper.getChineseDao();
            if (dao != null) {
                try {
                    List<Chinese> list = dao.queryForEq("ENCODE", encodeQuery);
                    if (list != null && list.size() != 0) {
                        Log.d("jiangbin", list.get(0).points);
                        return BihuaParser.convertFromChinese(list.get(0));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(BihuaParser.Bihua bihua) {
            super.onPostExecute(bihua);
            mHanzibihuaView.setBihua(bihua);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mChineseDatabaseHelper.close();
        this.mChineseAnimationDatabaseHelper.close();
    }
}


//class QueryTask extends AsyncTask<String, Void, BihuaParser.Bihua> {
//    @Override
//    protected BihuaParser.Bihua doInBackground(String... params) {
//        try {
//            String query = params[0];
//            String encodeQuery = URLEncoder.encode(query).replace("%", "");
//            String urlStr = "http://bihua.51240.com/web_system/51240_com_www/system/file/bihua/get_0/?font=" + encodeQuery + "&shi_fou_zi_dong=1&cache_sjs1=15123011";
//
//            URL url = new URL(urlStr);
//            // 得到URLConnection对象
//            URLConnection connection = url.openConnection();
//            InputStream is = connection.getInputStream();
//            byte[] bs = new byte[1024];
//            int len = 0;
//            StringBuffer sb = new StringBuffer();
//            while ((len = is.read(bs)) != -1) {
//                String str = new String(bs, 0, len);
//                sb.append(str);
//            }
//            return BihuaParser.parse(sb.toString());
//        } catch (Exception e) {
//
//        }
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(BihuaParser.Bihua bihua) {
//        super.onPostExecute(bihua);
//        mHanzibihuaView.setBihua(bihua);
//    }
//}

//class QueryAnimationTask extends AsyncTask<String, Void, String> {
//    @Override
//    protected String doInBackground(String... params) {
//        try {
//            String query = params[0];
//            String encodeQuery = URLEncoder.encode(query).replace("%", "");
//            String urlStr = "http://www.chiguome.com/bishun/bishun.aspx?functionid=10002&word=" + encodeQuery;
//
//            URL url = new URL(urlStr);
//            // 得到URLConnection对象
//            URLConnection connection = url.openConnection();
//            InputStream is = connection.getInputStream();
//            byte[] bs = new byte[1024];
//            int len = 0;
//            StringBuffer sb = new StringBuffer();
//            while ((len = is.read(bs)) != -1) {
//                String str = new String(bs, 0, len);
//                sb.append(str);
//            }
//            return sb.toString();
//        } catch (Exception e) {
//
//        }
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(String data) {
//        super.onPostExecute(data);
//        Log.d("jiangbin", data);
//        mHanziAnimateView.setData(data);
//    }
//}