package uk.gov.hmcts.pdda.business.services.validation.sax;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import uk.gov.hmcts.pdda.business.services.validation.ValidationException;
import uk.gov.hmcts.pdda.business.services.validation.ValidationResult;
import uk.gov.hmcts.pdda.business.services.validation.ValidationService;

import java.io.IOException;
import java.io.StringReader;
import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.SchemaFactory;

/**
 * Simple Service for Validating XML.
 * 
 * @author William Fardell
 */
public class SaxValidationService implements ValidationService {

    private static final Logger LOG = LoggerFactory.getLogger(SaxValidationService.class);

    private final EntityResolver entityResolver;

    private SchemaFactory schemaFactory;
    private SAXParserFactory saxParserFactory;

    public SaxValidationService(final EntityResolver entitiyResolver) {
        this.entityResolver = entitiyResolver;
    }

    // Junit constructor
    public SaxValidationService(final EntityResolver entityResolver, SchemaFactory schemaFactory,
        SAXParserFactory saxParserFactory) {
        this(entityResolver);
        this.schemaFactory = schemaFactory;
        this.saxParserFactory = saxParserFactory;
    }

    @Override
    public ValidationResult validate(final String xml, final String schemaName) throws ValidationException {
        LOG.debug("entered validate method");

        try {
            SAXParserFactory factory = getSaxParserFactory();
            factory.setNamespaceAware(true);

            factory.setSchema(
                getSchemaFactory(false).newSchema(new SAXSource(entityResolver.resolveEntity(schemaName, schemaName))));
            SAXParser parser = factory.newSAXParser();


            XMLReader reader = parser.getXMLReader();
            ErrorHandlerValidationResult result = new ErrorHandlerValidationResult();
            reader.setErrorHandler(result);
            reader.parse(new InputSource(new StringReader(xml)));
            LOG.debug("Valid: {}", result.isValid());
            if (!result.isValid()) {
                LOG.debug("Validation Failed: {}", result);
            }
            return result;

        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new ValidationException("An error occurred validating.", e);
        }
    }

    protected SchemaFactory getSchemaFactory(boolean test) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (schemaFactory == null) {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            // to be compliant, completely disable DOCTYPE declaration:
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            // or prohibit the use of all protocols by external entities:
            if (!test) {
                factory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
                factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            }
            return factory;
        }
        return schemaFactory;
    }

    protected SAXParserFactory getSaxParserFactory() {
        if (saxParserFactory == null) {
            return SAXParserFactory.newInstance();
        }
        return saxParserFactory;
    }
}
