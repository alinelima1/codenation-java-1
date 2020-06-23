package br.com.codenation;

import br.com.codenation.exceptions.CapitaoNaoInformadoException;
import br.com.codenation.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.exceptions.JogadorNaoEncontradoException;
import br.com.codenation.exceptions.TimeNaoEncontradoException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;


public class DesafioMeuTimeApplication implements MeuTimeInterface {

	private ArrayList<Time> time = new ArrayList<Time>();
	private ArrayList<Jogador> jogador = new ArrayList<Jogador>();

	public void incluirTime(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal, String corUniformeSecundario) {
		if(id != null && nome != null && dataCriacao != null && corUniformePrincipal != null && corUniformeSecundario != null){
			if(time.size() > 0){
				int count = 0;
				for(Time t : time){
					if(t.getId().equals(id)){
						throw new IdentificadorUtilizadoException();
					}
				}
			}
			time.add(new Time(id, nome, dataCriacao, corUniformePrincipal, corUniformeSecundario));
		}else{
			throw new UnsupportedOperationException();
		}
	}

	public void incluirJogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario) {
		int count = 0;
		for(Time t : time){
			if(t.getId().equals(idTime)){
				count++;
			}
		}
		if(count == 0) throw new TimeNaoEncontradoException();
		if(id != null && idTime != null && nome != null && dataNascimento  != null && nivelHabilidade  != null && salario != null) {
			if(jogador.size() > 0){
				for(Jogador t : jogador){
					if(t.getId().equals(id)){
						throw new IdentificadorUtilizadoException();
					}
				}
			}
			jogador.add(new Jogador(id, idTime, nome, dataNascimento, nivelHabilidade, salario));
		}else{
			throw new UnsupportedOperationException();
		}
	}

	public void definirCapitao(Long idJogador) {
		int count = 0;
		for(Jogador j : jogador){
			if(j.getId().equals(idJogador)){
				count++;
				j.setNome("Capitao");
			}else{
				j.setNome("Jogador");
			}
		}
		if(count == 0){
			throw new JogadorNaoEncontradoException();
		}
	}

	public Long buscarCapitaoDoTime(Long idTime) {
		int count = 0;
		for(Time t : time){
			if(t.getId().equals(idTime)){
				count++;
			}
		}
		if(count == 0) throw new TimeNaoEncontradoException();

		int capitao = 0;
		for(Jogador j : jogador){
			if(j.getIdTime().equals(idTime)){
				count++;
				if(j.getNome().equals("Capitao")){
					capitao++;
					return j.getId();
				}
			}
		}
		if(capitao == 0) throw new CapitaoNaoInformadoException();
		return null;
	}

	public String buscarNomeJogador(Long idJogador) {
		for(Jogador j : jogador){
			if(j.getId().equals(idJogador)){
				return j.getNome();
			}
		}
		throw new JogadorNaoEncontradoException();
	}

	public String buscarNomeTime(Long idTime) {
		for(Time j : time){
			if(j.getId().equals(idTime)){
				return j.getNome();
			}
		}
		throw new TimeNaoEncontradoException();
	}

	public List<Long> buscarJogadoresDoTime(Long idTime) {
		int count = 0;
		for(Jogador j : jogador){
			if(j.getIdTime().equals(idTime)){
				count++;
			}
		}
		if(count == 0) throw new TimeNaoEncontradoException();
		List<Long> retorno = new ArrayList<Long>();
		if(jogador.size() == 0){
			return new ArrayList<Long>();
		}else{
			for(Jogador t : jogador){
				retorno.add(t.getId());
			}
			return (List<Long>) retorno;
		}
	}

	public Long buscarMelhorJogadorDoTime(Long idTime) {
		long melhorJogador = 0l;
		long habilidade = 0l;
		int time = 0;
		for(Jogador j : jogador){
			if(j.getIdTime().equals(idTime)){
				time++;
				if(j.getNivelHabilidade() > habilidade){
					melhorJogador = j.getId();
					habilidade = j.getNivelHabilidade();
				}
			}
		}
		if(time == 0){
			throw new TimeNaoEncontradoException();
		}else{
			return melhorJogador;
		}
	}

	public Long buscarJogadorMaisVelho(Long idTime) {
		long jogadorMaisVelho = 0l;
		LocalDate idade = LocalDate.now();
		int time = 0;
		for(Jogador j : jogador){
			if(j.getIdTime().equals(idTime)){
				time++;
				if(j.getDataNascimento().isBefore(idade)){
					jogadorMaisVelho = j.getId();
					idade = j.getDataNascimento();
				}
			}
		}
		if(time == 0){
			throw new TimeNaoEncontradoException();
		}else{
			return jogadorMaisVelho;
		}
	}

	public List<Long> buscarTimes() {
		List<Long> retorno = new ArrayList<Long>();
		if(time.size() == 0){
			return new ArrayList<Long>();
		}else{
			for(Time t : time){
				retorno.add(t.getId());
			}
			return (List<Long>) retorno;
		}
	}

	public Long buscarJogadorMaiorSalario(Long idTime) {
		long jog = 0l;
		BigDecimal salario = BigDecimal.valueOf(0);
		int time = 0;
		for(Jogador j : jogador){
			if(j.getIdTime().equals(idTime)){
				time++;
				if(j.getSalario().floatValue() > salario.floatValue()){
					jog = j.getId();
					salario = j.getSalario();
				}
			}
		}
		if(time == 0){
			throw new TimeNaoEncontradoException();
		}else{
			return jog;
		}
	}

	public BigDecimal buscarSalarioDoJogador(Long idJogador) {
		int time = 0;
		for(Jogador j : jogador){
			if(j.getId().equals(idJogador)){
				return j.getSalario();
			}
		}
		throw new JogadorNaoEncontradoException();
	}

	public List<Long> buscarTopJogadores(Integer top) {
		List<Long> topJogadores = new ArrayList<Long>();
		if(jogador.size() == 0){
			return new ArrayList<Long>();
		}else{
			Collections.sort(jogador, new Comparador());
			for(int x = 0; x < top; x++){
				topJogadores.add(jogador.get(x).getId());
			}
			return topJogadores;
		}
	}
}
