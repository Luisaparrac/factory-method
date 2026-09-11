public class ElectronicInvoiceFactory extends DocumentProcessorFactory {
    protected Document createDocument(String fileName, String format, String country) {
        return new ElectronicInvoice(fileName, format, country);
    }
}
