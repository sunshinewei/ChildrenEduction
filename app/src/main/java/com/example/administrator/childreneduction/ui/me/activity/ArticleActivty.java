package com.example.administrator.childreneduction.ui.me.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.bmob.ArticleTable;
import com.example.administrator.childreneduction.model.Content;
import com.example.administrator.childreneduction.model.LoginInfo;
import com.example.administrator.childreneduction.ui.base.EduBaseActivity;
import com.example.administrator.childreneduction.utils.SharePrefernceUtils;
import com.github.mr5.icarus.Callback;
import com.github.mr5.icarus.Icarus;
import com.github.mr5.icarus.TextViewToolbar;
import com.github.mr5.icarus.Toolbar;
import com.github.mr5.icarus.button.Button;
import com.github.mr5.icarus.button.FontScaleButton;
import com.github.mr5.icarus.button.TextViewButton;
import com.github.mr5.icarus.entity.Options;
import com.github.mr5.icarus.popover.FontScalePopoverImpl;
import com.github.mr5.icarus.popover.HtmlPopoverImpl;
import com.github.mr5.icarus.popover.ImagePopoverImpl;
import com.github.mr5.icarus.popover.LinkPopoverImpl;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.HashMap;

import cn.bmob.v3.listener.SaveListener;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Administrator on 2017/5/16.
 * 发表文章
 */

public class ArticleActivty extends EduBaseActivity {

    private TextView mTvActivityArticleBack;
    private TextView mTvActivityArticlePub;
    private EditText mEdtActivityArticleTilte;
    private WebView mWebviewEditor;
    private TextView mButtonBold;
    private TextView mButtonLink;
    private TextView mButtonImage;
    private TextView mButtonListOl;
    private TextView mButtonListUl;
    private TextView mButtonBlockquote;
    private TextView mButtonHr;
    private TextView mButtonAlignLeft;
    private TextView mButtonAlignCenter;
    private TextView mButtonAlignRight;
    private TextView mButtonItalic;
    private TextView mButtonIndent;
    private TextView mButtonOutdent;
    private TextView mButtonMath;
    private TextView mButtonUnderline;
    private TextView mButtonStrikeThrough;
    private TextView mButtonFontScale;
    private TextView mButtonHtml5;


    private Icarus mIcarus;
    private SharePrefernceUtils mPrefernceUtils;
    private Gson mGson;


    public static Intent createInent(Context context){
        return new Intent(context,ArticleActivty.class);
    }

    @Override
    public void initActivityComponent() {

    }

    @Override
    public int setLayout() {
        return R.layout.activity_article;
    }

    @Override
    public void initView() {
        mTvActivityArticleBack = (TextView) findViewById(R.id.tv_activity_article_back);
        mTvActivityArticlePub = (TextView) findViewById(R.id.tv_activity_article_pub);
        mEdtActivityArticleTilte = (EditText) findViewById(R.id.edt_activity_article_tilte);
        mWebviewEditor = (WebView) findViewById(R.id.webview_editor);
        mButtonBold = (TextView) findViewById(R.id.button_bold);
        mButtonLink = (TextView) findViewById(R.id.button_link);
        mButtonImage = (TextView) findViewById(R.id.button_image);
        mButtonListOl = (TextView) findViewById(R.id.button_list_ol);
        mButtonListUl = (TextView) findViewById(R.id.button_list_ul);
        mButtonBlockquote = (TextView) findViewById(R.id.button_blockquote);
        mButtonHr = (TextView) findViewById(R.id.button_hr);
        mButtonAlignLeft = (TextView) findViewById(R.id.button_align_left);
        mButtonAlignCenter = (TextView) findViewById(R.id.button_align_center);
        mButtonAlignRight = (TextView) findViewById(R.id.button_align_right);
        mButtonItalic = (TextView) findViewById(R.id.button_italic);
        mButtonIndent = (TextView) findViewById(R.id.button_indent);
        mButtonOutdent = (TextView) findViewById(R.id.button_outdent);
        mButtonMath = (TextView) findViewById(R.id.button_math);
        mButtonUnderline = (TextView) findViewById(R.id.button_underline);
        mButtonStrikeThrough = (TextView) findViewById(R.id.button_strike_through);
        mButtonFontScale = (TextView) findViewById(R.id.button_font_scale);
        mButtonHtml5 = (TextView) findViewById(R.id.button_html5);

        mPrefernceUtils=new SharePrefernceUtils(this, Content.SP_NAME);
        setListener();
        initData();
    }

    private void setListener(){
        //发表文章
        mTvActivityArticlePub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title = mEdtActivityArticleTilte.getText().toString().trim();
                if (title.length()==0){
                    Toast.makeText(ArticleActivty.this,"文章标题不能为空！",Toast.LENGTH_LONG);
                    return;
                }
                mIcarus.getContent(new Callback() {
                    @Override
                    public void run(final String params) {
                        //
                        Observable.just(params)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<String>() {
                                    @Override
                                    public void accept(@NonNull String s) throws Exception {
                                        System.out.println("params"+params);
                                        if(params.length()==0){
                                            Toast.makeText(ArticleActivty.this,"文章内容不能为空！",Toast.LENGTH_LONG);
                                            return;
                                        }
                                        else {
                                            String string = mPrefernceUtils.getString(Content.SP_NAME);
                                            mGson=new Gson();
                                            LoginInfo loginInfo = mGson.fromJson(string, LoginInfo.class);
                                            ArticleTable articleTable=new ArticleTable();
                                            articleTable.setA_title(title);
                                            articleTable.setA_content(params);
                                            articleTable.setU_id(loginInfo.getId());
                                            articleTable.setA_type("0");
                                            articleTable.setU_name(loginInfo.getName());
                                            saveArticle(articleTable);
                                        }
                                    }
                                });
                    }
                });
            }
        });
    }

    /**
     * 保存文章
     * @param articleTable
     */
    private void saveArticle(ArticleTable articleTable){
        articleTable.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
               Toast.makeText(ArticleActivty.this,"发表成功！",Toast.LENGTH_LONG);
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(ArticleActivty.this,"发表失败！",Toast.LENGTH_LONG);
            }
        });
    }

    private void initData(){
        Options options = new Options();
        options.setPlaceholder("Input something...");
        options.addAllowedAttributes("img", Arrays.asList("data-type", "data-id", "class", "src", "alt", "width", "height", "data-non-image"));
        options.addAllowedAttributes("iframe", Arrays.asList("data-type", "data-id", "class", "src", "width", "height"));
        options.addAllowedAttributes("a", Arrays.asList("data-type", "data-id", "class", "href", "target", "title"));
        TextViewToolbar toolbar = new TextViewToolbar();
//        mIcarus = new Icarus(toolbar,options,mWebviewEditor);
        mIcarus=new Icarus(toolbar,options,mWebviewEditor);
        prepareToolbar(toolbar, mIcarus);
        mIcarus.loadCSS("file:///android_asset/editor.css");
        mIcarus.loadJs("file:///android_asset/test.js");
        mIcarus.render();
    }
    private Toolbar prepareToolbar(TextViewToolbar toolbar, Icarus icarus) {
        Typeface iconfont = Typeface.createFromAsset(getAssets(), "Simditor.ttf");
        HashMap<String, Integer> generalButtons = new HashMap<>();
        generalButtons.put(Button.NAME_BOLD, R.id.button_bold);
        generalButtons.put(Button.NAME_OL, R.id.button_list_ol);
        generalButtons.put(Button.NAME_BLOCKQUOTE, R.id.button_blockquote);
        generalButtons.put(Button.NAME_HR, R.id.button_hr);
        generalButtons.put(Button.NAME_UL, R.id.button_list_ul);
        generalButtons.put(Button.NAME_ALIGN_LEFT, R.id.button_align_left);
        generalButtons.put(Button.NAME_ALIGN_CENTER, R.id.button_align_center);
        generalButtons.put(Button.NAME_ALIGN_RIGHT, R.id.button_align_right);
        generalButtons.put(Button.NAME_ITALIC, R.id.button_italic);
        generalButtons.put(Button.NAME_INDENT, R.id.button_indent);
        generalButtons.put(Button.NAME_OUTDENT, R.id.button_outdent);
        generalButtons.put(Button.NAME_CODE, R.id.button_math);
        generalButtons.put(Button.NAME_UNDERLINE, R.id.button_underline);
        generalButtons.put(Button.NAME_STRIKETHROUGH, R.id.button_strike_through);

        for (String name : generalButtons.keySet()) {
            TextView textView = (TextView) findViewById(generalButtons.get(name));
            if (textView == null) {
                continue;
            }
            textView.setTypeface(iconfont);
            TextViewButton button = new TextViewButton(textView, icarus);
            button.setName(name);
            toolbar.addButton(button);
        }
        TextView linkButtonTextView = (TextView) findViewById(R.id.button_link);
        linkButtonTextView.setTypeface(iconfont);
        TextViewButton linkButton = new TextViewButton(linkButtonTextView, icarus);
        linkButton.setName(Button.NAME_LINK);
        linkButton.setPopover(new LinkPopoverImpl(linkButtonTextView, icarus));
        toolbar.addButton(linkButton);

        TextView imageButtonTextView = (TextView) findViewById(R.id.button_image);
        imageButtonTextView.setTypeface(iconfont);
        TextViewButton imageButton = new TextViewButton(imageButtonTextView, icarus);
        imageButton.setName(Button.NAME_IMAGE);
        imageButton.setPopover(new ImagePopoverImpl(imageButtonTextView, icarus));
        toolbar.addButton(imageButton);

        TextView htmlButtonTextView = (TextView) findViewById(R.id.button_html5);
        htmlButtonTextView.setTypeface(iconfont);
        TextViewButton htmlButton = new TextViewButton(htmlButtonTextView, icarus);
        htmlButton.setName(Button.NAME_HTML);
        htmlButton.setPopover(new HtmlPopoverImpl(htmlButtonTextView, icarus));
        toolbar.addButton(htmlButton);

        TextView fontScaleTextView = (TextView) findViewById(R.id.button_font_scale);
        fontScaleTextView.setTypeface(iconfont);
        TextViewButton fontScaleButton = new FontScaleButton(fontScaleTextView, icarus);
        fontScaleButton.setPopover(new FontScalePopoverImpl(fontScaleTextView, icarus));
        toolbar.addButton(fontScaleButton);
        return toolbar;
    }


    public void onDestroy() {
        mWebviewEditor.destroy();
        super.onDestroy();
    }

}
