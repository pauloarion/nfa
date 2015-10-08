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
              ProcessarStrings(maquina, scanner);
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
            if (linha == null || linha.isEmpty())return transicoes;
            String[] transicoesEmTexto = linha.split(";");
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
            Estado estado = BuscarEstado(estados, linha);
            if (estado != null) estado.inicial = true;
        }catch (MaquinaInvalidaException e){
            LogarErroEmArquivo(e);
            throw e;
        } catch (Exception e) {
            LogarErroEmArquivo(e);
            throw new MaquinaInvalidaException("Não foi possível configurar estado inicial do autômato.");
        }
    }

    private static Estado BuscarEstado(Set<Estado> estados, String linha) {
        try {
            return estados.stream().filter(x -> x.simbolo.equals(linha)).findFirst().get();
        } catch (Exception e) {
            return null;
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
            if(aceitadores == null || aceitadores.length == 0)return;
            for (String aceitador : aceitadores) {
                Estado estado = BuscarEstado(estados, aceitador);
                if (estado != null) {
                    estado.aceitador = true;
                }else{
                    throw new MaquinaInvalidaException("Não foi possível configurar o autômato. Conjunto de estados finais informado não está contido no conjunto de estados do autômato.");
                }
            }
            
        }catch (MaquinaInvalidaException e){
            LogarErroEmArquivo(e);
            throw e;
        } catch (Exception e) {
            LogarErroEmArquivo(e);
            throw new MaquinaInvalidaException("Não foi possível configurar estados finais do autômato.");
        }
    }
    
    private static void LogarErroEmArquivo(Exception e) {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream("Log"));
            e.printStackTrace(pw);
            pw.flush();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    private static void ProcessarStrings(Maquina maquina, Scanner scanner) {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream("Respostas.txt", false));
            pw.println("");
            pw.println("Respostas:");
            while (scanner.hasNext()){
                String next = scanner.next();
                maquina.processar(next);
                pw.println(next + " : " + (maquina.isStringPertenceALinguagem()?"Pertence":"Não pertence"));
            }
            pw.flush();
        } catch (Exception ex) {
            LogarErroEmArquivo(ex);
            ex.printStackTrace(System.err);
        }
    }
}
