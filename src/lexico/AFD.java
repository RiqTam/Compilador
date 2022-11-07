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
                        if(trans.getSimb() == '\0'){
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
                if(trans.getSimb() == simb){
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

    public void crearAFD(AFN afn){
        HashSet<Estado> edos = new HashSet<Estado>();
        HashSet<Estado> edosAux = new HashSet<Estado>();
        Queue<HashSet<Estado>> colaEdos = new LinkedList<>();
        HashSet<HashSet<Estado>> conjuntoEdos = new HashSet<HashSet<Estado>>();
        ArrayList<Estado> edosAFD = new ArrayList<Estado>();

        edos = cEpsilon(afn.getEdos(), afn.getEdoIn().getId());
        colaEdos.add(edos);
        conjuntoEdos.add(edos);
        Estado edoInAFD = new Estado(false, -1, edos);
        edosAFD.add(edoInAFD);

        while(!colaEdos.isEmpty()){
            edos = colaEdos.poll();
            for(char simb : afn.getAlfabeto()){
                edosAux = irA(edos, afn.getEdos(), simb);
                if(!conjuntoEdos.contains(edosAux) && !edosAux.isEmpty()){
                    colaEdos.add(edosAux);
                    conjuntoEdos.add(edosAux);
                    boolean edoAcept = false;
                    int token = -1;
                    for(Estado edo : edosAux){
                        if(edo.getEdoAcept()){
                            edoAcept = true;
                        }
                        if(edo.getToken() != -1){
                            token = edo.getToken();
                        }
                        System.out.println("tiene: "+edo.getId());
                    }
                    Estado edoAFD = new Estado(edoAcept, token, edosAux);
                    edosAFD.add(edoAFD);
                    Estado quitarEdo = new Estado(false, -1);
                    Estado agregarEdo = new Estado(false, -1);
                    for(Estado edoAct : edosAFD){
                        if(edoAct.getEdos().equals(edos)){
                            quitarEdo = edoAct;
                            Transicion trans = new Transicion(simb, edoAFD);
                            edoAct.setTrans(trans);
                            agregarEdo = edoAct;
                        }
                    }
                    edosAFD.remove(quitarEdo);
                    edosAFD.add(agregarEdo);
                }else if(!edosAux.isEmpty()){
                    Estado quitarEdo = new Estado(false, -1);
                    Estado agregarEdo = new Estado(false, -1);
                    for(Estado edoAct : edosAFD){
                        if(edos.equals(edoAct.getEdos())){
                            for(Estado edoTrans : edosAFD){
                                if(edosAux.equals(edoTrans.getEdos())){
                                    quitarEdo = edoAct;
                                    Transicion trans = new Transicion(simb, edoTrans);
                                    edoAct.setTrans(trans);
                                    agregarEdo = edoAct;
                                }
                            }
                        }
                    }
                    edosAFD.remove(quitarEdo);
                    edosAFD.add(agregarEdo);
                }
            }
        }
        for(Estado edo : edosAFD){
            System.out.println("\nEstado: "+edo.getId());
            for(Estado edoP : edo.getEdos()){
                System.out.println("Tiene: "+edoP.getId());
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
                tabla[edosAFD.lastIndexOf(edo)][(int) trans.getSimb()] = edosAFD.lastIndexOf(trans.getEdo());
                tabla[edosAFD.lastIndexOf(edo)][127] = edo.getToken();
            }
        }

        for(int i = 0; i < edosAFD.size(); i++){
            System.out.println("\nEstado "+i);
            for(int j = 0; j < 128; j++){
                System.out.println("\nAscii "+j);
                System.out.println(tabla[i][j]);
            }
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
    }
}