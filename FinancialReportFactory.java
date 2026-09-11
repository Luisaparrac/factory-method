public class FinancialReportFactory extends DocumentProcessorFactory {
    protected Document createDocument(String fileName, String format, String country) {
        return new FinancialReport(fileName, format, country);
    }
}
