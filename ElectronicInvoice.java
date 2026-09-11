import java.util.List;

public class ElectronicInvoice extends Document {

    public ElectronicInvoice(String fileName, String format, String country) {
        super(fileName, format, country);
    }

    public String getDocumentType() { return "Electronic Invoice"; }

    public List<String> getAllowedFormats() { return List.of("pdf", "xlsx", "csv"); }

    public String process() {
        return "Electronic invoice '" + fileName + "' processed and registered under "
                + country + "'s tax authority requirements.";
    }
}
