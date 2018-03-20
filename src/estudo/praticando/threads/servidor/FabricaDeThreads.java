package estudo.praticando.threads.servidor;

import java.util.concurrent.ThreadFactory;

public class FabricaDeThreads implements ThreadFactory {

	private static int numero = 1;

	@Override
	public Thread newThread(Runnable runable) {

		Thread thread = new Thread(runable, "Thread - " + numero + " [ " + runable.getClass().getSimpleName()+" ]");
		
		thread.setUncaughtExceptionHandler(new TratadorDeExcecao());
		thread.setDaemon(true);
		
		numero++;
		
		return 	thread;
	}

}
