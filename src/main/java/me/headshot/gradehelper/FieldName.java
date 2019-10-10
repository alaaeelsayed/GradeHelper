package me.headshot.gradehelper;

/**
 * This class is only to organize the field names so it wouldn't be necessary to change everything in case
 * the template file is ever changed with different field names.
 */
public enum FieldName {
    STUDENT_NAME("student_name"),
    ASSIGNMENT_NUMBER("assignment_number"),
    PROBLEM1_PRESENTATION("problem1_pr"),
    PROBLEM1_DESIGN("problem1_de"),
    PROBLEM1_FUNCTIONALITY("problem1_fu"),
    PROBLEM1_TOTAL("problem1_to"),
    PROBLEM1_ACHIEVEMENTS("problem1_ach"),
    PROBLEM1_IMPROVEMENTS("problem1_imp"),
    PROBLEM2_PRESENTATION("problem2_pr"),
    PROBLEM2_DESIGN("problem2_de"),
    PROBLEM2_FUNCTIONALITY("problem2_fu"),
    PROBLEM2_TOTAL("problem2_to"),
    PROBLEM2_ACHIEVEMENTS("problem2_ach"),
    PROBLEM2_IMPROVEMENTS("problem2_imp"),
    TOTAL_MARKS("total_marks"),
    FINAL_MARK("final_mark");

    private String qualifiedName;
    FieldName(String qualifiedName){
        this.qualifiedName = qualifiedName;
    }

    public String getQualifiedName(){
        return qualifiedName;
    }
}
