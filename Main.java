import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final BatchProcessor batchProcessor = new BatchProcessor();

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", Main::serveStatic);
        server.createContext("/api/process-batch", Main::handleProcessBatch);
        server.setExecutor(null);
        server.start();
        System.out.println("Server running at http://localhost:8080");
    }

    private static void serveStatic(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if (path.equals("/")) path = "/index.html";

        File file = new File("web" + path);
        if (!file.exists()) {
            exchange.sendResponseHeaders(404, -1);
            return;
        }

        byte[] bytes = Files.readAllBytes(file.toPath());
        String contentType = path.endsWith(".css") ? "text/css"
                : path.endsWith(".js") ? "application/javascript" : "text/html";

        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.sendResponseHeaders(200, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.close();
    }

    private static void handleProcessBatch(HttpExchange exchange) throws IOException {
        if (!"POST".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        String body = new String(exchange.getRequestBody().readAllBytes());
        List<DocumentRequest> requests = parseDocumentRequests(body);
        List<ProcessResult> results = batchProcessor.processBatch(requests);

        String json = toJsonArray(results);
        byte[] bytes = json.getBytes();

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.close();
    }


    private static List<DocumentRequest> parseDocumentRequests(String json) {
        List<DocumentRequest> requests = new ArrayList<>();
        int depth = 0;
        int objectStart = -1;

        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '{') {
                if (depth == 0) objectStart = i;
                depth++;
            } else if (c == '}') {
                depth--;
                if (depth == 0 && objectStart != -1) {
                    String obj = json.substring(objectStart, i + 1);
                    requests.add(new DocumentRequest(
                            extractJsonValue(obj, "fileName"),
                            extractJsonValue(obj, "documentType"),
                            extractJsonValue(obj, "format"),
                            extractJsonValue(obj, "country")));
                }
            }
        }
        return requests;
    }

    private static String extractJsonValue(String json, String key) {
        String marker = "\"" + key + "\":\"";
        int start = json.indexOf(marker);
        if (start == -1) return "";
        start += marker.length();
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }

    private static String toJsonArray(List<ProcessResult> results) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < results.size(); i++) {
            ProcessResult r = results.get(i);
            sb.append("{");
            sb.append("\"fileName\":\"").append(esc(r.getFileName())).append("\",");
            sb.append("\"documentType\":\"").append(esc(r.getDocumentType())).append("\",");
            sb.append("\"success\":").append(r.isSuccess()).append(",");
            sb.append("\"message\":\"").append(esc(r.getMessage())).append("\"");
            sb.append("}");
            if (i < results.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }

    private static String esc(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }
}
