package com.charles.copilotfabric.api.service.jobhandler;

import com.alibaba.fastjson2.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.exception.NacosException;
import com.charles.copilotfabric.api.configuration.NacosConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;
import java.util.Properties;

/**
 * @author charles
 * @date 2024/1/24 20:38
 */
public class OptimizerJobTest {
//    @Autowired
//    private ConfigService nacosConfiguration;

    @Test
    public void connectToNacos() throws Exception {
        String serverAddr = "192.168.3.39:8848";
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        properties.put(PropertyKeyConst.NAMESPACE, "d8eedad9-8853-419e-8dbc-ef064baf24f5");
        ConfigService configService = NacosFactory.createConfigService(properties);
        String config = configService.getConfig("test", "test", 5000);
//        System.out.println(config);
        Yaml yaml = new Yaml();
        Map<String, Object> mapFromYml = yaml.load(config);
        mapFromYml.put("peer_keepalive_minInterval", 323);
        String dump = yaml.dump(mapFromYml);
        configService.publishConfig("test", "test", dump, ConfigType.YAML.toString());

//        General_Authentication_TimeWindow: 22.0
        //General_Keepalive_ServerInterval: 1.0
        //Orderer_BatchSize_AbsoluteMaxBytes: 21.0
        //Orderer_BatchSize_MaxMessageCount: 1.0
        //Orderer_BatchSize_PreferredMaxBytes: 1.0
        //peer_deliveryclient_connTimeout: 27.0
        //peer_deliveryclient_reConnectBackoffThreshold: 2.0
        //peer_discovery_authCacheMaxSize: 12.0
        //peer_discovery_authCachePurgeRetentionRatio: 13.0
        //peer_gossip_aliveTimeInterval: 4.0
        //peer_gossip_dialTimeout: 17.0
        //peer_gossip_election_leaderElectionDuration: 13.0
        //peer_gossip_maxBlockCountToStore: 9.0
        //peer_gossip_publishCertPeriod: 24.0
        //peer_gossip_requestStateInfoInterval: 19.0
        //peer_keepalive_client_timeout: 41.0
        //peer_keepalive_minInterval: 21.0
        //peer_keepalive_minInterval_2: 21.2
    }

    @Test
    public void connect2Flask() throws NacosException {
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        String response = client("http://192.168.3.11:80/api/optimize/model/mopsoOptimize", HttpMethod.POST, params);
        System.out.println(response);
        Map mapType = JSON.parseObject(response, Map.class);
        String data = mapType.get("data").toString();
        String serverAddr = "192.168.3.39:8848";
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        properties.put(PropertyKeyConst.NAMESPACE, "d8eedad9-8853-419e-8dbc-ef064baf24f5");
        ConfigService configService = NacosFactory.createConfigService(properties);
        configService.publishConfig("test.hyperledger.fabric.mopso.config", "optimize:mopso", data, ConfigType.YAML.toString());
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
