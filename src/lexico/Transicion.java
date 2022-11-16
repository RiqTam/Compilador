package lexico;

public class Transicion {
    private char simbInf, simbSup;
    private Estado edo;
    
    public char getSimbInf(){
        return this.simbInf;
    }

    public char getSimbSup(){
        return this.simbSup;
    }

    public Estado getEdo(){
        return this.edo;
    }

    public void setSimbInf(char simb){
        this.simbInf = simb;
    }

    public void setSimbSup(char simb){
        this.simbSup = simb;
    }

    public void setEdo(Estado edo){
        this.edo = edo;
    }

    public Transicion(char simbInf, char simbSup, Estado edo) {
        this.simbInf = simbInf;
        this.simbSup = simbSup;
        this.edo = edo;
    }
}