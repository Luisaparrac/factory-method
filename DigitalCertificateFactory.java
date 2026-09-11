public class DigitalCertificateFactory extends DocumentProcessorFactory {
    protected Document createDocument(String fileName, String format, String country) {
        return new DigitalCertificate(fileName, format, country);
    }
}
