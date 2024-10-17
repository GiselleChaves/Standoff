public class Saloon {
    private int n;  // Dimensão do tabuleiro
    private int b;  // Número de Bigodudos
    private int c;  // Número de Capetas
    private int[][] board;  // Tabuleiro n x n

    // Construtor da classe Saloon
    public Saloon(int n, int b, int c) {
        this.n = n;
        this.b = b;
        this.c = c;
        this.board = new int[n][n];  // Inicializa o tabuleiro vazio
    }

    // Função de backtracking que conta as configurações válidas
    public int contarConfiguracoesValidas() {
        return backtracking(0, 0, b, c);
    }

    // Módulo: Função de backtracking
    private int backtracking(int row, int col, int remainingB, int remainingC) {
        if (remainingB == 0 && remainingC == 0) {
            return validarConfiguracaoFinal() ? 1 : 0;
        }
        if (row == n) return 0; // Se ultrapassarmos as linhas, retornamos 0

        int nextRow = (col == n - 1) ? row + 1 : row;  // Próxima linha
        int nextCol = (col + 1) % n;  // Próxima coluna

        int count = 0;

        // Tenta colocar um Bigodudo
        if (remainingB > 0 && posicaoValida(row, col, 1)) {
            board[row][col] = 1;  // Coloca Bigodudo
            count += backtracking(nextRow, nextCol, remainingB - 1, remainingC);
            board[row][col] = 0;  // Desfaz a colocação
        }

        // Tenta colocar um Capeta
        if (remainingC > 0 && posicaoValida(row, col, 2)) {
            board[row][col] = 2;  // Coloca Capeta
            count += backtracking(nextRow, nextCol, remainingB, remainingC - 1);
            board[row][col] = 0;  // Desfaz a colocação
        }

        // Tenta não colocar ninguém
        count += backtracking(nextRow, nextCol, remainingB, remainingC);

        return count;
    }

    // Verifica se a posição é válida
    private boolean posicaoValida(int row, int col, int tipo) {
        for (int i = 0; i < n; i++) {
            if (board[row][i] == tipo || board[i][col] == tipo) return false;
        }
        // Verifica diagonais
        for (int i = 1; i < n; i++) {
            if ((isDentroTabuleiro(row + i, col + i) && board[row + i][col + i] == tipo) ||
                    (isDentroTabuleiro(row - i, col - i) && board[row - i][col - i] == tipo) ||
                    (isDentroTabuleiro(row - i, col + i) && board[row - i][col + i] == tipo) ||
                    (isDentroTabuleiro(row + i, col - i) && board[row + i][col - i] == tipo)) {
                return false;
            }
        }
        return true;  // Se nenhuma condição foi violada, a posição é válida
    }

    // Verifica se as coordenadas estão dentro do tabuleiro
    private boolean isDentroTabuleiro(int row, int col) {
        return row >= 0 && row < n && col >= 0 && col < n;
    }

    // Verifica se a configuração final é válida
    private boolean validarConfiguracaoFinal() {
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (board[row][col] == 1 || board[row][col] == 2) {
                    int inimigosVisiveis = contarInimigos(row, col, board[row][col]);
                    if (inimigosVisiveis < 2) return false;
                }
            }
        }
        return true;  // Todos os pistoleiros veem pelo menos 2 inimigos
    }

    // Contar inimigos visíveis a partir de uma posição (mesma linha, coluna, diagonal)
    private int contarInimigos(int row, int col, int tipo) {
        int inimigos = 0;
        int inimigoTipo = (tipo == 1) ? 2 : 1;  // Define o tipo do inimigo

        // Conta inimigos na mesma linha e coluna
        for (int i = 0; i < n; i++) {
            if (board[row][i] == inimigoTipo) inimigos++;  // Linha
            if (board[i][col] == inimigoTipo) inimigos++;  // Coluna
        }

        // Conta inimigos nas diagonais
        for (int i = 1; i < n; i++) {
            if (isDentroTabuleiro(row + i, col + i) && board[row + i][col + i] == inimigoTipo) inimigos++;
            if (isDentroTabuleiro(row - i, col - i) && board[row - i][col - i] == inimigoTipo) inimigos++;
            if (isDentroTabuleiro(row + i, col - i) && board[row + i][col - i] == inimigoTipo) inimigos++;
            if (isDentroTabuleiro(row - i, col + i) && board[row - i][col + i] == inimigoTipo) inimigos++;
        }

        return inimigos;
    }
}
