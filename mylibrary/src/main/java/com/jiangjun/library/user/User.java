package com.jiangjun.library.user;

import java.io.Serializable;


/**
 * Created by LeBron James on 2018/8/27.
 */

public class User implements Serializable {


    private int code;
    private DataDTO data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataDTO implements Serializable{
        private String appWxOpenId;
        private String area;
        private String areaId;
        private int balance;
        private String birthday;
        private String city;
        private String cityId;
        private int couponCount;
        private String courseFirstTypeId;
        private String courseFirstTypeName;
        private String delEtime;
        private String delReason;
        private String dtime;
        private String headPic;
        private String id;
        private String intime;
        private String introduction;
        private int isVip;
        private int isdel;
        private String mobile;
        private int msgCount;
        private String mutualAidNumber;
        private int myPoints;
        private String nickName;
        private String officialAccountWxOpenId;
        private String openId;
        private String password;
        private String profession;
        private String province;
        private String provinceId;
        private String sessionKey;
        private int sex;
        private int studyVideoCount;
        private String testPaperFirstTypeId;
        private int testPaperFirstTypeName;
        private String testPaperSecondTypeId;
        private int testPaperSecondTypeName;
        private int totalDuration;
        private int unReadCount;
        private String uptime;
        private String userName;
        private String vipEndTime;
        private String yqm;
        private String token;

        public String getAppWxOpenId() {
            return appWxOpenId == null ? "" : appWxOpenId;
        }

        public void setAppWxOpenId(String appWxOpenId) {
            this.appWxOpenId = appWxOpenId;
        }

        public String getArea() {
            return area == null ? "" : area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAreaId() {
            return areaId == null ? "" : areaId;
        }

        public void setAreaId(String areaId) {
            this.areaId = areaId;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public String getBirthday() {
            return birthday == null ? "" : birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getCity() {
            return city == null ? "" : city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCityId() {
            return cityId == null ? "" : cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public int getCouponCount() {
            return couponCount;
        }

        public void setCouponCount(int couponCount) {
            this.couponCount = couponCount;
        }

        public String getCourseFirstTypeId() {
            return courseFirstTypeId == null ? "" : courseFirstTypeId;
        }

        public void setCourseFirstTypeId(String courseFirstTypeId) {
            this.courseFirstTypeId = courseFirstTypeId;
        }

        public String getCourseFirstTypeName() {
            return courseFirstTypeName == null ? "" : courseFirstTypeName;
        }

        public void setCourseFirstTypeName(String courseFirstTypeName) {
            this.courseFirstTypeName = courseFirstTypeName;
        }

        public String getDelEtime() {
            return delEtime == null ? "" : delEtime;
        }

        public void setDelEtime(String delEtime) {
            this.delEtime = delEtime;
        }

        public String getDelReason() {
            return delReason == null ? "" : delReason;
        }

        public void setDelReason(String delReason) {
            this.delReason = delReason;
        }

        public String getDtime() {
            return dtime == null ? "" : dtime;
        }

        public void setDtime(String dtime) {
            this.dtime = dtime;
        }

        public String getHeadPic() {
            return headPic == null ? "" : headPic;
        }

        public void setHeadPic(String headPic) {
            this.headPic = headPic;
        }

        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIntime() {
            return intime == null ? "" : intime;
        }

        public void setIntime(String intime) {
            this.intime = intime;
        }

        public String getIntroduction() {
            return introduction == null ? "" : introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public int getIsVip() {
            return isVip;
        }

        public void setIsVip(int isVip) {
            this.isVip = isVip;
        }

        public int getIsdel() {
            return isdel;
        }

        public void setIsdel(int isdel) {
            this.isdel = isdel;
        }

        public String getMobile() {
            return mobile == null ? "" : mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getMsgCount() {
            return msgCount;
        }

        public void setMsgCount(int msgCount) {
            this.msgCount = msgCount;
        }

        public String getMutualAidNumber() {
            return mutualAidNumber == null ? "" : mutualAidNumber;
        }

        public void setMutualAidNumber(String mutualAidNumber) {
            this.mutualAidNumber = mutualAidNumber;
        }

        public int getMyPoints() {
            return myPoints;
        }

        public void setMyPoints(int myPoints) {
            this.myPoints = myPoints;
        }

        public String getNickName() {
            return nickName == null ? "" : nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getOfficialAccountWxOpenId() {
            return officialAccountWxOpenId == null ? "" : officialAccountWxOpenId;
        }

        public void setOfficialAccountWxOpenId(String officialAccountWxOpenId) {
            this.officialAccountWxOpenId = officialAccountWxOpenId;
        }

        public String getOpenId() {
            return openId == null ? "" : openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public String getPassword() {
            return password == null ? "" : password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getProfession() {
            return profession == null ? "" : profession;
        }

        public void setProfession(String profession) {
            this.profession = profession;
        }

        public String getProvince() {
            return province == null ? "" : province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getProvinceId() {
            return provinceId == null ? "" : provinceId;
        }

        public void setProvinceId(String provinceId) {
            this.provinceId = provinceId;
        }

        public String getSessionKey() {
            return sessionKey == null ? "" : sessionKey;
        }

        public void setSessionKey(String sessionKey) {
            this.sessionKey = sessionKey;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getStudyVideoCount() {
            return studyVideoCount;
        }

        public void setStudyVideoCount(int studyVideoCount) {
            this.studyVideoCount = studyVideoCount;
        }

        public String getTestPaperFirstTypeId() {
            return testPaperFirstTypeId == null ? "" : testPaperFirstTypeId;
        }

        public void setTestPaperFirstTypeId(String testPaperFirstTypeId) {
            this.testPaperFirstTypeId = testPaperFirstTypeId;
        }

        public int getTestPaperFirstTypeName() {
            return testPaperFirstTypeName;
        }

        public void setTestPaperFirstTypeName(int testPaperFirstTypeName) {
            this.testPaperFirstTypeName = testPaperFirstTypeName;
        }

        public String getTestPaperSecondTypeId() {
            return testPaperSecondTypeId == null ? "" : testPaperSecondTypeId;
        }

        public void setTestPaperSecondTypeId(String testPaperSecondTypeId) {
            this.testPaperSecondTypeId = testPaperSecondTypeId;
        }

        public int getTestPaperSecondTypeName() {
            return testPaperSecondTypeName;
        }

        public void setTestPaperSecondTypeName(int testPaperSecondTypeName) {
            this.testPaperSecondTypeName = testPaperSecondTypeName;
        }

        public int getTotalDuration() {
            return totalDuration;
        }

        public void setTotalDuration(int totalDuration) {
            this.totalDuration = totalDuration;
        }

        public int getUnReadCount() {
            return unReadCount;
        }

        public void setUnReadCount(int unReadCount) {
            this.unReadCount = unReadCount;
        }

        public String getUptime() {
            return uptime == null ? "" : uptime;
        }

        public void setUptime(String uptime) {
            this.uptime = uptime;
        }

        public String getUserName() {
            return userName == null ? "" : userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getVipEndTime() {
            return vipEndTime == null ? "" : vipEndTime;
        }

        public void setVipEndTime(String vipEndTime) {
            this.vipEndTime = vipEndTime;
        }

        public String getYqm() {
            return yqm == null ? "" : yqm;
        }

        public void setYqm(String yqm) {
            this.yqm = yqm;
        }

        public String getToken() {
            return token == null ? "" : token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
