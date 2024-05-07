package conta;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import conta.util.Cores;
import conta.controller.ContaController;
import conta.model.ContaCorrente;
import conta.model.ContaPoupanca;

public class Menu {

	public static void main(String[] args) {
		
		ContaController contas = new ContaController();
		
		Scanner leia = new Scanner(System.in);
		
		int opcao, numero, agencia, tipo, aniversario, numeroDestino;
		String titular;
		float saldo, limite, valor = 0.0f;
		
		System.out.println("\nCriar contas\n");
		ContaCorrente cc1 = new ContaCorrente(contas.gerarNumero(), 123, 1, "João da Silva", 1000f, 100.0f);
		contas.cadastrar(cc1);
		
		ContaCorrente cc2 = new ContaCorrente(contas.gerarNumero(), 124, 1, "Maria da Silva", 2000f, 100.0f);
		contas.cadastrar(cc2);
		
		ContaPoupanca cp1 = new ContaPoupanca(contas.gerarNumero(), 125, 2, "Mariana dos Santos", 4000f, 12);
		contas.cadastrar(cp1);
		
		ContaPoupanca cp2 = new ContaPoupanca(contas.gerarNumero(), 125, 2, "Mariana dos Santos", 8000f, 15);
		contas.cadastrar(cp2);
		
		contas.listarTodas();
		
		while (true) {
			
			System.out.println(Cores.TEXT_YELLOW + Cores.ANSI_BLACK_BACKGROUND); // Deixa texto amarelo em fundo preto
			
			System.out.println("╭┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄ · · ✫ · · ┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄╮");
			System.out.println("           ❝ Banco do Brasil com Z ❞            ");
			System.out.println("╰┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄ · · ✫ · · ┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄╯");
			System.out.println("                                               ");
			System.out.println("          1 ➤ Criar conta                      ");
			System.out.println("          2 ➤ Listar todas as contas           ");
			System.out.println("          3 ➤ Buscar conta por número          ");
			System.out.println("          4 ➤ Atualizar dados da conta         ");
			System.out.println("          5 ➤ Apagar conta                     ");
			System.out.println("          6 ➤ Sacar                            ");
			System.out.println("          7 ➤ Depositar                        ");
			System.out.println("          8 ➤ Transferir valores entre contas  ");
			System.out.println("          9 ➤ Sair                             ");
			System.out.println("                                               ");
			System.out.println("— — — — — — — — — — — — — — — — — — — — — — — —");
			System.out.println("Entre com a opção desejada:                    ");
			System.out.println("                                               " + Cores.TEXT_RESET);
			
			// Trata do erro de input inválido.
			try {
				opcao = leia.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("\nPor favor, digite um valor inteiro e válido, conforme o menu.");
				leia.nextLine();
				opcao = 0;
			}
			
			
			// Fecha o programa quando pressionado o número 9.
			if (opcao == 9) {
				System.out.println(Cores.TEXT_WHITE_BOLD + "\nBanco do Brazil com Z - O seu futuro começa aqui!");
				sobre();
				leia.close();
				System.exit(0); 
			}
			
			
			// Funcionalidades da conta
			switch (opcao) {
			case 1:
				System.out.println(Cores.TEXT_WHITE + "Criar conta \n\n");
				
				System.out.println("Digite o número da agência: ");
				agencia = leia.nextInt();
				System.out.println("Digite o número do titular: ");
				leia.skip("\\R?"); // Ignora a entrada que corresponde ao padrão especificado
				titular = leia.nextLine();
				
				do {
					System.out.println("Digite o tipo da conta (1 - Corrente ou 2 - Poupança): ");
					tipo = leia.nextInt();
				} while (tipo < 1 && tipo > 2);
				
				System.out.println("Digite o saldo da conta (R$): ");
				saldo = leia.nextFloat();
				
				switch (tipo) {
				case 1 -> {
					System.out.println("Digite o limite de crédito (R$): ");
					limite = leia.nextFloat();
					contas.cadastrar(new ContaCorrente(contas.gerarNumero(), agencia, tipo, titular, saldo, limite));
				}
				
				case 2 -> {
					System.out.println("Digite o dia do aniversário da conta");
					aniversario = leia.nextInt();
					contas.cadastrar(new ContaPoupanca(contas.gerarNumero(), agencia, tipo, titular, saldo, aniversario));
				}
				}
				
				keyPress();
				break;
				
			case 2: 
				System.out.println(Cores.TEXT_WHITE + "Listar todas as contas\n\n");
				
				contas.listarTodas();
				
				keyPress();
				break;
				
			case 3:
				System.out.println(Cores.TEXT_WHITE + "Consultar dados da conta - por número\n\n");
				
				System.out.println("Digite o número da conta: ");
				numero = leia.nextInt();
				
				contas.procurarPorNumero(numero);
				
				keyPress();
				break;
				
			case 4: 
				System.out.println(Cores.TEXT_WHITE + "Atualizar dados da conta\n\n");
				
				System.out.println("Digite o número da conta: ");
				numero = leia.nextInt();
				
				var buscaConta = contas.buscarNaCollection(numero);
				
				if(buscaConta != null) {
					
					tipo = buscaConta.getTipo();
					
					System.out.println("Digite o número da agência: ");
					agencia = leia.nextInt();
					
					System.out.println("Digite o nome do titular: ");
					leia.skip("\\R?");
					titular = leia.nextLine();
					
					System.out.println("Digite o saldo da conta: ");
					saldo = leia.nextFloat();
					
					switch(tipo) {
					
					case 1 -> {
						System.out.println("Digite o limite de crédito (R$):");
						limite = leia.nextFloat();
						
						contas.atualizar (new ContaCorrente(numero, agencia, tipo, titular, saldo, limite));
					}
					case 2 -> {
						System.out.println("Digite o dia do aniversário da conta: ");
						aniversario = leia.nextInt();
						
						contas.atualizar (new ContaPoupanca(numero, agencia, tipo, titular, saldo, aniversario));
					}
					default -> {
						System.out.println("Tipo de conta inválido!");
					}
					
					}
				} else {
					System.out.println("A conta não foi encontrada. ");
				}
				
				keyPress();
				break;
				
			case 5:
				System.out.println(Cores.TEXT_WHITE + "Apagar conta\n\n");
				
				System.out.println("Digite o número da conta a ser deletada: ");
				numero = leia.nextInt();
				
				contas.deletar(numero);	
				
				keyPress();
				break;
				
			case 6:
				System.out.println(Cores.TEXT_WHITE + "Saque\n\n");
				
				System.out.println("Digite o número da conta: ");
				numero = leia.nextInt();
				
				do {
					System.out.println("Digite o valor do saque (R$): ");
					valor = leia.nextFloat();
				} while (valor <= 0);
				
				contas.sacar(numero, valor);
				
				keyPress();
				break;
				
			case 7:
				System.out.println(Cores.TEXT_WHITE + "Deposito\n\n");
				
				System.out.println("Digite o número da conta: ");
				numero = leia.nextInt();
				
				do {
					System.out.println("Digite o valor do depósito (R$): ");
					numero = leia.nextInt();
				} while (valor <= 0);
				
				contas.depositar(numero, valor);
				
				keyPress();
				break;
				
			case 8:
				System.out.println(Cores.TEXT_WHITE + "Transferência entre contas\n\n");
				
				System.out.println("Digite o número da conta de origem: ");
				numero = leia.nextInt();
				System.out.println("Digite o número da conta de destino: ");
				numeroDestino = leia.nextInt();
				
				do {
					System.out.println("Digite o valor da transferência (R$):");
					valor = leia.nextFloat();
				} while (valor <= 0);
				
				contas.transferir(numero, numeroDestino, valor);
				
				keyPress();
				break;
				
			default:
				System.out.println(Cores.TEXT_RED_BOLD + "\nOpção inválida!\n");
				
				keyPress();
				break;
			
			}

		}

	}
	
	
	// Trata do erro ao pressionar tecla diferente de Enter.
	public static void keyPress() {
		
		try {
			
			System.out.println(Cores.TEXT_RESET + "\n\nPressione Enter para continuar");
			System.in.read();
			
		} catch (IOException e){
			
			System.out.println("Você pressionou uma tecla diferente de Enter.");
			
		}
	}
	
	
		public static void sobre() {
		System.out.println("✦ •····················• ✦ •····················• ✦");
		System.out.println("   ❝ Projeto desenvolvido por Sayuri Cristina ❞    ");
		System.out.println("      ˗ˏˋ sayuri.cristinass@gmail.com ˎˊ˗          ");
		System.out.println("       https://github.com/SayuriCristina           ");
		System.out.println("✦ •····················• ✦ •····················• ✦");
	}

}