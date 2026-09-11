public class TaxDeclarationFactory extends DocumentProcessorFactory {
    protected Document createDocument(String fileName, String format, String country) {
        return new TaxDeclaration(fileName, format, country);
    }
}
