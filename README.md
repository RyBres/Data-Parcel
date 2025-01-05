# **DataParcel: Partition Processing App**

A JavaFX-based (Java 22) desktop application designed for processing and partitioning large datasets with ease using univocity parsers. This app allows users to select various partitioning methods, process input files, and manage output files seamlessly. The interface is simple and intuitive, making it suitable for both novice and experienced users.

## **Features**

- **Multiple Partitioning Methods:** Choose from different partitioning methods such as row number, partition number, and partition size.

- **Real-time Processing:** Start and stop processing tasks with a single button click.

- **File Management:** Easily specify input and output files for processing.

- **Responsive UI:** The UI remains responsive even during long-running tasks, allowing users to interrupt and stop processes as needed.

## **Screenshots**
![Screenshot 2025-01-05 155722](https://github.com/user-attachments/assets/3b685f7d-8b60-4cf1-af02-9a83458c24c2)

![Screenshot 2025-01-05 160748](https://github.com/user-attachments/assets/f77baad5-9a08-48c6-b594-753e58b7049d)

![Screenshot 2025-01-05 161057](https://github.com/user-attachments/assets/211e0ea4-068b-46e2-b8b3-73f785e19ee6)

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

- Java 22

- JavaFX SDK

### **Running the App**

Ensure you have JavaFX set up in your development environment. Compile and run the application using your IDE.
