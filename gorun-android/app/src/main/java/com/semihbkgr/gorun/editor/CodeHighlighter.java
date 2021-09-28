package com.semihbkgr.gorun.editor;

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

            for(Highlight highlight : Highlight.values())
                if(highlight.isOnly())
                    for(String startWord:highlight.startWords)
                        for(int i = code.indexOf(startWord); i>-1; i=code.indexOf(startWord,i+ startWord.length()))
                            newCodeHighlightContextList.add(CodeHighlightContext.of(this.editText.getContext(), highlight,i,i+startWord.length()));
                else{
                    boolean isStarting=true;
                    int index=0;
                    for(String startWord: highlight.startWords)
                        for(String endWord: highlight.endWords)
                            for(int i = code.indexOf(startWord); i>-1; i=code.indexOf(isStarting?startWord :endWord,i+1),
                                    index+=isStarting?startWord.length():endWord.length())
                                if(!isStarting){
                                    newCodeHighlightContextList.add(CodeHighlightContext.of(editText.getContext(), highlight,index,i));
                                    isStarting=true;
                                    if(highlight==Highlight.CUSTOM_FUNCTION){
                                        String functionName=code.substring(index+startWord.length()-1,i);
                                        int functionNameLength=functionName.length();
                                        for(int j=code.indexOf(functionName);j>-1;j=code.indexOf(functionName,j+functionNameLength))
                                            newCodeHighlightContextList.add(CodeHighlightContext.of(editText.getContext(), highlight,j,j+functionNameLength));
                                    }
                                }else{
                                    index=i;
                                    isStarting=false;
                                }
                }

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
