/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rybres.dataparcel.model;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.univocity.parsers.common.processor.RowListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.csv.CsvRoutines;
import java.io.File;
import static java.lang.Math.ceil;

public class PartitionMethods {
    
    /* Start and stop methods */
    private volatile boolean shouldStop = false;
    
    public void startParsingMethod(String methodType, String inputFile, String outputFile, int rowNumber) throws IOException {
        
        System.out.println("Method type: " + methodType);
        System.out.println("Input file: " + inputFile);
        System.out.println("Output file: " + outputFile);
        System.out.println("Row number: " + rowNumber);
        
        switch (methodType) {
            case "Row number":
                rowMethod(inputFile, outputFile, rowNumber);
                break;
            case "Partition number":
                int rowNum = calculatePartitions(inputFile, rowNumber);
                rowMethod(inputFile, outputFile, rowNum);
                break;
            case "Partition size":
                break;
            default:
                break;
        }
    }
    
    public void stopParsingMethod() {
        shouldStop = true;
    }
    
    /* Partition calculation methods */
    
    // By partition number calc
    public int calculatePartitions(String inputFile, int numPart) {
        long inputFileRowNumber = (long) 0.0;
        long perPartRowNumber = (long) 0.0;
        int ceilPerPartRowNumber = 0;
        
        // Get input file details
        File file = new File(inputFile);
        inputFileRowNumber = new CsvRoutines().getInputDimension(file).rowCount();
        
        // Calculate number partitions
        perPartRowNumber = inputFileRowNumber / numPart;
        
        ceilPerPartRowNumber = (int) ceil(perPartRowNumber);
        
        return ceilPerPartRowNumber;
    }
    
    /* Parsing methods */
    public void rowMethod(String inputFile, String outputFile, int rowNumber) throws IOException {
        // Set processor settings + processor
        CsvParserSettings parserSettings = new CsvParserSettings();
        parserSettings.setLineSeparatorDetectionEnabled(true);
        parserSettings.setHeaderExtractionEnabled(true);

        // Initialize RowListProcessor
        RowListProcessor rowProcessor = new RowListProcessor();

        // Set the processor with RowListProcessor
        parserSettings.setProcessor(rowProcessor);

        // Create parser instance
        CsvParser parser = new CsvParser(parserSettings);
        FileReader parserFileReader = new FileReader(inputFile);
        parser.beginParsing(parserFileReader);
        
        // Get headers - will be at top of each partition
        String[] headers = parser.getContext().headers();

        int fileCount = 1; // Starting with the first output file
        int currentRowCount = 0; // Counts rows in the current partition

        BufferedWriter writer = openNewFile(inputFile, outputFile, fileCount); // Open the first output file
        writeHeaders(writer, headers); // Write headers to the first file

        // Iterate over rows - read, append to list, write to file after n-1 iterations
        String[] rowContent;
        while ((rowContent = parser.parseNext()) != null) {
            
            writer.write(String.join(",", rowContent) + "\n");
            currentRowCount++;

            if (currentRowCount >= rowNumber || (rowContent) == null) {
                writer.close();
                fileCount++;
                writer = openNewFile(inputFile, outputFile, fileCount);
                writeHeaders(writer, headers);
                currentRowCount = 0; // Reset row count for next partition
            }
            
        }

        // Close the last file
        writer.close();
        parser.stopParsing(); // Stop parsing the input file
        shouldStop = false; // reset flag
    }
    
    /* Helper methods */
    
    // Helper method to open a new file for writing
    private BufferedWriter openNewFile(String inputFileName, String outputPathName, int fileNumber) throws IOException {
        
        // Extract the parent directory and the base file name
        Path inputPath = Paths.get(inputFileName);
        Path outputPath = Paths.get(outputPathName);
        String fileName = inputPath.getFileName().toString().replace(".csv", "");

        // Construct the new file name with the number appended
        String newFileName = fileName + "_" + fileNumber + ".csv";

        // Combine the directory and new file name
        Path filePath = (outputPath == null) ? Paths.get(newFileName) : outputPath.resolve(newFileName); // in place of if-else

        // Ensure the parent directory exists, create it if it doesn't
        if (outputPath != null && !Files.exists(outputPath)) {
            Files.createDirectories(outputPath);
        }

        // Open the file for writing
        return Files.newBufferedWriter(filePath);
    }

    // Helper method to write headers to a file
    private void writeHeaders(Writer writer, String[] headers) throws IOException {
        if (headers != null && headers.length > 0) {
            writer.write(String.join(",", headers) + "\n");
        }
    }
}
