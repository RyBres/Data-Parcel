/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rybres.dataparcel;

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

public class Model {

    public void startAnyMethod(String methodType, String inputFile, String outputFile, int rowNumber) throws IOException {
        System.out.println("Method type: " + methodType);
        System.out.println("Input file: " + inputFile);
        System.out.println("Output file: " + outputFile);
        System.out.println("Row number: " + rowNumber);
        if (methodType.equals("Row number")) {
            rowMethod(inputFile, outputFile, rowNumber);
        }
    }

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
        
        // Extract input file name for naming partitions
        //int indexInputFileName = inputFile.lastIndexOf('/');
        //String inpFileName = inputFile.substring(0, indexInputFileName);
        //System.out.println(inpFileName);
        
        // Get headers - will be at top of each partition
        String[] headers = parser.getContext().headers();

        int fileCount = 1; // Starting with the first output file
        int currentRowCount = 0; // Counts rows in the current partition

        BufferedWriter writer = openNewFile(outputFile, fileCount); // Open the first output file
        writeHeaders(writer, headers); // Write headers to the first file

        // Iterate over rows - read, append to list, write to file after n-1 iterations
        String[] rowContent;
        while ((rowContent = parser.parseNext()) != null) {
            writer.write(String.join(",", rowContent) + "\n");
            currentRowCount++;

            if (currentRowCount >= rowNumber) {
                writer.close(); // Close current file
                fileCount++; // Increment file count for next partition
                writer = openNewFile(outputFile, fileCount); // Open new file
                writeHeaders(writer, headers); // Write headers to the new file
                currentRowCount = 0; // Reset row count for next partition
            }
        }

        // Close the last file
        writer.close();
        parser.stopParsing(); // Stop parsing the input file
    }

    // Helper method to open a new file for writing
    private BufferedWriter openNewFile(String baseFileName, int fileNumber) throws IOException {
        
        // Extract the parent directory and the base file name
        Path basePath = Paths.get(baseFileName);
        Path directory = basePath.getParent();
        String fileName = basePath.getFileName().toString().replace(".csv", "");

        // Construct the new file name with the number appended
        String newFileName = fileName + "_" + fileNumber + ".csv";

        // Combine the directory and new file name
        Path filePath = (directory == null) ? Paths.get(newFileName) : directory.resolve(newFileName);

        // Ensure the parent directory exists, create it if it doesn't
        if (directory != null && !Files.exists(directory)) {
            Files.createDirectories(directory);
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
