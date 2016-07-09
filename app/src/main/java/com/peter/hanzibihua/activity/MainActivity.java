package com.peter.hanzibihua.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.peter.hanzibihua.R;
import com.peter.hanzibihua.adapter.ChineseSpellStepAdapter;
import com.peter.hanzibihua.bean.Chinese;
import com.peter.hanzibihua.bean.ChineseAnimation;
import com.peter.hanzibihua.database.ChineseAnimationDatabaseHelper;
import com.peter.hanzibihua.database.ChineseDatabaseHelper;
import com.peter.hanzibihua.utils.BihuaParser;
import com.peter.hanzibihua.utils.TtsHelper;
import com.peter.hanzibihua.view.HanzibihuaView;
import com.peter.hanzibihua.view.HanzipinyinAnimateView;

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
    private TtsHelper ttsHelper;

    private TextView mStepTextView;
    private Button mSpeakButton;
    private RecyclerView mRecyclerView;
    private ChineseSpellStepAdapter mChineseSpellStepAdapter;

    private String mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQueryButton = (Button) findViewById(R.id.button_query);
        mQueryEditText = (EditText) findViewById(R.id.edit_text);
        mHanzibihuaView = (HanzibihuaView) findViewById(R.id.bihuaView);
        mHanziAnimateView = (HanzipinyinAnimateView) findViewById(R.id.bihuaAnimateView);
        mStepTextView = (TextView) findViewById(R.id.textView_step);

        mSpeakButton = (Button) findViewById(R.id.speakButton);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        mRecyclerView.setHasFixedSize(true);
        mChineseSpellStepAdapter = new ChineseSpellStepAdapter(this);

        mRecyclerView.setAdapter(mChineseSpellStepAdapter);
        mQueryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String queryText = mQueryEditText.getText().toString();
                if (!TextUtils.isEmpty(queryText)) {
                    query(queryText);
                }
            }
        });
        mSpeakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(mText))
                ttsHelper.speek(mText);
            }
        });
        mChineseDatabaseHelper = ChineseDatabaseHelper.getHelper(this);
        mChineseAnimationDatabaseHelper = ChineseAnimationDatabaseHelper.getHelper(this);
//        new ChineseAnimationDatabaseHelper(this);
//        new ChineseDatabaseHelper(this);
        ttsHelper = new TtsHelper(this);

    }

    private void query(String queryText) {
        mText= queryText;
        new QueryAnimationDbTask().execute(queryText);
        new QueryDbTask().execute(queryText);
//      startActivity(  new Intent(this,TtsDemo.class));
//        ttsHelper.speek(queryText);
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
            mStepTextView.setText("一共" + bihua.bihuaStep.length() + "笔画");
            mChineseSpellStepAdapter.setBihua(bihua);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mChineseDatabaseHelper.close();
        this.mChineseAnimationDatabaseHelper.close();
    }
}


