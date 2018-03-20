package estudo.praticando.threads.servidor;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class ComandoC2AcessaBanco implements Callable<String> {

	PrintStream saida;

	public ComandoC2AcessaBanco(PrintStream saida) {
		this.saida = saida;
	}

	@Override

	public String call() throws Exception {
		System.out.println("Servidor recebeu comando c2 - WS");

		saida.println("Processando comando c2 - WS");

		Thread.sleep(15000);

		int numero = new Random().nextInt(100) + 1;

		System.out.println("Servidor finalizou comando c2 - WS");

		return Integer.toString(numero);
	}
}