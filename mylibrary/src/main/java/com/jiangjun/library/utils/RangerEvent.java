 package com.jiangjun.library.utils;


import org.greenrobot.eventbus.EventBus;


 /**
  * Created by Administrator姜军 on 2018/3/13.
  */

 public class RangerEvent {
     private static RangerEvent mRangerEvent;
     private EventBus mEventBus;

     public RangerEvent() {
         mEventBus = EventBus.getDefault();
     }

     public static RangerEvent getInstance() {
         return mRangerEvent;
     }

     public static void init() {
         if (mRangerEvent == null) {
             mRangerEvent = new RangerEvent();
         }
     }

     public EventBus getEventBus() {
         return mEventBus;
     }


     //刷新数据
     public static class RefreshData {
         /**
          * 1：登录失效，需要重新登录
          * 2：修改了用户信息
          */
         public String type;


         private RefreshData(String type) {
             this.type = type;

         }

         public static RefreshData obtain(String type) {
             return new RefreshData(type);
         }
     }


     public static class WXPayReturn {
         //type = 0  微信回调
         public String type;

         private WXPayReturn(String type) {
             this.type = type;
         }

         public static WXPayReturn obtain(String type) {
             return new WXPayReturn(type);
         }
     }


 }
