package com.liuzq;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootMybatisApplicationTests {
Logger LOGGER= LoggerFactory.getLogger(SpringbootMybatisApplicationTests.class);
	@Test
	public void contextLoads() {
		JSONObject jsonObj=new JSONObject();
		Map<String , Object> resultJsonObj=new HashMap<>();
		String uid = null;
		try {
			String params = jsonObj.getString("params");
			JSONObject jsonObject = StringUtils.isEmpty(params) ? null : JSONObject.parseObject(params);
			JSONObject initResult = jsonObject == null ? null : jsonObject.getJSONObject("initResult");
			uid = initResult == null ? "" : initResult.getString("uid");
			resultJsonObj.put("uid", uid);
		} catch (Exception e) {
			LOGGER.error("获取uid失败,error: ", e);
		}
	}


}
