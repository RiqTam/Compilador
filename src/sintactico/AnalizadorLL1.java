package sintactico;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;
import lexico.AnalizadorLexico;
import java.util.List;

public class AnalizadorLL1 {

    public boolean analizar(String cadena, String[][] tabla, ArrayList<Regla> reglas, String afd, List<Integer> diccionario){
        Stack<String> pilaReglas = new Stack<String>();
        Queue<Integer> filaTokens = new LinkedList<>();

        List<String> simbolosN = new ArrayList<String>();
        List<String> simbolosT = new ArrayList<String>();
        for(Regla regla : reglas){
            simbolosN.add(regla.getLadoIzquierdo());
            for(Nodo nodo : regla.getSimbolos()){
                if(nodo.getTerminal()){
                    simbolosT.add(nodo.getSimbolo());
                }else{
                    simbolosN.add(nodo.getSimbolo());
                }
            }
        }
        LL ll = new LL();
        simbolosN = ll.quitarDuplicados(simbolosN);
        simbolosT = ll.quitarDuplicados(simbolosT);
        simbolosT.remove("epsilon");

        AnalizadorLexico lexico = new AnalizadorLexico(afd, cadena);
        int tokenLex = -10;
        while(tokenLex != 0){
            tokenLex = lexico.yylex();
            filaTokens.add(tokenLex);
        }


        pilaReglas.push(reglas.get(0).getLadoIzquierdo());
        String printReglas = "" + pilaReglas;
        String printTokens = "" + filaTokens;
        int tokenAct = filaTokens.poll();
        while(!pilaReglas.empty()){
            String simbAct = pilaReglas.pop();
            busqueda: for(int i = 1; i < (simbolosN.size() + simbolosT.size() + 2); i++){
                if(tabla[i][0].equals(simbAct)){
                    for(int j = 1; j < (simbolosT.size() + 2); j++){
                        if(tokenAct == 0){
                            if(tabla[0][j].equals("$")){
                                switch(tabla[i][j]){
                                    case "aceptar":
                                        System.out.println(printReglas + " " + printTokens + " aceptar");
                                        return true;
                                    case "-10":
                                        System.out.println("Error");
                                    break;
                                    default:
                                        for(int k = reglas.get(Integer.parseInt(tabla[i][j])).getSimbolos().size() - 1; k >= 0; k--){
                                            pilaReglas.push(reglas.get(Integer.parseInt(tabla[i][j])).getSimbolos().get(k).getSimbolo());
                                        }
                                        if(pilaReglas.contains("epsilon")){
                                            pilaReglas.remove("epsilon");
                                        }
                                        System.out.println(printReglas + " " + printTokens + " " + tabla[i][j]);
                                        printReglas = "" + pilaReglas;
                                    break busqueda;
                                }
                            }
                        }else if(tabla[0][j].equals(simbolosT.get(diccionario.indexOf(tokenAct)))){
                            switch(tabla[i][j]){
                                case "pop":
                                    System.out.println(printReglas + " " + printTokens + " pop");
                                    printTokens = "" + filaTokens;
                                    printReglas = "" + pilaReglas;
                                    tokenAct = filaTokens.poll();
                                break busqueda;
                                case "-10":
                                    System.out.println("Error");
                                break;
                                default:
                                    for(int k = reglas.get(Integer.parseInt(tabla[i][j])).getSimbolos().size() - 1; k >= 0; k--){
                                        pilaReglas.push(reglas.get(Integer.parseInt(tabla[i][j])).getSimbolos().get(k).getSimbolo());
                                    }
                                    if(pilaReglas.contains("epsilon")){
                                        pilaReglas.remove("epsilon");
                                    }
                                    System.out.println(printReglas + " " + printTokens + " " + tabla[i][j]);
                                    printReglas = "" + pilaReglas;
                                break busqueda;
                            }
                        }
                    }
                }
            }
        }
        if(pilaReglas.empty() && filaTokens.isEmpty()){
            System.out.println(printReglas + " " + printTokens + " aceptar");
            return true;
        }
        System.out.println("Error sintactico");
        return false;
    }
}