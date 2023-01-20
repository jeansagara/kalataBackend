package com.kalata.spring.login.img;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import org.springframework.web.multipart.MultipartFile;

public class SaveImage {
    public static String localhost = "http://127.0.0.1/";
    public static String serveruser = localhost + "kalata/img/";
    public static String userLocation = "C:/xampp/htdocs/kalata/img/";

    public static String save(MultipartFile file, String fileName) {
        String src = "";
        String server = "";
        String location = "";

        location = userLocation;
        server = serveruser;

        try {
            Path filePath = Paths.get(location + fileName);

            if (!Files.exists(filePath)) {
                Files.createDirectories(filePath.getParent());
                Files.copy(file.getInputStream(), filePath);
                src = server + fileName;
            } else {
                Files.delete(filePath);
                Files.copy(file.getInputStream(), filePath);
                src = server + fileName;
            }
        } catch (IOException e) {
            e.printStackTrace();
            src = null;
        }

        return src;
    }
}