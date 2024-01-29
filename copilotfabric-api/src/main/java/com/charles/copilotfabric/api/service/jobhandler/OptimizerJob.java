package com.charles.copilotfabric.api.service.jobhandler;

import com.alibaba.fastjson2.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.exception.NacosException;
import com.charles.copilotfabric.api.configuration.NacosConfiguration;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author charles
 * @date 2024/1/23 23:44
 */
@Component
public class OptimizerJob {
    private static Logger logger = LoggerFactory.getLogger(OptimizerJob.class);

    @Autowired
    private NacosConfiguration nacosConfiguration;

    @XxlJob("fabricBenchmarkJobHandler")
    public void fabricBenchmarkJobHandler() throws Exception {
        String status = sendWithStatus("fabricBenchmark");
        XxlJobHelper.log("fabricBenchmarkJobHandler job " + status);
    }

    @XxlJob("bpnnPredictionJobHandler")
    public void bpnnPredictionJobHandler() throws Exception {
        String status = sendWithStatus("bpnnPrediction");
        XxlJobHelper.log("bpnnPredictionJobHandler job " + status);
    }

    @XxlJob("baselinePredictionJobHandler")
    public void baselinePredictionJobHandler() throws Exception {
        String status = sendWithStatus("baselinePrediction");
        XxlJobHelper.log("baselinePredictionJobHandler job " + status);
    }

    private String sendWithStatus(String task) {
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        String url = "http://192.168.3.11:80/api/optimize/model/" + task;
        if (task.equals("aspsa") || task.equals("moaspsa") || task.equals("mopso")) {
            url += "Optimize";
        }
        String response = client(url, HttpMethod.POST, params);
        Map mapType = JSON.parseObject(response, Map.class);
        return mapType.get("data").toString();
    }

    @XxlJob("aspsaOptimizeJobHandler")
    public void aspsaOptimizeJobHandler() throws Exception {
        XxlJobHelper.log("aspsa job begins");
        modelPushConfig("aspsa");
        XxlJobHelper.log("aspsa job success");
    }

    @XxlJob("moaspsaOptimizeJobHandler")
    public void moaspsaOptimizeJobHandler() throws Exception {
        XxlJobHelper.log("moaspsa job begins");
        modelPushConfig("moaspsa");
        XxlJobHelper.log("moaspsa job success");
    }

    @XxlJob("mopsoOptimizeJobHandler")
    public void mopsoOptimizeJobHandler() throws Exception {
        XxlJobHelper.log("mopso job begins");
        modelPushConfig("mopso");
        XxlJobHelper.log("mopso job success");
    }

    private void modelPushConfig(String algo) throws NacosException {
        String data = sendWithStatus(algo);
        String serverAddr = "192.168.3.39:8848";
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        properties.put(PropertyKeyConst.NAMESPACE, "d8eedad9-8853-419e-8dbc-ef064baf24f5");
        ConfigService configService = NacosFactory.createConfigService(properties);
        String group = "optimize:" + algo;
        configService.publishConfig("test.hyperledger.fabric.config", group, data, ConfigType.YAML.toString());
    }

    public String client(String url, HttpMethod method, MultiValueMap<String, String> params){
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class);
        return response.getBody();
    }
}
