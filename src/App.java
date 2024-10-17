public class App {
    public static void main(String[] args) {
        // Verifica se os parâmetros foram passados corretamente
        if (args.length != 3) {
            System.out.println("Uso correto: java App <n> <b> <c>");
            return;
        }

        // Lê os valores a partir de args[]
        int n = Integer.parseInt(args[0]);  // Dimensão do piso (n x n)
        int b = Integer.parseInt(args[1]);  // Número de Bigodudos
        int c = Integer.parseInt(args[2]);  // Número de Capetas

        // Inicializa a classe Saloon e executa o backtracking
        Saloon saloon = new Saloon(n, b, c);
        int resultado = saloon.contarConfiguracoesValidas();
        System.out.println("Número de configurações válidas: " + resultado);
    }
}
