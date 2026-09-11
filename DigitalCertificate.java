import java.util.List;

public class DigitalCertificate extends Document {

    public DigitalCertificate(String fileName, String format, String country) {
        super(fileName, format, country);
    }

    public String getDocumentType() { return "Digital Certificate"; }

    public List<String> getAllowedFormats() { return List.of("pdf", "txt"); }

    public String process() {
        return "Digital certificate '" + fileName + "' verified and stored securely for "
                + country + ".";
    }
}
