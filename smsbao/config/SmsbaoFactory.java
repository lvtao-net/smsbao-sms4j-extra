package com.ruoyi.sms.supplier.smsbao.config;

import com.ruoyi.sms.supplier.smsbao.service.SmsbaoSmsImpl;
import org.dromara.sms4j.provider.factory.AbstractProviderFactory;
import org.springframework.stereotype.Component;

@Component
public class SmsbaoFactory extends AbstractProviderFactory<SmsbaoSmsImpl, SmsbaoConfig> {

    private static final SmsbaoFactory INSTANCE = new SmsbaoFactory();

    public static SmsbaoFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public SmsbaoSmsImpl createSms(SmsbaoConfig smsbaoConfig) {
        return new SmsbaoSmsImpl(smsbaoConfig);
    }

    @Override
    public Class<SmsbaoConfig> getConfigClass() {
        return SmsbaoConfig.class;
    }

    @Override
    public String getSupplier() {
        return SmsbaoConstant.SMSBAO;
    }
}
