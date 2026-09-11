public class LegalContractFactory extends DocumentProcessorFactory {
    protected Document createDocument(String fileName, String format, String country) {
        return new LegalContract(fileName, format, country);
    }
}
