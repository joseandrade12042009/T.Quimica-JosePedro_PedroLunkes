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
            default -> String.valueOf(n); // fallback
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
        potenciais.put("Cu", +0.34);
        potenciais.put("Ag", +0.80);
        potenciais.put("Au", +1.50);

        // Cargas iônicas mais comuns
        Map<String, Integer> cargas = new HashMap<>();
        cargas.put("Li", 1);
        cargas.put("K", 1);
        cargas.put("Na", 1);
        cargas.put("Ag", 1);

        cargas.put("Mg", 2);
        cargas.put("Ca", 2);
        cargas.put("Zn", 2);
        cargas.put("Fe", 2);
        cargas.put("Sn", 2);
        cargas.put("Pb", 2);
        cargas.put("Ni", 2);
        cargas.put("Cu", 2);

        cargas.put("Al", 3);
        cargas.put("Au", 3);

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

        double p1 = (estado1.equals("s")? -potenciais.get(e1) : potenciais.get(e1));
        double p2 = (estado2.equals("s")? -potenciais.get(e2) : potenciais.get(e2));

        System.out.println("\nPotenciais escolhidos:");
        System.out.printf("%s (%s) = %.2f V%n", e1, estado1, p1);
        System.out.printf("%s (%s) = %.2f V%n", e2, estado2, p2);

        String reduz, oxida;

        if (p1 > p2) {
            reduz = e1;
            oxida = e2;
        } else if (p1 < p2) {
            reduz = e2;
            oxida = e1;
        } else {
            System.out.println("Os potenciais são iguais, a pilha não funciona.");
            return;
        }

        System.out.println("\n=== Resultado ===");
        System.out.println("A espécie que oxida é: " + oxida);
        System.out.println("A espécie que reduz é: " + reduz);

        int cargaOx = cargas.get(oxida);
        int cargaRed = cargas.get(reduz);

        String ionOx = oxida + potenciar(cargaOx) + "⁺(aq)";
        String ionRed = reduz + potenciar(cargaRed) + "⁺(aq)";

        // Semirreações (com sobrescritos)
        String eqOx = oxida + "(s) → " + ionOx + " + " + cargaOx + "e⁻";
        String eqRed = ionRed + " + " + cargaRed + "e⁻ → " + reduz + "(s)";

        System.out.println("\n=== Semirreações ===");
        System.out.println("Oxidação: " + eqOx);
        System.out.println("Redução : " + eqRed);

        // Balanceamento por elétrons
        int m1 = cargaRed; // multiplicador da oxidação
        int m2 = cargaOx; // multiplicador da redução

        String eqGlobal = m1 + oxida + "(s) + " +
                m2 + ionRed + " → " +
                m1 + ionOx + " + " +
                m2 + reduz + "(s)";

        System.out.println("\n=== Equação Global ===");
        System.out.println(eqGlobal);

        double fem = Math.abs(p1 - p2);
        System.out.printf("\nO Potencial da pilha é %.2f V%n", fem);
    }
}
