package ru.aora.erp.presentation.controller.xlsout;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.aora.erp.model.entity.business.Contract;
import ru.aora.erp.model.entity.business.Ks;
import ru.aora.erp.presentation.entity.dto.compose.KsContractCounteragentDto;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Document;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class ExcelGenerator {
    private List<Contract> contractList;
    private List<Ks> ksList;
    private List<KsContractCounteragentDto> ksDtoList;
    private Workbook workbook;
    private Sheet sheet;
    static String fileDictName = "";

    public ExcelGenerator(List<Contract> contractList, List<Ks> ksList, List<KsContractCounteragentDto> ksDtoList) {
        this.contractList = contractList;
        this.ksList = ksList;
        this.ksDtoList=ksDtoList;
        workbook = new XSSFWorkbook();
    }

    private void writeContractHeader(String print_counteragent_name) {
        sheet = workbook.createSheet("Договоры");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "Имя контрагента:", style);
        createCell(row, 1, print_counteragent_name, style);
        row = sheet.createRow(2);
        createCell(row, 0, "Номер договора", style);
        createCell(row, 1, "Дата", style);
        createCell(row, 2, "Предмет", style);
        createCell(row, 3, "Сумма", style);
    }

    private void writeKsHeader(String print_counteragent_name, String print_contract_number) {
        sheet = workbook.createSheet("КС-3");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "Имя контрагента:", style);
        createCell(row, 1, print_counteragent_name, style);
        createCell(row, 2, "Номер контракта:", style);
        createCell(row, 3, print_contract_number, style);
        row = sheet.createRow(2);
        createCell(row, 0, "Номер справки КС", style);
        createCell(row, 1, "Дата", style);
        createCell(row, 2, "Сумма по КС", style);
    }

    private void writeGarantHeader() {
        sheet = workbook.createSheet("Гарантийки");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "Рейтинг гарантийных платежей", style);
        row = sheet.createRow(2);
        createCell(row, 0, "Номер справки КС", style);
        createCell(row, 1, "Дата справки КС", style);
        createCell(row, 2, "Сумма по КС", style);
        createCell(row, 3, "Гарантийное удержание", style);
        createCell(row, 4, "Номер договора", style);
        createCell(row, 5, "Контрагент", style);
        createCell(row, 6, "Осталось до возврата гарантии", style);
        createCell(row, 7, "Дата возврата гарантии", style);
    }

    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style);
    }

    private void writeContract(String print_counteragent_id) {
        int rowCount = 3;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontHeight(14);
        style.setFont(font);
        style.setWrapText(true);
        for (Contract contract : contractList) {
            if (contract.getCounteragentId().equals(print_counteragent_id)) {
                if (contract != null && contract.getActiveStatus() == 0) {
                    if (contract.getContractSum() != null) {
                        Row row = sheet.createRow(rowCount++);
                        int columnCount = 0;
                        createCell(row, columnCount++, contract.getContractNumber(), style);
                        createCell(row, columnCount++, contract.getContractDate().toString(), style);
                        createCell(row, columnCount++, contract.getContractSubject(), style);
                        createCell(row, columnCount++, contract.getContractSum().toString(), style);
                    }
                }
            }
        }
    }

    private void writeKs(String print_contract_id) {
        int rowCount = 3;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontHeight(14);
        style.setFont(font);
        style.setWrapText(true);
        for (Ks ks : ksList) {
            if (ks.getContractId().equals(print_contract_id)) {
                if (ks != null && ks.getActiveStatus() == 0) {
                    if (ks.getKsSum() != null) {
                        Row rowKs = sheet.createRow(rowCount++);
                        int columnCount = 0;
                        createCell(rowKs, columnCount++, ks.getKsNumber(), style);
                        createCell(rowKs, columnCount++, ks.getKsDate().toString(), style);
                        createCell(rowKs, columnCount++, ks.getKsSum().toString(), style);
                    }
                }
            }
        }
    }

    private void writeGarant() {
        int rowCount = 3;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontHeight(14);
        style.setFont(font);
        style.setWrapText(true);
        for (KsContractCounteragentDto ksContractCounteragent : requireNonNull(ksDtoList)) {
                if (ksContractCounteragent != null && !ksContractCounteragent.getKsStatus()) {
                    if (ksContractCounteragent.getGarantSum() != null) {
                            Row rowGarant = sheet.createRow(rowCount++);
                            int columnCount = 0;
                            createCell(rowGarant, columnCount++, ksContractCounteragent.getKsNumber(), style);
                            createCell(rowGarant, columnCount++, ksContractCounteragent.getKsDate().toString(), style);
                            createCell(rowGarant, columnCount++, ksContractCounteragent.getKsSum().toString(), style);
                            createCell(rowGarant, columnCount++, ksContractCounteragent.getGarantSum().toString(), style);
                            createCell(rowGarant, columnCount++, ksContractCounteragent.getContractNumber(), style);
                            createCell(rowGarant, columnCount++, ksContractCounteragent.getConteragentName(), style);
                            createCell(rowGarant, columnCount++, ksContractCounteragent.getDaysToGarantDate().toString(), style);
                            createCell(rowGarant, columnCount++, ksContractCounteragent.getGarantDate().toString(), style);

                    }
                }
        }
    }
    public void generateContractExcelFile(String print_counteragent_name, String print_counteragent_id) throws IOException {
        writeContractHeader(print_counteragent_name);
        writeContract(print_counteragent_id);
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());
        String fileLocation = path.substring(0, path.length() - 1) + "contracts"+currentDateTime+".xlsx";

        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();
    }

    public void generateKsExcelFile(String print_counteragent_name, String print_contract_id, String print_contract_number) throws IOException {
        writeKsHeader(print_counteragent_name, print_contract_number);
        writeKs(print_contract_id);
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());
        String fileLocation = path.substring(0, path.length() - 1) + "KS"+currentDateTime+".xlsx";

        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();
    }
    public void generateGarantExcelFile() throws IOException {
        writeGarantHeader();
        writeGarant();
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());
        String fileLocation = path.substring(0, path.length() - 1) + "Garant"+currentDateTime+".xlsx";

        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();
    }
}
