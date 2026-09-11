import java.util.List;

public class TaxDeclaration extends Document {

    public TaxDeclaration(String fileName, String format, String country) {
        super(fileName, format, country);
    }

    public String getDocumentType() { return "Tax Declaration"; }

    public List<String> getAllowedFormats() { return List.of("xlsx", "csv", "pdf"); }

    public String process() {
        return "Tax declaration '" + fileName + "' submitted to the relevant tax authority in "
                + country + ".";
    }
}
