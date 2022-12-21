package sintactico;
import lexico.AnalizadorLexico;

public class GdeG {
    AnalizadorLexico lexico;
    String cadena, nombreAFD;
    static final int SIMB = 10;
    static final int FLECHA = 20;
    static final int OR = 30;
    static final int PC = 40;
    
    public boolean descRec(String nombreAFD, String cadena){
        lexico = new AnalizadorLexico(nombreAFD, cadena);
        return listaReglas();
    }

    private boolean listaReglas(){
        if(reglas()){
            int token = lexico.yylex();
            if(token == PC){
                if(listaReglasP()){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean listaReglasP(){
        if(reglas()){
            int token = lexico.yylex();
            if(token == PC){
                if(listaReglasP()){
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    private boolean reglas(){
        if(ladoIzquierdo()){
            int token = lexico.yylex();
            if(token == FLECHA){
                if(ladosDerechos()){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean ladoIzquierdo(){
        int token = lexico.yylex();
        if(token == SIMB){
            return true;
        }
        return false;
    }

    private boolean ladosDerechos(){
        if(secSimbolos()){
            if(ladosDerechosP()){
                return true;
            }
        }
        return false;
    }

    private boolean ladosDerechosP(){
        int token = lexico.yylex();
        if(token == OR){
            if(secSimbolos()){
                if(ladosDerechos()){
                    return true;
                }
            }
            return false;
        }
        lexico.undoToken();
        return true;
    }

    private boolean secSimbolos(){
        int token = lexico.yylex();
        if(token == SIMB){
            if(secSimbolosP()){
                return true;
            }
        }
        return false;
    }

    private boolean secSimbolosP(){
        int token = lexico.yylex();
        if(token == SIMB){
            if(secSimbolosP()){
                return true;
            }
            return false;
        }
        lexico.undoToken();
        return true;
    }
}