# **DataParcel: Partition Processing App**

A JavaFX-based (Java 22) desktop application designed for processing and partitioning large datasets with ease using univocity parsers. This app allows users to select various partitioning methods, process input files, and manage output files seamlessly. The interface is simple and intuitive, making it suitable for both novice and experienced users.

## **Features**

- **Multiple Partitioning Methods:** Choose from different partitioning methods such as row number, partition number, and partition size.

- **Real-time Processing:** Start and stop processing tasks with a single button click.

- **File Management:** Easily specify input and output files for processing.

- **Responsive UI:** The UI remains responsive even during long-running tasks, allowing users to interrupt and stop processes as needed.

## **Screenshots**

## **Installation**

Clone the repository:

`git clone https://github.com/RyBres/Data-Parcel.git`

Navigate to the project directory:

`cd partition-processing-app`

Open the project in your preferred IDE (e.g., IntelliJ, Eclipse).

Build and run the project. (Note that this still needs to be properly packaged - a .jar cannot be made since this was made with Java 22 and JavaFX. Jlink is problematic since this requires univocity parsers)

## **Usage**

1. Launch the application (can be run from your IDE).

2. Select the desired partitioning method from the dropdown menu.

3. Enter the input file path, output file path, and necessary parameters.

4. Click the "Start" button to begin processing.

5. To stop processing, click the "Stop" button.

## **Development**

### **Prerequisites**

Java 8 or higher

JavaFX SDK

### **Running the App**

Ensure you have JavaFX set up in your development environment. Compile and run the application using your IDE.
