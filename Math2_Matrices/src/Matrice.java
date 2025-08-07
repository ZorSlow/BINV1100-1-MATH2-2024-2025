 public class Matrice {
    private final int nbLignes;              // nombre de lignes
    private final int nbColonnes;            // nombre de colonnes
    private final double[][] data;           // matrice (nbLignes,nbColonnes)

    // ce constructeur cree la matrice nulle de genre (a,b)
    public Matrice(int a, int b) throws IllegalArgumentException { 
    	//TODO : Les lignes ci-dessous sont là uniquement pour qu'il n'y ait pas d'erreur. Elles doivent être modifiées
        nbLignes=1 ;
        nbColonnes=1 ;
        data =null ;
    }

    //  Ce constructeur permet de construire la matrice correspondant 
    //  au tableau en parametre. 
    public Matrice(double[][] tab)  throws IllegalArgumentException {
    	//TODO : La ligne suivante est là uniquement pour qu'il n'y ait pas d'erreur. Elle doit être modifiée ou supprimée
    	this(1,1);
    }

    // constructeur par recopie
    public Matrice(Matrice a)  throws IllegalArgumentException {
        //TODO : La ligne suivante est là uniquement pour qu'il n'y ait pas d'erreur. Elle doit être modifiée ou supprimée
    	this(1,1);
	}

    // cree la matrice identite d'ordre a
    public static Matrice identite(int a)  throws IllegalArgumentException {
    	//TODO
    	return null;
    }
    
    //Cette methode renvoie l'element de la ligne numLigne et de la 
    //colonne numColonne de la matrice. Si cet element n'existe pas, la 
    //methode lance une IllegalArgumentException 
	public double getElement(int numLigne, int numColonne)
			throws IllegalArgumentException {
		//TODO
		return 0;
	 }
    
    // Calcule la somme this+b de la matrice courante avec b si c'est possible
    public Matrice somme(Matrice b)  throws IllegalArgumentException {
    	//TODO
    	return null;
    }

    // calcule le produit scalaire.this de la matrice courante avec scalaire
    public Matrice produitParScalaire(double scalaire){
    	//TODO
    	return null;
    }

    // calcule le produit this*b de la matrice courante avec b si possible
    public Matrice produitAGauche(Matrice b)  throws IllegalArgumentException {
    	//TODO
    	return null;
    }
    
	// calcule le produit b*this de b avec la matrice courante si possible
    public Matrice produitADroite(Matrice b)  throws IllegalArgumentException {
    	//TODO
    	return null;
    }
	 
   // renvoie true si la matrice courante est carrée
	 public boolean carree(){
		//TODO
		 return false;
    }
    
    // Calcule this^n. 
	// Lance une Mathexception si this n'est pas carrée
	// Lance une IllegalArgumentException en cas de paramètre invalide
    public Matrice puissance(int n) throws  IllegalArgumentException {
    	//TODO
    	return null;
    }
    
	//Calcule this^T : la tranposée de this
	public Matrice transposee() {
		//TODO
		return null ;
	}
	 
    // affiche la matrice en format standard
    public String toString(){
    	//TODO
    	return null;
    }

    public Matrice pageRank() {
        // A FAIRE QUAND MARKOV AURA ÉTÉ VU
        return null ;
    }
  }   
