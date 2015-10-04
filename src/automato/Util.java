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
import java.util.List;
import java.util.Scanner;
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
              List<Estado> estados = LerEstados(scanner.next());
              char[] alfabeto = LerAlfabeto(scanner.next());
              List<Transicao> transicoes = LerTransicoes(scanner.next());
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
    
    public static char[] LerAlfabeto(String linha) {
        char[] alfabeto = null;
        try {
            
        } catch (Exception e) {
            LogarErroEmArquivo(e);
            throw new MaquinaInvalidaException("Não foi possível extrair alfabeto do autômato.");
        }
        return alfabeto;
    }
    
    public static List<Estado> LerEstados(String linha) {
        List<Estado> estados = null;
        try {
            
        } catch (Exception e) {
            LogarErroEmArquivo(e);
            throw new MaquinaInvalidaException("Não foi possível extrair os estados do autômato.");
        }
        return estados;
    }
    
    public static List<Transicao> LerTransicoes(String linha){
        List<Transicao> transicoes = null;
        try {
            
        } catch (Exception e) {
            LogarErroEmArquivo(e);
            throw new MaquinaInvalidaException("Não foi possível extrair as transições do autômato.");
        }
        return transicoes;
    }
    
    private static void ConfigurarEstadoInicial(String next, List<Estado> estados) {
    }

    private static void ConfigurarEstadosFinais(String next, List<Estado> estados) {
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

    static boolean contains(char[] alfabeto, char simboloParaConsumir) {
        for (char element : alfabeto) {
            if (element == simboloParaConsumir)return true;
        }
        return false;
    }
}
