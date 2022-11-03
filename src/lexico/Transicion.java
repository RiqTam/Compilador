package lexico;

public class Transicion {
    private char simb;
    private Estado edo;
    
    public char getSimb(){
        return this.simb;
    }

    public Estado getEdo(){
        return this.edo;
    }

    public void setSimb(char simb){
        this.simb = simb;
    }

    public void setEdo(Estado edo){
        this.edo = edo;
    }

    public Transicion(char simb, Estado edo) {
        this.simb = simb;
        this.edo = edo;
    }
}