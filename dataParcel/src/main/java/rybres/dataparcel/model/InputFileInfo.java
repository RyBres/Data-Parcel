/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rybres.dataparcel.model;

import com.univocity.parsers.common.processor.RowListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.csv.CsvRoutines;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author bresn
 */
public class InputFileInfo {
    // Define variables
    private File file;
    private String inputPath;
    private String[] columnNames;
    private int columnCount;
    private long rowCount;
    private long fileSize;
    private List<String> includedColumns;
    private List<String> excludedColumns;
    private boolean confirmSwitch;
    
    // Constructor
    public InputFileInfo(String inputFile) {
        this.file = new File(inputFile);
        this.inputPath = inputFile;
        this.fileSize = file.length();
        this.columnNames = parseColumnNames();
        this.columnCount = columnNames.length;
        this.rowCount = parseRowCount();
        this.includedColumns = new ArrayList<>(Arrays.asList(columnNames));
        this.excludedColumns = new ArrayList<>();
        this.confirmSwitch = false;
    }
    
    // Public methods
    
    // Get
    public List<String> getIncludedColumns() {
        return includedColumns;
    }
    
    public List<String> getExcludedColumns() {
        return excludedColumns;
    }
    
    public String getInputPath() {
        return inputPath;
    }
    
    public String[] getColumnNames() {
        return columnNames;
    }
    
    public int getColumnCount() {
        return columnCount;
    }
    
    public long getRowCount() {
        return rowCount;
    }
    
    public long getFileSize() {
        return fileSize;
    }
    
    public boolean getConfirmSwitchValue() {
        return confirmSwitch;
    }
    
    // Set
    public void setIncludedColumns(List<String> ic) {
        this.includedColumns = ic;
    }
    
    public void setExcludedColumns(List<String> ec) {
        this.excludedColumns = ec;
    }
    
    public void setConfirmSwitchTrue() {
        this.confirmSwitch = true;
    }
    
    // Private methods, which actually do some processing (hence why they're not public)
    private String[] parseColumnNames() {
        String[] colNames = null;
        
        // Parser settings
        CsvParserSettings parserSettings = new CsvParserSettings();
        parserSettings.setLineSeparatorDetectionEnabled(true);
        parserSettings.setHeaderExtractionEnabled(true);

        // Initialize RowListProcessor
        RowListProcessor rowProcessor = new RowListProcessor();

        // Set the processor with RowListProcessor
        parserSettings.setProcessor(rowProcessor);

        // Create parser instance
        CsvParser parser = new CsvParser(parserSettings);
        try {
            FileReader parserFileReader = new FileReader(file);
            parser.beginParsing(parserFileReader);

            // Get headers - will be at top of each partition
            colNames = parser.getContext().headers();
            parser.stopParsing();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return colNames;
    }
    
    private long parseRowCount() {
        return new CsvRoutines().getInputDimension(file).rowCount();
    }
    
}
