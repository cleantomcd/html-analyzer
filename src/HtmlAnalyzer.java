import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HtmlAnalyzer {

    public static void main(String[] args) {
        String urlString = args[0];

        try {
            HttpURLConnection connection = getConnection(urlString);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String textoMaisProfundo = getTextoMaisProfundo(reader);
                if (textoMaisProfundo != null) {
                    System.out.println(textoMaisProfundo);
                }
            }

        } catch (IOException e) {
            System.err.println("URL connection error: " + e.getMessage());
        }
    }

    static HttpURLConnection getConnection(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        return connection;
    }

    static String getTextoMaisProfundo(BufferedReader reader) throws IOException {
        String linha;
        ExtratorTextoMaisProfundo extratorTextoMaisProfundo = new ExtratorTextoMaisProfundo();

        while ((linha = reader.readLine()) != null) {
            extratorTextoMaisProfundo.extraiTextoMaisProfundo(linha);
            if(!extratorTextoMaisProfundo.htmlBemFormado()) {
                System.out.println("malformed HTML");
                return null;
            }
        }
        if(!extratorTextoMaisProfundo.isStackEmpty()) return null;

        reader.close();
        return extratorTextoMaisProfundo.getTextoMaisProfundo();
    }
}