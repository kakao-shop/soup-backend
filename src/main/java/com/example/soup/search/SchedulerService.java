package com.example.soup.search;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class SchedulerService {
    public void job() {
        String content = "spring.elasticsearch.indexName=product-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm"));
        System.out.println("테이블명: " + content);
        try {
            String filePath = System.getProperty("user.dir");
            System.out.println(filePath);
            File file = new File(filePath + "/src/main/resources/product.properties");

            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file, false);
            fileOutputStream.write(content.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
