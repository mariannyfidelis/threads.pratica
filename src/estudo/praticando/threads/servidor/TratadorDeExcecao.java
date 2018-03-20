package estudo.praticando.threads.servidor;

import java.lang.Thread.UncaughtExceptionHandler;

public class TratadorDeExcecao implements UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread thread, Throwable excecao) {

		System.out.println("Deu exceção na thread " + thread.getName() + ", " + excecao.getMessage());
	}

}
