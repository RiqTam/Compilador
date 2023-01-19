package sintactico;

public class Nodo {
    private String simbolo;
    private boolean terminal = true;

    public String getSimbolo(){
        return this.simbolo;
    }

    public boolean getTerminal(){
        return this.terminal;
    }

    public void setTerminal(boolean terminal){
        this.terminal = terminal;
    }

    public Nodo(String simbolo){
        this.simbolo = simbolo;
    }
}
