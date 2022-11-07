import lexico.Thompson;
import lexico.AFN;
import lexico.AFD;
import java.util.Scanner;
import java.util.ArrayList;

public class Compilador {
    public static void main(String[] args) throws Exception {
        boolean continuar = true;
        int opcion;
        Scanner in = new Scanner(System.in);
        ArrayList<AFN> afns = new ArrayList<AFN>();
        Thompson thomp = new Thompson();
        AFD afd = new AFD();
        while(continuar){
            System.out.println("\nEliga opcion:\n1. Basico\n2. Unir\n3. Concatenar\n4. Cerradura mas\n5. Cerradura de Kleene\n6. Opcional\n7. Convertir a AFD\n0. Salir");
            opcion = in.nextInt();
            int inAFN0, inAFN1;
            switch(opcion){
                case 1:
                    System.out.println("\nIntroduzca el simbolo");
                    char simb = in.next().charAt(0);
                    afns.add(thomp.basico(simb));
                    break;
                case 2:
                    System.out.println("\nIntroduzca el indice del primer AFN");
                    inAFN0 = in.nextInt();
                    System.out.println("\nIntroduzca el indice del segundo AFN");
                    inAFN1 = in.nextInt();
                    afns.set(inAFN0, thomp.unir(afns.get(inAFN0), afns.get(inAFN1)));
                    afns.remove(inAFN1);
                    break;
                case 3:
                    System.out.println("\nIntroduzca el indice del primer AFN");
                    inAFN0 = in.nextInt();
                    System.out.println("\nIntroduzca el indice del segundo AFN");
                    inAFN1 = in.nextInt();
                    afns.set(inAFN0, thomp.concatenar(afns.get(inAFN0), afns.get(inAFN1)));
                    afns.remove(inAFN1);
                    break;
                case 4:
                    System.out.println("\nIntroduzca el indice del AFN");
                    inAFN0 = in.nextInt();
                    afns.set(inAFN0, thomp.cMas(afns.get(inAFN0)));
                    break;
                case 5:
                    System.out.println("\nIntroduzca el indice del AFN");
                    inAFN0 = in.nextInt();
                    afns.set(inAFN0, thomp.cKleene(afns.get(inAFN0)));
                    break;
                case 6:
                    System.out.println("\nIntroduzca el indice del AFN");
                    inAFN0 = in.nextInt();
                    afns.set(inAFN0, thomp.opcional(afns.get(inAFN0)));
                    break;
                case 7:
                    System.out.println("\nIntroduzca el indice del AFN");
                    inAFN0 = in.nextInt();
                    afd.crearAFD(afns.get(inAFN0));
                case 0:
                    System.out.println("\nAdios");
                    continuar = false;
                    break;
            }
        }
    }
}

/*
 * Probar constructores de Thompson
    AFN afnp;
    afnp = afns.get(0);
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