import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BatchProcessor {

    private final Map<String, DocumentProcessorFactory> factories = Map.of(
            "Electronic Invoice", new ElectronicInvoiceFactory(),
            "Legal Contract", new LegalContractFactory(),
            "Financial Report", new FinancialReportFactory(),
            "Digital Certificate", new DigitalCertificateFactory(),
            "Tax Declaration", new TaxDeclarationFactory()
    );

    public List<ProcessResult> processBatch(List<DocumentRequest> requests) {
        List<ProcessResult> results = new ArrayList<>();

        for (DocumentRequest request : requests) {
            DocumentProcessorFactory factory = factories.get(request.getDocumentType());

            if (factory == null) {
                results.add(new ProcessResult(request.getFileName(), request.getDocumentType(),
                        false, "No factory registered for document type: " + request.getDocumentType()));
                continue;
            }

            results.add(factory.processDocument(request.getFileName(), request.getFormat(), request.getCountry()));
        }

        return results;
    }
}
