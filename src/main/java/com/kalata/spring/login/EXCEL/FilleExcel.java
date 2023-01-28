
package com.kalata.spring.login.EXCEL;

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

    // Methode qui retourne la liste des postulants à travers le fichier excel
    // fournit
    public static List<Utilisateurs> saveElecteur(MultipartFile file) {

        try {
            // creation d'une liste dans la quelle on va mettre la liste recuperée
            List<Utilisateurs> electeurs = new ArrayList<Utilisateurs>();

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
                Utilisateurs electeur = new Utilisateurs();
                // parcour des colonnes d'une ligne
                while (cellIterator.hasNext()) {
                    Cell colonneCourante = cellIterator.next();
                    if (colonneCourante == null) {
                        continue;
                    }
                    // recuperation des infos de chaque colonne
                    switch (colonneCourante.getColumnIndex()) {
                        case 0:
                            electeur.setUsername(formatter.formatCellValue(colonneCourante));
                            break;
                        case 1:
                            electeur.setBiometrie(colonneCourante.getStringCellValue());
                            break;
                        case 2:
                            try {
                                electeur.setDatenaissance(colonneCourante.getDateCellValue());
                            } catch (IllegalStateException e) {
                                electeur.setDatenaissance(null);
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            electeur.setTelephone(String.valueOf(Long.parseLong(formatter.formatCellValue(colonneCourante))));
                            break;
                        case 4:
                            electeur.setSexe(formatter.formatCellValue(colonneCourante));
                            break;
                        case 5:
                            electeur.setEmail(formatter.formatCellValue(colonneCourante));
                            break;
                        case 6:
                            electeur.setPassword(formatter.formatCellValue(colonneCourante));
                            break;
                        default:
                            break;
                    }
                }
                electeurs.add(electeur);
            }

            /*while (sheet.hasNext()) {

                int numeroLigne = 0;

                // System.out.println(ligne.next().getRowNum());

                Sheet sh = sheet.next();
                Iterator<Row> iterator = sh.iterator();
                // parcour du fichier excel ligne par ligne
                while (iterator.hasNext()) {
                    Row row = iterator.next();
                    Iterator<Cell> cellIterator = row.iterator();
                    // Recuperation de la ligne courante
                    // Row ligneCourante = ligne.next();
                    // on lui dit de sauter la première ligne du fichier, qui est l'entête
                    if (numeroLigne == 0) {
                        numeroLigne++;
                        continue;
                    }

                    // Après avoir recuperer une ligne, on crée un postulant et on recupère ses
                    // attributs;
                    Utilisateurs electeur = new Utilisateurs();

                    int numeroColonne = 0;
                    // parcour des colonnes d'une ligne
                    while (cellIterator.hasNext()) {
                        // Recuperation de la colonne courante
                        Cell colonneCourante = cellIterator.next();
                        // recuperation des infos de chaque colonne
                        switch (numeroColonne) {
                            // première colonne contenant le nom
                            case 0:
                                electeur.setUsername(formatter.formatCellValue(colonneCourante));

                                // System.out.println(colonneCourante.getStringCellValue());
                                break;
                            // troixième colonne contenant le numero

                            case 1:
                                electeur.setBiometrie(colonneCourante.getStringCellValue());
                                break;

                            case 2:
                                Date d=null;
                                try {
                                    d = colonneCourante.getDateCellValue();
                                } catch (IllegalStateException e) {
                                    // TODO Auto-generated catch block
                                    d=null;
                                    e.printStackTrace();
                                    continue;
                                }

                                SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
                                electeur.setDatenaissance(d);
                                break;

                            case 3:
                                electeur.setTelephone(String.valueOf(Long.parseLong(formatter.formatCellValue(colonneCourante))));
                                break;

                            // deuxième colonne contenant le prenom
                            case 4:
                                electeur.setSexe(formatter.formatCellValue(colonneCourante));
                                break;

                            // dernière colonne contenant l'adresse mail
                            case 5:
                                electeur.setEmail(formatter.formatCellValue(colonneCourante));
                                break;

                            case 6:
                                electeur.setPassword(formatter.formatCellValue(colonneCourante));
                                break;

                            default:
                                break;
                        }
                        numeroColonne++;
                    }
                    electeurs.add(electeur);
                    numeroLigne++;
                }
            }*/

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




/*
import com.kalata.spring.login.models.Utilisateurs;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class FilleExcel {

    public static List<Utilisateurs> readExcelFile(MultipartFile excelFile) {
        List<Utilisateurs> userList = new ArrayList<>();
        try {
            //FileInputStream excelFile = new FileInputStream(new File(filePath));
            Workbook workbook = WorkbookFactory.create(excelFile.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();

                Utilisateurs user = new Utilisateurs();
                while (cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();
                    if (currentCell.getColumnIndex() == 0) {
                        user.setUsername(currentCell.getStringCellValue());
                    } else if (currentCell.getColumnIndex() == 1) {
                        user.setBiometrie(String.valueOf(currentCell.getNumericCellValue()));
                    } else if (currentCell.getColumnIndex() == 2) {
                        Date d=null;
                        try {
                            d = currentCell.getDateCellValue();
                        } catch (IllegalStateException e) {
                            // TODO Auto-generated catch block
                            d=null;
                            e.printStackTrace();
                            continue;
                        }

                        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
                        user.setDatenaissance(d);
                    } else if (currentCell.getColumnIndex() == 3) {
                        user.setTelephone(String.valueOf(currentCell.getNumericCellValue()));
                    } else if (currentCell.getColumnIndex() == 4) {
                        user.setSexe(currentCell.getStringCellValue());
                    } else if (currentCell.getColumnIndex() == 5) {
                        user.setEmail(currentCell.getStringCellValue());
                    } else if (currentCell.getColumnIndex() == 6) {
                        user.setPassword(currentCell.getStringCellValue());
                    }
                }
                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }*/

