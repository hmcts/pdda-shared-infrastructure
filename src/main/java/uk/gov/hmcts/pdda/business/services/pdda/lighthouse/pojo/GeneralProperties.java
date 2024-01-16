package uk.gov.hmcts.pdda.business.services.pdda.lighthouse.pojo;

public class GeneralProperties {

    private Integer numThreads;

    public GeneralProperties(Integer numThreads) {
        setNumThreads(numThreads);
    }

    public int getNumThreads() {
        return numThreads;
    }

    public void setNumThreads(int numThreads) {
        this.numThreads = numThreads;
    }
}
