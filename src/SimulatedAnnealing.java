import java.util.Random;

public class SimulatedAnnealing {
    private int[] board;
    private int n;

    public SimulatedAnnealing(int n) {
        this.n = n;
        board = new int[n];
    }

    public void solve() {
        // Створити випадкове початкове розташування N ферзів на дошці.
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            board[i] = rand.nextInt(n);
        }

        System.out.println("Initial board:");
        for (int i = 0; i < n; i++) {
            System.out.print(board[i] + " ");
        }
        System.out.println("\n");

        System.out.println("Initial board:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i] == j) {
                    System.out.print(" X ");
                } else {
                    System.out.print(" 0 ");
                }
            }
            System.out.println();
        }

        // Обчислити кількість конфліктів для початкового розташування ферзів.
        int conflicts = countConflicts(board);

        // Початкова температура.
        double temp = 1.0;

        // Кінцева температура.
        double minTemp = 0.00001;

        // Коефіцієнт охолодження.
        double coolingRate = 0.999;

        // Повторювати досягнення кінцевої температури.
        while (temp > minTemp) {
            // Випадковим чином обрати ферзя і перемістити його на нову позицію в тому ж рядку.
            int row = rand.nextInt(n);
            int col = rand.nextInt(n);
            int[] newBoard = board.clone();
            newBoard[row] = col;

            // Обчислити кількість конфліктів для нового розташування ферзя.
            int newConflicts = countConflicts(newBoard);

            // Якщо кількість конфліктів для нового розташування менша за кількість конфліктів для поточного розташування, прийняти нове розташування ферзя.
            if (newConflicts < conflicts) {
                board = newBoard;
                conflicts = newConflicts;
            } else {
                // Якщо кількість конфліктів для нового розташування більша за кількість конфліктів для поточного розташування, прийняти нове розташування з деякою ймовірністю, залежною від різниці відносних конфліктів та температури алгоритму.
                double diff = (double) (conflicts - newConflicts) / temp;
                double prob = Math.exp(diff);
                if (rand.nextDouble() < prob) {
                    board = newBoard;
                    conflicts = newConflicts;
                }
            }

            // Зменшити температуру алгоритму.
            temp *= coolingRate;
        }

        // Вивести розв'язок.
        System.out.println("Solution:");
        for (int i = 0; i < n; i++) {
            System.out.print(board[i] + " ");
        }
        System.out.println("\n");

        // Вивести схематичну дошку з розташуванням ферзів.
        System.out.println("Board:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i] == j) {
                    System.out.print(" X ");
                } else {
                    System.out.print(" 0 ");
                }
            }
            System.out.println();
        }
    }

    private int countConflicts(int[] board) {
        int conflicts = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (board[i] == board[j] || Math.abs(i - j) == Math.abs(board[i] - board[j])) {
                    conflicts++;
                }
            }
        }
        return conflicts;
    }

    public static void main(String[] args) {
        SimulatedAnnealing sa = new SimulatedAnnealing(16);
        sa.solve();
    }
}