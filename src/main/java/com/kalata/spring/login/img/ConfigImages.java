package com.kalata.spring.login.img;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ConfigImages {
    //pour l'image de l'entite
    public static void saveimg(String uploaDir, String nomfile, MultipartFile multipartFile) throws IOException{

        Path UploadPath = Paths.get(uploaDir);

        if(!Files.exists(UploadPath)) {
            Files.createDirectories(UploadPath);
        }
                Path fichierPath = UploadPath.resolve(nomfile);


        }


    }

