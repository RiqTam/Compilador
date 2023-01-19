package sintactico;
import java.util.List;
import java.util.ArrayList;

public class LL {
    
    public List<String> primero(List<Nodo> simbolos, ArrayList<Regla> reglas){
        List<String> r = new ArrayList<String>();
        if(simbolos.get(0).getTerminal() || simbolos.get(0).getSimbolo().equals("epsilon")){
            r.add(simbolos.get(0).getSimbolo());
            return quitarDuplicados(r);
        }
        for(int i = 0; i < reglas.size(); i++){
            if(reglas.get(i).getLadoIzquierdo().equals(simbolos.get(0).getSimbolo())){
                r.addAll(primero(reglas.get(i).getSimbolos(), reglas));
            }
        }
        if(simbolos.size() == 1){
            return quitarDuplicados(r);
        }
        if(r.contains("epsilon")){
            r.remove("epsilon");
            simbolos.remove(0);
            r.addAll(primero(simbolos, reglas));
        }
        return quitarDuplicados(r);
    }

    public List<String> siguiente(String simb, ArrayList<Regla> reglas){
        List<String> r = new ArrayList<String>();
        List<String> f = new ArrayList<String>();
        if(simb.equals(reglas.get(0).getLadoIzquierdo())){
            r.add("$");
        }
        for(int i = 0; i < reglas.size(); i++){
            int indice = -1;
            for(Nodo nodo : reglas.get(i).getSimbolos()){
                if(simb.equals(nodo.getSimbolo())){
                    indice = reglas.get(i).getSimbolos().indexOf(nodo);
                    break;
                }
            }
            if(indice == -1){
                continue;
            }
            if((indice + 1) == reglas.get(i).getSimbolos().size()){
                if(simb.equals(reglas.get(i).getLadoIzquierdo())){
                    continue;
                }
                r.addAll(siguiente(reglas.get(i).getLadoIzquierdo(), reglas));
                continue;
            }
            f = primero(reglas.get(i).getSimbolos().subList(indice + 1, reglas.get(i).getSimbolos().size()), reglas);
            if(f.contains("epsilon")){
                f.remove("epsilon");
                r.addAll(f);
                r.addAll(siguiente(reglas.get(i).getLadoIzquierdo(), reglas));
            }else{
                r.addAll(f);
            }
        }
        return quitarDuplicados(r);
    }

    public List<String> quitarDuplicados(List<String> lista){
        List<String> listaN = new ArrayList<String>();
        for(String elemento : lista){
            if(!listaN.contains(elemento)){
                listaN.add(elemento);
            }
        }
        return listaN;
    }

    public String[][] crearTabla(ArrayList<Regla> reglas){
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
        simbolosN = quitarDuplicados(simbolosN);
        simbolosT = quitarDuplicados(simbolosT);
        simbolosT.remove("epsilon");
        System.out.println("Terminales: "+simbolosT);
        System.out.println("No terminales: "+simbolosN);
        String tabla[][] = new String[simbolosN.size() + simbolosT.size() + 2][simbolosT.size() + 2];
        for(int i = 1; i <= (simbolosN.size()); i++){
            tabla[i][0] = simbolosN.get(i - 1);
        }
        for(int i = 1; i <= (simbolosT.size()); i++){
            tabla[simbolosN.size() + i][0] = simbolosT.get(i - 1);
        }
        tabla[simbolosN.size() + simbolosT.size() + 1][0] = "$";

        for(int i = 1; i <= (simbolosT.size()); i++){
            tabla[0][i] = simbolosT.get(i - 1);
        }
        tabla[0][simbolosT.size() + 1] = "$";

        for(int i = 1; i < (simbolosN.size() + simbolosT.size() + 2); i++){
            for(int j = 1; j < (simbolosT.size() + 2); j++){
                tabla[i][j] = "-10";
            }
        }

        for(int i = 1; i < (simbolosN.size() + simbolosT.size() + 2); i++){
            for(int j = 0; j < (simbolosT.size() + 2); j++){
                if(tabla[i][0].equals(tabla[0][j])){
                    tabla[i][j] = "pop";
                }
            }
        }
        tabla[simbolosN.size() + simbolosT.size() + 1][simbolosT.size() + 1] = "aceptar";

        for(Regla regla : reglas){
            List<String> lista = primero(regla.getSimbolos(), reglas);
            if(lista.contains("epsilon")){
                lista.remove("epsilon");
                lista.addAll(siguiente(regla.getLadoIzquierdo(), reglas));
            }
            for(int i = 1; i <= simbolosN.size(); i++){
                if(tabla[i][0].equals(regla.getLadoIzquierdo())){
                    for(String simb : lista){
                        for(int j = 1; j <= (simbolosT.size() + 1); j++){
                            if(tabla[0][j].equals(simb)){
                                tabla[i][j] = Integer.toString(reglas.indexOf(regla));
                            }
                        }
                    }
                }
            }
        }

        String tablita;
        for(int i = 0; i < (simbolosN.size() + simbolosT.size() + 2); i++){
            tablita = "";
            for(int j = 0; j < (simbolosT.size() + 2); j++){
                tablita = tablita + tabla[i][j] + " ";
            }
            System.out.println(tablita);
        }

        return tabla;
    }
}