package sintactico;
import lexico.AnalizadorLexico;
import java.util.List;
import java.util.ArrayList;

public class GdeG {
    AnalizadorLexico lexico;
    String cadena, nombreAFD;
    static final int SIMB = 10;
    static final int FLECHA = 20;
    static final int OR = 30;
    static final int PC = 40;
    ArrayList<Regla> reglas = new ArrayList<Regla>();

    public ArrayList<Regla> descRec(String nombreAFD, String cadena){
        lexico = new AnalizadorLexico(nombreAFD, cadena);
        if(conjReglas()){
            terminales();
            return reglas;
        }
        return null;
    }

    private boolean conjReglas(){
        if(listaReglas()){
            if(conjReglasP()){
                return true;
            }
        }
        return false;
    }

    private boolean conjReglasP(){
        if(listaReglas()){
            if(conjReglasP()){
                return true;
            }
            return false;
        }
        return true;
    }

    private boolean listaReglas(){
        int token = lexico.yylex();
        if(token == SIMB){
            String ladoIzquierdo = lexico.yytext();
            token = lexico.yylex();
            if(token == FLECHA){
                if(listaLadosDerechos(ladoIzquierdo)){
                    token = lexico.yylex();
                    if(token == PC){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean listaLadosDerechos(String ladoIzquierdo){
        if(ladoDerecho(ladoIzquierdo)){
            if(listaLadosDerechosP(ladoIzquierdo)){
                return true;
            }
        }
        return false;
    }

    private boolean listaLadosDerechosP(String ladoIzquierdo){
        int token = lexico.yylex();
        if(token == OR){
            if(ladoDerecho(ladoIzquierdo)){
                if(listaLadosDerechosP(ladoIzquierdo)){
                    return true;
                }
            }
            return false;
        }
        lexico.undoToken();
        return true;
    }

    private boolean ladoDerecho(String ladoIzquierdo){
        int token = lexico.yylex();
        List<Nodo> lista = new ArrayList<Nodo>();
        if(token == SIMB){
            String text = lexico.yytext();
            lista = ladoDerechoP(lista);
            if(lista != null){
                Nodo nodo = new Nodo(text);
                lista.add(0, nodo);
                Regla regla = new Regla(ladoIzquierdo, lista);
                reglas.add(regla);
                return true;
            }
        }
        return false;
    }

    private List<Nodo> ladoDerechoP(List<Nodo> lista){
        int token = lexico.yylex();
        if(token == SIMB){
            String text = lexico.yytext();
            List<Nodo> listaP = ladoDerechoP(lista);
            if(listaP != null){
                Nodo nodo = new Nodo(text);
                lista.add(0, nodo);
                return lista;
            }
            return null;
        }
        lexico.undoToken();
        lista.clear();
        return lista;
    }

    private void terminales(){
        for(int i = 0; i < reglas.size(); i++){
            for(int j = 0; j < reglas.get(i).getSimbolos().size(); j++){
                for(int k = 0; k < reglas.size(); k++){
                    if(reglas.get(k).getLadoIzquierdo().equals(reglas.get(i).getSimbolos().get(j).getSimbolo())){
                        reglas.get(i).getSimbolos().get(j).setTerminal(false);
                    }
                }
            }
        }
    }
}