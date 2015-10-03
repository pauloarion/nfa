package automato;

/**
 *
 * @author arion/humberto
 */
class Transicao {
    
    Estado estadoOrigem;
    Estado estadoDestino;
    char simboloParaConsumir;
    Transicao transicaoAnterior;
    
    /*Foi definido que o símbolo que representa transição vazia é o '&'.*/
    boolean isEmpty() {
        if (simboloParaConsumir == '&')return true;
        else return false;
    }

    boolean deveConsumirSimbolo(String input) {
        
        /*Se não existem símbolos para consumir então o processamento acaba.*/
        if (input.isEmpty()) return false;
        
        char simboloConsumido = input.charAt(input.length() - 1);
        /*Se os símbolos para consumir e o consumido são iguais.*/
        if (simboloParaConsumir == simboloConsumido) return true;
        /*Se os símbolos para consumir e o consumido não são iguais.*/
        return false;
        
    }

    /*Transação atual é uma transação inversa da transação testada.*/
    boolean isReverse(Transicao transicao) {
        return this.estadoDestino.equals(transicao.estadoOrigem) && this.estadoOrigem.equals(transicao.estadoDestino);
    }
    
}
