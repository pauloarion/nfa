package automato;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author arion/humberto
 */
class Maquina {
    
    boolean stringPertenceALinguagem = false;
    List<Estado> estados;
    List<Transicao> transicoes;

    public Maquina(List<Estado> estados, List<Transicao> transicoes) {
        this.estados = estados;
        this.transicoes = transicoes;
        Validar();
    }
    
    
    /*Retorna o estado inicial.*/
    Estado EstadoInicial() {
        return estados.stream().filter(x -> x.Inicial()).findFirst().get();
    }

    /*Retorna as transições do estado.*/
    List<Transicao> getTransicoes(Estado estado) {
        return transicoes.stream().filter(x -> x.estadoOrigem.equals(estado)).collect(Collectors.toList());
    }

    public void processar(String input) {
        
        Estado estadoInicial = this.EstadoInicial();
        if (input == null) input = "";
        processarEstado(estadoInicial, input);
        
    }
    
    private void processarEstado(Estado estado, String input) {
        
        /*Chegou ao estado aceitador consumindo todos os símbolos*/
        if ((input.isEmpty()) && estado.Aceitador()){
            this.stringPertenceALinguagem = true;
            return;
        }
        
        /*Se não consumio todos os símbolos ou não está no estado aceitador tentar executar transações. Mesmo estando vazio pode existir alguma transição vazia
        para um estado aceitador.*/
        List<Transicao> transicoes = this.getTransicoes(estado);
        for (Transicao transicao : transicoes) {
            processarTransicao(transicao, input);
        }
    }
    
    private void processarTransicao(Transicao transicao, String input) {
        
        /*Se a transação é vazia, independente dos símbolos que podem existir ou não(sem símbolos, todos consumidos), é realizada a transação para o 
        pŕoximo estado. Porém é verificado primeiro se a transação gera um loop. Gerando loop para o processamento.*/
        if (transicao.isEmpty()){
            if (!loop(transicao)){
                processarEstado(transicao.estadoDestino, input);
            }
            return;
        }
        
        /*Consome símbolo. Se não conseguir consumir o símbolo o processamento acaba.*/
        if (transicao.deveConsumirSimbolo(input)){
            processarEstado(transicao.estadoDestino, input.substring(0, input.length() - 1));
            return;
        }
        
    }

    /*Testa se a transação ao ser executada vai gerar um loop, ou seja, a transação anterior era vazia e o estado de destino e origem são o inverso da 
    transação vazia atual.*/
    private boolean loop(Transicao transicao) {
        if (transicao.transicaoAnterior == null)return false;
        if(transicao.transicaoAnterior.isEmpty() && transicao.transicaoAnterior.isReverse(transicao)){
            return true;
        }return false;
    }

    private void Validar() {
        DevePossuirUmEstadoInicial();
        DevePossuirEstadoFinal();
    }

    private void DevePossuirUmEstadoInicial() {
        int count = estados.stream().filter(x -> x.Inicial()).collect(Collectors.toList()).size();
        if (count == 1) return;
        throw new MaquinaInvalidaException("Autômato deve possuir um estado inicial.");
    }

    private void DevePossuirEstadoFinal() {
        int count = estados.stream().filter(x -> x.Aceitador()).collect(Collectors.toList()).size();
        if (count > 0) return;
        throw new MaquinaInvalidaException("Autômato deve possuir estado(s) aceitador(es).");
    }

    
    
}
