package com.peter.hanzibihua.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peter.hanzibihua.App;
import com.peter.hanzibihua.R;
import com.peter.hanzibihua.utils.BihuaParser;
import com.peter.hanzibihua.view.HanzibihuaCurrentView;

import java.util.ArrayList;

/**
 * Created by jiangbin on 16/7/9.
 */
public class ChineseSpellStepAdapter extends RecyclerView.Adapter<ChineseSpellStepAdapter.AdapterViewHolder> {

    private Context mContext;
    private ArrayList<String> mSpells = new ArrayList<>();
    private BihuaParser.Bihua mBihua;

    public ChineseSpellStepAdapter(Context context) {
        this.mContext = context;

    }

    public void setBihua(BihuaParser.Bihua bihua){
        mSpells.clear();
        this.mBihua = bihua;
        String spell = bihua.bihuaStep;
//        String[] array= spell.split("");
        for(int i=0;i<spell.length();i++)
            mSpells.add(""+spell.charAt(i));
        notifyDataSetChanged();
    }

    @Override
    public ChineseSpellStepAdapter.AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ChineseSpellStepAdapter.AdapterViewHolder adapterViewHolder = new AdapterViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_spell_item, parent, false));
        return adapterViewHolder;
    }

    @Override
    public void onBindViewHolder(ChineseSpellStepAdapter.AdapterViewHolder holder, int position) {
        holder.mSpellNameTextView.setText(App.sCharacterAndSpell.get(mSpells.get(position)));
        holder.mHanzibihuaCurrentView.setBihua(this.mBihua);
        holder.mHanzibihuaCurrentView.setCurrentStep(position);
    }

    @Override
    public int getItemCount() {
        return mSpells.size();
    }

    class AdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView mSpellNameTextView;
        public HanzibihuaCurrentView mHanzibihuaCurrentView;

        public AdapterViewHolder(View itemView) {
            super(itemView);
            mSpellNameTextView = (TextView) itemView.findViewById(R.id.spellTextView);
            mHanzibihuaCurrentView = (HanzibihuaCurrentView) itemView.findViewById(R.id.hanzibihuaCurrentView);
        }
    }
}
