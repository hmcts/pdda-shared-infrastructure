package uk.gov.hmcts.pdda.business.services.pdda;

public enum CpDocumentStatus {
    VALID_NOT_PROCESSED("VN"), INVALID("INV");

    public final String status;

    CpDocumentStatus(String status) {
        this.status = status;
    }
}
