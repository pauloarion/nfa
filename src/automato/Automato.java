package automato;

import java.util.List;
import java.util.Scanner;

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
            
            String input = args[0];
            List<Estado> estados = null;
            List<Transicao> transicoes = null;
            
            Maquina maquina = new Maquina(estados, transicoes, new char[]{'a'});
            maquina.processar(input);
            
            if (maquina.isStringPertenceALinguagem())
                System.out.append("Máquina reconhece a string :" + input);
            else
                System.out.append("Máquina não reconhece a string :" + input);
            
            System.out.println("Arquivo de resultados criado.");
            
        } catch (MaquinaInvalidaException e) {
            System.err.append(e.mensagem);
        }
    }
}
