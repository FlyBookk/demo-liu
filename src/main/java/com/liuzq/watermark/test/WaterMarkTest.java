package com.liuzq.watermark.test;

import com.alibaba.excel.EasyExcel;
import com.liuzq.watermark.DemoData;
import com.liuzq.watermark.WaterMark;
import com.liuzq.watermark.WaterMarkHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class WaterMarkTest {
    @Test
    public void WaterMarkReader() {
        WaterMark watermark = new WaterMark();
        watermark.setContent("万华-刘泽权");
        watermark.setWidth(500);
        watermark.setHeight(200);
        watermark.setYAxis(100);
        // 注意文件是要.xlsx
        String fileName = "/Users/wanhua/Documents/" + System.currentTimeMillis() + ".xlsx";
        //导出
        EasyExcel.write(fileName, DemoData.class)
                .inMemory(true)
                .sheet("sheet1")
                .registerWriteHandler(new WaterMarkHandler(watermark))
                .doWrite(data());
    }

    private List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }
}
