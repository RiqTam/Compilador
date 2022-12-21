package sintactico;
import lexico.AnalizadorLexico;
import lexico.AFN;
import lexico.Thompson;

public class ERaAFN {
    AnalizadorLexico lexico;
    Thompson thompson = new Thompson();
    String cadena, nombreAFD;
    static final int SIMB = 10;
    static final int OR = 20;
    static final int CONC = 30;
    static final int MAS = 40;
    static final int KLEENE = 50;
    static final int OPC = 60;
    static final int PARENTESIS_IZQ = 70;
    static final int PARENTESIS_DER = 80;
    static final int CORCHETE_IZQ = 90;
    static final int CORCHETE_DER = 100;
    static final int GUION = 110;

    public AFN descRec(String nombreAFD, String cadena){
        lexico = new AnalizadorLexico(nombreAFD, cadena);
        AFN afn = new AFN();
        return E(afn);
    }

    private AFN E(AFN afn){
        afn = T(afn);
        if(afn != null){
            afn = Ep(afn);
            if(afn != null){
                return afn;
            }
        }
        return null;
    }

    private AFN Ep(AFN afn){
        AFN afn2 = new AFN();
        int token = lexico.yylex();
        if(token == OR){
            afn2 = T(afn2);
            if(afn2 != null){
                afn = thompson.unir(afn, afn2);
                afn = Ep(afn);
                if(afn != null){
                    return afn;
                }
                return null;
            }
        }
        lexico.undoToken();
        return afn;
    }

    private AFN T(AFN afn){
        afn = C(afn);
        if(afn != null){
            afn = Tp(afn);
            if(afn != null){
                return afn;
            }
        }
        return null;
    }

    private AFN Tp(AFN afn){
        AFN afn2 = new AFN();
        int token = lexico.yylex();
        if(token == CONC){
            afn2 = C(afn2);
            if(afn2 != null){
                afn = thompson.concatenar(afn, afn2);
                afn = Tp(afn);
                if(afn != null){
                    return afn;
                }
            }
            return null;
        }
        lexico.undoToken();
        return afn;
    }

    private AFN C(AFN afn){
        afn = F(afn);
        if(afn != null){
            afn = Cp(afn);
            if(afn != null){
                return afn;
            }
        }
        return null;
    }

    private AFN Cp(AFN afn){
        int token = this.lexico.yylex();
        switch(token){
            case MAS:
                afn = this.thompson.cMas(afn);
                afn = Cp(afn);
                if(afn != null){
                    return afn;
                }
                return null;
            case KLEENE:
                afn = this.thompson.cKleene(afn);
                afn = Cp(afn);
                if(afn != null){
                    return afn;
                }
                return null;
            case OPC:
                afn = this.thompson.opcional(afn);
                afn = Cp(afn);
                if(afn != null){
                    return afn;
                }
                return null;
        }
        lexico.undoToken();
        return afn;
    }

    private AFN F(AFN afn){
        char simb1, simb2;
        int token = this.lexico.yylex();
        switch(token){
            case PARENTESIS_IZQ:
                afn = E(afn);
                if(afn != null){
                    token = this.lexico.yylex();
                    if(token == PARENTESIS_DER){
                        return afn;
                    }
                }
                return null;
            case CORCHETE_IZQ:
                token = this.lexico.yylex();
                if(token == SIMB){
                    simb1 = lexico.yytext().charAt(lexico.yytext().length() - 1);
                    token = lexico.yylex();
                    if(token == GUION){
                        token = lexico.yylex();
                        if(token == SIMB){
                            simb2 = lexico.yytext().charAt(lexico.yytext().length() - 1);
                            token = lexico.yylex();
                            if(token == CORCHETE_DER){
                                afn = this.thompson.basico(simb1, simb2);
                                return afn;
                            }
                        }
                    }
                }
            return null;
            case SIMB:
                simb1 = lexico.yytext().charAt(lexico.yytext().length() - 1);
                afn = thompson.basico(simb1, simb1);
                return afn;
        }
        return null;
    }
}