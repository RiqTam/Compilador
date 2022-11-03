package lexico;
import java.util.HashSet;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

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

    public HashSet<Estado> irA(HashSet<Estado> edos, char simb){
        HashSet<Estado> edosIrA = new HashSet<Estado>();
        for(Estado edo : edos){
            edosIrA.addAll(cEpsilon(mover(edos, simb), edo.getId()));
        }
        return edosIrA;
    }

    public void crearAFD(AFN afn){
        HashSet<Estado> edos = new HashSet<Estado>();
        HashSet<Estado> edosAux = new HashSet<Estado>();
        Queue<HashSet<Estado>> colaEdos = new LinkedList<>();
        HashSet<HashSet<Estado>> conjuntoEdos = new HashSet<HashSet<Estado>>();
        HashSet<Estado> edosAFD = new HashSet<Estado>();

        edos = cEpsilon(afn.getEdos(), afn.getEdoIn().getId());
        colaEdos.add(edos);
        conjuntoEdos.add(edos);
        Estado edoInAFD = new Estado(false, -1, edos);
        edosAFD.add(edoInAFD);

        while(!colaEdos.isEmpty()){
            edos = colaEdos.poll();
            for(char simb : afn.getAlfabeto()){
                edosAux = irA(edos, simb);
                if(!conjuntoEdos.contains(edosAux)){
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
                    }
                    Estado edoAFD = new Estado(edoAcept, token, edosAux);
                    edosAFD.add(edoAFD);
                    for(Estado edoAct : edosAFD){
                        edosAFD.remove(edoAct);
                        Transicion trans = new Transicion(simb, edoAFD);
                        edoAct.setTrans(trans);
                        edosAFD.add(edoAct);
                    }
                }else{
                    for(Estado edoAct : edosAFD){
                        if(edos.equals(edoAct.getEdos())){
                            for(Estado edoTrans : edosAFD){
                                if(edosAux.equals(edoTrans.getEdos())){
                                    edosAFD.remove(edoAct);
                                    Transicion trans = new Transicion(simb, edoTrans);
                                    edoAct.setTrans(trans);
                                    edosAFD.add(edoAct);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}