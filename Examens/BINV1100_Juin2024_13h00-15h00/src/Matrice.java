import java.util.Arrays ;
public class Matrice {
    private final int nbLignes;              // nombre de lignes
    private final int nbColonnes;            // nombre de colonnes
    private final double[][] data;           // matrice (nbLignes,nbColonnes)

    // ce constructeur cree la matrice nulle de genre (a,b)
    public Matrice(int a, int b) throws IllegalArgumentException {
        if (a<=0 || b<=0)
            throw new IllegalArgumentException("a ou b négatif") ;
        data = new double[a][b] ;
        nbLignes = a ;
        nbColonnes = b ;
    }

    // Construit, si possible, la matrice formée des matrices h et b mises l'une en-dessous de l'autre en commençant par h
    //   Autrement dit crée la matrice this = (h)
    //                                        (b)
    //
    // Lance une IllegalArgumentException en cas de paramètres invalides (si la création ne peut pas se faire)
    public Matrice(Matrice h, Matrice b) {
		//TODO : La ligne suivante est là uniquement pour qu'il n'y ait pas d'erreur. Elle doit être modifiée ou supprimée
    	this(1,1);
		
    }

    // affiche la matrice en format standard //NE PAS MODIFIER CETTE METHODE !!!
    public String toString(){
        String st = " -" ;

        int[] tmaxs = new int[nbColonnes] ;
        int lmax = 0 ;

        for (int j=0 ; j<nbColonnes ;j++) {
            for (int i=0 ; i<nbLignes ; i++) {
                String s = "" + data[i][j] ;
                if (data[i][j]>=0)
                    s = " "+s ;
                if (s.length()>tmaxs[j])
                    tmaxs[j] = s.length() ;
            }
            lmax = lmax + tmaxs[j] ;
        }
        lmax = lmax + 2*(nbColonnes-1) -1  ;

        for (int i=0 ; i<lmax ; i++) {
            st = st + " " ;
        }
        st = st + "- " + '\n' ;

        for (int i=0 ; i<nbLignes ; i++) {
            st = st+"|" ;
            for (int j=0 ; j<nbColonnes ;j++) {
                String s = "" + data[i][j] ;
                if (data[i][j]>=0)
                    s = " "+s ;
                st = st + s ;
                int nbBlanc = tmaxs[j]-s.length()+2;
                if (j==nbColonnes-1) {
                    nbBlanc = tmaxs[j]-s.length()+1;
                }
                for (int k=0 ; k<nbBlanc ; k++)
                    st = st + " " ;
            }
            st = st + "|" ;
            st = st + '\n' ;
        }
        st = st + " -" ;
        for (int i=0 ; i<lmax ; i++) {
            st = st + " " ;
        }
        st = st + "- " ;
        return st ;
    }

}
