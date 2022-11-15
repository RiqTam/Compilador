import lexico.Thompson;
import lexico.AFN;
import lexico.AnalizadorLexico;
import lexico.Estado;
import lexico.AFD;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashSet;

public class Compilador {
    public static void main(String[] args) throws Exception {
        boolean continuar = true;
        int opcion;
        Scanner in = new Scanner(System.in);
        ArrayList<AFN> afns = new ArrayList<AFN>();
        ArrayList<String> afds = new ArrayList<String>();
        Thompson thomp = new Thompson();
        AFD afd = new AFD();
        while(continuar){
            System.out.println("\nElija opci\u00f3n:\n1. B\u00e1sico\n2. Unir\n3. Concatenar\n4. Cerradura transitiva" +
            "\n5. Cerradura de Kleene\n6. Opcional\n7. Unir AFNs\n8. Convertir a AFD\n9. Cargar AFD\n10. Probar analizador l\u00e9xico\n0. Salir");
            System.out.println("Hay " + afns.size() + " AFNs y " + afds.size() + " AFDs");
            opcion = in.nextInt();
            in.nextLine();
            int inAFN0, inAFN1;
            switch(opcion){
                case 1:
                    System.out.println("\nIntroduzca el simbolo inferior");
                    char simbInf = in.next().charAt(0);
                    System.out.println("\nIntroduzca el simbolo superior");
                    char simbSup = in.next().charAt(0);
                    afns.add(thomp.basico(simbInf, simbSup));
                    break;
                case 2:
                    System.out.println("\nIntroduzca el \u00edndice del primer AFN");
                    inAFN0 = in.nextInt();
                    System.out.println("\nIntroduzca el \u00edndice del segundo AFN");
                    inAFN1 = in.nextInt();
                    afns.set(inAFN0, thomp.unir(afns.get(inAFN0), afns.get(inAFN1)));
                    afns.remove(inAFN1);
                    break;
                case 3:
                    System.out.println("\nIntroduzca el \u00edndice del primer AFN");
                    inAFN0 = in.nextInt();
                    System.out.println("\nIntroduzca el \u00edndice del segundo AFN");
                    inAFN1 = in.nextInt();
                    afns.set(inAFN0, thomp.concatenar(afns.get(inAFN0), afns.get(inAFN1)));
                    afns.remove(inAFN1);
                    break;
                case 4:
                    System.out.println("\nIntroduzca el \u00edndice del AFN");
                    inAFN0 = in.nextInt();
                    afns.set(inAFN0, thomp.cMas(afns.get(inAFN0)));
                    break;
                case 5:
                    System.out.println("\nIntroduzca el \u00edndice del AFN");
                    inAFN0 = in.nextInt();
                    afns.set(inAFN0, thomp.cKleene(afns.get(inAFN0)));
                    break;
                case 6:
                    System.out.println("\nIntroduzca el \u00edndice del AFN");
                    inAFN0 = in.nextInt();
                    afns.set(inAFN0, thomp.opcional(afns.get(inAFN0)));
                    break;
                case 7:
                    HashSet<AFN> agregarAFNs = new HashSet<AFN>();
                    ArrayList<AFN> seleccion = new ArrayList<AFN>();
                    int indice = -2;
                    while(indice != -1){
                        System.out.println("\nIntroduce el \u00edndice del AFN a unir\nIntroduce -1 para terminar");
                        indice = in.nextInt();
                        if(indice > -1){
                            seleccion.add(afns.get(indice));
                        }
                    }
                    for(AFN afn : seleccion){
                        System.out.println("\nIntroduzca el token del AFN " + afns.indexOf(afn));
                        int token = in.nextInt();
                        HashSet<Estado> eliminarEdos = new HashSet<Estado>();
                        HashSet<Estado> agregarEdos = new HashSet<Estado>();
                        for(Estado edoAcept : afn.getEdosAcept()){
                            eliminarEdos.add(edoAcept);
                            edoAcept.setToken(token);
                            agregarEdos.add(edoAcept);
                        }
                        for(Estado edo : eliminarEdos){
                            afn.removeEdo(edo);
                            afn.removeEdoAcept(edo);
                        }
                        for(Estado edo : agregarEdos){
                            afn.setEdo(edo);
                            afn.setEdoAcept(edo);
                        }
                        agregarAFNs.add(afn);
                    }
                    afns.removeAll(seleccion);
                    afns.add(afd.unirAFNs(agregarAFNs));
                    break;
                case 8:
                    System.out.println("\nIntroduzca el \u00edndice del AFN");
                    inAFN0 = in.nextInt();
                    afds.add(afd.crearAFD(afns.get(inAFN0)));
                    break;
                case 9:
                    System.out.println("\nIntroduzca el nombre del AFD");
                    afds.add(in.nextLine());
                    break;
                case 10:
                    System.out.println("\nIntroduzca el \u00edndice del AFD");
                    inAFN0 = in.nextInt();
                    in.nextLine();
                    System.out.println("\nIntroduzca la cadena a analizar");
                    AnalizadorLexico lexico = new AnalizadorLexico(afds.get(inAFN0), in.nextLine());
                    boolean continuarLexema = true;
                    while(continuarLexema){
                        int token = lexico.yylex();
                        System.out.println("Token: " + token);
                        System.out.println("Lexema: " + lexico.getLexema() + "\n");
                        if(token == 0 || token == -10){
                            continuarLexema = false;
                        }
                    }
                    break;
                case 0:
                    System.out.println("\nAdi\u00f3s");
                    continuar = false;
                    break;
            }
        }
    }
}

/*
 * Probar constructores de Thompson
    AFN afnp;
    afnp = afns.get(inAFN0);
    System.out.println("\nEstado inicial: "+afnp.getEdoIn().getId());
    for(Estado edo : afnp.getEdos()){
        for(Transicion trans : edo.getTrans()){
            if(trans != null){
                Estado edoT = trans.getEdo();
                System.out.println("\nEstado "+edo.getId()+"\nTransición a "+edoT.getId());
                System.out.println("Simbolo: "+trans.getSimb());
            }
        }
    }
    for(Estado edo : afnp.getEdosAcept()){
        System.out.println("\nEstado de aceptacion: "+edo.getId());
    }
 */