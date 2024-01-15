package uk.gov.hmcts.pdda.business.vos.translation.impl;

import java.util.Locale;

/**
 * <p>
 * Title: The default translation bundle assumes the keys are in the default language and therfore
 * contain the correct text.
 * </p>
 * <p>
 * Description: A translation
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: Electronic Data Systems
 * </p>
 * 
 * @author William Fardell, Xdevelopment (2004)
 * @version $Id: EmptyTranslationBundle.java,v 1.1 2005/11/23 16:13:38 bzjrnl Exp $
 */
public class EmptyTranslationBundle extends AbstractTranslationBundle {
    private static final long serialVersionUID = 1L;

    /**
     * Create a new default translation bundle.
     * 
     * @param locale Locale
     */
    public EmptyTranslationBundle(Locale locale) {
        super(locale);
    }

    /**
     * Return null as no translation data.
     * 
     * @return the translation for the specified key or null if not found
     */
    @Override
    public String getTranslation(String key) {
        return null;
    }

    /**
     * Return null as no translation data.
     * 
     * @return the translation for the specified key in the given context or null if not found
     */
    @Override
    public String getTranslation(String key, String context) {
        return null;
    }

}
