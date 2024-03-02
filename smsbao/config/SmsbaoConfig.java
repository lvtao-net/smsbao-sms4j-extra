package com.ruoyi.sms.supplier.smsbao.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.sms4j.provider.config.BaseConfig;

@Data
@EqualsAndHashCode(callSuper = true)
public class SmsbaoConfig extends BaseConfig {
    //请求地址
    private String requestUrl = "https://api.smsbao.com/sms";
    //编码
    private String charset = "UTF-8";
    //回复接收 通过此接口，我们将为您实时推送最新的用户回复短信。您需要提供一个url地址，接受http get请求。 并在短信宝短信后台中设置好。该接口一次推送1个手机用户回复信息
    //m 发送方的手机号
    //c 用户回复的短信内容,采用UTF-8 URLENCODE
    private String responseUrl = "";

    @Override
    public String getConfigId() {
        return SmsbaoConstant.SMSBAO;
    }

    @Override
    public String getSupplier() {
        return SmsbaoConstant.SMSBAO;
    }
}
