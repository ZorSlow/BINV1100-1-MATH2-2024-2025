import java.lang.reflect.Field;
import java.util.Scanner;

public class TestOrdre {
    private final static Scanner scanner = new Scanner(System.in);
    private static final Class cl = Ordre.class;
    private static Field field;
    private static final String[] NOMS_METHODE = {"estBornee", "tousLesComplements"};

    public static void main(String[] args) throws NoSuchFieldException,
            SecurityException, IllegalArgumentException, IllegalAccessException {
        field = cl.getDeclaredField("couples");
        field.setAccessible(true);
        System.out.println("**************************************");
        System.out.println("Programme Test pour la classe Ordre");
        System.out.println("**************************************");
        int choix = 0;
        while (true) {
            for (int i = 0; i < NOMS_METHODE.length; i++) {
                System.out.println((i + 1) + " -> Tester la méthode '"
                        + NOMS_METHODE[i] + "'");
            }

            choix = scanner.nextInt();
            boolean testOK;
            switch (choix) {
                case 1:
                    testOK = testBornee();
                    break;

                case 2:
                    testOK = testTousLesComplements();
                    break;

                default:
                    return;

            }

            System.out.println();
            if (testOK)
                System.out.println("Le test de la méthode "
                        + NOMS_METHODE[choix - 1] + " a réussi.");
            else
                System.out.println("Le test de la méthode "
                        + NOMS_METHODE[choix - 1] + " a échoué.");
            System.out.println();
        }
    }

    /**
     * Teste la méthode estBornee de la classe Ordre.
     *
     * @return true si le test est réussi, false sinon
     */

    private static boolean testBornee() {
        boolean testOK = true;
        Ordre or1 = new Ordre(Io.chargerRelation("or1.rel"));
        Ordre or2 = new Ordre(Io.chargerRelation("or2.rel"));
        Ordre or4 = new Ordre(Io.chargerRelation("or4.rel"));
        Ordre or5 = new Ordre(Io.chargerRelation("or5.rel"));

        try {
            if (or1.estBornee()) {
                testOK = false;
                System.out.println("La methode estBornee a retourné true alors que or1.rel n'est pas bornée.");
            }

        } catch (Exception e) {
            testOK = false;
            System.out.println("Une exception non attendue a été lancée: ");
            e.printStackTrace(System.out);
        }

        try {
            if (!or2.estBornee()) {
                testOK = false;
                System.out.println("La methode estBornee a retourné false alors que or2.rel est bornée.");
            }

        } catch (Exception e) {
            testOK = false;
            System.out.println("Une exception non attendue a été lancée: ");
            e.printStackTrace(System.out);
        }

        try {
            if (or4.estBornee()) {
                testOK = false;
                System.out.println("La methode estBornee a retourné true alors que or4.rel n'a pas de minimum.");
            }

        } catch (Exception e) {
            testOK = false;
            System.out.println("Une exception non attendue a été lancée: ");
            e.printStackTrace(System.out);
        }

        try {
            if (or5.estBornee()) {
                testOK = false;
                System.out.println("La methode estBornee a retourné true alors que or5.rel n'a pas de maximum.");
            }

        } catch (Exception e) {
            testOK = false;
            System.out.println("Une exception non attendue a été lancée: ");
            e.printStackTrace(System.out);
        }

        return testOK;
    }


    /**
     * Teste la méthode tousLesComplements de la classe Ordre.
     *
     * @return true si le test est réussi, false sinon
     */
    private static boolean testTousLesComplements() {
        boolean testOK = true;
        Ordre or1 = new Ordre(Io.chargerRelation("or1.rel"));
        Ordre or2 = new Ordre(Io.chargerRelation("or2.rel"));
        Ordre or3 = new Ordre(Io.chargerRelation("or3.rel"));
        Ordre or6 = new Ordre(Io.chargerRelation("or6.rel"));

        Ordre or2copy = new Ordre(Io.chargerRelation("or2.rel"));

        // paramètre null
        try {
            Elt paramNull = null;
            or2.tousLesComplements(paramNull);
            testOK = false;
            System.out.println("On a passé un paramètre null à la méthode complement.");
            System.out.println("--> Il fallait lancer une IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            testOK = false;
            System.out.println("Une exception non attendue a été lancée: " );
            e.printStackTrace(System.out);
        }

        // élément qui n'existe pas dans l'ordre
        try {
            Elt e = new Elt(9); // An element that does not exist in the order
            or2.tousLesComplements(e);
            testOK = false;
            System.out.println("On a passé un élément qui n'existe pas dans l'ordre or1.rel à la méthode complement.");
            System.out.println("--> Il fallait lancer une IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            testOK = false;
            System.out.println("Une exception non attendue a été lancée: " );
            e.printStackTrace(System.out);
        }

        // relation non bornée
        try {
            Elt e = new Elt(11); // Une relation qui n'est pas bornée
            or1.tousLesComplements(e);
            testOK = false;
            System.out.println("La relation or1.rel n'est pas bornée.");
            System.out.println("--> Il fallait lancer une MathException");
        } catch (MathException e) {
            // Expected exception
        } catch (Exception e) {
            testOK = false;
            System.out.println("Une exception non attendue a été lancée: " );
            e.printStackTrace(System.out);
        }

        // test avec un élément qui a un complément
        try {
            Elt e = new Elt(38); // An element that has complements
            EnsembleAbstrait complements = or2.tousLesComplements(e);
            EnsembleAbstrait expectedComplements = new Ensemble(new Elt(12));
            if (!complements.equals(expectedComplements)) {
                testOK = false;
                System.out.println("Résultat de la méthode : " + complements);
                System.out.println("Résultat attendu: " + expectedComplements);
            }
            if (!or2.equals(or2copy)) {
                testOK = false;
                System.out.println("La méthode tousLesComplements a modifié l'ordre or2.rel.");
            }
        } catch (Exception e) {
            testOK = false;
            System.out.println("Une exception non attendue a été lancée: " );
            e.printStackTrace(System.out);
        }

        // test avec un élément qui a plusieurs compléments
        try {
            Elt e = new Elt(38); // An element that has complements
            EnsembleAbstrait complements = or3.tousLesComplements(e);
            EnsembleAbstrait expectedComplements = new Ensemble();
            expectedComplements.ajouter(new Elt(39));
            expectedComplements.ajouter(new Elt(14));
            if (!complements.equals(expectedComplements)) {
                testOK = false;
                System.out.println("Résultat de la méthode : " + complements);
                System.out.println("Résultat attendu : " + expectedComplements);
            }
        } catch (Exception e) {
            testOK = false;
            System.out.println("Une exception non attendue a été lancée: " );
            e.printStackTrace(System.out);
        }

        // test avec un élément qui n'a pas de compléments
        try {
            Elt e = new Elt(10); // An element that does not have complements
            EnsembleAbstrait complements = or3.tousLesComplements(e);
            if (!complements.estVide()) {
                testOK = false;
                System.out.println("La méthode tousLesComplements n'a pas retourné un ensemble vide pour un élément qui n'a pas de compléments.");
            }
        } catch (Exception e) {
            testOK = false;
            System.out.println("Une exception non attendue a été lancée: " );
            e.printStackTrace(System.out);
        }

        // élément qui n'a pas de infimum
        try {
            Elt e = new Elt(5);
            or6.tousLesComplements(e);
        } catch (Exception e) {
            testOK = false;
            System.out.println("La méthode n'a pas détecté que l'élément n'as pas de infimum." );
            e.printStackTrace(System.out);
        }

        // élément qui n'a pas de supremum
        try {
            Elt e = new Elt(2);
            or6.tousLesComplements(e);
        } catch (Exception e) {
            testOK = false;
            System.out.println("La méthode n'a pas détecté que l'élément n'as pas de supremum." );
            e.printStackTrace(System.out);
        }

        return testOK;
    }


}
