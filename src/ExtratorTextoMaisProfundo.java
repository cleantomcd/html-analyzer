import java.util.Stack;

public class ExtratorTextoMaisProfundo {

    private String textoMaisProfundo;
    private int profundidadeMaxima;
    private int profundidadeAtual;
    private boolean htmlBemFormado;
    private final Stack<String> tagsStack = new Stack<>();

    public ExtratorTextoMaisProfundo() {
        this.textoMaisProfundo = "";
        this.profundidadeMaxima = 0;
        this.profundidadeAtual = 1;
        this.htmlBemFormado = true;
    }

    public void extraiTextoMaisProfundo(String linha) {
        linha = linha.trim();

        if (linha.startsWith("<") && linha.endsWith(">")) {
            if (linha.startsWith("</")) {
                this.profundidadeAtual--;
                String tag = linha.substring(2, linha.length() - 1);
                verificaValidadeHtml(tag, false);
            }
            else {
                profundidadeAtual++;
                String tag = linha.substring(1, linha.length() - 1);
                verificaValidadeHtml(tag, true);
            }
        }
        else { //quando chegar no texto,
            if (profundidadeAtual > profundidadeMaxima) {
                profundidadeMaxima = profundidadeAtual;
                textoMaisProfundo = linha;
            }
        }
    }

    void verificaValidadeHtml(String tag, boolean ehAbertura) {
        if(ehAbertura) tagsStack.push(tag);
        else {
            if (tagsStack.isEmpty() || !tagsStack.peek().equals(tag)) { // para tag mal fechada, está entrando aqui
                htmlBemFormado = false;
                return;
            }
            tagsStack.pop();
        }
        if (tagsStack.isEmpty()) htmlBemFormado = true;

     }

    public boolean htmlBemFormado() {
        return this.htmlBemFormado;
    }

    public String getTextoMaisProfundo() {
        return textoMaisProfundo;
    }

    public boolean isStackEmpty() {
        return this.tagsStack.isEmpty();
    }


}
