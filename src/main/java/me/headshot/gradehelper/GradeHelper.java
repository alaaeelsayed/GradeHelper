package me.headshot.gradehelper;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * This is the main class of GradeHelper, a simple utility program I have created in order to
 * assist me in grading student assignments bulk downloaded from github and creating feedback for each student.
 */
public class GradeHelper {
    private static Scanner inputScanner = new Scanner(System.in);
    private static File templateFile;

    public static void main(String[] args) {
        System.out.print("Enter template PDF file: ");
        String fileName = inputScanner.nextLine();
        File file = new File(fileName);
        if(!file.exists()){
            System.out.println("Invalid file: " + fileName);
            return;
        }
        templateFile = file;
        System.out.print("Enter student assignments directory: ");
        String studentsDir = inputScanner.nextLine();
        File studentsFile = new File(studentsDir);
        if(!studentsFile.isDirectory()){
            System.out.println("Not a directory: " + fileName);
            return;
        }
        File[] files = studentsFile.listFiles();
        for(File studentFolder : files){
            if(!studentFolder.isDirectory()){
                System.out.println("Invalid student directory: " + studentFolder.getName());
                continue;
            }
            String studentName = studentFolder.getName().split("_")[0];
            StudentGrader.setStudent(studentName, studentFolder);
        }
        displayStudentPrompt();

    }

    /**
     * Displays the prompt for the main program, getting the assignment number
     * and getting the grade and feedback of each student.
     */
    private static void displayStudentPrompt(){
        System.out.print("Enter the assignment number: ");
        int assignmentNumber = inputScanner.nextInt();
        List<String> students = StudentGrader.getAvailableStudents();
        Iterator<String> studentIterator = students.iterator();
        while(studentIterator.hasNext()){
            String student = studentIterator.next();
            System.out.println("Currently grading: " + student);
            System.out.println("Students left: " + students);
            System.out.println(students.size() + " students remaining!");
            File studentDir = StudentGrader.getStudentFile(student);
            try {
                Desktop.getDesktop().open(studentDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
            getUserGrade(student, assignmentNumber);
            studentIterator.remove();
        }

    }

    /**
     * Prompt the user for the grade and feedback of a certain student for a certain assignment.
     * Saves the feedback in a file called 'Feedback.pdf'
     * @param student - The name of the student (must be present in the {@link StudentGrader} map)
     * @param assignmentNumber - The number of the assignment
     */
    private static void getUserGrade(String student, int assignmentNumber){
        PDFFiller pdfFiller = new PDFFiller(templateFile);
        pdfFiller.setFieldValue(FieldName.ASSIGNMENT_NUMBER, assignmentNumber + "", true);
        pdfFiller.setFieldValue(FieldName.STUDENT_NAME, student, true);
        int totalMarks = 0;
        for(int i = 1; i <= 2; i++){
            System.out.println("Current problem: " + i);
            System.out.print("Enter presentation mark: ");
            int presentationMark = inputScanner.nextInt();
            if(presentationMark == -1){
                System.out.println("Redoing grades for: " + student + " on assignment " + i);
                i--;
                continue;
            }
            System.out.print("Enter design mark: ");
            int designMark = inputScanner.nextInt();
            if(designMark == -1){
                System.out.println("Redoing grades for: " + student + " on assignment " + i);
                i--;
                continue;
            }
            System.out.print("Enter functionality mark: ");
            int functionalityMark = inputScanner.nextInt();
            if(functionalityMark == -1){
                System.out.println("Redoing grades for: " + student + " on assignment " + i);
                i--;
                continue;
            }
            inputScanner.nextLine();
            System.out.print("Enter areas of achievement: ");
            String areasOfAchievement = inputScanner.nextLine();
            if(areasOfAchievement.equals("-1")){
                System.out.println("Redoing grades for: " + student + " on assignment " + i);
                i--;
                continue;
            }
            System.out.print("Enter areas of improvement: ");
            String areasOfImprovement = inputScanner.nextLine();
            if(areasOfImprovement.equals("-1")){
                System.out.println("Redoing grades for: " + student + " on assignment " + i);
                i--;
                continue;
            }
            pdfFiller.setFieldValue(FieldName.valueOf("PROBLEM" + i + "_DESIGN"), designMark + "", true);
            pdfFiller.setFieldValue(FieldName.valueOf("PROBLEM" + i + "_PRESENTATION"), presentationMark + "", true);
            pdfFiller.setFieldValue(FieldName.valueOf("PROBLEM" + i + "_FUNCTIONALITY"), functionalityMark + "", true);
            int assignmentMark = designMark + presentationMark + functionalityMark;
            pdfFiller.setFieldValue(FieldName.valueOf("PROBLEM" + i + "_TOTAL"), assignmentMark + "", true);
            totalMarks += assignmentMark;
            pdfFiller.setFieldValue(FieldName.valueOf("PROBLEM" + i + "_ACHIEVEMENTS"), areasOfAchievement.replace(";", "\n") + "", true);
            pdfFiller.setFieldValue(FieldName.valueOf("PROBLEM" + i + "_IMPROVEMENTS"), areasOfImprovement.replace(";", "\n") + "", true);
        }
        pdfFiller.setFieldValue(FieldName.TOTAL_MARKS, totalMarks + "", true);
        pdfFiller.setFieldValue(FieldName.FINAL_MARK, totalMarks/2.0 + "", true);
        try {
            pdfFiller.save(new File(StudentGrader.getStudentFile(student), "Feedback.pdf"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
