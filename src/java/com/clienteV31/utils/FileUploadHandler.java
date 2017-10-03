/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import javax.ejb.Stateless;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author chernandez
 */
public class FileUploadHandler {
    
    
    public Result handleUploadedFile(UploadedFile uploadedFile, int numberOfColums){
        Result result = loadFile(uploadedFile, numberOfColums);
        if(result.errorCode==Constants.OK){
            result.result = deleteEmptyData((String[][]) result.result, numberOfColums);
        }
        return result;
    }
    /**
     * Return matriz with data storage in excel file
     *
     * @param uploadedFile
     * @return
     */
    private Result loadFile(UploadedFile uploadedFile, int numberOfColums) {
        String[][] tmp;//here even empty cells will be stored
        try {
            InputStream file = uploadedFile.getInputstream();
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            String cellValue;
            // Iterate through each rows from first sheet
            Iterator<Row> rowIterator = sheet.rowIterator();
            //Initialize values
            int numberOfRows = sheet.getPhysicalNumberOfRows();
            tmp = new String[numberOfRows][numberOfColums];
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                // For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                int columsCount = 0;
                while (cellIterator.hasNext() && columsCount < numberOfColums) {//Only the number of colums defined in constants are taken in consideration
                    columsCount++;
                    cellValue = null;
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {

                        case Cell.CELL_TYPE_BOOLEAN:
                            cellValue = String.valueOf(cell.getBooleanCellValue());

                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                cellValue = new SimpleDateFormat("dd-MM-yyyy").format(cell.getDateCellValue());
                            } else {
                                cellValue = String.valueOf(cell.getNumericCellValue());
                                cellValue = cellValue.substring(0, cellValue.length() - 2);
                                cellValue = JsfUtil.formatNumber(cellValue);
                            }
                            break;
                        case Cell.CELL_TYPE_STRING:
                            cellValue = cell.getStringCellValue().trim();
                            break;
                    }
                    if (cellValue != null) {
                        tmp[cell.getRowIndex()][cell.getColumnIndex()] = cellValue;
                    }

                }
            }
            file.close();
            workbook.close();
            return new Result(tmp, 0);
        } catch (FileNotFoundException e) {
            return new Result(e.getLocalizedMessage(), Constants.FILE_NOT_FOUND_EXCEPTION);
        } catch (IOException e) {
            return new Result(e.getLocalizedMessage(), Constants.IOEXCEPTION);
        } catch (Exception e) {
            return new Result(e.getLocalizedMessage(), Constants.UNKNOWN_EXCEPTION);
        }
    }
    
    private String[][] deleteEmptyData(String[][] tmp, int numberOfColums) {
        int realRows = 0;
        for (int i = 0; i < tmp.length; i++) {
            String[] cell = tmp[i];
            int validRow = 0;
            for (int j = 0; j < cell.length; j++) {
                if (cell[j] != null || (cell[j] instanceof String && !"-".equals(cell[j]))) {
                    validRow++;//if at least one colum in the row has data its a valid row
                }
            }
            if (validRow != 0) {//if validRow != 0 means that at least one colum in the row has data
                realRows++;
            }
        }
        String[][] values = new String[realRows - 1][numberOfColums];//realRows-1 to avoid row where names are defined
        System.arraycopy(tmp, 1, values, 0, realRows - 1);//From 1 to avoid row where names are defined
        return values;
    }
}
