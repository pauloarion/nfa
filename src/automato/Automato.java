package automato;

import java.util.List;

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
            String input = args[0];
            List<Estado> estados = null;
            List<Transicao> transicoes = null;
            
            Maquina maquina = new Maquina(estados, transicoes);
            maquina.processar(input);
            
            if (maquina.stringPertenceALinguagem)
                System.out.append("Máquina reconhece a string :" + input);
            else
                System.out.append("Máquina não reconhece a string :" + input);
            
            
        } catch (MaquinaInvalidaException e) {
            System.err.append(e.mensagem);
        }
    }
}
