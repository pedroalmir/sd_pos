package com.paulorego;

import java.io.Serializable;

public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String nome;
	public String cidade;
	public long telefone;
	public int ano;

	public Pessoa(String nome, String cidade, long telefone, int ano) {
		super();
		this.nome = nome;
		this.cidade = cidade;
		this.telefone = telefone;
		this.ano = ano;
	}

	@Override
	public String toString() {
		return this.nome + ", " + this.cidade + ", " + this.telefone + ", " + this.ano;
	}
}
