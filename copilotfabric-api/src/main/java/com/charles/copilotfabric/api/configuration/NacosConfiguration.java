package com.charles.copilotfabric.api.configuration;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author charles
 * @date 2024/1/24 20:31
 */
@Component
public class NacosConfiguration {
    private ConfigService configService;

    public ConfigService getConfigService() {
        return configService;
    }

    public void setConfigService(ConfigService configService) {
        this.configService = configService;
    }

    public NacosConfiguration() {
        try {
            String serverAddr = "192.168.3.39:8848";
            Properties properties = new Properties();
            properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
            properties.put(PropertyKeyConst.NAMESPACE, "d8eedad9-8853-419e-8dbc-ef064baf24f5");
            this.configService = NacosFactory.createConfigService(properties);
        } catch (NacosException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
