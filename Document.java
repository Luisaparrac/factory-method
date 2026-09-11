import java.util.ArrayList;
import java.util.List;

// Abstract Product: every document type must say its type, its allowed
// formats, and how it processes itself once validated
public abstract class Document {

    private static final List<String> SUPPORTED_COUNTRIES =
            List.of("Colombia", "Mexico", "Argentina", "Chile");

    protected final String fileName;
    protected final String format;
    protected final String country;

    public Document(String fileName, String format, String country) {
        this.fileName = fileName;
        this.format = format;
        this.country = country;
    }

    public String getFileName() { return fileName; }
    public String getFormat() { return format; }
    public String getCountry() { return country; }

    public abstract String getDocumentType();
    public abstract List<String> getAllowedFormats();
    public abstract String process();

    // Shared validation: format must fit the document type, country must be supported
    public ValidationResult validate() {
        List<String> errors = new ArrayList<>();

        if (!getAllowedFormats().contains(format.toLowerCase())) {
            errors.add("Format '" + format + "' is not valid for " + getDocumentType()
                    + ". Allowed formats: " + String.join(", ", getAllowedFormats()));
        }

        if (!SUPPORTED_COUNTRIES.contains(country)) {
            errors.add("Country '" + country + "' is not supported. Supported countries: "
                    + String.join(", ", SUPPORTED_COUNTRIES));
        }

        return new ValidationResult(errors.isEmpty(), errors);
    }
}
