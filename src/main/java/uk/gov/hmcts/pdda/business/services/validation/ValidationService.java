
package uk.gov.hmcts.pdda.business.services.validation;


/**
 * Simple Service for Validating XML.
 * 
 * @author William Fardell
 */
public interface ValidationService {
    ValidationResult validate(String xml, String schemaName) throws ValidationException;
}
