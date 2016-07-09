package com.peter.hanzibihua;

import android.app.Application;

import com.iflytek.cloud.SpeechUtility;

import java.util.HashMap;

/**
 * Created by jiangbin on 16/7/9.
 */
public class App extends Application {
    public static HashMap<String, String> sCharacterAndSpell = new HashMap<>();

    @Override
    public void onCreate() {

        SpeechUtility.createUtility(App.this, "appid=" + getString(R.string.iflytek_appid));
        super.onCreate();
        initCharacterAndSpell();
    }

    private void initCharacterAndSpell() {

        sCharacterAndSpell.put("a", "横折折撇");
        sCharacterAndSpell.put("b", "竖折/竖弯");
        sCharacterAndSpell.put("c", "横折");
        sCharacterAndSpell.put("d", "点");
        sCharacterAndSpell.put("e", "横撇/横钩");
        sCharacterAndSpell.put("f", "竖");
        sCharacterAndSpell.put("g", "竖钩");
        sCharacterAndSpell.put("h", "竖提");
        sCharacterAndSpell.put("i", "提");
        sCharacterAndSpell.put("j", "横");
        sCharacterAndSpell.put("k", "点");
        sCharacterAndSpell.put("l", "捺");
        sCharacterAndSpell.put("m", "撇点");
        sCharacterAndSpell.put("n", "撇折");
        sCharacterAndSpell.put("o", "横折弯钩/横斜钩");
        sCharacterAndSpell.put("p", "横折提");
        sCharacterAndSpell.put("q", "横折折折");
        sCharacterAndSpell.put("r", "横折钩");
        sCharacterAndSpell.put("s", "撇");
        sCharacterAndSpell.put("t", "弯钩");
        sCharacterAndSpell.put("u", "竖弯钩");
        sCharacterAndSpell.put("v", "横折折/横折弯");
        sCharacterAndSpell.put("w", "横折折折钩/横撇弯钩");
        sCharacterAndSpell.put("x", "竖折撇/竖折折");
        sCharacterAndSpell.put("y", "斜钩");
        sCharacterAndSpell.put("z", "竖折折钩");

    }
}
