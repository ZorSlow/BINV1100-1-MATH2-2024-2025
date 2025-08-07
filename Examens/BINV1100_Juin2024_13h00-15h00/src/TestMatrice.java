import java.util.*;
import java.lang.reflect.Field;

public class TestMatrice {
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    private static Class classe = Matrice.class;
    private static Field data;
    private static Field nbL;
    private static Field nbC;
    private static String[] NOMS_METHODES = {"constructeur Matrice(Matrice h, Matrice b)"};

    private static void preparerField() {
        Field[] champs = classe.getDeclaredFields();
        for (Field f : champs) {
            if (f.getName().equals("data")) {
                data = f;
                data.setAccessible(true);
            } else if (f.getName().equals("nbLignes")) {
                nbL = f;
                nbL.setAccessible(true);
            } else if (f.getName().equals("nbColonnes")) {
                nbC = f;
                nbC.setAccessible(true);
            }
        }
    }

    public static void main(String[] args) throws IllegalAccessException {
        System.out.println("*****************************************");
        System.out.println("* Programme Test pour la classe Matrice *");
        System.out.println("*****************************************");
        preparerField();
        System.out.println() ;
        System.out.println("=====================================================") ;
        System.out.println(" Tests du "+NOMS_METHODES[0]) ;
        System.out.println("=====================================================") ;

        boolean testOK = testConstructeur2Matrices();

        if (testOK)
            System.out.println("Les tests du " + NOMS_METHODES[0]+" ont réussi.");
        else
            System.out.println("Les tests du " + NOMS_METHODES[0]+ " ont échoué.");
    }

    public static boolean testConstructeur2Matrices() throws IllegalAccessException {
        boolean testOK = true ;
        System.out.println() ;

        System.out.print("Test 1 : ");

        double[][] hTest = null ;
        double[][] hTestI = null ;
        double[][] bTest =  {{0, 1}, {-1, 0}} ;
        double[][] bTestI =  {{0, 1}, {-1, 0}} ;
        Matrice h = null ;
        Matrice b = new Matrice (2,2) ;
        Matrice m = null ;

        try {
            data.set(b,bTest) ;
            m = new Matrice(h,b) ;
            System.out.println("KO : il fallait une exception car h est null") ;
            testOK = false ;
        } catch(IllegalArgumentException e) {
            double[][] dataD = (double[][]) data.get(b);
            if (!Arrays.deepEquals(bTestI,dataD)) {
                System.out.println("KO : vous avez modifié la matrice b passée en paramètre mais il ne fallait pas.") ;
                testOK = false ;
            } else if (nbL.getInt(b)!=2) {
                System.out.println("KO : vous avez modifié le nombre de lignes de la matrice b mais il ne fallait pas.") ;
                testOK = false ;
            } else if (nbC.getInt(b)!=2) {
                System.out.println("KO : vous avez modifié le nombre de colonnes de la matrice b mais il ne fallait pas.");
                testOK = false;
            } else {
                System.out.println("OK") ;
            }
        } catch(Exception e) {
            System.out.println("Mauvais type d'exception : Attendu IllegalArgumentException mais reçu : ") ;
            System.out.println(RED) ;
            e.printStackTrace(System.out);
            System.out.println(RESET);
            testOK = false;
        }

        System.out.println() ;

        System.out.print("Test 2 : ");

        double[][] hTest2 = {{0, 1}, {-1, 0}} ;
        double[][] hTestI2 = {{0, 1}, {-1, 0}};
        bTest =  null ;
        bTestI =  null ;
        h = new Matrice(2,2) ;
        b = null ;
        m = null ;

        try {
            data.set(h,hTest2) ;
            m = new Matrice(h,b) ;
            System.out.println("KO : il fallait une exception car b est null") ;
            testOK = false ;
        } catch(IllegalArgumentException e) {
            double[][] dataG = (double[][]) data.get(h);
            if (!Arrays.deepEquals(hTestI2,dataG)) {
                System.out.println("KO : vous avez modifié la matrice h passée en paramètre mais il ne fallait pas.") ;
                testOK = false ;
            } else if (nbL.getInt(h)!=2) {
                System.out.println("KO : vous avez modifié le nombre de lignes de la matrice h mais il ne fallait pas.") ;
                testOK = false ;
            } else if (nbC.getInt(h)!=2) {
                System.out.println("KO : vous avez modifié le nombre de colonnes de la matrice h mais il ne fallait pas.");
                testOK = false;
            } else {
                System.out.println("OK") ;
            }
        } catch(Exception e) {
            System.out.println("Mauvais type d'exception : Attendu IllegalArgumentException mais reçu : ") ;
            System.out.println(RED) ;
            e.printStackTrace(System.out);
            System.out.println(RESET);
            testOK = false;
        }

        System.out.println() ;

        System.out.print("Test 3 : ");

        double[][] hTest2b = {{0,-1,2},{1, 0,-3}} ;
        double[][] hTest2bI = {{0,-1,2},{1, 0,-3}};
        double[][] bTest2b  =  {{2},{3}} ;
        double[][] bTest2bI =  {{2},{3}} ;
        h = new Matrice(2,3) ;
        b = new Matrice(2,1) ;
        m = null ;

        try {
            data.set(h,hTest2b) ;
            data.set(b,bTest2b) ;
            m = new Matrice(h,b) ;
            System.out.println("KO : il fallait une exception car les matrices b et h ne peuvent pas être fusionnées") ;
            testOK = false ;
        } catch(IllegalArgumentException e) {
            double[][] dataH = (double[][]) data.get(h);
            double[][] dataB = (double[][]) data.get(b);
            if (!Arrays.deepEquals(hTest2bI,dataH)) {
                System.out.println("KO : vous avez modifié la matrice h passée en paramètre mais il ne fallait pas.") ;
                testOK = false ;
            } else if (nbL.getInt(h)!=2) {
                System.out.println("KO : vous avez modifié le nombre de lignes de la matrice h mais il ne fallait pas.") ;
                testOK = false ;
            } else if (nbC.getInt(h)!=3) {
                System.out.println("KO : vous avez modifié le nombre de colonnes de la matrice h mais il ne fallait pas.");
                testOK = false;
            } else if (!Arrays.deepEquals(bTest2bI,dataB)) {
                System.out.println("KO : vous avez modifié la matrice b passée en paramètre mais il ne fallait pas.") ;
                testOK = false ;
            } else if (nbL.getInt(b)!=2) {
                System.out.println("KO : vous avez modifié le nombre de lignes de la matrice b mais il ne fallait pas.") ;
                testOK = false ;
            } else if (nbC.getInt(b)!=1) {
                System.out.println("KO : vous avez modifié le nombre de colonnes de la matrice b mais il ne fallait pas.");
                testOK = false;
            } else {
                System.out.println("OK") ;
            }
        } catch(Exception e) {
            System.out.println("Mauvais type d'exception : Attendu IllegalArgumentException mais reçu : ") ;
            System.out.println(RED) ;
            e.printStackTrace(System.out);
            System.out.println(RESET);
            testOK = false;
        }

        System.out.println() ;

        System.out.print("Test 4 : ");

        double[][] hTest3 = {{1,0}, {20, -3},{-2,2}} ;
        double[][] hTestI3 = {{1,0}, {20, -3},{-2,2}} ;
        double[][] bTest3 =  {{4, -3}, {-2,0}} ;
        double[][] bTestI3 =  {{4, -3}, {-2,0}} ;
        double[][] mAtt = {{1,0},{20,-3},{-2,2},{4,-3}, {-2,0}} ;
        Matrice mAttendue = new Matrice(5,2);
        Matrice hi = new Matrice(3,2);
        Matrice bi = new Matrice (2,2) ;
        h = new Matrice(3,2) ;
        b = new Matrice(2,2) ;

        m = null ;

        try {
            data.set(h,hTest3) ;
            data.set(hi,hTestI3) ;
            data.set(b,bTest3) ;
            data.set(bi,bTestI3) ;
            data.set(mAttendue,mAtt) ;
            m = new Matrice(h,b) ;
            double[][] dataM = (double[][]) data.get(m);
            double[][] dataH = (double[][]) data.get(h);
            double[][] dataB = (double[][]) data.get(b) ;
            if (!Arrays.deepEquals(mAtt,dataM)) {
                System.out.println("KO : La matrice créée n'est pas la bonne") ;
                System.out.println("Matrices passées en paramètres : ") ;
                System.out.println(hi) ;
                System.out.println(bi) ;
                System.out.println("Matrice attendue : ") ;
                System.out.println(mAttendue) ;
                System.out.println("Matrice recue : ") ;
                System.out.println(m);
                testOK = false ;
            } else if (nbL.getInt(m)!=5) {
                System.out.println("KO : vous avez mal initialisé nbLignes : attendu 5 mais recu "+nbL.getInt(m)) ;
                testOK = false ;
            } else if (nbC.getInt(m)!=2) {
                System.out.println("KO : vous avez mal initialisé nbColonnes : attendu 2 mais recu "+nbC.getInt(m));
                testOK = false;
            } else if (!Arrays.deepEquals(hTestI3,dataH)) {
                System.out.println("KO : vous avez modifié la matrice h passée en paramètre mais il ne fallait pas.") ;
                testOK = false ;
            } else if (nbL.getInt(h)!=3) {
                System.out.println("KO : vous avez modifié le nombre de lignes de la matrice h mais il ne fallait pas.") ;
                testOK = false ;
            } else if (nbC.getInt(h)!=2) {
                System.out.println("KO : vous avez modifié le nombre de colonnes de la matrice h mais il ne fallait pas.");
                testOK = false;
            } else if (!Arrays.deepEquals(bTestI3,dataB)) {
                System.out.println("KO : vous avez modifié la matrice b passée en paramètre mais il ne fallait pas.") ;
                testOK = false ;
            } else if (nbL.getInt(b)!=2) {
                System.out.println("KO : vous avez modifié le nombre de lignes de la matrice b mais il ne fallait pas.") ;
                testOK = false ;
            } else if (nbC.getInt(b)!=2) {
                System.out.println("KO : vous avez modifié le nombre de colonnes de la matrice b mais il ne fallait pas.");
                testOK = false;
            } else {
                System.out.println("OK") ;
            }
        } catch(Exception e) {
            System.out.println("KO : il ne fallait pas d'exception") ;
            System.out.println(RED) ;
            e.printStackTrace(System.out);
            System.out.println(RESET);
            testOK = false ;
        }

        System.out.println() ;

        System.out.print("Test 5 : ");

        double[][] hTest4 = {{1}, {20}, {-2},{2}} ;
        double[][] hTestI4 = {{1}, {20}, {-2},{2}} ;
        double[][] bTest4 =  {{4}, {-3}, {5}} ;
        double[][] bTestI4 =  {{4}, {-3}, {5}} ;
        double[][] mAtt4 = {{1},{20},{-2},{2},{4},{-3},{5}} ;
        mAttendue = new Matrice(7,1);
        hi = new Matrice(4,1);
        bi = new Matrice (3,1) ;
        h = new Matrice(4,1) ;
        b = new Matrice(3,1) ;
        m = null ;

        try {
            data.set(h,hTest4) ;
            data.set(hi,hTestI4) ;
            data.set(b,bTest4) ;
            data.set(bi,bTestI4) ;
            data.set(mAttendue,mAtt4) ;
            m = new Matrice(h,b) ;
            double[][] dataM = (double[][]) data.get(m);
            double[][] dataH = (double[][]) data.get(h);
            double[][] dataB = (double[][]) data.get(b) ;
            if (!Arrays.deepEquals(mAtt4,dataM)) {
                System.out.println("KO : La matrice créée n'est pas la bonne") ;
                System.out.println("Matrices passées en paramètres : ") ;
                System.out.println(hi) ;
                System.out.println(bi) ;
                System.out.println("Matrice attendue : ") ;
                System.out.println(mAttendue) ;
                System.out.println("Matrice recue : ") ;
                System.out.println(m);
                testOK = false ;
            } else if (nbL.getInt(m)!=7) {
                System.out.println("KO : vous avez mal initialisé nbLignes : attendu 7 mais recu "+nbL.getInt(m)) ;
                testOK = false ;
            } else if (nbC.getInt(m)!=1) {
                System.out.println("KO : vous avez mal initialisé nbColonnes : attendu 1 mais recu "+nbC.getInt(m));
                testOK = false;
            } else if (!Arrays.deepEquals(hTestI4,dataH)) {
                System.out.println("KO : vous avez modifié la matrice h passée en paramètre mais il ne fallait pas.") ;
                testOK = false ;
            } else if (nbL.getInt(h)!=4) {
                System.out.println("KO : vous avez modifié le nombre de lignes de la matrice h mais il ne fallait pas.") ;
                testOK = false ;
            } else if (nbC.getInt(h)!=1) {
                System.out.println("KO : vous avez modifié le nombre de colonnes de la matrice h mais il ne fallait pas.");
                testOK = false;
            } else if (!Arrays.deepEquals(bTestI4,dataB)) {
                System.out.println("KO : vous avez modifié la matrice b passée en paramètre mais il ne fallait pas.") ;
                testOK = false ;
            } else if (nbL.getInt(b)!=3) {
                System.out.println("KO : vous avez modifié le nombre de lignes de la matrice b mais il ne fallait pas.") ;
                testOK = false ;
            } else if (nbC.getInt(b)!=1) {
                System.out.println("KO : vous avez modifié le nombre de colonnes de la matrice b mais il ne fallait pas.");
                testOK = false;
            } else {
                System.out.println("OK") ;
            }
        } catch(Exception e) {
            System.out.println("KO : il ne fallait pas d'exception") ;
            System.out.println(RED) ;
            e.printStackTrace(System.out);
            System.out.println(RESET);
            testOK = false ;
        }

        System.out.println() ;

        System.out.print("Test 6 : ");

        double[][] hTest5 = {{1, 20, -2}} ;
        double[][] hTestI5 = {{1, 20, -2}} ;
        double[][] bTest5 =  {{4,-3,5}, {1,2,3}} ;
        double[][] bTestI5 =  {{4,-3,5}, {1,2,3}} ;
        double[][] mAtt5 = {{1,20,-2},{4,-3,5},{1,2,3}} ;
        mAttendue = new Matrice(3,3);
        hi = new Matrice(1,3);
        bi = new Matrice (2,3) ;
        h = new Matrice(1,3) ;
        b = new Matrice(2,3) ;
        m = null ;

        try {
            data.set(h,hTest5) ;
            data.set(b,bTest5) ;
            data.set(hi,hTestI5) ;
            data.set(bi,bTestI5) ;
            data.set(mAttendue,mAtt5) ;
            m = new Matrice(h,b) ;
            double[][] dataM = (double[][]) data.get(m);
            double[][] dataH = (double[][]) data.get(h);
            double[][] dataB = (double[][]) data.get(b) ;
            if (!Arrays.deepEquals(mAtt5,dataM)) {
                System.out.println("KO : La matrice créée n'est pas la bonne") ;
                System.out.println("Matrices passées en paramètres : ") ;
                System.out.println(hi) ;
                System.out.println(bi) ;
                System.out.println("Matrice attendue : ") ;
                System.out.println(mAttendue) ;
                System.out.println("Matrice recue : ") ;
                System.out.println(m);
                testOK = false ;
            } else if (nbL.getInt(m)!=3) {
                System.out.println("KO : vous avez mal initialisé nbLignes : attendu 3 mais recu "+nbL.getInt(m)) ;
                testOK = false ;
            } else if (nbC.getInt(m)!=3) {
                System.out.println("KO : vous avez mal initialisé nbColonnes : attendu 3 mais recu "+nbC.getInt(m));
                testOK = false;
            } else if (!Arrays.deepEquals(hTestI5,dataH)) {
                System.out.println("KO : vous avez modifié la matrice h passée en paramètre mais il ne fallait pas.") ;
                testOK = false ;
            } else if (nbL.getInt(h)!=1) {
                System.out.println("KO : vous avez modifié le nombre de lignes de la matrice h mais il ne fallait pas.") ;
                testOK = false ;
            } else if (nbC.getInt(h)!=3) {
                System.out.println("KO : vous avez modifié le nombre de colonnes de la matrice h mais il ne fallait pas.");
                testOK = false;
            } else if (!Arrays.deepEquals(bTestI5,dataB)) {
                System.out.println("KO : vous avez modifié la matrice b passée en paramètre mais il ne fallait pas.") ;
                testOK = false ;
            } else if (nbL.getInt(b)!=2) {
                System.out.println("KO : vous avez modifié le nombre de lignes de la matrice b mais il ne fallait pas.") ;
                testOK = false ;
            } else if (nbC.getInt(b)!=3) {
                System.out.println("KO : vous avez modifié le nombre de colonnes de la matrice b mais il ne fallait pas.");
                testOK = false;
            } else {
                System.out.println("OK") ;
            }
        } catch(Exception e) {
            System.out.println("KO : il ne fallait pas d'exception") ;
            System.out.println(RED) ;
            e.printStackTrace(System.out);
            System.out.println(RESET);
            testOK = false ;
        }

        System.out.println() ;

        System.out.print("Test 7 : ");

        double[][] hTest6 = {{1,20,-2,-3}, {1,2,3,4}} ;
        double[][] hTestI6 = {{1,20,-2,-3}, {1,2,3,4}} ;
        double[][] bTest6 =  {{4,-3, 3, 2}} ;
        double[][] bTestI6 =  {{4,-3, 3, 2}} ;
        double[][] mAtt6 = {{1,20,-2,-3},{1,2,3,4},{4,-3,3,2}} ;
        mAttendue = new Matrice(3,4);
        h = new Matrice(2,4) ;
        b = new Matrice(1,4) ;
        hi = new Matrice(2,4) ;
        bi = new Matrice(1,4) ;
        m = null ;

        try {
            data.set(h,hTest6) ;
            data.set(b,bTest6) ;
            data.set(hi,hTestI6) ;
            data.set(bi,bTestI6) ;
            data.set(mAttendue,mAtt6) ;
            m = new Matrice(h,b) ;
            double[][] dataM = (double[][]) data.get(m);
            double[][] dataH = (double[][]) data.get(h);
            double[][] dataB = (double[][]) data.get(b) ;
            if (!Arrays.deepEquals(mAtt6,dataM)) {
                System.out.println("KO : La matrice créée n'est pas la bonne") ;
                System.out.println("Matrices passées en paramètres : ") ;
                System.out.println(hi) ;
                System.out.println(bi) ;
                System.out.println("Matrice attendue : ") ;
                System.out.println(mAttendue) ;
                System.out.println("Matrice recue : ") ;
                System.out.println(m);
                testOK = false ;
            } else if (nbL.getInt(m)!=3) {
                System.out.println("KO : vous avez mal initialisé nbLignes : attendu 4 mais recu "+nbL.getInt(m)) ;
                testOK = false ;
            } else if (nbC.getInt(m)!=4) {
                System.out.println("KO : vous avez mal initialisé nbColonnes : attendu 3 mais recu "+nbC.getInt(m));
                testOK = false;
            } else if (!Arrays.deepEquals(hTestI6,dataH)) {
                System.out.println("KO : vous avez modifié la matrice h passée en paramètre mais il ne fallait pas.") ;
                testOK = false ;
            } else if (nbL.getInt(h)!=2) {
                System.out.println("KO : vous avez modifié le nombre de lignes de la matrice h mais il ne fallait pas.") ;
                testOK = false ;
            } else if (nbC.getInt(h)!=4) {
                System.out.println("KO : vous avez modifié le nombre de colonnes de la matrice h mais il ne fallait pas.");
                testOK = false;
            } else if (!Arrays.deepEquals(bTestI6,dataB)) {
                System.out.println("KO : vous avez modifié la matrice b passée en paramètre mais il ne fallait pas.") ;
                testOK = false ;
            } else if (nbL.getInt(b)!=1) {
                System.out.println("KO : vous avez modifié le nombre de lignes de la matrice b mais il ne fallait pas.") ;
                testOK = false ;
            } else if (nbC.getInt(b)!=4) {
                System.out.println("KO : vous avez modifié le nombre de colonnes de la matrice b mais il ne fallait pas.");
                testOK = false;
            } else {
                System.out.println("OK") ;
            }
        } catch(Exception e) {
            System.out.println("KO : il ne fallait pas d'exception") ;
            System.out.println(RED) ;
            e.printStackTrace(System.out);
            System.out.println(RESET);
            testOK = false ;
        }

        System.out.println() ;

        System.out.print("Test 8 : ");

        double[][] hTest7 = {{1,20,-2}, {1,2,3}} ;
        double[][] hTestI7 = {{1,20,-2}, {1,2,3}} ;
        double[][] bTest7 =  {{4,-3,3},{3,0,1},{2,-1,0},{1,1,1}} ;
        double[][] bTestI7 = {{4,-3,3},{3,0,1},{2,-1,0},{1,1,1}} ;
        double[][] mAtt7 = {{1,20,-2},{1,2,3},{4,-3,3},{3,0,1},{2,-1,0},{1,1,1}} ;
        mAttendue = new Matrice(6,3);
        h = new Matrice(2,3) ;
        b = new Matrice(4,3) ;
        hi = new Matrice(2,3) ;
        bi = new Matrice(4,3) ;
        m = null ;

        try {
            data.set(h,hTest7) ;
            data.set(b,bTest7) ;
            data.set(hi,hTestI7) ;
            data.set(bi,bTestI7) ;
            data.set(mAttendue,mAtt7) ;
            m = new Matrice(h,b) ;
            double[][] dataM = (double[][]) data.get(m);
            double[][] dataH = (double[][]) data.get(h);
            double[][] dataB = (double[][]) data.get(b);
            if (!Arrays.deepEquals(mAtt7,dataM)) {
                System.out.println("KO : La matrice créée n'est pas la bonne") ;
                System.out.println("Matrices passées en paramètres : ") ;
                System.out.println(hi) ;
                System.out.println(bi) ;
                System.out.println("Matrice attendue : ") ;
                System.out.println(mAttendue) ;
                System.out.println("Matrice recue : ") ;
                System.out.println(m);
                testOK = false ;
            } else if (nbL.getInt(m)!=6) {
                System.out.println("KO : vous avez mal initialisé nbLignes : attendu 6 mais recu "+nbL.getInt(m)) ;
                testOK = false ;
            } else if (nbC.getInt(m)!=3) {
                System.out.println("KO : vous avez mal initialisé nbColonnes : attendu 3 mais recu "+nbC.getInt(m));
                testOK = false;
            } else if (!Arrays.deepEquals(hTestI7,dataH)) {
                System.out.println("KO : vous avez modifié la matrice h passée en paramètre mais il ne fallait pas.") ;
                testOK = false ;
            } else if (nbL.getInt(h)!=2) {
                System.out.println("KO : vous avez modifié le nombre de lignes de la matrice h mais il ne fallait pas.") ;
                testOK = false ;
            } else if (nbC.getInt(h)!=3) {
                System.out.println("KO : vous avez modifié le nombre de colonnes de la matrice h mais il ne fallait pas.");
                testOK = false;
            } else if (!Arrays.deepEquals(bTestI7,dataB)) {
                System.out.println("KO : vous avez modifié la matrice b passée en paramètre mais il ne fallait pas.") ;
                testOK = false ;
            } else if (nbL.getInt(b)!=4) {
                System.out.println("KO : vous avez modifié le nombre de lignes de la matrice b mais il ne fallait pas.") ;
                testOK = false ;
            } else if (nbC.getInt(b)!=3) {
                System.out.println("KO : vous avez modifié le nombre de colonnes de la matrice b mais il ne fallait pas.");
                testOK = false;
            } else {
                System.out.println("OK") ;
            }
        } catch(Exception e) {
            System.out.println("KO : il ne fallait pas d'exception") ;
            System.out.println(RED) ;
            e.printStackTrace(System.out);
            System.out.println(RESET);
            testOK = false ;
        }

        System.out.println() ;

        return testOK ;
    }

}
