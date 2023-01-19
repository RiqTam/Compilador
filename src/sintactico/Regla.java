package sintactico;
import java.util.List;

public class Regla {
    private String ladoIzquierdo;
    private List<Nodo> simbolos;

    public String getLadoIzquierdo(){
        return this.ladoIzquierdo;
    }

    public List<Nodo> getSimbolos(){
        return this.simbolos;
    }

    public Regla(String ladoIzquierdo, List<Nodo> simbolos){
        this.ladoIzquierdo = ladoIzquierdo;
        this.simbolos = simbolos;
    }
}