package automato;

/**
 *
 * @author arion/humberto
 */
public class Automato {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            
            System.out.println("Lendo arquivo.. ");
            
            Util.LerAutomato();
            
            System.out.println("Arquivo de resultados criado.");
            
        } catch (MaquinaInvalidaException e) {
            System.err.append(e.mensagem);
        }
    }
}
