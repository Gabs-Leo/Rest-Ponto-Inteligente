package com.gabs.ponto.response;

import java.util.ArrayList;

public class Response<T> {
	private T data;
	private ArrayList<String> erros = new ArrayList<String>();
	
	public Response() {}

	public Response(T data, ArrayList<String> erros) {
		super();
		this.data = data;
		this.erros = erros;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public ArrayList<String> getErros() {
		return erros;
	}

	public void setErros(ArrayList<String> erros) {
		this.erros = erros;
	}
}
