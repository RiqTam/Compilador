package lexico;
import java.util.HashSet;

public class AFN {
    Estado edoIn;
    HashSet<Estado> edos = new HashSet<Estado>();
    HashSet<Estado> edosAcept = new HashSet<Estado>();
    HashSet<Character> alfabeto = new HashSet<Character>();

    public Estado getEdoIn() {
        return edoIn;
    }

    public HashSet<Estado> getEdos(){
        return edos;
    }

    public HashSet<Estado> getEdosAcept(){
        return edosAcept;
    }

    public HashSet<Character> getAlfabeto(){
        return alfabeto;
    }

    public void setEdoIn(Estado edo){
        this.edoIn = edo;
    }

    public void setEdo(Estado edo) {
        this.edos.add(edo);
    }
    
    public void removeEdo(Estado edo){
        this.edos.remove(edo);
    }


    public void setEdoAcept(Estado edo){
        this.edosAcept.add(edo);
    }

    public void removeEdoAcept(Estado edo){
        this.edosAcept.remove(edo);
    }

    public void setSimb(char simb){
        this.alfabeto.add(simb);
    }

    public AFN(){
    }

    public AFN(Estado edoIn){
        this.edoIn = edoIn;
        this.edos.add(edoIn);
    }

    public AFN(Estado edoIn, Estado edoAcept) {
        this.edoIn = edoIn;
        this.edos.add(edoIn);
        this.edos.add(edoAcept);
        this.edosAcept.add(edoAcept);
    }
}