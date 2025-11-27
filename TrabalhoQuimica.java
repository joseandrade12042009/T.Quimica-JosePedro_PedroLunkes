import java.util.*;

public class TrabalhoQuimica {

    // ============================================
    // Converte entrada para formato químico padrão
    // Ex: cu -> Cu, cU -> Cu, zn -> Zn, FE -> Fe
    // ============================================
    public static String normalizarElemento(String s) {
        if (s == null || s.isEmpty()) return s;
        if (s.length() == 1)
            return s.substring(0, 1).toUpperCase();
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    public static void main(String[] args) {

        // Tabela de potenciais padrão de redução (V)
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

        Scanner sc = new Scanner(System.in);

        System.out.println("=== Tabela Simplificada de Potenciais de Redução ===");
        for (Map.Entry<String, Double> e : potenciais.entrySet()) {
            System.out.printf("%-3s : %5.2f V%n", e.getKey(), e.getValue());
        }

        // Entrada dos elementos com normalização automática
        System.out.println("\nEscolha dois elementos (ex: Cu, Zn, Fe):");
        System.out.print("Elemento 1: ");
        String e1 = normalizarElemento(sc.next());

        System.out.print("Elemento 2: ");
        String e2 = normalizarElemento(sc.next());

        // Verificação
        if (!potenciais.containsKey(e1) || !potenciais.containsKey(e2)) {
            System.out.println("Elemento inválido!");
            return;
        }

        // Escolha dos estados físicos
        System.out.println("\nEscolha o estado físico (s ou aq):");
        System.out.print(e1 + ": ");
        String estado1 = sc.next().toLowerCase();

        System.out.print(e2 + ": ");
        String estado2 = sc.next().toLowerCase();

        if (!estado1.matches("s|aq") || !estado2.matches("s|aq")) {
            System.out.println("Estado físico inválido! Use apenas 's' ou 'aq'.");
            return;
        }

        double p1 = potenciais.get(e1);
        double p2 = potenciais.get(e2);

        System.out.println("\nPotenciais escolhidos:");
        System.out.printf("%s (%s) = %.2f V%n", e1, estado1, p1);
        System.out.printf("%s (%s) = %.2f V%n", e2, estado2, p2);

        // Determinação de quem reduz e quem oxida
        String reduz, oxida;

        if (p1 > p2) {
            reduz = e1;
            oxida = e2;
        } else if (p1 < p2) {
            reduz = e2;
            oxida = e1;
        } else {
            System.out.println("Os potenciais são iguais — não há reação espontânea.");
            return;
        }

        System.out.println("\n=== Resultado ===");
        System.out.println("A espécie que reduz é: " + reduz);
        System.out.println("A espécie que oxida é: " + oxida);

        // Equação global usando estados físicos:
        String oxidaSolido = oxida + "(s)";
        String oxidaIon = oxida + "²⁺(aq)";
        String reduzIon = reduz + "²⁺(aq)";
        String reduzSolido = reduz + "(s)";

        System.out.println("\nEquação global (simplificada):");
        System.out.printf("%s + %s → %s + %s%n",
                oxidaSolido, reduzIon, oxidaIon, reduzSolido);

        double fem = Math.abs(p1 - p2);
        System.out.printf("\nForça eletromotriz (Eº da célula) = %.2f V%n", fem);
    }
}
