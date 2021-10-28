package com.bjsn909429077.stz.api;

/**
 * Created by jiangjun on 2019/12/30 17:46
 */
public class BaseUrl {
    /**
     * 登录注册协议页面
     */
    public static final String registerAgreementH5 = "api/app/v1/h5/agreement?typeName=";
    /**
     * 发送短信验证码
     */
    public static final String get_sms_code = "api/frame/v1/common/smscode/send";
    /**
     * 校验验证码
     */
    public static final String verification_code = "api/frame/v1/customer/verification/code";
    /**
     * APP端用户手机号，密码登录
     */
    public static final String mobilePwdAppLogin = "api/frame/v1/app/mobile/password/login";
    /**
     * APP验证码登录
     */
    public static final String sms_code_login = "api/frame/v1/app/smscode/login";
    /**
     * 友盟一键登录
     */
    public static final String um_login = "api/frame/v1/app/um/login";
    /**
     * 绑定手机号
     */
    public static final String binding_mobile = "api/frame/v1/customer/binding/mobile";
    /**
     * 注销账号
     */
    public static final String destroy = "api/app/v1/mine/destroy";
    /**
     * 意见反馈
     */
    public static final String feedback_add = "api/frame/v1/feedback/add";
    /**
     *
     * 获取用户信息
     */
    public static final String getUserInfo = "api/app/v1/mine/index";
    /**
     * 修改用户信息
     */
    public static final String setUserInfo = "api/app/v1/mine/edit/info";
     /**
     * 修改密码
     */
    public static final String setPassword = "api/frame/v1/customer/update/password";
     /**
     * 忘记密码：设置新密码
     */
    public static final String setNewPassword = "api/frame/v1/customer/forgetpassword/setnewpassword";
    /**
     * 课程一级分类
     */
    public static final String courseList = "api/app/v1/index/course/type/list";
    /**
     * 首页banner
     */
    public static final String home_page_banner = "/api/app/v1/index/banner/list";
    /**
     * homeBanner
     */
    public static final String homeBanner = "api/app/v1/index/banner/list";
    /**
     * homeReserve
     */
    public static final String homeReserve = "api/app/v1/live/reserve";
    /**
     * wdBanner
     */
    public static final String wdBanner = "api/app/v1/wd/banner/list";
    /**
     * wdType
     */
    public static final String wdType = "api/app/v1/wd/type/list";
    /**
     * wdInfo
     */
    public static final String wdInfo = "api/app/v1/wd/detail/list";
    /**
     * wdCollect
     */
    public static final String wdCollect = "api/app/v1/wd/collect";
    /**
     * upWd 追问
     */
    public static final String zwWd = "api/app/v1/wd/closely/question";
    /**
     * zjInfoList 專家回答列表
     */
    public static final String zjInfoList = "api/app/v1/wd/expert/reply/list";
    /**
     * upWd 发布问题
     */
    public static final String upWd = "api/app/v1/wd/publish/question";
    /**
     * vipList
     */
    public static final String vipList = "api/app/v1/wd/member/item/list";
    /**
     * vipList
     */
    public static final String buyVip = "api/app/v1/wd/open/member";
    /**
     * homeKing
     */
    public static final String homeKing = "api/app/v1/index/course/type/list";
    /**
     * homeLive
     */
    public static final String homeLive = "api/app/v1/index/live/list";
    /**
     * homeRecommend
     */
    public static final String homeRecommend = "api/app/v1/index/course/recommend/type/list";
    /**
     * 课程套餐列表
     */
    public static final String coursePackage = "/api/app/v1/course/package/list";
    /**
     * 课程套餐详情
     */
    public static final String coursePackageInfo = "/api/app/v1/course/package/detail";


    /**
     * homeRecommendType
     */
    public static final String homeRecommendType = "api/app/v1/index/wd/recommend/list";
    /**
     * homeRecommendList
     */
    public static final String homeRecommendList = "api/app/v1/index/course/recommend/list";
    /**
     * wdRecommendList
     */
    public static final String wdRecommendList = "api/app/v1/wd/recommend/list";
    /**
     * wdNewList
     */
    public static final String wdNewList = "api/app/v1/wd/latest/list";
    /**
     * wdZhuanJiaList
     */
    public static final String wdZhuanJiaList = "api/app/v1/wd/expert/list";
    /**
     * orderInfo课程订单
     */
    public static final String orderInfo = "api/app/v1/course/order/get/order";
    /**
     * 直播订单api/app/v1/live/gen/order
     */
    public static final String orderLiveInfo = "api/app/v1/live/gen/order";
    /**
     * 支付直播订单
     */
    public static final String orderLivePay = "api/app/v1/live/order/pay";
    /**
     * 课程订单支付
     */
    public static final String orderPay = "api/app/v1/course/order/pay";


    /**
     * app-支付宝-签名
     */
    public static final String getAliPaySign = "api/app/v1/pay/zfb/sign";

    /**
     * app-微信-签名
     */
    public static final String getWXPaySign = "api/app/v1/pay/wx/sign";


    /**
     * 地址列表api/app/v1/order/address/list
     */
    public static final String addressList = "api/app/v1/order/address/list";
    /**
     * 热门搜索数据
     */
    public static final String hotList = "api/app/v1/index/hot/search/list";
    /**
     * 课程套餐搜索
     */
    public static final String searchList = "api/app/v1/index/course/search/list";
    /**
     * 问答搜索
     */
    public static final String wendaSearchList = "api/app/v1/index/wd/search/list";
    /**
     * 添加地址
     */
    public static final String addAddress = "api/app/v1/order/address/add";
    /**
     * 删除地址
     */
    public static final String deleteAddress = "api/app/v1/order/address/delete";
    /**
     * 修改地址
     */
    public static final String modAddress = "api/app/v1/order/address/edit";
    /**
     * 优惠券列表
     */
    public static final String couponLst = "api/app/v1/mine/coupon/list";

    /**
     * 课堂笔记列表
     */
    public static final String classhour = "/api/app/v1/course/study/coursepackage/course/classhour/note/list";
    /**
     * 讲义列表
     */
    public static final String coursewareList = "/api/app/v1/course/study/coursepackage/course/courseware";
    /**
     * 课程讲义
     */
    public static final String courseware = "/api/app/v1/course/study/coursepackage/course/courseware/list";
    /**
     * 章节列表
     */
    public static final String chaptersList = "/api/app/v1/course/study/coursepackage/course/chapters";
    /**
     * 直播课列表
     */
    public static final String liveList = "/api/app/v1/live/list";
    /**
     * 学习中心首页
     */
    public static final String studyHome = "/api/app/v1/course/study/index";
    /**
     * 已购课程列表
     */
    public static final String hasBuyList = "/api/app/v1/course/study/has/buy";
    /**
     * 我的笔记
     */
    public static final String myNoteList = "/api/app/v1/course/study/coursepackage/mine/notes";
    /**
     * 学习方向
     */
    public static final String studyDirection = "/api/app/v1/course/study/first/types";
    /**
     * 最近浏览
     */
    public static final String recentBrowse = "/api/app/v1/course/study/recent/browse";
    /**
     * 课包列表
     */
    public static final String coursePackageList = "/api/app/v1/course/study/coursepackage/course/list";
    /**
     * 我的课程
     */
    public static final String myCourseList = "/api/app/v1/course/study/mine/coures";
    /**
     * 添加笔记
     */
    public static final String addNote = "/api/app/v1/course/study/coursepackage/course/classhour/note/add";
    /**
     * 修改笔记
     */
    public static final String editNote = "/api/app/v1/course/study/coursepackage/course/classhour/note/edit";
    /**
     * 通过ID查询信息
     */
    public static final String customerMsg = "/api/frame/v1/customer/select/id";
    /**
     * 首页-题库一二级类列表
     */
    public static final String firstOrSecondLists = "/api/app/v1/test_paper/type/firstOrSecondList";
    /**
     * 首页-根据二级类Id进入题库首页
     */
    public static final String questionHome = "/api/app/v1/test_paper/home";
    /**
     * 通过ID查询信息
     */
    public static final String customerSelect = "/api/frame/v1/customer/select/id";
    /**
     * 首页-题库二级类列表
     */
    public static final String SecondLists = "/api/app/v1/test_paper/type/second_list";
    /**
     * 首页-章节/专项练习列表
     */
    public static final String exerciseList = "/api/app/v1/test_paper/exercise/list";
    /**
     * 首页-历年/模拟/考前试卷列表
     */
    public static final String paperList = "/api/app/v1/test_paper/paper/list";
    /**
     * 历年/模拟/考前试卷-获取题
     */
    public static final String subjectList = "/api/app/v1/test_paper/paper/subjectList";
    /**
     * 首页-历年/错题本/章节列表
     */
    public static final String errorBookChapter = "/api/app/v1/error_subject/chapter/list";
    /**
     * 首页-历年/错题本/试卷列表
     */
    public static final String errorBookPaper = "/api/app/v1/error_subject/paper/list";
    /**
     * 首页-历年/错题本/习题收藏列表
     */
    public static final String collectList = "/api/app/v1/subject_collect/collect_list";
    /**
     * 首页-历年/错题本/删除收藏
     */
    public static final String delCollect = "/api/app/v1/subject_collect/deleted_collect";
    /**
     * 首页-历年/做题记录/章节列表
     */
    public static final String recordChapterList = "/api/app/v1/exercise_subject_eecord/chapter/list";
    /**
     * 首页-历年/做题记录/试卷列表
     */
    public static final String recordPaperList = "/api/app/v1/exercise_subject_eecord/paper/list";
    /**
     * 首页—解析
     */
    public static final String subjectAnalysis = "/api/app/v1/subject_collect/subject_analysis";
    /**
     * 首页-章节/专项练习-题解析列表
     */
    public static final String subjectAnalysisList = "/api/app/v1/test_paper/exercise/subject/analysisList";
    /**
     * 历年/模拟/考前试卷-题解析列表
     */
    public static final String testSubjectAnalysisList = "/api/app/v1/test_paper/paper/subject/analysisList";
    /**
     * 题目-添加收藏
     */
    public static final String addCollect = "/api/app/v1/subject_collect/add_collect";
    /**
     * 题目-添加收藏
     */
    public static final String cancelCollect = "/api/app/v1/subject_collect/deleted_collect";
    /**
     * 添加/删除标记
     */
    public static final String addSign = "/api/app/v1/test_paper/add_sign";
    /**
     * 首页-章节/专项练习-答题获取题列表
     */
    public static final String getQuestionBankList = "/api/app/v1/test_paper/exercise/subjectList";
    /**
     * 首页-章节/专项练习-保存答题
     */
    public static final String saveSubject = "/api/app/v1/test_paper/exercise/save_subject";
    /**
     * 历年/模拟/考前试卷-保存答题
     */
    public static final String TestSaveSubject = "/api/app/v1/test_paper/paper/save_subject";
    /**
     * 首页-章节/专项练习-交卷or答题报告
     */
    public static final String AnswerReport = "/api/app/v1/test_paper/exercise/assignment_paper";
    /**
     * 历年/模拟/考前试卷-交卷or答题报告
     */
    public static final String testAnswerReport = "/api/app/v1/test_paper/paper/assignment_paper";
    /**
     * 首页-排行榜
     */
    public static final String rankingList = "/api/app/v1/test_paper/home/ranking_list";
    /**
     * 我的积分
     */
    public static final String point = "/api/app/v1/mine/point/list";
    /**
     * 我要兑换/获取兑换信息
     */
    public static final String exchange = "/api/app/v1/mine/exchange/get/info";
    /**
     * 我要兑换/进行兑换
     */
    public static final String doExchange = "/api/app/v1/mine/exchange/do/exchange";
    /**
     * 问答列表
     */
    public static final String answerList = "/api/app/v1/mine/wd/list";
    /**
     * 我的订单/订单列表
     */
    public static final String AllOrder = "/api/app/v1/mine/order/list";
    /**
     * 我的答疑
     */
    public static final String myAnswerList = "/api/app/v1/course/study/coursepackage/mine/course/dayi/list";
    /**
     * k课程课时答疑
     */
    public static final String daYiList = "/api/app/v1/course/study/coursepackage/course/dayi/list";
    /**
     * 直播回放
     */
    public static final String playback = "/api/app/v1/live/playback/list";
    /**
     * 直播课程:套餐列表
     */
    public static final String liveCoursePackageList = "/api/app/v1/course/package/live/coursepackage/list";
    /**
     * 直播课程:课程列表
     */
    public static final String liveCourseList = "/api/app/v1/course/package/live/course/list";
    /**
     * 直播课程：直播列表
     */
    public static final String liveList2 = "/api/app/v1/course/package/live/list";
    /**
     * 直播课程：课程列表 tab栏
     */
    public static final String tabList = "/api/app/v1/course/package/live/course/type/list";
    /**
     * 报考指南信息
     */
    public static final String guideInfo = "/api/app/v1/index/get/application/guide/info";
    /**
     * 一级分类
     */
    public static final String fnLeiyj = "/api/app/v1/course/type/first/types";
    /**
     * 领取优惠券
     */
    public static final String receiveCoupon = "/api/app/v1/course/package/receive/course/coupon";
    /**
     * 套餐详情H5
     */
    public static final String detail = "/api/app/v1/h5/course/package/detail";
}
