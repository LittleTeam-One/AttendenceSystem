package com.example.mrc.campus_social.buildlayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.mrc.campus_social.R;
import com.example.mrc.campus_social.adapter.CommentItemAdapter;
import com.example.mrc.campus_social.entity.TranObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class CommentLayout extends BaseFrameLayoutCard {

    @BindView(R.id.content)
    RecyclerView mRecyclerViewContent;

    @BindView(R.id.title)
    TextView mTitle;

    LinearLayoutManager mLLayoutManager;
    CommentItemAdapter mCommentItemAdapter;
    Map<Integer, Comment> mData = new HashMap<>();


    public CommentLayout(@NonNull Context context) {
        this(context, null);
    }

    public CommentLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommentLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CommentLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        LayoutInflater.from(context).inflate(R.layout.card_root_comment, this);
    }

    /**
     * 子类直接调用这个方法关闭应用
     */
    @Override
    public void close() {
        super.close();
    }

    /**
     * Finalize inflating a view from XML.  This is called as the last phase
     * of inflation, after all child views have been added.
     *
     * <p>Even if the subclass overrides onFinishInflate, they should always be
     * sure to call the super method, so that we get called.
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public void changeView(Object object) {
        super.changeView(object);
    }

    @Override
    public View getView() {
        return super.getView();
    }

    @Override
    public void initView() {
        super.initView();
        mData.clear();
        Comment c0 = new Comment();
        c0.setAll("小明"
                , ""
                , "河南大学建设双一流大学取得成效");
        mData.put(1, c0);
        Comment c1 = new Comment();
        c1.setAll("小红"
                , "小明"
                , "那当然！办事效率超快的");
        mData.put(2, c1);
        Comment c2 = new Comment();
        c2.setAll("小黄"
                , ""
                , "恭喜恭喜啊");
        mData.put(3, c2);
        Comment c3 = new Comment();
        c3.setAll("小新"
                , ""
                , "真的是。。。。超级厉害！");
        mData.put(4, c3);
        Comment c4 = new Comment();
        c4.setAll("小伟"
                , "小新"
                , "同感");
        mData.put(5, c4);
        mLLayoutManager = new LinearLayoutManager(getContext());
        mCommentItemAdapter = new CommentItemAdapter("id", getContext(), mData);
        mRecyclerViewContent.setAdapter(mCommentItemAdapter);
        mRecyclerViewContent.setLayoutManager(mLLayoutManager);
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void getMessage(TranObject msg) {

    }

    public static class Comment {
        public String fromName;
        public String toName;
        public String content;

        public void setAll(String fromName, String toName, String content) {
            this.fromName = fromName;
            this.toName = toName;
            this.content = content;
        }

        public String getFromName() {
            return fromName;
        }

        public void setFromName(String fromName) {
            this.fromName = fromName;
        }

        public String getToName() {
            return toName;
        }

        public void setToName(String toName) {
            this.toName = toName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
