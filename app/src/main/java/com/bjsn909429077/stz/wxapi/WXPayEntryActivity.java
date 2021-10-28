package com.bjsn909429077.stz.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bjsn909429077.stz.api.Const;
import com.jiangjun.library.utils.RangerEvent;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Const.WX_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }


    @Override
    public void onResp(BaseResp resp) {

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {//支付成功
                RangerEvent.getInstance().getEventBus().post(RangerEvent.WXPayReturn.obtain("1"));
            } else {
                RangerEvent.getInstance().getEventBus().post(RangerEvent.WXPayReturn.obtain("2"));
            }
            finish();
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }
}
