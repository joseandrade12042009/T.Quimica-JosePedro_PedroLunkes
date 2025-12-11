import java.util.*;

public class TrabalhoQuimica {

    final static Scanner sc = new Scanner(System.in);

    public static String normalizarElemento(String s) {
        if (s == null || s.isEmpty())
            return s;
        if (s.length() == 1)
            return s.substring(0, 1).toUpperCase();
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    // Converte número para sobrescrito (1,2,3...)
    public static String potenciar(int n) {
        return switch (n) {
            case 1 -> "¹";
            case 2 -> "²";
            case 3 -> "³";
            case 4 -> "⁴";
            default -> String.valueOf(n);
        };
    }

    public static void main(String[] args) {
        Map<String, Double> potenciais = new LinkedHashMap<>();
        potenciais.put("Li", -3.04);
        potenciais.put("K", -2.93);
        potenciais.put("Ca", -2.87);
        potenciais.put("Na", -2.71);
        potenciais.put("Mg", -2.37);
        potenciais.put("Al", -1.66);
        potenciais.put("Zn", -0.76);
        potenciais.put("Fe", -0.44);
        potenciais.put("Ni", -0.25);
        potenciais.put("Sn", -0.14);
        potenciais.put("Pb", -0.13);
        potenciais.put("H2", 0.00);
        potenciais.put("Cu", 0.34);
        potenciais.put("Ag", 0.80);
        potenciais.put("Au", 1.50);

        // Cargas iônicas mais comuns
        Map<String, Integer> cargas = new HashMap<>();
        cargas.put("Li", 1); cargas.put("K", 1); cargas.put("Na", 1); cargas.put("Ag", 1);
        cargas.put("Mg", 2); cargas.put("Ca", 2); cargas.put("Zn", 2); cargas.put("Fe", 2);
        cargas.put("Sn", 2); cargas.put("Pb", 2); cargas.put("Ni", 2); cargas.put("Cu", 2);
        cargas.put("Al", 3); cargas.put("Au", 3);

        System.out.println("=== Tabela Simplificada de Potenciais de Redução ===");
        for (Map.Entry<String, Double> e : potenciais.entrySet()) {
            System.out.printf("%-3s : %5.2f V%n", e.getKey(), e.getValue());
        }

        System.out.println("\nEscolha dois elementos (ex: Cu, Zn, Fe):");
        System.out.print("Elemento 1: ");
        String e1 = normalizarElemento(sc.next());

        System.out.print("Elemento 2: ");
        String e2 = normalizarElemento(sc.next());

        if (!potenciais.containsKey(e1) || !potenciais.containsKey(e2)) {
            System.out.println("Elemento inválido!");
            return;
        }

        System.out.println("\nEscolha o estado físico (s ou aq):");
        System.out.print(e1 + ": ");
        String estado1 = sc.next().toLowerCase();
        System.out.print(e2 + ": ");
        String estado2 = sc.next().toLowerCase();

        if (!estado1.matches("s|aq") || !estado2.matches("s|aq")) {
            System.out.println("Estado físico inválido! Use apenas 's' ou 'aq'.");
            return;
        }

        if (estado1.equals(estado2)) {
            System.out.println("Inválido! Escolha um sólido e um aquoso.");
            return;
        }

        // Potenciais padrão
        double p1 = potenciais.get(e1);
        double p2 = potenciais.get(e2);

        System.out.println("\nPotenciais escolhidos:");
        System.out.printf("%s (%s) = %.2f V%n", e1, estado1, p1);
        System.out.printf("%s (%s) = %.2f V%n", e2, estado2, p2);

        // Determinar cátodo e ânodo com base no potencial e estado físico
        String catodo, anodo;
        double Ecat, Ean;

        if (estado1.equals("s") && estado2.equals("aq")) {
            anodo = e1; Ean = p1;
            catodo = e2; Ecat = p2;
        } else if (estado2.equals("s") && estado1.equals("aq")) {
            anodo = e2; Ean = p2;
            catodo = e1; Ecat = p1;
        } else {
            System.out.println("Combinação inválida de estados para a pilha.");
            return;
        }

        // Potencial da pilha
        double fem = Ecat - Ean;
        System.out.printf("\nO Potencial da pilha é %.2f V%n", fem);

        if (fem <= 0) {
            System.out.println("\nA pilha não funciona!");
            return;
        } else {
            System.out.println("\nA pilha funciona!");
        }

        System.out.println("\n=== Resultado ===");
        System.out.println("Ânodo (oxida) : " + anodo);
        System.out.println("Cátodo (reduz): " + catodo);

        int cargaAn = cargas.get(anodo);
        int cargaCat = cargas.get(catodo);

        String ionAn = anodo + potenciar(cargaAn) + "⁺(aq)";
        String ionCat = catodo + potenciar(cargaCat) + "⁺(aq)";

        // Semirreações
        String eqOx = anodo + "(s) → " + ionAn + " + " + cargaAn + "e⁻";
        String eqRed = ionCat + " + " + cargaCat + "e⁻ → " + catodo + "(s)";

        System.out.println("\n=== Semirreações ===");
        System.out.println("Oxidação: " + eqOx);
        System.out.println("Redução : " + eqRed);

        // Balanceamento por MMC
        int mmc = lcm(cargaAn, cargaCat);
        int mAn = mmc / cargaAn;
        int mCat = mmc / cargaCat;

        // Equação global
        String eqGlobal =
                (mAn == 1 ? "" : mAn) + anodo + "(s) + " +
                (mCat == 1 ? "" : mCat) + ionCat + " → " +
                (mAn == 1 ? "" : mAn) + ionAn + " + " +
                (mCat == 1 ? "" : mCat) + catodo + "(s)";

        System.out.println("\n=== Equação Global ===");
        System.out.println(eqGlobal);
    }

    public static int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }

    public static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
