
public abstract class DocumentProcessorFactory {

    protected abstract Document createDocument(String fileName, String format, String country);

    public ProcessResult processDocument(String fileName, String format, String country) {
        try {
            Document document = createDocument(fileName, format, country);
            ValidationResult validation = document.validate();

            if (!validation.isValid()) {
                return new ProcessResult(fileName, document.getDocumentType(), false,
                        String.join(" | ", validation.getErrors()));
            }

            return new ProcessResult(fileName, document.getDocumentType(), true, document.process());

        } catch (Exception e) {
            return new ProcessResult(fileName, "Unknown", false,
                    "Unexpected error while processing: " + e.getMessage());
        }
    }
}
