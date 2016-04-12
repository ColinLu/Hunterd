package com.colin.hunter.data;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2016/1/19.
 */
public abstract class Constants {
    // sd地址
    public static final String BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
            + "hunter" + File.separator;
    // 图片保存位置
    public static final String IMAGE_PATH = BASE_PATH + "image";
    // 头像图片保存位置
    public static final String IMAGE_HEAD_PATH = IMAGE_PATH + File.separator + "head";
    // 图片异步任务缓存保存位置
    public static final String IMAGE_LOAD_CACHE_PATH = IMAGE_PATH + File.separator + "cache";

    // 异步任务一次加载数据总数
    public static final int LOAD_DATA_COUNT = 10;
    // 双击退出App时间
    public static final int TIME_EXIT = 2000;

    // 数据库名字
    public static final String SQLITE_NAME = "hunter.db";
    // 数据库版本号
    public static final int SQLITE_VERSION = 1;


    // 列表Fragment：派单
    public static final int FRAGMENT_MAIN_ID_ORDER = 0X00000000;
    // 列表Fragment：人才
    public static final int FRAGMENT_MAIN_ID_CANDIDATE = 0X00000001;
    // 列表Fragment：消息
    public static final int FRAGMENT_MAIN_ID_MESSAGE = 0X00000002;
    // 列表Fragment：我的
    public static final int FRAGMENT_MAIN_ID_MINE = 0X00000003;

    // 派单：可接单
    public static final int FRAGMENT_ORDER_LIST_ID_0 = 0X00000004;
    // 派单：已接单
    public static final int FRAGMENT_ORDER_LIST_ID_1 = 0X00000005;
    // 派单：已结束
    public static final int FRAGMENT_ORDER_LIST_ID_2 = 0X00000006;


    // 派单:详情
    public static final int FRAGMENT_ORDER_ITEM_VIEW = 0X00000007;
    // 派单:反馈记录
    public static final int FRAGMENT_ORDER_ITEM_FEEDBACK = 0X00000008;
    // 派单:已推荐
    public static final int FRAGMENT_ORDER_ITEM_RECOMMEND = 0X00000009;
    // 派单:推荐人才
    public static final int FRAGMENT_ORDER_ITEM_RECOMMEND_STATE = 0X0000000A;

    // 候选人:推荐人才
    public static final int FRAGMENT_CANDIDATE_LIST_ID_0 = 0X0000000B;
    // 候选人:过往人才
    public static final int FRAGMENT_CANDIDATE_LIST_ID_1 = 0X0000000C;


    // 已接单——>已推荐：推荐人才
    public static final int FRAGMENT_CANDIDATE_RECOMMEND_STATE_LIST_ID_0 = 0X0000000D;
    // 已接单——>已推荐：过往人才
    public static final int FRAGMENT_CANDIDATE_RECOMMEND_STATE_LIST_ID_1 = 0X0000000E;

    // 已接单——>推荐人才:推荐人才
    public static final int FRAGMENT_RECOMMEND_CANDIDATE_LIST_ID_0 = 0X0000000F;
    // 已接单——>推荐人才:过往人才
    public static final int FRAGMENT_RECOMMEND_CANDIDATE_LIST_ID_1 = 0X00000011;

    // 候选人Item点击事件:反馈记录
    public static final int FRAGMENT_CANDIDATE_LIST_FEEDBACK_RECORD = 0X00000012;
    // 候选人Item点击事件:访谈记录
    public static final int FRAGMENT_CANDIDATE_LIST_INTERVIEW_RECORD = 0X00000013;
    // 候选人Item点击事件:推荐记录
    public static final int FRAGMENT_CANDIDATE_LIST_RECOMMEND_RECORD = 0X00000014;
    // 候选人Item点击事件:推荐状态
    public static final int FRAGMENT_CANDIDATE_LIST_RECOMMEND_STATE = 0X00000015;
    // 候选人Item点击事件:推荐职位
    public static final int FRAGMENT_CANDIDATE_LIST_RECOMMEND_POSITION = 0X00000016;
    // 候选人Item点击事件:Item原始简历
    public static final int FRAGMENT_CANDIDATE_LIST_VIEW = 0X00000017;
    // 候选人Item点击事件:已推荐职位
    public static final int FRAGMENT_CANDIDATE_LIST_RECOMMEND_POSITION_FINISH = 0X00000018;
    // 候选人Item点击事件:确认推荐
    public static final int FRAGMENT_CANDIDATE_LIST_RECOMMEND_OK = 0X00000019;
    //    推荐列表的Item点击：整个Item
    public static final int RECOMMEND_LIST_ITEM_CLICK_VIEW = 0X0000001A;
    //    推荐列表的Item点击：职位详情
    public static final int RECOMMEND_LIST_ITEM_CLICK_DETAIL = 0X0000001B;
    //    推荐列表的Item点击：确认推荐
    public static final int RECOMMEND_LIST_ITEM_CLICK_RECOMMEND = 0X0000001C;

    //主界面的标签
    public static final String[] TAB_MAIN_FRAGMENT_TITLE = new String[]{
            "派单", "人才", "消息", "我的"
    };
    public static final int[] TAB_MAIN_FRAGMENT_TITLE_ID = new int[]{
            FRAGMENT_MAIN_ID_ORDER,
            FRAGMENT_MAIN_ID_CANDIDATE,
            FRAGMENT_MAIN_ID_MESSAGE,
            FRAGMENT_MAIN_ID_MINE
    };

    //职位管理的标签
    public static final String[] ORDER_TAB_TITLE = new String[]{
            "可接单", "已接单", "已结束"
    };
    public static final int[] ORDER_TAB_TITLE_ID = new int[]{
            FRAGMENT_ORDER_LIST_ID_0,
            FRAGMENT_ORDER_LIST_ID_1,
            FRAGMENT_ORDER_LIST_ID_2
    };

    //候选人管理的标签
    public static final String[] CANDIDATE_TAB_TITLE = new String[]{
            "推荐人才", "过往人才"
    };
    public static final int[] CANDIDATE_TAB_TITLE_ID = new int[]{
            FRAGMENT_CANDIDATE_LIST_ID_0,
            FRAGMENT_CANDIDATE_LIST_ID_1
    };

    //候选人推荐状态管理的标签
    public static final String[] CANDIDATE_RECOMMEND_STATE_TAB_TITLE = new String[]{
            "进程中", "过往人才"
    };
    public static final int[] CANDIDATE_RECOMMEND_STATE_TAB_TITLE_ID = new int[]{
            FRAGMENT_CANDIDATE_RECOMMEND_STATE_LIST_ID_0,
            FRAGMENT_CANDIDATE_RECOMMEND_STATE_LIST_ID_1
    };
    //推荐人才的id数组
    public static final int[] CANDIDATE_RECOMMEND_STATE_ID = new int[]{
            FRAGMENT_RECOMMEND_CANDIDATE_LIST_ID_0,
            FRAGMENT_RECOMMEND_CANDIDATE_LIST_ID_1
    };
    //候选人获取的数据根据id显示不同获取不同类型的数据
    //CandidateRecommendStateActivity界面对某个职业进行推荐：推荐人才
    public static final int ACTIVITY_CANDIDATE_RECOMMMEND_STATE_ID_RECOMMEND = 0X00000000;
    //CandidateRecommendStateActivity界面显示搜索按钮，以及推荐人才
    public static final int ACTIVITY_CANDIDATE_RECOMMMEND_STATE_ID_RECOMMEND_CANDIDATE = 0X00000001;


    //反馈记录界面获取数据：HR：Hunter:Candidate
    //HR
    public static final int FEEDBACK_ID_HR = 0X00000000;
    //Hunter
    public static final int FEEDBACK_ID_HUNTER = 0X00000001;
    //Candidate
    public static final int FEEDBACK_ID_CANDIDATE = 0X00000002;


    //推荐状态  0,全部，1进程中；2简历不合适；3面试不合适；4未过保障期
    public static final int RECOMMEND_STATE_ALL = 0X00000000;
    public static final int RECOMMEND_STATE_ING = 0X00000001;
    public static final int RECOMMEND_STATE_CV_FAIL = 0X00000002;
    public static final int RECOMMEND_STATE_INTERVIEW_FAIL = 0X00000003;
    public static final int RECOMMEND_STATE_FAIL = 0X00000004;

    public static final int[] RECOMMEND_STATE = new int[]{
            RECOMMEND_STATE_ALL,
            RECOMMEND_STATE_ING,
            RECOMMEND_STATE_CV_FAIL,
            RECOMMEND_STATE_INTERVIEW_FAIL,
            RECOMMEND_STATE_FAIL
    };


    //    职位
    //建筑职位
    public static final String[] RELEASE_PROFESSION_STATE = new String[]{
            "放弃推荐", "offer阶段", "已入职", "已过保障期"
    };
    // 获取用户信息修改  姓名
    public static final int EDIT_USER_NAME = 0X00000010;
    // 获取用户信息修改  公司
    public static final int EDIT_USER_COMPANY = 0X00000011;
    // 获取用户信息修改  有效期
    public static final int EDIT_USER_VALIDITY = 0X00000012;
    // 获取用户信息修改  联系电话
    public static final int EDIT_USER_CONTACT = 0X00000013;
    // 获取用户信息修改  邮件
    public static final int EDIT_USER_EMAIL = 0X00000014;

    //初始化城市数据库数据
    public static final String INIT_CITY_SQLITE = "init_city_sqlite";
    //十张图片网址
    public static final String[] image_url = new String[]{
            "http://g.hiphotos.baidu.com/image/h%3D360/sign=87cb5fadab014c08063b2ea33a7a025b/359b033b5bb5c9ea7b21d1e7d739b6003bf3b3e0.jpg",
            "http://c.hiphotos.baidu.com/image/h%3D300/sign=4c2ae377f1246b60640eb474dbf81a35/b90e7bec54e736d1dd0d312c9c504fc2d56269fc.jpg",
            "http://g.hiphotos.baidu.com/image/h%3D300/sign=daf4f50ad51b0ef473e89e5eedc451a1/b151f8198618367aa482c2f929738bd4b31ce58f.jpg",
            "http://f.hiphotos.baidu.com/image/h%3D360/sign=c189db24d01b0ef473e89e58edc451a1/b151f8198618367abfffecd72c738bd4b31ce542.jpg",
            "http://c.hiphotos.baidu.com/image/h%3D360/sign=a0f6c3040ed162d99aee641a21dda950/b7003af33a87e95097d47eac14385343faf2b42b.jpg",
            "http://b.hiphotos.baidu.com/image/h%3D360/sign=4205dfdc014f78f09f0b9cf549330a83/63d0f703918fa0ecb1122585249759ee3c6ddb65.jpg",
            "http://c.hiphotos.baidu.com/image/h%3D360/sign=7474f0b09c510fb367197191e932c893/b999a9014c086e068f2c814200087bf40ad1cb37.jpg",
            "http://g.hiphotos.baidu.com/image/h%3D360/sign=f7f14f1ac45c10383b7ec8c48210931c/2cf5e0fe9925bc3142c14e985cdf8db1cb137015.jpg",
            "http://g.hiphotos.baidu.com/image/h%3D360/sign=e749bf13c45c10383b7ec8c48211931c/2cf5e0fe9925bc315279be915cdf8db1cb137096.jpg",
            "http://h.hiphotos.baidu.com/image/h%3D360/sign=d5eb92930ef431ada3d2453f7b37ac0f/d058ccbf6c81800a7e84fe0fb33533fa828b4749.jpg"
    };
}
