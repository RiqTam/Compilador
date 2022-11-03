package lexico;

public class Thompson {

    public AFN basico(char simb){
        Estado edo1 = new Estado(true, -1);
        Transicion trans = new Transicion(simb, edo1);
        Estado edo0 = new Estado(trans, false, -1);
        AFN afn = new AFN(edo0, edo1);
        afn.setAlfabeto(simb);
        return afn;
    }

    public AFN unir(AFN afn0, AFN afn1){
        Transicion trans0 = new Transicion('\0', afn0.getEdoIn());
        Estado edo0 = new Estado(trans0, false, -1);
        Transicion trans1 = new Transicion('\0', afn1.getEdoIn());
        edo0.setTrans(trans1);
        Estado edo1 = new Estado(true, -1);
        Transicion trans2 = new Transicion('\0', edo1);
        afn0.setEdoIn(edo0);
        afn0.setEdos(edo0);
        for(Estado edoA : afn0.getEdosAcept()){
            edoA.setTrans(trans2);
            edoA.setEdoAcept(false);
            for(Estado edo : afn0.getEdos()){
                if(edoA.getId() == edo.getId()){
                    edo.setEdoAcept(false);
                }
            }
            afn0.removeEdosAcept(edoA);
        }
        for(Estado edoA : afn1.getEdosAcept()){
            edoA.setTrans(trans2);
            edoA.setEdoAcept(false);
            for(Estado edo : afn1.getEdos()){
                if(edoA.getId() == edo.getId()){
                    edo.setEdoAcept(false);
                }
                afn0.setEdos(edo);
            }
        }
        for(char simb : afn1.getAlfabeto()){
            afn0.setAlfabeto(simb);
        }
        afn0.setEdos(edo1);
        afn0.setEdosAcept(edo1);
        return afn0;
    }

    public AFN concatenar(AFN afn0, AFN afn1){
        AFN afn = new AFN(afn0.getEdoIn());
        for(Estado edo : afn0.getEdos()){
            afn.setEdos(edo);
            for(Transicion trans : edo.getTrans()){
                for(Estado edoA : afn0.getEdosAcept()){
                    if(edoA == trans.getEdo()){
                        edoA.setEdoAcept(false);
                        trans.setEdo(afn1.getEdoIn());
                        afn.setEdos(edoA);
                    }
                }
            }
        }
        for(Estado edo : afn1.getEdosAcept()){
            afn.setEdos(edo);
            afn.setEdosAcept(edo);
        }
        for(Estado edo : afn1.getEdos()){
            afn.setEdos(edo);
        }
        for(char simb : afn0.getAlfabeto()){
            afn.setAlfabeto(simb);
        }
        for(char simb : afn1.getAlfabeto()){
            afn.setAlfabeto(simb);
        }
        return afn;
    }

    public AFN cMas(AFN afn){
        Transicion trans0 = new Transicion('\0', afn.getEdoIn());
        Estado edo0 = new Estado(trans0, false, -1);
        afn.setEdoIn(edo0);
        afn.setEdos(edo0);
        Estado edo1 = new Estado(true, -1);
        Transicion trans1 = new Transicion('\0', edo1);
        for(Estado edo : afn.getEdos()){
            for(Transicion trans : edo.getTrans()){
                for(Estado edoA : afn.getEdosAcept()){
                    if(edoA == trans.getEdo()){
                        edoA.setEdoAcept(false);
                        edoA.setTrans(trans0);
                        edoA.setTrans(trans1);
                        afn.setEdos(edoA);
                        afn.removeEdosAcept(edoA);
                    }
                }
            }
        }
        afn.setEdos(edo1);
        afn.setEdosAcept(edo1);
        return afn;
    }

    public AFN cKleene(AFN afn){
        Transicion trans0 = new Transicion('\0', afn.getEdoIn());
        Estado edo0 = new Estado(trans0, false, -1);
        Estado edo1 = new Estado(true, -1);
        Transicion trans1 = new Transicion('\0', edo1);
        edo0.setTrans(trans1);
        afn.setEdoIn(edo0);
        afn.setEdos(edo0);
        afn.setEdos(edo1);
        for(Estado edo : afn.getEdos()){
            for(Transicion trans : edo.getTrans()){
                for(Estado edoA : afn.getEdosAcept()){
                    if(edoA == trans.getEdo()){
                        edoA.setEdoAcept(false);
                        edoA.setTrans(trans0);
                        edoA.setTrans(trans1);
                        afn.setEdos(edoA);
                        afn.removeEdosAcept(edoA);
                    }
                }
            }
        }
        afn.setEdosAcept(edo1);
        return afn;
    }

    public AFN opcional(AFN afn){
        Transicion trans0 = new Transicion('\0', afn.getEdoIn());
        Estado edo0 = new Estado(trans0, false, -1);
        Estado edo1 = new Estado(true, -1);
        Transicion trans1 = new Transicion('\0', edo1);
        edo0.setTrans(trans1);
        afn.setEdoIn(edo0);
        for(Estado edo : afn.getEdos()){
            for(Transicion trans : edo.getTrans()){
                for(Estado edoA : afn.getEdosAcept()){
                    if(edoA == trans.getEdo()){
                        edoA.setEdoAcept(false);
                        edoA.setTrans(trans1);
                        afn.removeEdosAcept(edoA);
                    }
                }
            }
        }
        afn.setEdos(edo0);
        afn.setEdos(edo1);
        afn.setEdosAcept(edo1);
        return afn;
    }
}