package me.headshot.gradehelper;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Utility class to store the main directory for each student.
 */
public class StudentGrader {
    private static Map<String, File> students = new HashMap<>();

    /**
     * Sets the student directory for a certain student
     *
     * @param name - Student name
     * @param file - The student directory
     */
    public static void setStudent(String name, File file){
        students.put(name, file);
    }

    /**
     * Returns whether or not there has been feedback given to the student
     *
     * @param student - Student name
     * @return true if the map contains the student and there is feedback, false otherwise.
     */
    public static boolean hasSubmitted(String student) {
        return students.containsKey(student) && new File(students.get(student), "Feedback.pdf").exists();
    }

    /**
     * Get the main student directory.
     *
     * @param student - The name of the student
     * @return the main student directory
     */
    public static File getStudentFile(String student){
        return students.get(student);
    }

    /**
     * Gets all the students that have not been given feedback yet.
     *
     * @return {@link List} of all the students that have not been given feedback
     */
    public static List<String> getAvailableStudents(){
        return students.keySet().stream().filter(student -> !StudentGrader.hasSubmitted(student)).collect(Collectors.toList());
    }
}
