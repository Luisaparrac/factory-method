public class DocumentRequest {
    private String fileName;
    private String documentType;
    private String format;
    private String country;

    public DocumentRequest(String fileName, String documentType, String format, String country) {
        this.fileName = fileName;
        this.documentType = documentType;
        this.format = format;
        this.country = country;
    }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getDocumentType() { return documentType; }
    public void setDocumentType(String documentType) { this.documentType = documentType; }

    public String getFormat() { return format; }
    public void setFormat(String format) { this.format = format; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
}
