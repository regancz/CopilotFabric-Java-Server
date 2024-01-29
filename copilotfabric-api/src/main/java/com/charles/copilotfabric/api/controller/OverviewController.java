package com.charles.copilotfabric.api.controller;

import com.charles.copilotfabric.api.utils.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author charles
 * @date 2024/1/22 23:02
 */

@RequestMapping("/overview")
@RestController
public class OverviewController {

    @RequestMapping(value = "/queryAllChannel", method = RequestMethod.GET)
    public Result<List<Map<String, String>>> queryAllChannel() {
        List<Map<String, String>> channelList = new ArrayList<>();
        Map<String, String> channelInfo = new HashMap<>();
        channelInfo.put("name", "mychannel");
        channelInfo.put("domain", "192.168.3.12");
        channelInfo.put("orgCount", "2");
        channelInfo.put("ordererCount", "3");
        channelInfo.put("version", "1.0.0");
        channelInfo.put("createTime", "2024-01-24 10:32:33");
        channelInfo.put("status", "Active");
        channelInfo.put("remarks", "");
        channelList.add(channelInfo);
        return Result.success(channelList);
    }

    @RequestMapping(value = "/queryAllOrg", method = RequestMethod.GET)
    public Result<List<Map<String, String>>> queryAllOrg() {
        List<Map<String, String>> orgs = new ArrayList<>();
        Map<String, String> org1 = new HashMap<>();
        org1.put("name", "org1");
        org1.put("domain", "192.168.3.12");
        org1.put("peerCount", "1");
        org1.put("version", "1.0.0");
        org1.put("status", "Active");
        org1.put("remarks", "");
        orgs.add(org1);
        Map<String, String> org2 = new HashMap<>();
        org2.put("name", "org2");
        org2.put("domain", "192.168.3.12");
        org2.put("peerCount", "1");
        org2.put("version", "1.0.0");
        org2.put("status", "Active");
        org2.put("remarks", "");
        orgs.add(org2);
        return Result.success(orgs);
    }

    @RequestMapping(value = "/queryAllChaincode", method = RequestMethod.GET)
    public Result<List<Map<String, String>>> queryAllChaincode() {
        List<Map<String, String>> ccs = new ArrayList<>();
        Map<String, String> cc1 = new HashMap<>();
        cc1.put("name", "simple");
        cc1.put("domain", "192.168.3.12");
        cc1.put("version", "1.0.0");
        cc1.put("status", "true");
        cc1.put("remark", "");
        ccs.add(cc1);
        Map<String, String> cc2 = new HashMap<>();
        cc2.put("name", "smallbank");
        cc2.put("domain", "192.168.3.12");
        cc2.put("version", "1.0.0");
        cc2.put("status", "Active");
        cc2.put("remark", "");
        ccs.add(cc2);
        return Result.success(ccs);
    }

}
