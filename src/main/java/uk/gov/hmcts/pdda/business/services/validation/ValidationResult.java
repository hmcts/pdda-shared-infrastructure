package uk.gov.hmcts.pdda.business.services.validation;

public interface ValidationResult {
    /**
     * isValid.
     * @return true if the document was valid.
     */
    boolean isValid();

    /**
     * toString.
     * @return a String containg details of any errors.
     */
    @Override
    String toString();
}
