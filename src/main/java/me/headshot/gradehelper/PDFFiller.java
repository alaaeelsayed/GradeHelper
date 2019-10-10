package me.headshot.gradehelper;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;
import java.io.IOException;

public class PDFFiller {
    // The template document to use when saving.
    private PDDocument pdfDocument;

    /**
     * Construct a new pdf file filler
     * @param pdfFile - The template file to use.
     */
    public PDFFiller(File pdfFile) {
        try {
            this.pdfDocument = PDDocument.load(pdfFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set an AcroForm field value in the template pdf file.
     *
     * @param field - {@link FieldName} to set.
     * @param value - The value to set it to.
     * @param readOnly - Whether the field should be read only or not.
     */
    public void setFieldValue(FieldName field, String value, boolean readOnly){
        PDAcroForm pDAcroForm = pdfDocument.getDocumentCatalog().getAcroForm();
        PDField pDField = pDAcroForm.getField(field.getQualifiedName());
        try {
            pDField.setValue(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
        pDField.setReadOnly(readOnly);
    }

    /**
     * Saves and closes the filled document to a file.
     *
     * @param file - The file to save to
     * @throws IOException if the file is already closed
     */
    public void save(File file) throws IOException {
        pdfDocument.save(file);
        pdfDocument.close();
    }


}
