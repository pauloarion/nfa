package automato;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author arion/humberto
 */
public class Maquina {
    
    private boolean stringPertenceALinguagem = false;
    private List<Estado> estados;
    private List<Transicao> transicoes;
    public static char EpsilonSymbol = '&';

    public Maquina(List<Estado> estados, List<Transicao> transicoes) {
        this.estados = estados;
        this.transicoes = transicoes;
        Validar();
    }
    
    
    /*Retorna o estado inicial.*/
    Estado EstadoInicial() {
        return getEstados().stream().filter(x -> x.Inicial()).findFirst().get();
    }

    /*Retorna as transições do estado.*/
    List<Transicao> getTransicoes(Estado estado) {
        return getTransicoes().stream().filter(x -> x.estadoOrigem.equals(estado)).collect(Collectors.toList());
    }

    public void processar(String input) {
        
        Estado estadoInicial = this.EstadoInicial();
        if (input == null) input = "";
        processarEstado(estadoInicial, input, null);
        
    }
    
    private void processarEstado(Estado estado, String input, Transicao transicaoAnterior) {
        
        /*Chegou ao estado aceitador consumindo todos os símbolos*/
        if ((input.isEmpty()) && estado.Aceitador()){
            this.stringPertenceALinguagem = true;
            return;
        }
        
        /*Se não consumio todos os símbolos ou não está no estado aceitador tentar executar transações. Mesmo estando vazio pode existir alguma transição vazia
        para um estado aceitador.*/
        List<Transicao> transicoes = this.getTransicoes(estado);
        for (Transicao transicao : transicoes) {
            processarTransicao(transicao, input, transicaoAnterior);
        }
    }
    
    private void processarTransicao(Transicao transicao, String input, Transicao transicaoAnterior) {
        
        /*Se a transação é vazia, independente dos símbolos que podem existir ou não(sem símbolos, todos consumidos), é realizada a transação para o 
        pŕoximo estado. Porém é verificado primeiro se a transação gera um loop. Gerando loop para o processamento.*/
        if (transicao.isEmpty()){
            if (!loop(transicao, transicaoAnterior)){
                processarEstado(transicao.estadoDestino, input, transicao);
            }
            return;
        }
        
        /*Consome símbolo. Se não conseguir consumir o símbolo o processamento acaba.*/
        if (transicao.deveConsumirSimbolo(input)){
            processarEstado(transicao.estadoDestino, input.substring(0, input.length() - 1), transicao);
            return;
        }
        
    }

    /*Testa se a transação ao ser executada vai gerar um loop, ou seja, a transação anterior era vazia e o estado de destino e origem são o inverso da 
    transação vazia atual.*/
    private boolean loop(Transicao transicao, Transicao transicaoAnterior) {
        if (transicaoAnterior == null)return false;
        if(transicaoAnterior.isEmpty() && transicaoAnterior.isReverse(transicao)){
            return true;
        }return false;
    }

    private void Validar() {
        DevePossuirUmEstadoInicial();
        DevePossuirEstadoFinal();
    }

    private void DevePossuirUmEstadoInicial() {
        int count = getEstados().stream().filter(x -> x.Inicial()).collect(Collectors.toList()).size();
        if (count == 1) return;
        throw new MaquinaInvalidaException("Autômato deve possuir um estado inicial.");
    }

    private void DevePossuirEstadoFinal() {
        int count = getEstados().stream().filter(x -> x.Aceitador()).collect(Collectors.toList()).size();
        if (count > 0) return;
        throw new MaquinaInvalidaException("Autômato deve possuir estado(s) aceitador(es).");
    }

    /**
     * @return the stringPertenceALinguagem
     */
    public boolean isStringPertenceALinguagem() {
        return stringPertenceALinguagem;
    }

    /**
     * @return the estados
     */
    public List<Estado> getEstados() {
        return estados;
    }

    /**
     * @param estados the estados to set
     */
    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    /**
     * @return the transicoes
     */
    public List<Transicao> getTransicoes() {
        return transicoes;
    }

    /**
     * @param transicoes the transicoes to set
     */
    public void setTransicoes(List<Transicao> transicoes) {
        this.transicoes = transicoes;
    }

    
    
}
