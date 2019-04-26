package com.example.mrc.campus_social.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mrc.campus_social.R;
import com.example.mrc.campus_social.adapter.ModeRecyclerViewAdapter;
import com.example.mrc.campus_social.adapter.PostItemRecyclerViewAdapter;
import com.example.mrc.campus_social.entity.TranObject;
import com.example.mrc.campus_social.provider.ModelType;
import com.example.mrc.campus_social.tool.FragmentControl;
import com.example.mrc.campus_social.tool.GlideImageLoader;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommunityFragment extends BaseFragment implements ModeRecyclerViewAdapter.OnItemClickListener ,PostItemRecyclerViewAdapter.OnItemClickListener{

    @BindView(R.id.banner)
    com.youth.banner.Banner mBanner;
    @BindView(R.id.recyclerview_mode)
    RecyclerView mRecyclerViewMode;
    @BindView(R.id.recyclerview_news)
    RecyclerView mRecyclerViewNews;

    private ArrayList<String> mImages;
    private ArrayList<String> mImageTitle;
    private ArrayList<String> mModelTitle;
    private ArrayList<Integer> mModelImageId;
    private ModeRecyclerViewAdapter mModeRecyclerViewAdapter;
    private PostItemRecyclerViewAdapter mPostItemRecyclerViewAdapter;
    private LinearLayoutManager mNewsLayoutManager;
    private GridLayoutManager mModeLayoutManager;


    public static CommunityFragment newInstance(String param1 , Context context) {
        CommunityFragment fragment = new CommunityFragment();
        mContext = context;
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        Log.d("cqx1" ,param1);
        fragment.setArguments(args);
        return fragment;
    }

    public CommunityFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     *
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d("CCC", "onCreateView()");
        View view = inflater.inflate(R.layout.fragment_community, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView();
        initRecycler();
        return view;
    }

    /**
     * Called to do initial creation of a fragment.  This is called after
     * {@link #onAttach(Activity)} and before
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}, but is not called if the fragment
     * instance is retained across Activity re-creation (see {@link #setRetainInstance(boolean)}).
     *
     * <p>Note that this can be called while the fragment's activity is
     * still in the process of being created.  As such, you can not rely
     * on things like the activity's content view hierarchy being initialized
     * at this point.  If you want to do work once the activity itself is
     * created, see {@link #onActivityCreated(Bundle)}.
     *
     * <p>If your app's <code>targetSdkVersion</code> is {@link Build.VERSION_CODES#M}
     * or lower, child fragments being restored from the savedInstanceState are restored after
     * <code>onCreate</code> returns. When targeting {@link Build.VERSION_CODES#N} or
     * above and running on an N or newer platform version
     * they are restored by <code>Fragment.onCreate</code>.</p>
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CCC", "onCreate()");
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to {@link Activity#onResume() Activity.onResume} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onResume() {
        super.onResume();
        FragmentControl.getInstance().setFullScreen(false);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * 注销广播
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 子类直接调用这个方法关闭应用
     */
    @Override
    public void close() {
        super.close();
    }

    @Override
    public void getMessage(TranObject msg) {

    }

    private void initData() {
        //设置图片资源:url或本地资源
        mImages = new ArrayList<>();
        mImages.add("http://www.studyems.com/upnews2015pics/20150414/ems20150414091801577.jpg");
        mImages.add("http://jjc.henu.edu.cn/images/20150109184830347.jpg");
        mImages.add("http://img.zcool.cn/community/0110cf55e2d0a66ac7251df82a4ba1.jpg@1280w_1l_2o_100sh.jpg");
        mImages.add("http://youimg1.c-ctrip.com/target/100c0s000000hp1seF6A1.jpg");
        mImages.add("http://img.mp.itc.cn/upload/20170419/a153f1944d294f9abbeee6f834f02357_th.jpg");
        mImages.add("http://www.iliyu.com/newsattachment/20140706/20140706cyhup3w3.dvr.jpg");
        //设置图片标题:自动对应
        mImageTitle = new ArrayList<>();
        mImageTitle.add("河南大学历史沿革");
        mImageTitle.add("河南大学官网招生网");
        mImageTitle.add("历史上的河南大学实力如何啊?");
        mImageTitle.add("河南大学建设双一流大学");
        mImageTitle.add("河南大学考研论坛");
        mImageTitle.add("《河南大学校歌》");
    }

    private void initView() {
        //设置样式,默认为:Banner.NOT_INDICATOR(不显示指示器和标题)
        //可选样式如下:
        //1. Banner.CIRCLE_INDICATOR    显示圆形指示器
        //2. Banner.NUM_INDICATOR   显示数字指示器
        //3. Banner.NUM_INDICATOR_TITLE 显示数字指示器和标题
        //4. Banner.CIRCLE_INDICATOR_TITLE  显示圆形指示器和标题
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置标题集合（当banner样式有显示title时）
        mBanner.setBannerTitles(mImageTitle);
        //设置轮播样式（没有标题默认为右边,有标题时默认左边）
        //可选样式:
        //Banner.LEFT   指示器居左
        //Banner.CENTER 指示器居中
        //Banner.RIGHT  指示器居右
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //设置是否允许手动滑动轮播图
        mBanner.setViewPagerIsScroll(true);
        //设置是否自动轮播（不设置则默认自动）
        mBanner.isAutoPlay(true);
        //设置轮播图片间隔时间（不设置默认为2000）
        mBanner.setDelayTime(3000);
        //设置图片资源:可选图片网址/资源文件，默认用Glide加载,也可自定义图片的加载框架
        //所有设置参数方法都放在此方法之前执行
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.setImages(mImages)
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        toDetailsPage("id");
                    }
                })
                .start();
    }

    //功能模块的点击事件
    @Override
    public void onItemClick(View itemView, int position) {
        Log.d("KKKKKK", "点击了");
        ModuleListFragment moduleListFragment = ModuleListFragment.newInstance(ModelType.MODEL_STRING.get(position), mContext);
        FragmentControl.getInstance().setFragment(moduleListFragment, true, true);
    }

    //列表的点击事件
    @Override
    public void onItemClick(String tag, View itemView, int position) {
        toDetailsPage(String.valueOf(position));
    }

    private void toDetailsPage(String tag) {
        CommunityItemDetailsFragment communityItemDetailsFragment = CommunityItemDetailsFragment.newInstance(tag ,mContext);
        FragmentControl.getInstance().setFragment(communityItemDetailsFragment, true, true);
    }

    private void  initRecycler() {
        //社区模块设置名称
        mModelTitle = new ArrayList<>();
        mModelTitle.add("最新闻");
        mModelTitle.add("活动组织");
        mModelTitle.add("跳蚤街");
        mModelTitle.add("失物招领");
        mModelTitle.add("表白墙");
        mModelTitle.add("校园周边");
        mModelTitle.add("知识问答");
        mModelTitle.add("其他");

        //社区模块图片id
        mModelImageId = new ArrayList<>();
        mModelImageId.add(R.drawable.ic_newest_message);
        mModelImageId.add(R.drawable.ic_events_organize);
        mModelImageId.add(R.drawable.ic_flea_street);
        mModelImageId.add(R.drawable.ic_lost_and_found);
        mModelImageId.add(R.drawable.ic_confession_wall);
        mModelImageId.add(R.drawable.ic_around);
        mModelImageId.add(R.drawable.ic_answer_and_question);
        mModelImageId.add(R.drawable.ic_other);

        mModeRecyclerViewAdapter = new ModeRecyclerViewAdapter(mContext, mModelImageId, mModelTitle);
        mModeRecyclerViewAdapter.setOnItemClickListener(this);
        mRecyclerViewMode.setAdapter(mModeRecyclerViewAdapter);
        mModeLayoutManager = new GridLayoutManager(mContext, 4, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewMode.setLayoutManager(mModeLayoutManager);


        Map<Integer, Post> news = new HashMap<>();
        Post p1 = new Post();
        p1.setAll(
                "我校2019年春季运动会开幕",
                "巳末谷雨欲弄晴，志义东风逐积云。4月24日上午，我校2019年春季运动会在金明校区志义体育场隆重开幕。河南省教育厅总督学李金川，河南省教育厅体育卫生处副处长高翔，校党委书记卢克平、校长宋纯鹏等全体校领导及各职能部门、院系负责人出席开幕式。7000余人齐聚志义体育场，共赴开幕盛宴。开幕式由校党委常委、副校长许绍康主持。\n" +
                        "\n" +
                        "上午八点三十分，伴随着慷慨激昂的运动员进行曲，庄严肃穆的国旗护卫队、热情洋溢的花束队及校旗队意气风发地走进运动赛场。随后，我校老干部管乐团身着礼服，步履沉稳，奏响经典旋律，谱出动人华章。各院方阵紧随其后，英武飒爽的身姿、清脆嘹亮的口号，我校师生迈着整齐的步伐向领导和观众展示他们蓬勃向上的风采。欧亚国际学院用“1949”“70”“2019”一连串的数字，祝福建国70周年；拉丁舞、石锁表演、啦啦操……体育学院十八般武艺齐上阵，赢来掌声连连，喝彩不断。\n" +
                        "\n" +
                        "宋纯鹏致辞，他表示各个方阵所展现出来的良好形象续写了河大体育文化新的篇章，尤其是老干部管乐团最具特色，最彰显河大精神，对大家是一个极大的精神鼓励。他希望这次盛会可以提高全校师生员工自觉进行体育锻炼的意识，推动学校体育工作改革，推进校园体育文化建设。广大师生要以良好的体育精神激发干事、创业的热情，凝聚学校事业发展合力，汇集“双一流”建设强劲动力。",
                "河南大学",
                15,
                27
        );
        news.put(1, p1);
        Post p2 = new Post();
        p2.setAll(
                "龙子湖校区南大门落成典礼暨向阳基金捐赠仪式举行",
                "2015年4月20日，我校1980级校友、北京校友会会长、居易国际集团董事局主席刘向阳向母校捐资1000万元，用于建设龙子湖校区南大门。此次仪式的举行标志着经过四年的精心打造和匠心打磨，龙子湖校区南大门正式落成使用。\n" +
                        "\n" +
                        "仪式上，校长宋纯鹏，郑东新区管委会党工委副书记马安庄，校友刘向阳分别致辞。校总会计师韩守富介绍出席仪式的领导和嘉宾。校党委常委、副校长、龙子湖校区建设与管理委员会党工委书记、主任许绍康主持仪式。",
                "河南大学新闻与传媒学院",
                25,
                156
        );
        news.put(2, p2);
        Post p3 = new Post();
        p3.setAll(
                "坚定信念 戮力同心 为实现“双一流”建设目标不懈奋斗",
                "一、2018年工作回顾\n" +
                        "\n" +
                        "2018年，是贯彻落实党的十九大精神的开局之年，也是改革开放40周年。一年来，全校认真学习贯彻习近平新时代中国特色社会主义思想，全面加强党的建设，坚持立德树人根本任务，聚焦内涵式发展，持续深化改革，加快推进“双一流”建设，学校事业发展迈上了新台阶。\n" +
                        "\n" +
                        "一是坚持目标导向，一流学科建设成效显著。深入贯彻落实省委、省政府支持“双一流”建设若干意见和学校一流学科建设方案，主动对接相关部门，争取政策支持，完善管理体制，成立学科建设处和一流学科建设专家指导委员会，推动任务落实，完成了一流学科年度建设任务。深化生物学学科体制机制改革，建设生物学一流本科教育，推进人才培养国际化。实施“人才特区”政策，拓展人力资源渠道，高层次人才队伍建设取得新突破。棉花生物学国家重点实验室技术平台建设更加完善，作物逆境适应与改良省部共建国家重点实验室通过省部会商，加快推进建设；依托作物逆境生物学“创新引智基地”和“协同创新中心”，逐步建立国内外学术合作体系；汇聚相关学科资源，促进学科交叉融合，组建现代农业与生物技术研究院、信阳森林生态系统观测研究站、植物代谢组学研究中心等研究平台。实施“一流学科影响力提升计划”，一流学科承担国家级研究任务的能力明显提升，在国际重要学术刊物发表一批标志性研究成果。\n" +
                        "\n" +
                        "二是坚持立德树人根本任务，人才培养能力稳步提升。坚持问题导向，认真查摆存在问题与不足，持续推进整改落实，顺利通过教育部本科教学工作审核评估。学习贯彻全国教育大会和新时代全国高等学校本科教育工作会议精神，召开学校本科教育大会，部署一流本科教育建设，开展本科教育思想大讨论，培养一流本科人才的“框架图”和“路线图”逐步清晰。推进教育教学改革，创新人才培养模式，提高教育教学能力，获批国家级、省级教育教学成果奖10项，16位教授入选教育部高等学校教学指导委员会。推进研究生培养模式改革，加强研究生优质课程建设，强化研究生考试、录取、培养全过程管理，研究生培养能力显著提升。不断加大招生宣传工作力度，生源质量明显提升。加强就业指导与服务，就业率保持总体稳定。",
                "河南大学学生会",
                1,
                2
        );
        news.put(3, p3);
        Post p4 = new Post();
        p4.setAll(
                "七十载建国筑梦 千百代环保先行",
                "恰同学少年，风华正茂。4月22日，正值第50个世界地球日，由河南大学党委学生工作部、共青团河南大学委员会主办，河南财经政法大学、河南科技学院、洛阳师范学院、信阳师范学院、河南大学环境与规划学院共同承办的“球”生之路——4.22地球日系列活动，在金明校区马可广场举行。\n" +
                        "\n" +
                        "本次地球日活动以“七十载建国筑梦 千百代环保先行”为主题，通过“逐绿新时代，筑梦新中国”、“聚高校青年之力，求生态文明长青”等系列活动，带领广大师生了解环保知识，践行绿色理念。",
                "河南大学计算机与信息工程学院",
                503,
                695
        );
        news.put(4, p4);
        Post p5 = new Post();
        p5.setAll(
                "一座小城 一所大学",
                "小城叫开封，大学叫河南大学。\n" +
                        "\n" +
                        "开封不大，却曾经是七朝古都；开封古朴淡定，却曾经是世界上最繁华的城市，她成就了华夏文明的极致。\n" +
                        "\n" +
                        "一千多年前，当时中国唯一的高等学府——大宋王朝的国子监有幸见证这一时刻。 一百多年前，八国联军入侵北京，清王朝将全国的科举考试由北京转至河南贡院。河大这片土地，见证了一千多年科举制度的终结，也开启了新式高等教育。二十世纪初的欧风美雨和辛亥革命胜利的曙光，孕育催生了“河南欧美留学预备学校”，她与清华学堂、南洋公学一起成为当时中国的三大留学基地，她就是河南大学的前身。\n" +
                        "\n" +
                        "河南大学，札根在开封这座幽静古朴的小城里。她北依千年宋代铁塔，塔不是很高，号称“天下第一塔”；她东临明代古城墙，墙不是最长，仅次于南京古城墙；她经历了几度变迁，却依然谱写着灿烂的乐章，这就如同我深爱的河大：古朴而不奢华，低调而有风韵。",
                "王立群",
                664,
                876
        );
        news.put(5, p5);
        Post p6 = new Post();
        p6.setAll(
                "黎夏--地理模拟优化系统模拟器和优化器及其在空间规划中的应用",
                "黎夏，华东师范大学地理科学与规划学院教授、博士生导师，欧亚科学院院士、长江学者特聘教授，国家杰出青年科学基金获得者，国家重点基础研究发展计划首席专家、国家人事部首批新世纪百千万人才工程国家级人选。长期从事地理信息科学(GIS)研究，其较早结合GIS与复杂系统理论开展了中国城市扩张、土地利用变化模拟及其规划应用研究。创建了地理模拟系统的理论和方法，并应用在城市规划和全球土地利用变化模拟中，取得了系统性和创造性的成果。连续入选Elsevier中国高被引学者、多篇代表作位列SCI地理学领域引用前1%。所提出的GeoSOS被列为几大国际著名的土地利用模拟模型之一。",
                "黎夏",
                34,
                484
        );
        news.put(6, p6);
        Post p7 = new Post();
        p7.setAll(
                "吉姆•罗杰斯荣誉教授聘任暨赛艇捐赠仪式举行",
                "铁塔湖畔，擂鼓阵阵，一场盛大的舞狮表演正在进行。4月19日下午，量子基金前合伙人、世界著名投资家、金融学教授吉姆•罗杰斯荣誉教授聘任暨赛艇捐赠仪式在我校明伦校区三观园举行。校党委书记卢克平，校党委常委、副校长、校体委主任许绍康，河南省体育局水上运动管理中心主任付汝诚，校相关职能部门负责人及师生代表出席仪式。仪式由我校国际合作与交流处处长毛立群主持。\n" +
                        "\n" +
                        "仪式伊始，许绍康、付汝诚分别致辞。许绍康对罗杰斯的到来表示热烈的欢迎，并肯定了罗杰斯在推广赛艇运动和文化方面做出的努力。许绍康表示，与中国龙舟一样，赛艇运动是水文化和奋进精神的彰显和传承，赛艇文化所体现的拼搏、合作和进取精神历来被世人礼赞。同时，他表示河南大学将以本次赛艇捐赠为契机，让赛艇文化扎根河南大学，努力打造自己的赛艇队。\n" +
                        "\n" +
                        "付汝诚在讲话中回顾了河南赛艇运动的发展历程。他表示，河南是中国赛艇运动和赛艇文化传播的主要阵地，希望河南大学与罗杰斯共同奠建的国际化合作模式成为一种典范。王自松在致辞中提到，在文化名城开封发展体育文化是工作的重中之重，开封即将完成的“一渠六河”工程将为水运动文化的蓬勃开展奠定坚实的基础，并为我校赛艇运动的发展提供保障。",
                "河大新闻网",
                35,
                87
        );
        news.put(7, p7);


        mPostItemRecyclerViewAdapter = new PostItemRecyclerViewAdapter(mContext,news);
        mPostItemRecyclerViewAdapter.setOnItemClickListener(this);
        mRecyclerViewNews.setAdapter(mPostItemRecyclerViewAdapter);
        mNewsLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewNews.setLayoutManager(mNewsLayoutManager);
    }

    public static class Post{
        String title;
        String subTitle;
        String name;
        int like = 1;
        int comment = 2;

        public void setAll(String title, String subTitle, String name, int like, int comment) {
            this.title = title;
            this.subTitle = subTitle;
            this.name = name;
            this.like = like;
            this.comment = comment;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getLike() {
            return like;
        }

        public void setLike(int like) {
            this.like = like;
        }

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }
    }
}
