package automato;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author arion/humberto
 */
public class Maquina {
    
    private boolean stringPertenceALinguagem = false;
    private Set<Estado> estados;
    private Set<String> alfabeto;
    private Set<Transicao> transicoes;
    public static String Epsilon = "&";

    public Maquina(Set<Estado> estados, Set<Transicao> transicoes, Set<String> alfabeto) {
        this.transicoes = new HashSet<>();
        this.alfabeto = new HashSet<>();
        this.estados = new HashSet<>();
        
        for (Estado estado : estados) {
            addEstado(estado);
        }
        for (Transicao transicao : transicoes) {
            addTransicao(transicao);
        }
        for (String simbolo : alfabeto) {
            addSimboloDoAlfabeto(simbolo);
        }
        
        Validar();
    }
    
    boolean possuiEstado(String simbolo){
        return getEstado(simbolo) != null;
    }
    
    Estado getEstado(String simbolo){
        return getEstados().stream().filter(x -> x.simbolo.equals(simbolo)).findFirst().get();
    }
    
    /*Retorna o estado inicial.*/
    Estado EstadoInicial() {
        return getEstados().stream().filter(x -> x.Inicial()).findFirst().get();
    }

    /*Retorna as transições do estado.*/
    List<Transicao> getTransicoes(Estado estado) {
        return getTransicoes().stream().filter(x -> x.estadoOrigem.equals(estado.simbolo)).collect(Collectors.toList());
    }

    public void processar(String input) {
        this.stringPertenceALinguagem = false;
        Estado estadoInicial = this.EstadoInicial();
        if (input == null) input = "";
        processarEstado(estadoInicial, new StringBuilder(input).reverse().toString(), 0);
        
    }
    
    private void processarEstado(Estado estado, String input, int contadorDeTransacoesVazias) {
        
        /*Chegou ao estado aceitador consumindo todos os símbolos*/
        if ((input.isEmpty()) && estado.Aceitador()){
            this.stringPertenceALinguagem = true;
            return;
        }
        
        /*Se não consumio todos os símbolos ou não está no estado aceitador tentar executar transações. Mesmo estando vazio pode existir alguma transição vazia
        para um estado aceitador.*/
        List<Transicao> transicoes = this.getTransicoes(estado);
        for (Transicao transicao : transicoes) {
            processarTransicao(transicao, input, contadorDeTransacoesVazias);
        }
    }
    
    private void processarTransicao(Transicao transicao, String input, int contadorDeTransacoesVazias) {
        
        /*Se a transação é vazia, independente dos símbolos que podem existir ou não(sem símbolos, todos consumidos), é realizada a transação para o 
        pŕoximo estado. Porém é verificado primeiro se a transação gera um loop. Gerando loop para o processamento.*/
        if (transicao.isEmpty()){
            contadorDeTransacoesVazias++;
            if (contadorDeTransacoesVazias > estados.size()){return;}
            processarEstado(getEstado(transicao.estadoDestino), input, contadorDeTransacoesVazias);
            return;
        }
        
        /*Consome símbolo. Se não conseguir consumir o símbolo o processamento acaba.*/
        if (transicao.deveConsumirSimbolo(input)){
            processarEstado(getEstado(transicao.estadoDestino), input.substring(0, input.length() - 1), 0);
            return;
        }
        
    }

    private void Validar() {
        DevePossuirUmEstadoInicial();
        DevePossuirAlfabeto();
        TransicoesDevemPossuirSomenteSimbolosDoAlfabeto();
        TransicoesDevemPossuirSomenteEstadosInformados();
    }

    private void DevePossuirUmEstadoInicial() {
        int count = getEstados().stream().filter(x -> x.Inicial()).collect(Collectors.toList()).size();
        if (count == 1) return;
        throw new MaquinaInvalidaException("Autômato deve possuir um estado inicial.");
    }
    
    private void DevePossuirAlfabeto() {
        if (alfabeto.isEmpty())throw new MaquinaInvalidaException("Autômato deve possuir alfabeto.");
    }

    private void TransicoesDevemPossuirSomenteSimbolosDoAlfabeto() {
        for (Transicao transicao : transicoes) {
            if ( !alfabeto.contains(transicao.simbolo) && !transicao.simbolo.equals(Maquina.Epsilon))
                throw new MaquinaInvalidaException("Autômato deve possuir somente transições com símbolos do alfabeto ou com símbolo da string vazia.");
        }
    }
    
    private void TransicoesDevemPossuirSomenteEstadosInformados() {
        for (Transicao transicao : transicoes) {
            if (!possuiEstado(transicao.estadoOrigem) || !possuiEstado(transicao.estadoDestino))
                throw new MaquinaInvalidaException("Autômato deve possuir somente transições com os estados definidos.");
        }
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
    public Set<Estado> getEstados() {
        return estados;
    }
    
    /** 
     * @return the transicoes
     */
    public Set<Transicao> getTransicoes() {
        return transicoes;
    }

    /**
     * @return the alfabeto
     */
    public Set<String> getAlfabeto() {
        return alfabeto;
    }
    
    private void addEstado(Estado estado) {
        if(!estados.contains(estado))estados.add(estado);
    }

    private void addTransicao(Transicao transicao) {
        if (!transicoes.contains(transicao))transicoes.add(transicao);
    }

    private void addSimboloDoAlfabeto(String simbolo) {
        if (!alfabeto.contains(simbolo))alfabeto.add(simbolo);
    }
}
