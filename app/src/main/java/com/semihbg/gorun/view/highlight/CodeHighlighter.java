package com.semihbg.gorun.view.highlight;

import android.os.Handler;
import android.text.Spannable;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class CodeHighlighter {

    private final List<CodeHighlightContext> codeHighlightContextList;
    private final EditText editText;
    private final Handler handler;

    public CodeHighlighter(EditText editText) {
        this.editText=editText;
        this.codeHighlightContextList = new ArrayList<>();
        handler=new Handler();
    }

    public void update(){
        Spannable spannable=editText.getText();
        String code=spannable.toString();
        handler.post(()->{
            List<CodeHighlightContext> newCodeHighlightContextList=new ArrayList<>();
            for(CodeKeyword codeKeyword:CodeKeyword.values())
                for(int i=code.indexOf(codeKeyword.code);i>-1;i=code.indexOf(codeKeyword.code,i+codeKeyword.code.length()))
                    newCodeHighlightContextList.add(CodeHighlightContext.of(this.editText.getContext(), codeKeyword,i));
            editText.post(()->{
                for(CodeHighlightContext context:codeHighlightContextList)
                    spannable.removeSpan(context.getCharacterStyle());
                codeHighlightContextList.clear();
                codeHighlightContextList.addAll(newCodeHighlightContextList);
                for(CodeHighlightContext context:codeHighlightContextList)
                    spannable.setSpan(context.getCharacterStyle(),context.getStart(),context.getEnd(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            });
        });
    }

}
