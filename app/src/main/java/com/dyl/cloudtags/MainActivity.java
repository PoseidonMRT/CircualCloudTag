package com.dyl.cloudtags;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.dyl.cloudtags.utils.GlobalValues;
import com.dyl.cloudtags.view.CircleView;
import com.dyl.cloudtags.view.KeywordsFlow;

public class MainActivity extends Activity {

	private KeywordsFlow keywordsFlow;
	private String[] keywords;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initData();
	}

	private void initView() {
		keywordsFlow = (KeywordsFlow) findViewById(R.id.keywordsflow);
	}

	public void initData(){
		keywords = new String[] { "语文", "数学", "英语英语英语", "化学", "数学", "英语", "化学", "生物", "历史" };
		feedKeywordsFlow(keywordsFlow, keywords);
		keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);
		keywordsFlow.setDuration(800l);
		keywordsFlow.setOnItemClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (keywordsFlow.isScaleClick()){
					keywordsFlow.restore();
				}
				if (((CircleView)v).mCurrentState.equals(GlobalValues.CircleViewDragComplete)){
					String keyword = ((CircleView) v).getText().toString();
					keywords = null;
					keywords = new String[]{keyword,"Hello","Hello","Heloo"};
					keywordsFlow.rubKeywords();
					keywordsFlow.rubAllViews();
					feedKeywordsFlow(keywordsFlow, keywords);
					keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);
				}
			}
		});
	}

	private static void feedKeywordsFlow(KeywordsFlow keywordsFlow, String[] arr) {
		for (int i=0;i<arr.length;i++){
			keywordsFlow.feedKeyword(arr[i]);
		}
	}
}
