/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automato.test;

import automato.Estado;
import automato.MaquinaInvalidaException;
import automato.Transicao;
import automato.Util;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author gengis
 */
public class UtilTest {
    
    @Test
    public void DeveLerAlfabeto(){
        Set<String> alfabeto =  Util.LerAlfabeto("a,b");
        assertEquals(2,alfabeto.size());
        assertTrue(alfabeto.contains("a"));
        assertTrue(alfabeto.contains("b"));
    }
    
    @Test(expected = MaquinaInvalidaException.class)
    public void DeveSoltarExcecaoSeAlfabetoVazio(){
        Set<String> alfabeto =  Util.LerAlfabeto(",a");
    }
    
    @Test
    public void DeveLerEstados(){
        Set<Estado> estados =  Util.LerEstados("A,C");
        assertEquals(2,estados.size());
        Iterator<Estado> iterator = estados.iterator();
        List<String> simbolosDosEstados = new ArrayList<>();
        simbolosDosEstados.add("A");
        simbolosDosEstados.add("C");
        assertTrue(simbolosDosEstados.contains(iterator.next().simbolo));
        assertTrue(simbolosDosEstados.contains(iterator.next().simbolo));
    }
    
    @Test
    public void DeveLerTransicoes(){
        Set<Transicao> transicoes = Util.LerTransicoes("A,b,B;C,d,I");
        assertTrue(transicoes.size() == 2);
        Iterator<Transicao> iterator = transicoes.iterator();
        List<Transicao> transicoesQueDevemExistir = new ArrayList<>();
        transicoesQueDevemExistir.add(new Transicao("A","B","b"));
        transicoesQueDevemExistir.add(new Transicao("C","I","d"));
        assertTrue(transicoesQueDevemExistir.contains(iterator.next()));
        assertTrue(transicoesQueDevemExistir.contains(iterator.next()));
    }
    
    @Test
    public void DeveLerTransicoesVazias(){
        Util.LerTransicoes("");
    }
    
    @Test
    public void DeveSetarInicial(){
        Set<Estado> estados = new HashSet<>();
        estados.add(new Estado("T", false, false));
        Estado inicial = new Estado("Y", false, false);
        estados.add(inicial);
        estados.add(new Estado("T", false, false));
        Util.ConfigurarEstadoInicial("Y", estados);
        assertTrue(inicial.Inicial());
    }
    
    @Test
    public void DeveSetarAceitador(){
        Set<Estado> estados = new HashSet<>();
        estados.add(new Estado("T", false, false));
        Estado aceitador = new Estado("Y", false, false);
        estados.add(aceitador);
        estados.add(new Estado("T", false, false));
        Util.ConfigurarEstadosFinais("Y", estados);
        assertTrue(aceitador.Aceitador());
    }
    
    @Test
    public void DeveSetarAceitadores(){
        Set<Estado> estados = new HashSet<>();
        estados.add(new Estado("M", false, false));
        estados.add(new Estado("Y", false, false));
        estados.add(new Estado("T", false, false));
        Util.ConfigurarEstadosFinais("Y,T", estados);
        assertFalse(estados.stream().filter(x -> x.simbolo.equals("M")).findFirst().get().Aceitador());
        assertTrue(estados.stream().filter(x -> x.simbolo.equals("Y")).findFirst().get().Aceitador());
        assertTrue(estados.stream().filter(x -> x.simbolo.equals("T")).findFirst().get().Aceitador());
    }
    
    @Test
    public void DeveSetarAceitadoresMesmoComEstadoInvalido(){
        Set<Estado> estados = new HashSet<>();
        estados.add(new Estado("M", false, false));
        estados.add(new Estado("J", false, false));
        estados.add(new Estado("T", false, false));
        Util.ConfigurarEstadosFinais("Y,T", estados);
        assertFalse(estados.stream().filter(x -> x.simbolo.equals("M")).findFirst().get().Aceitador());
        assertFalse(estados.stream().filter(x -> x.simbolo.equals("J")).findFirst().get().Aceitador());
        assertTrue(estados.stream().filter(x -> x.simbolo.equals("T")).findFirst().get().Aceitador());
    }
    
}
