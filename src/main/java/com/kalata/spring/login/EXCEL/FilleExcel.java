
package com.kalata.spring.login.EXCEL;

import com.kalata.spring.login.models.ExcelDto;
import com.kalata.spring.login.models.Utilisateurs;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class FilleExcel {
    public static String excelType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    // Methode qui verifi si le fichier est un fichier Excel
    public static Boolean verifier(MultipartFile file) {

        if (excelType.equals(file.getContentType())) {
            return true;
        } else {
            return false;
        }
    }

    // Methode qui retourne la liste des electeur à travers le fichier excel
    public static List<ExcelDto> saveElecteur(MultipartFile file) {

        try {
            // creation d'une liste dans la quelle on va mettre la liste à recuperée
            List<ExcelDto> electeurs = new ArrayList<ExcelDto>();

            // lecture du fichier
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Iterator<Sheet> sheet = workbook.sheetIterator();

            DataFormatter formatter = new DataFormatter();

            Sheet sh = sheet.next();
            // parcour du fichier excel ligne par ligne
            Iterator<Row> iterator = sh.iterator();
            while (iterator.hasNext()) {
                Row row = iterator.next();
                Iterator<Cell> cellIterator = row.iterator();
                // on saute la première ligne qui est l'entête
                if (row.getRowNum() == 0) {
                    continue;
                }
                // création d'un nouvel utilisateur
                ExcelDto excelDto = new ExcelDto();
                // parcour des colonnes d'une ligne
                while (cellIterator.hasNext()) {
                    Cell colonneCourante = cellIterator.next();
                    if (colonneCourante == null) {
                        continue;
                    }
                    // recuperation des infos de chaque colonne
                    switch (colonneCourante.getColumnIndex()) {
                        case 0:
                            excelDto.setUsername(formatter.formatCellValue(colonneCourante));
                            break;
                        case 1:
                            excelDto.setBiometrie(colonneCourante.getStringCellValue());
                            break;
                        case 2:
                            try {
                                excelDto.setDatenaissance(colonneCourante.getLocalDateTimeCellValue().toString());
                            } catch (IllegalStateException e) {
                                excelDto.setDatenaissance(null);
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            excelDto.setTelephone(String.valueOf(Long.parseLong(formatter.formatCellValue(colonneCourante))));
                            break;
                        case 4:
                            excelDto.setSexe(formatter.formatCellValue(colonneCourante));
                            break;
                        case 5:
                            excelDto.setEmail(formatter.formatCellValue(colonneCourante));
                            break;
                        case 6:
                            excelDto.setPassword(formatter.formatCellValue(colonneCourante));
                            break;
                        default:
                            break;
                    }
                }
                electeurs.add(excelDto);
            }

            workbook.close();
            return electeurs;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            // TODO: handle exception
            return null;
        }

    }
}
