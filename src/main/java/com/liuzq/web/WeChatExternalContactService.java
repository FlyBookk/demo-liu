package com.liuzq.web;

import com.alibaba.fastjson2.JSON;
import com.beust.jcommander.internal.Lists;
import com.liuzq.entity.WeChatExternalContactResponse;
import com.liuzq.utils.CsvUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class WeChatExternalContactService {

    private RestTemplate restTemplate=new RestTemplate();

    private static final String ACCESS_TOKEN = "zPLZmV-BOzjOtCUzKr6CBE1Da2M4gy03S5D14eVk8K8lZRSIkU_6z15O_n4KxK1jnRGwdtrTpKtCFUdpNP9S3gFEjboEHYVSCE6TFfKxLUl_tIJytGnYW8lqF2aBfbL5IgT1_FDTcukvNqnNTsojZjUjrR9apq1j68tdVaqbdPRI96FVYGuog3_sTnvfgtlvtiJ-JzQInIT5GASTtO1lEg";
    private static final String API_URL = "https://qyapi.weixin.qq.com/cgi-bin/externalcontact/batch/get_by_user?access_token=" + ACCESS_TOKEN;

    public WeChatExternalContactResponse getExternalContactsByUserId(String useridList, String cursor, int limit) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userid_list", new String[]{useridList});
        requestBody.put("cursor", cursor);
        requestBody.put("limit", limit);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        List<List<String>> result = Lists.newArrayList();
        result.add(List.of("name", "unionID", "externalUserId"));
        try {
            ResponseEntity<WeChatExternalContactResponse> response = restTemplate.postForEntity(API_URL, request, WeChatExternalContactResponse.class);
            log.info("QW获取外部联系人信息：{}", JSON.toJSONString(response));
            WeChatExternalContactResponse body = response.getBody();
            List<WeChatExternalContactResponse.ExternalContactInfo> externalContactList = body.getExternalContactList();
            if (!CollectionUtils.isEmpty(externalContactList)) {
                externalContactList.stream().forEach(externalContactInfo -> {
                    WeChatExternalContactResponse.ExternalContactInfo.ExternalContact externalContact = externalContactInfo.getExternalContact();
                    log.info("外部联系人信息：{}", JSON.toJSONString(externalContact));
                    result.add(List.of(externalContact.getName(), StringUtils.isNotEmpty(externalContact.getUnionid()) ? externalContact.getUnionid() : "--", externalContact.getExternalUserid()));
                });
            }

            String nextCursor = body.getNextCursor();
            while (StringUtils.isNotEmpty(nextCursor)) {
                Map<String, Object> requestBodyNew = new HashMap<>();
                requestBodyNew.put("userid_list", new String[]{useridList});
                requestBodyNew.put("cursor", nextCursor);
                requestBodyNew.put("limit", limit);
                ResponseEntity<WeChatExternalContactResponse> responseCur = restTemplate.postForEntity(API_URL, new HttpEntity<>(requestBodyNew, headers), WeChatExternalContactResponse.class);
                log.info("QW获取外部联系人信息：{}", JSON.toJSONString(responseCur));
                WeChatExternalContactResponse curBody = responseCur.getBody();
                nextCursor = curBody.getNextCursor();
                List<WeChatExternalContactResponse.ExternalContactInfo> externalContactListCur = curBody.getExternalContactList();
                if (!CollectionUtils.isEmpty(externalContactListCur)) {
                    externalContactListCur.stream().forEach(externalContactInfo -> {
                        WeChatExternalContactResponse.ExternalContactInfo.ExternalContact externalContact = externalContactInfo.getExternalContact();
                        log.info("外部联系人信息：{}", JSON.toJSONString(externalContact));
                        result.add(List.of(externalContact.getName(), StringUtils.isNotEmpty(externalContact.getUnionid()) ? externalContact.getUnionid() : "--", externalContact.getExternalUserid()));
                    });
                }
            }

            if (!CollectionUtils.isEmpty(result)) {
                String outputFilePath = "new_output.csv";
                try {
                    // 写入新的CSV文件
                    CsvUtil.writeNewCsv(outputFilePath, result);
                    System.out.println("新的CSV文件已生成并写入数据: " + outputFilePath);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return body;
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test() {
        restTemplate = new RestTemplate();
        WeChatExternalContactResponse amei = getExternalContactsByUserId("amei", "", 100);
    }

    public static void main(String[] args) {
        String csvRFile = "/Users/wanhua/Documents/qw.csv";
        String csvWFile = "/Users/wanhua/Documents/WX.csv";
        List<String> list = Lists.newArrayList();
        try (Reader reader = new FileReader(csvRFile);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord csvRecord : csvParser) {
                // 获取每个字段的值，通过列名获取
                String column1 = csvRecord.get("UserId");
                list.add(column1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<List<String>> result = Lists.newArrayList();
        result.add(List.of("userId", "name", "phone"));
        try (Reader reader = new FileReader(csvWFile);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord csvRecord : csvParser) {
                // 获取每个字段的值，通过列名获取
                String column1 = csvRecord.get("userId");
                if (list.contains(column1)) {
                    // 更新字段
                    result.add(List.of("`" + column1, csvRecord.get("微信昵称"), csvRecord.get("用户手机号")));
                    log.info(column1);
                }
            }
            if (!CollectionUtils.isEmpty(result)) {
                String outputFilePath = "new_qw_out.csv";
                try {
                    // 写入新的CSV文件
                    CsvUtil.writeNewCsv(outputFilePath, result);
                    System.out.println("新的CSV文件已生成并写入数据: " + outputFilePath);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public  void test() {
//        String inputCsvFile = "path/to/your/input.csv"; // 输入CSV文件路径
//        String outputCsvFile = "path/to/your/output.csv"; // 输出CSV文件路径（可以是同一个文件路径以覆盖原文件）
//
//        List<CSVRecord> records = new ArrayList<>();
//
//        try (Reader reader = Files.newBufferedReader(Paths.get(inputCsvFile));
//             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
//
//            for (CSVRecord csvRecord : csvParser) {
//                // 假设我们要根据某一列的值来更新另一列
//                String oldValue = csvRecord.get("OldColumn");
//                String newValue = updateLogic(oldValue); // 调用你的逻辑方法来更新值
//
//                // 创建一个新的CSVRecord，因为CSVRecord是不可变的
//                CSVRecord updatedRecord = csvRecord.toBuilder()
//                        .replace("OldColumn", newValue) // 替换旧值
//                        .build();
//
//                records.add(updatedRecord);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return;
//        }
//
//        // 将更新后的记录写回到CSV文件
//        try (FileWriter out = new FileWriter(outputCsvFile);
//             CSVPrinter csvPrinter = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(records.get(0).getHeaderMap().keySet().toArray(new String[0])))) {
//
//            for (CSVRecord record : records) {
//                csvPrinter.printRecord(record);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    // 这是一个示例逻辑方法，用于根据旧值计算新值
    private static String updateLogic(String oldValue) {
        // 在这里添加你的逻辑
        // 例如，简单地将所有值转换为大写
        return oldValue != null ? oldValue.toUpperCase() : null;
    }
}