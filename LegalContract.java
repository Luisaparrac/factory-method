import java.util.List;

public class LegalContract extends Document {

    public LegalContract(String fileName, String format, String country) {
        super(fileName, format, country);
    }

    public String getDocumentType() { return "Legal Contract"; }

    public List<String> getAllowedFormats() { return List.of("pdf", "doc", "docx"); }

    public String process() {
        return "Legal contract '" + fileName + "' archived and indexed for compliance review in "
                + country + ".";
    }
}
