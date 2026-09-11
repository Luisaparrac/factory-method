public class ProcessResult {
    private final String fileName;
    private final String documentType;
    private final boolean success;
    private final String message;

    public ProcessResult(String fileName, String documentType, boolean success, String message) {
        this.fileName = fileName;
        this.documentType = documentType;
        this.success = success;
        this.message = message;
    }

    public String getFileName() { return fileName; }
    public String getDocumentType() { return documentType; }
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}
