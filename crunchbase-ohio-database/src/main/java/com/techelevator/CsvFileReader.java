package com.techelevator;

import com.techelevator.dao.JdbcFinanceRoundDao;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CsvFileReader {

    private final String crunchbaseFilePath;

    public CsvFileReader(String crunchbaseFilePath) {
        this.crunchbaseFilePath = crunchbaseFilePath;
    }

    public void readCsvFile() throws FileNotFoundException {

        JdbcFinanceRoundDao application = setupDatabase();

        File file = new File(crunchbaseFilePath);

        List<String> financeRoundRawData = new ArrayList<>();

        int lineCounter = 0;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                financeRoundRawData.add(line);
                lineCounter++;
                if (lineCounter == 24) {
                    application.createFinanceRound(financeRoundRawData);
                    financeRoundRawData.clear();
                    lineCounter = 0;
                }
            }
        }
    }

    public void readCsvFileForInvestorRaw() throws FileNotFoundException {

        JdbcFinanceRoundDao application = setupDatabase();

        File file = new File(crunchbaseFilePath);

        int lineCounter = 0;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                lineCounter++;
                if (lineCounter == 22) {
                    if (line.length() > 1) {
                        if(line.substring(0,1).equals("\"")){
                            line = line.substring(1,line.length()-1);
                        }
                        String[] financeRoundInvestorRawData = line.split(",");
                        for (String investorName : financeRoundInvestorRawData) {
                            application.createInvestor(investorName.trim());
                        }
                        financeRoundInvestorRawData = null;
                    }
                }
                if (lineCounter == 24) {
                    lineCounter = 0;
                }
            }
        }
    }

    public JdbcFinanceRoundDao setupDatabase() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/CrunchbaseOhioData");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");

        JdbcFinanceRoundDao application = new JdbcFinanceRoundDao(dataSource);

        return application;
    }

    public static void main(String[] args) {
        CsvFileReader fileReader = new CsvFileReader("Crunchbase Finance Round Data - Sheet1.csv");
        try {
            fileReader.readCsvFileForInvestorRaw();
        } catch(FileNotFoundException e){
            System.out.println("No File");
            }
        }
    }
