/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automato;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gengis
 */
public class Util {
    
    public static Maquina LerAutomato() {
        Maquina maquina = null;
        try {
            Scanner scanner = new Scanner(new FileReader("automato.txt"))
                       .useDelimiter("\\n");
            while (scanner.hasNext()) {
              Set<Estado> estados = LerEstados(scanner.next());
              Set<String> alfabeto = LerAlfabeto(scanner.next());
              Set<Transicao> transicoes = LerTransicoes(scanner.next());
              ConfigurarEstadoInicial(scanner.next(), estados);
              ConfigurarEstadosFinais(scanner.next(), estados);
              maquina = new Maquina(estados, transicoes, alfabeto);
            }
        } catch (MaquinaInvalidaException e){
            throw e;
        }catch (Exception e) {
            LogarErroEmArquivo(e);
            throw new MaquinaInvalidaException("Não foi possível ler o autômato.");
        }
        return maquina;
    }
    
    public static Set<String> LerAlfabeto(String linha) {
        Set<String> alfabeto = null;
        try {
            String[] split = linha.split(",");
            naoDevePossuirCamposVazios(split, "Símbolos do alfabeto");
            alfabeto = new HashSet<>(Arrays.asList(split));
        }catch (MaquinaInvalidaException e){
            LogarErroEmArquivo(e);
            throw e;
        } catch (Exception e) {
            LogarErroEmArquivo(e);
            throw new MaquinaInvalidaException("Não foi possível extrair alfabeto do autômato.");
        }
        return alfabeto;
    }
    
    public static Set<Estado> LerEstados(String linha) {
        Set<Estado> estados = null;
        try {
            String[] simbolos = linha.split(",");
            naoDevePossuirCamposVazios(simbolos, "Símbolos dos estados");
            estados = construirEstadosDeArray(simbolos);
        } catch (MaquinaInvalidaException e){
            LogarErroEmArquivo(e);
            throw e;
        } catch (Exception e) {
            LogarErroEmArquivo(e);
            throw new MaquinaInvalidaException("Não foi possível extrair os estados do autômato.");
        }
        return estados;
    }
    
    public static Set<Transicao> LerTransicoes(String linha){
        Set<Transicao> transicoes = new HashSet<>();
        try {
            String[] transicoesEmTexto = linha.split(";");
            naoDevePossuirCamposVazios(transicoesEmTexto, "Transições");
            for (String elemento : transicoesEmTexto) {
                transicoes.add(construirTransicao(elemento));
            }
            
        } catch (MaquinaInvalidaException e){
            LogarErroEmArquivo(e);
            throw e;
        } catch (Exception e) {
            LogarErroEmArquivo(e);
            throw new MaquinaInvalidaException("Não foi possível extrair as transições do autômato.");
        }
        return transicoes;
    }
    
    public static void ConfigurarEstadoInicial(String linha, Set<Estado> estados) {
        try {
            Estado estado = estados.stream().filter(x -> x.simbolo.equals(linha)).findFirst().get();
            if (estado != null) estado.inicial = true;
        }catch (MaquinaInvalidaException e){
            LogarErroEmArquivo(e);
            throw e;
        } catch (Exception e) {
            LogarErroEmArquivo(e);
            throw new MaquinaInvalidaException("Não foi possível extrair alfabeto do autômato.");
        }
    }

    public static void ConfigurarEstadosFinais(String linha, Set<Estado> estados) {
        try {
            String[] aceitadores = null;
            if (linha.contains(",")) {
                aceitadores = linha.split(",");
                naoDevePossuirCamposVazios(aceitadores, "Estados finais");
            }else{
                if (!linha.isEmpty()){
                    aceitadores = new String[1];
                    aceitadores[0] = linha;
                }
            }
            
            for (String aceitador : aceitadores) {
                Estado estado = estados.stream().filter(x -> x.simbolo.equals(aceitador)).findFirst().get();
                if (estado != null) estado.aceitador = true;
            }
            
        }catch (MaquinaInvalidaException e){
            LogarErroEmArquivo(e);
            throw e;
        } catch (Exception e) {
            LogarErroEmArquivo(e);
            throw new MaquinaInvalidaException("Não foi possível extrair alfabeto do autômato.");
        }
    }
    
    private static void LogarErroEmArquivo(Exception e) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileOutputStream("Log"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        e.printStackTrace(pw);
    }

    private static void naoDevePossuirCamposVazios(String[] values, String campo) {
        for (String element : values) {
            if(element == null || element.trim().isEmpty())throw new MaquinaInvalidaException(campo  + " não podem ser vazios(as).");
        }
    }

    private static Set<Estado> construirEstadosDeArray(String[] simbolos) {
        Set<Estado> estados = new HashSet<>();
        for (String simbolo : simbolos) {
           estados.add(new Estado(simbolo, false, false));
        }
        return estados;
    }

    private static Transicao construirTransicao(String elemento) {
        String[] elementos = elemento.split(",");
        naoDevePossuirCamposVazios(elementos, "Símbolo e estados de uma transação");
        if (elementos.length !=  3) throw new MaquinaInvalidaException("Transição inválida: " +  elemento);
        return new Transicao(elementos[0], elementos[2], elementos[1] );
    }
}
