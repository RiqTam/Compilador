package lexico;
import java.util.HashSet;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AFD {

    public AFN unirAFNs(HashSet<AFN> afns){
        Estado edo = new Estado(false, -1);
        AFN afn = new AFN();
        for(AFN afnT : afns){
            Transicion trans = new Transicion('\0', '\0', afnT.getEdoIn());
            edo.setTrans(trans);
            for(Estado edoT : afnT.getEdos()){
                afn.setEdo(edoT);
            }
            for(Estado edoA : afnT.getEdosAcept()){
                afn.setEdoAcept(edoA);
            }
            for(char simb : afnT.getAlfabeto()){
                afn.setSimb(simb);
            }
        }
        afn.setEdoIn(edo);
        afn.setEdo(edo);
        return afn;
    }

    public HashSet<Estado> cEpsilon(HashSet<Estado> edos, int edoId){
        HashSet<Estado> edosEpsilon = new HashSet<Estado>();
        Stack<Estado> pilaEdos = new Stack<Estado>();
        Estado edoActual = new Estado(false, -1);
        for(Estado edo : edos){
            if(edo.getId() == edoId){
                pilaEdos.push(edo);
                while(!pilaEdos.empty()){
                    edoActual = pilaEdos.pop();
                    edosEpsilon.add(edoActual);
                    for(Transicion trans : edoActual.getTrans()){
                        if(trans.getSimbInf() == '\0' && trans.getSimbSup() == '\0'){
                            for(Estado edoE : edos){
                                if(edoE.getId() == trans.getEdo().getId() && !edosEpsilon.contains(edoE)){
                                        pilaEdos.push(edoE);
                                }
                            }
                        }
                    }
                }
            }
        }
        return edosEpsilon;
    }

    public HashSet<Estado> mover(HashSet<Estado> edos, char simb){
        HashSet<Estado> edosMover = new HashSet<Estado>();
        for(Estado edo : edos){
            for(Transicion trans : edo.getTrans()){
                if((int) trans.getSimbInf() <= (int) simb && (int) trans.getSimbSup() >= (int) simb){
                    edosMover.add(trans.getEdo());
                }
            }
        }
        return edosMover;
    }

    public HashSet<Estado> irA(HashSet<Estado> edos, HashSet<Estado> edosAFN, char simb){
        HashSet<Estado> edosIrA = new HashSet<Estado>();
        HashSet<Estado> edosAux = new HashSet<Estado>();
        edosAux = mover(edos, simb);
        for(Estado edoAux : edosAux){
            edosIrA.addAll(cEpsilon(edosAFN, edoAux.getId()));
        }
        return edosIrA;
    }

    public String crearAFD(AFN afn){
        HashSet<Estado> edosAux = new HashSet<Estado>();
        Queue<HashSet<Estado>> colaEdos = new LinkedList<>();
        HashSet<HashSet<Estado>> conjuntoEdos = new HashSet<HashSet<Estado>>();
        ArrayList<Estado> edosAFD = new ArrayList<Estado>();

        HashSet<Estado> edos = cEpsilon(afn.getEdos(), afn.getEdoIn().getId());
        colaEdos.add(edos);
        conjuntoEdos.add(edos);
        Estado edoInAFD = new Estado(false, -1, edos);
        edosAFD.add(edoInAFD);

        while(!colaEdos.isEmpty()){
            edos = colaEdos.poll();
            for(char simb : afn.getAlfabeto()){
                edosAux = irA(edos, afn.getEdos(), simb);
                boolean contiene = false;
                for(HashSet<Estado> edosConjunto : conjuntoEdos){
                    if(edosConjunto.equals(edosAux)){
                        contiene = true;
                    }
                }
                if(!contiene && !edosAux.isEmpty()){
                    colaEdos.add(edosAux);
                    conjuntoEdos.add(edosAux);
                    boolean edoAcept = false;
                    int token = -1;
                    for(Estado edo : edosAux){
                        if(edo.getEdoAcept()){
                            edoAcept = true;
                            token = edo.getToken();
                        }
                    }
                    Estado edoAFD = new Estado(edoAcept, token, edosAux);
                    Estado quitarEdo = new Estado(false, -1);
                    Estado agregarEdo = new Estado(false, -1);
                    edosAFD.add(edoAFD);
                    for(Estado edoAct : edosAFD){
                        if(edoAct.getEdos().equals(edos)){
                            quitarEdo = edoAct;
                            Transicion trans = new Transicion(simb, edosAFD.lastIndexOf(edoAFD));
                            edoAct.setTrans(trans);
                            agregarEdo = edoAct;
                        }
                    }
                    edosAFD.set(edosAFD.indexOf(quitarEdo), agregarEdo);
                }else if(!edosAux.isEmpty()){
                    Estado quitarEdo = new Estado(false, -1);
                    Estado agregarEdo = new Estado(false, -1);
                    for(Estado edoAct : edosAFD){
                        if(edos.equals(edoAct.getEdos())){
                            for(Estado edoTrans : edosAFD){
                                if(edosAux.equals(edoTrans.getEdos())){
                                    quitarEdo = edoAct;
                                    Transicion trans = new Transicion(simb, edosAFD.lastIndexOf(edoTrans));
                                    edoAct.setTrans(trans);
                                    agregarEdo = edoAct;
                                }
                            }
                        }
                    }
                    edosAFD.set(edosAFD.indexOf(quitarEdo), agregarEdo);
                }
            }
        }

        int tabla[][] = new int[edosAFD.size()][128];
        for(int i = 0; i < edosAFD.size(); i++){
            for(int j = 0; j < 128; j++){
                tabla[i][j] = -1;
            }
        }

        for(Estado edo : edosAFD){
            for(Transicion trans : edo.getTrans()){
                tabla[edosAFD.lastIndexOf(edo)][(int) trans.getSimbInf()] = trans.getPosEdo();
            }
                tabla[edosAFD.lastIndexOf(edo)][127] = edo.getToken();
        }

        Scanner in = new Scanner(System.in);
        System.out.println("\nEscribe el nombre para el archivo");
        String nombre = in.nextLine();
        File archivo = new File(nombre+".txt");
        try{
            archivo.createNewFile();
            FileWriter fw = new FileWriter(nombre+".txt");
            for(int i = 0; i < edosAFD.size(); i++){
                String linea = "";
                for(int j = 0; j < 128; j++){
                    linea = linea + tabla[i][j] + ';';
                }
                fw.write(linea + '\n');
            }
            fw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        in.close();

        return nombre;
        /*AFN afd = new AFN();
        edosAFD.clear();
        for(int i = 0; i < tabla.length; i++){
            Estado edo;
            if(tabla[i][127] != -1){
                edo = new Estado(true, tabla[i][127]);
            }else{
                edo = new Estado(false, -1);
            }
            edosAFD.add(edo);
        }
        for(int i = 0; i < edosAFD.size(); i++){
            Estado edo = edosAFD.get(i);
            for(int j = 0; j < 127; j++){
                if(tabla[i][j] != -1){
                    Transicion trans = new Transicion((char) j, edosAFD.get(tabla[i][j]));
                    edo.setTrans(trans);
                    afd.setSimb((char) j);
                }
            }
            if(i == 0){
                afd.setEdoIn(edo);
            }
            if(edo.getEdoAcept()){
                afd.setEdoAcept(edo);
            }
            afd.setEdo(edo);
        }
        return afd;*/
    }
}