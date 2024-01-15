package uk.gov.hmcts.pdda.integration.publicdisplay.jms;

/**
 * NotifierAttributes.
 * 
 * @author Luke Gittins
 */
public final class NotifierAttributes {
    
    private String providerUrl;
    private String connectionFactoryName;
    private String destinationName;
    private int courtId;
    private String date;
    
    public String getProviderUrl() {
        return providerUrl;
    }
    
    public void setProviderUrl(String providerUrl) {
        this.providerUrl = providerUrl;
    }
    
    public String getConnectionFactoryName() {
        return connectionFactoryName;
    }
    
    public void setConnectionFactoryName(String connectionFactoryName) {
        this.connectionFactoryName = connectionFactoryName;
    }
    
    public String getDestinationName() {
        return destinationName;
    }
    
    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }
    
    public int getCourtId() {
        return courtId;
    }
    
    public void setCourtId(int courtId) {
        this.courtId = courtId;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
}
