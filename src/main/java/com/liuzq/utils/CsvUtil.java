package com.liuzq.utils;

import java.io.*;
import java.nio.file.*;  
import java.util.*;  
  
public class CsvUtil {  
  
    // 将List<List<String>>写入新的CSV文件  
    public static void writeNewCsv(String filePath, List<List<String>> data) throws IOException {  
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(filePath))) {  
            for (List<String> row : data) {  
                String line = String.join(",", row);  
                bw.write(line);  
                bw.newLine();  
            }  
        }  
    }  
  
    // 示例用法：创建一个新的CSV文件并添加元素  
    public static void main(String[] args) {  
        String outputFilePath = "new_output.csv";  
  
        // 准备要写入CSV文件的数据  
        List<List<String>> csvData = new ArrayList<>();  
        csvData.add(Arrays.asList("Header1", "Header2", "Header3")); // 添加表头  
        csvData.add(Arrays.asList("Data1", "Data2", "Data3"));       // 添加第一行数据  
        csvData.add(Arrays.asList("Data4", "Data5", "Data6"));       // 添加第二行数据  
  
        // 你可以继续添加更多的数据...  
  
        try {  
            // 写入新的CSV文件  
            writeNewCsv(outputFilePath, csvData);  
            System.out.println("新的CSV文件已生成并写入数据: " + outputFilePath);  
  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
}