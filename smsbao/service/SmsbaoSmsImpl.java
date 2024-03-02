package com.ruoyi.sms.supplier.smsbao.service;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpUtil;
import com.ruoyi.sms.supplier.smsbao.config.SmsbaoConfig;
import com.ruoyi.sms.supplier.smsbao.config.SmsbaoConstant;
import org.dromara.sms4j.api.entity.SmsResponse;
import org.dromara.sms4j.comm.delayedTime.DelayedTime;
import org.dromara.sms4j.comm.exception.SmsBlendException;
import org.dromara.sms4j.comm.utils.SmsUtils;
import org.dromara.sms4j.provider.service.AbstractSmsBlend;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.Executor;

public class SmsbaoSmsImpl extends AbstractSmsBlend<SmsbaoConfig> {
    private int retry = 0;

    public SmsbaoSmsImpl(SmsbaoConfig config, Executor pool, DelayedTime delayedTime) {
        super(config, pool, delayedTime);
    }

    public SmsbaoSmsImpl(SmsbaoConfig config){
        super(config);
    }

    @Override
    public SmsResponse sendMessage(String phone, String message) {
        System.out.println("sendMessage(String phone, String message)");
        String url = getConfig().getRequestUrl();
        StringBuffer httpArg = new StringBuffer();
        httpArg.append("u=").append(getConfig().getAccessKeyId()).append("&");
        httpArg.append("p=").append(DigestUtil.md5Hex(getConfig().getAccessKeySecret())).append("&");
        httpArg.append("m=").append(phone).append("&");
        httpArg.append("c=").append(encodeUrlString(getConfig().getSignature() + message, getConfig().getCharset()));
        SmsResponse smsResponse;
        try {
            smsResponse = getResponse(HttpUtil.get(url + "?" + httpArg.toString()));
        } catch (SmsBlendException e) {
            smsResponse = new SmsResponse();
            smsResponse.setSuccess(false);
            smsResponse.setData(e.getMessage());
        }
        if (smsResponse.isSuccess() || retry == getConfig().getMaxRetries()) {
            retry = 0;
            return smsResponse;
        }
        return requestRetry(phone, message);
    }

    @Override
    public SmsResponse sendMessage(String phone, LinkedHashMap<String, String> messages) {
        System.out.println("sendMessage(String phone, LinkedHashMap<String, String> messages)");
        SmsResponse smsResponse = new SmsResponse();
        smsResponse.setSuccess(false);
        return smsResponse;
    }

    @Override
    public SmsResponse sendMessage(String phone, String templateId, LinkedHashMap<String, String> messages) {
        System.out.println("sendMessage(String phone, String templateId, LinkedHashMap<String, String> messages)");
        SmsResponse smsResponse = new SmsResponse();
        smsResponse.setSuccess(false);
        return smsResponse;
    }

    @Override
    public SmsResponse massTexting(List<String> phones, String message) {
        System.out.println("SmsResponse massTexting(List<String> phones, String message)");
        return sendMessage(SmsUtils.listToString(phones), message);
    }

    @Override
    public SmsResponse massTexting(List<String> phones, String templateId, LinkedHashMap<String, String> messages) {
        System.out.println(" massTexting(List<String> phones, String templateId, LinkedHashMap<String, String> messages)");
        SmsResponse smsResponse = new SmsResponse();
        smsResponse.setSuccess(false);
        return smsResponse;
    }

    @Override
    public String getConfigId() {
        return SmsbaoConstant.SMSBAO;
    }

    @Override
    public String getSupplier() {
        return SmsbaoConstant.SMSBAO;
    }

    private SmsResponse requestRetry(String phone, String message) {
        System.out.println("requestRetry(String phone, String message)");
        http.safeSleep(getConfig().getRetryInterval());
        retry++;
        return sendMessage(phone, message);
    }

    private SmsResponse getResponse(String res) {
        System.out.println("SmsResponse getResponse(String res) ");
        SmsResponse smsResponse = new SmsResponse();
        smsResponse.setSuccess("0".equalsIgnoreCase(res));
        smsResponse.setData(res);
        smsResponse.setConfigId(getConfigId());
        return smsResponse;
    }

    private static String encodeUrlString(String str, String charset) {
        String strret = null;
        if (str == null)
            return str;
        try {
            strret = java.net.URLEncoder.encode(str, charset);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return strret;
    }
}
