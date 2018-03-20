package estudo.praticando.threads.servidor;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;


public class DistribuirTarefas implements Runnable {

	private Socket socket;
	private ServidorTarefas servidor;
	private ExecutorService threadPool;

	public DistribuirTarefas(ExecutorService threadPool, Socket socket, ServidorTarefas servidor) {
		this.threadPool = threadPool;
		this.socket = socket;
		this.servidor = servidor;
	}

	@Override
	public void run() {

		try {

			System.out.println("Distribuindo as tarefas para o cliente " + socket);

			Scanner entradaCliente = new Scanner(socket.getInputStream());

			PrintStream saidaCliente = new PrintStream(socket.getOutputStream());

			while (entradaCliente.hasNextLine()) {

				String comando = entradaCliente.nextLine();
				System.out.println("Comando recebido " + comando);

				switch (comando) {
				case "c1": {
					// confirmação do o cliente
					saidaCliente.println("Confirmação do comando c1");
					ComandoC1 c1 = new ComandoC1(saidaCliente);
					this.threadPool.execute(c1);
					break;
				}
				case "c2": {
					saidaCliente.println("Confirmação do comando c2");
					ComandoC2 c2 = new ComandoC2(saidaCliente);
					this.threadPool.execute(c2);

					/*
					 * Tarefas com retorno semelhante ao execute. Porém recebe um Callable
					 * Diferentemente do execute() que recebe um Runnable.
					 * 
					 * O método submit não bloqueia a execução e podemos submeter quantas tarefas
					 * quisermos e retorna um Future generics <T> nesse caso uma String.
					 * 
					 * 
					 * Future<String> futureBD = this.threadPool.submit(new
					 * ComandoC2AcessaBanco(saidaCliente)); String resultadoDoCallableBD =
					 * futureBD.get();
					 * 
					 * Future<String> futureWS = this.threadPool.submit(new
					 * ComandoC2WS(saidaCliente)); String resultadoDoCallableWS = futureWS.get();
					 * 
					 * this.threadPool.submit(new JuntaResultadosFutureWSFutureBanco(futureWS,
					 * futureBD, saidaCliente));
					 */
					break;
				}
				case "fim": {
					saidaCliente.println("Desligando o servidor");
					servidor.parar();
					return;
				}
				default: {
					saidaCliente.println("Comando não encontrado");
				}
				}

				System.out.println(comando);
			}

			saidaCliente.close();
			entradaCliente.close();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
