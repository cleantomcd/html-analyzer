import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.StringReader;
import static org.junit.jupiter.api.Assertions.*;

public class HtmlAnalyzerTest {

    @Test
    void testHtmlBemFormadoComTextoMaisProfundo() {
        String html = "<html>\n" +
                "<body>\n" +
                "<div>\n" +
                "<p>\n" +
                "Texto mais profundo\n" +
                "</p>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";

        BufferedReader reader = new BufferedReader(new StringReader(html));
        ExtratorTextoMaisProfundo extrator = new ExtratorTextoMaisProfundo();

        reader.lines().forEach(extrator::extraiTextoMaisProfundo);

        assertTrue(extrator.htmlBemFormado());
        assertEquals("Texto mais profundo", extrator.getTextoMaisProfundo());
    }

    @Test
    void testHtmlMalFormadoTagNaoFechada() {
        String html = "<html>\n" +
                "<body>\n" +
                "<div>\n" +
                "<p>\n" +
                "Texto\n" +
                "</p>\n" +
                "</body>\n" +  // <div> nunca foi fechado
                "</html>";

        BufferedReader reader = new BufferedReader(new StringReader(html));
        ExtratorTextoMaisProfundo extrator = new ExtratorTextoMaisProfundo();

        reader.lines().forEach(extrator::extraiTextoMaisProfundo);

        assertFalse(extrator.htmlBemFormado());
    }

    @Test
    void testHtmlMalFormadoFechamentoErrado() {
        String html = "<html>\n" +
                "<body>\n" +
                "<div>\n" +
                "<p>\n" +
                "Texto\n" +
                "</p>\n" +
                "</span>\n" + // Fechamento incorreto (</span> deveria ser </div>)
                "</body>\n" +
                "</html>";

        BufferedReader reader = new BufferedReader(new StringReader(html));
        ExtratorTextoMaisProfundo extrator = new ExtratorTextoMaisProfundo();

        reader.lines().forEach(extrator::extraiTextoMaisProfundo);

        assertFalse(extrator.htmlBemFormado());
    }

    @Test
    void testHtmlComVariosNiveisAninhados() {
        String html = "<html>\n" +
                "<body>\n" +
                "<div>\n" +
                "<span>\n" +
                "<p>\n" +
                "Texto mais profundo\n" +
                "</p>\n" +
                "</span>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";

        BufferedReader reader = new BufferedReader(new StringReader(html));
        ExtratorTextoMaisProfundo extrator = new ExtratorTextoMaisProfundo();

        reader.lines().forEach(extrator::extraiTextoMaisProfundo);

        assertTrue(extrator.htmlBemFormado());
        assertEquals("Texto mais profundo", extrator.getTextoMaisProfundo());
    }

    @Test
    void testHtmlSemTagsApenasTexto() {
        String html = "Apenas texto puro, sem tags HTML.";

        BufferedReader reader = new BufferedReader(new StringReader(html));
        ExtratorTextoMaisProfundo extrator = new ExtratorTextoMaisProfundo();

        reader.lines().forEach(extrator::extraiTextoMaisProfundo);

        assertTrue(extrator.htmlBemFormado());
        assertEquals("Apenas texto puro, sem tags HTML.", extrator.getTextoMaisProfundo());
    }

    @Test
    void testHtmlComVariasTagsMasNenhumTexto() {
        String html = "<html>\n" +
                "<body>\n" +
                "<div>\n" +
                "<span>\n" +
                "</span>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";

        BufferedReader reader = new BufferedReader(new StringReader(html));
        ExtratorTextoMaisProfundo extrator = new ExtratorTextoMaisProfundo();

        reader.lines().forEach(extrator::extraiTextoMaisProfundo);

        assertTrue(extrator.htmlBemFormado());
        assertEquals("", extrator.getTextoMaisProfundo());
    }

    @Test
    void testHtmlComTagNaoCorrespondente() {
        String html = "<html>\n" +
                "<body>\n" +
                "<div>\n" +
                "<p>\n" +
                "Texto dentro do P\n" +
                "</p>\n" +
                "</div>\n" +
                "</p>\n" + // Tag p fechada errada
                "</body>\n" +
                "</html>";

        BufferedReader reader = new BufferedReader(new StringReader(html));
        ExtratorTextoMaisProfundo extrator = new ExtratorTextoMaisProfundo();
        String[] linhas = html.split("\n");

        for(String s: linhas) {
            extrator.extraiTextoMaisProfundo(s);
            if(!extrator.htmlBemFormado()) break; // simulando o que ocorre no main
        }

        assertFalse(extrator.htmlBemFormado());
    }

    @Test
    void testHtmlComMuitasTagsMasSemTextoValido() {
        String html = "<html>\n" +
                "<body>\n" +
                "<div>\n" +
                "<span>\n" +
                "<p>\n" +
                "</p>\n" +
                "</span>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";

        BufferedReader reader = new BufferedReader(new StringReader(html));
        ExtratorTextoMaisProfundo extrator = new ExtratorTextoMaisProfundo();

        reader.lines().forEach(extrator::extraiTextoMaisProfundo);

        assertTrue(extrator.htmlBemFormado());
        assertEquals("", extrator.getTextoMaisProfundo());
    }
}
