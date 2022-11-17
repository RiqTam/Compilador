package sintactico;
import lexico.AnalizadorLexico;

public class Calculadora {
    AnalizadorLexico lexico;
    String cadena, nombreAFD;
    static final int MAS = 10;
    static final int MENOS = 20;
    static final int MULT = 30;
    static final int DIV = 40;
    static final int PARENTESIS_IZQ = 50;
    static final int PARENTESIS_DER = 60;
    static final int NUMERO = 70;

    public void descRec(String nombreAFD, String cadena){
        this.lexico = new AnalizadorLexico(nombreAFD, cadena);
        this.cadena = cadena;
        if(iniAnalisis()){
            System.out.println("La cadena es valida");
        }else{
            System.out.println("La cadena no es valida");
        }
    }

    private boolean iniAnalisis(){
        if(E()){
            return true;
        }
        return false;
    }

    private boolean E(){
        if(T()){
            if(Ep()){
                return true;
            }
        }
        return false;
    }

    private boolean Ep(){
        int token = lexico.yylex();
        if(token == MAS || token == MENOS){
            if(T()){
                if(Ep()){
                    return true;
                }
            }
            return false;
        }
        lexico.undoToken();
        return true;
    }

    private boolean T(){
        if(F()){
            if(Tp()){
                return true;
            }
        }
        return false;
    }

    private boolean Tp(){
        int token = lexico.yylex();
        if(token == MULT || token == DIV){
            if(F()){
                if(Tp()){
                    return true;
                }
            }
            return false;
        }
        lexico.undoToken();
        return true;
    }

    private boolean F(){
        int token = lexico.yylex();
        switch(token){
            case PARENTESIS_IZQ:
                if(E()){
                    token = lexico.yylex();
                    if(token == PARENTESIS_DER){
                        return true;
                    }
                }
                return false;
            case NUMERO:
                return true;
        }
        return false;
    }
}