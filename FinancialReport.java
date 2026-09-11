import java.util.List;

public class FinancialReport extends Document {

    public FinancialReport(String fileName, String format, String country) {
        super(fileName, format, country);
    }

    public String getDocumentType() { return "Financial Report"; }

    public List<String> getAllowedFormats() { return List.of("xlsx", "csv", "pdf"); }

    public String process() {
        return "Financial report '" + fileName + "' consolidated and validated against "
                + country + "'s accounting standards.";
    }
}
