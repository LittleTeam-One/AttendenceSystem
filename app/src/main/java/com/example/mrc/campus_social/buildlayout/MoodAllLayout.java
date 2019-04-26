package com.example.mrc.campus_social.buildlayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.example.mrc.campus_social.R;
import com.example.mrc.campus_social.adapter.TalkItemAdapter;
import com.example.mrc.campus_social.entity.TranObject;
import com.example.mrc.campus_social.fragment.CommunityFragment;
import com.example.mrc.campus_social.fragment.CommunityItemDetailsFragment;
import com.example.mrc.campus_social.tool.FragmentControl;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class MoodAllLayout extends BaseFrameLayoutCard implements TalkItemAdapter.OnItemClickListener {

    @BindView(R.id.recyclerview_mood_list)
    RecyclerView mMoodList;

    TalkItemAdapter mTalkItemAdapter;
    LinearLayoutManager mLayoutManager;
    Map<Integer, CommunityFragment.Post> mData = new HashMap<>();

    public MoodAllLayout(@NonNull Context context) {
        this(context, null);
    }

    public MoodAllLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoodAllLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MoodAllLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        LayoutInflater.from(context).inflate(R.layout.card_root_mood_all, this);

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
        CommunityFragment.Post p1 = new CommunityFragment.Post();
        p1.setAll(
                "",
                "古有集龙珠唤神龙之传说，现有集九景唤大美校园之神话，集景挑战赛来啦！带话题#大美校园#发布由学校内花、树、操场、图书馆、教学楼、体育馆、食堂、宿舍楼、林荫道九个场景组成的九图博文",
                "小斌哥",
                24,
                8
        );
        mData.put(4, p1);
        CommunityFragment.Post p2 = new CommunityFragment.Post();
        p2.setAll(
                "",
                "Day56:开封市2、河南大学，百年学府，1912年在古城开封清代贡院旧址诞生，中国最后一次科举考试的地方，校区内可以看到开封铁塔，铁塔素有“天下第一塔”的美称，建于公元1049年，塔高55.88米，八角十三层，因遍体通彻褐色琉璃砖，混似铁铸，称铁塔。",
                "小马哥",
                15,
                7
        );
        mData.put(3, p2);
        CommunityFragment.Post p3 = new CommunityFragment.Post();
        p3.setAll(
                "",
                "一路走来，我不优秀。但我善良不虚伪。我有话直来直去。做事坦坦荡荡。我不聪明。但我肯定不傻。很多事，我都能看明白。只是不想说而已。因为人太聪明了会很累。有时糊涂一些更快乐。",
                "小伟",
                4,
                3
        );
        mData.put(2, p3);
        CommunityFragment.Post p4 = new CommunityFragment.Post();
        p4.setAll(
                "",
                "在拒绝这件事上，越简单越好，明明是别人需求自己帮忙，解释半天变成自己亏欠了别人的感觉，帮得上，想帮就帮，帮不上，就拒绝。人际交往，简单明了有时最恰当，懂得拒绝，活得不纠结！",
                "小新",
                2,
                7
        );
        mData.put(1, p4);
        mTalkItemAdapter = new TalkItemAdapter("id", getContext(), mData);
        mTalkItemAdapter.setOnItemClickListener(this);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mMoodList.setAdapter(mTalkItemAdapter);
        mMoodList.setLayoutManager(mLayoutManager);
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

    @Override
    public void onItemClick(String tag, View itemView, int position) {
        CommunityItemDetailsFragment communityItemDetailsFragment = CommunityItemDetailsFragment.newInstance(tag ,getContext());
        FragmentControl.getInstance().setFragment(communityItemDetailsFragment, true, true);
    }
}
