import java.util.IllegalFormatCodePointException;
import java.util.Iterator;

public class Ordre extends RelationAbstraite {

    private Relation couples;

    //construit l'identité sur e
    //lance une IllegalArgumentException en cas de paramètre invalide
    public Ordre(EnsembleAbstrait e) {
        this.couples = Relation.identite(e);
    }

    //construit le plus petit ordre contenant r
    //lance une IllegalArgumentException si cette construction n'est pas possible
    public Ordre(Relation r) {
        if (r == null || !r.depart().equals(r.arrivee()) || !r.antisymetrique())
            throw new IllegalArgumentException();

        couples = r.clone();
        couples.cloReflex();
        couples.cloTrans();

        if (!couples.antisymetrique())
            throw new IllegalArgumentException();
    }

    //constructeur pas recopie
    //lance une IllegalArgumentException en cas de paramètre invalide
    public Ordre(Ordre or) {
        if (or == null) throw new IllegalArgumentException();
        couples = or.couples.clone();
    }

    //ajoute x à l'ensemble sous-jacent de la relation d'ordre
    //ne fait rien si x est déjà dans l'ensemble sous-jacent
    //lance une IllegalArgumentException en cas de paramètre invalide
    public void ajouterAuSousJacent(Elt x) {
        if (x == null) throw new IllegalArgumentException();
        couples.ajouterDepart(x);
        couples.ajouterArrivee(x);
        couples.ajouter(new Couple(x, x));
    }

    //enlève x de l'ensemble sous-jacent de la relation d'ordre
    //ainsi que toutes les flêches liées à x
    //ne fait rien si x n'est pas dans l'ensemble sous-jacent
    //lance une IllegalArgumentException en cas de paramètre invalide
    public void enleverDuSousJacent(Elt x) {
        if (x == null) throw new IllegalArgumentException();
        //Relation copie = couples.clone();
        for (Elt y : couples.depart()) {
            Couple c = new Couple(x, y);
            if (couples.contient(c) || couples.contient(c.reciproque())) {
                couples.enlever(c);
                couples.enlever(c.reciproque());
            }
        }
        couples.supprimerDepart(x);
        couples.supprimerArrivee(x);
    }

    @Override
    public Iterator<Couple> iterator() {
        return couples.iterator();
    }

    @Override
    public boolean estVide() {
        return couples.estVide();
    }

    @Override
    public boolean contient(Couple c) {
        if (c == null) throw new IllegalArgumentException();
        if (!couples.depart().contient(c.getX()) || !couples.arrivee().contient(c.getY()))
            throw new IllegalArgumentException();
        return couples.contient(c.getX(), c.getY());
    }

    @Override
    //crée (si possible) le plus petit ordre contenant this et c
    //lance une IllegalArgumentException en cas de paramètre invalide
    public void ajouter(Couple c) {
        if (c == null) throw new IllegalArgumentException();
        if (!couples.depart().contient(c.getX()) || !couples.arrivee().contient(c.getY()))
            throw new IllegalArgumentException();

        if (couples.contient(c.reciproque()))
            throw new IllegalArgumentException();

        Relation copie = couples.clone();

        copie.ajouter(c);
        copie.cloReflex();
        copie.cloTrans();

        if (!copie.antisymetrique())
            throw new IllegalArgumentException();

        couples = copie.clone();
    }


    @Override
    //Enlève (si possible) l'arête de Hasse c du la relation d'ordre
    //lance une IllegalArgumentException en cas de si le paramètre est invalide ou si c n'est pas une arête de Hasse
    //ne fait rien sinon
    public void enlever(Couple c) {
        if (c == null) throw new IllegalArgumentException();
        if (!this.depart().contient(c.getX())) throw new IllegalArgumentException();
        if (!this.depart().contient(c.getY())) throw new IllegalArgumentException();
        if (!this.contient(new Couple(c.getX(), c.getY()))) return;
        if (!estUneAreteDeHasse(c.getX(), c.getY()))
            throw new IllegalArgumentException();
        Ensemble plusPttX = this.plusPetitQue(c.getX());
        Ensemble plusGrdY = this.plusGrandQue(c.getY());
        for (Elt eX : plusPttX) {
            for (Elt eY : plusGrdY) {
                this.couples.enlever(eX, eY);
            }
        }
        this.couples.cloTrans();
    }

    private Ensemble plusPetitQue(Elt e) {
        Ensemble min = new Ensemble();
        for (Elt eC : couples.depart()) {
            if (couples.contient(eC, e)) min.ajouter(eC);
        }
        return min;
    }

    private Ensemble plusGrandQue(Elt e) {
        Ensemble maj = new Ensemble();
        for (Elt eC : couples.depart()) {
            if (couples.contient(e, eC)) maj.ajouter(eC);
        }
        return maj;
    }

    private boolean estUneAreteDeHasse(Elt x, Elt y) {
        if (!this.contient(new Couple(x, y)))
            return false;
        if (x.equals(y))
            return false;
        EnsembleAbstrait aParcourir = this.depart();
        aParcourir.enlever(x);
        aParcourir.enlever(y);
        for (Elt e : aParcourir) {
            if (this.contient(new Couple(x, e)) && this.contient(new Couple(e, y)))
                return false;
        }
        return true;
    }

    public boolean estUneAreteDeHasse(Couple c) {
        if (c == null) throw new IllegalArgumentException();
        return estUneAreteDeHasse(c.getX(), c.getY());
    }

    @Override
    public EnsembleAbstrait depart() {
        return couples.depart();
    }

    @Override
    public EnsembleAbstrait arrivee() {
        return couples.arrivee();
    }

    //renvoie true ssi x et y sont comparables pour l'ordre courant
    //lance une IllegalArgumentException en cas de paramètre invalide
    public boolean comparables(Elt x, Elt y) {
        if (x == null || y == null || !this.couples.depart().contient(x) || !this.couples.arrivee().contient(y)) {
            throw new IllegalArgumentException();
        }
        return this.couples.contient(x, y) || this.couples.contient(y, x);
    }

    // Renvoie l'ensemble des éléments minimaux de b
    //lance une IllegalArgumentException en cas de paramètre invalide
    public EnsembleAbstrait minimaux(EnsembleAbstrait b) {

        if (b == null) throw new IllegalArgumentException();
        if (!b.inclusDans(this.couples.depart())) throw new IllegalArgumentException();

        EnsembleAbstrait min = new Ensemble();

        for (Elt e1 : b) {
            boolean found = false;
            for (Elt e2 : b) {
                if (this.couples.contient(e2, e1) && !e2.equals(e1))
                    found = true;
            }
            if (!found)
                min.ajouter(e1);
        }

        return min;
    }

    // Renvoie l'ensemble des éléments maximaux de b
    //lance une IllegalArgumentException en cas de paramètre invalide
    public EnsembleAbstrait maximaux(EnsembleAbstrait b) {

        if (b == null) throw new IllegalArgumentException();
        if (!b.inclusDans(this.couples.depart())) throw new IllegalArgumentException();

        EnsembleAbstrait max = new Ensemble();

        for (Elt e1 : b) {
            boolean found = false;
            for (Elt e2 : b) {
                if (this.couples.contient(e1, e2) && !e2.equals(e1))
                    found = true;
            }
            if (!found)
                max.ajouter(e1);
        }

        return max;
    }

    // Renvoie le minimum de b s'il existe; renvoie null sinon
    //lance une IllegalArgumentException en cas de paramètre invalide
    public Elt minimum(EnsembleAbstrait b) {

        EnsembleAbstrait min = this.minimaux(b);
        if (min.cardinal() != 1)
            return null;

        return min.unElement();
    }

    // Renvoie le maximum de b s'il existe; renvoie null sinon
    //lance une IllegalArgumentException en cas de paramètre invalide
    public Elt maximum(EnsembleAbstrait b) {

        EnsembleAbstrait max = this.maximaux(b);
        if (max.cardinal() != 1)
            return null;

        return max.unElement();
    }


    public EnsembleAbstrait minor(EnsembleAbstrait b) {

        if (b == null) throw new IllegalArgumentException();
        if (!b.inclusDans(this.couples.depart())) throw new IllegalArgumentException();

        EnsembleAbstrait min = new Ensemble();

        for (Elt e1 : this.couples.depart()) {
            boolean found = false;
            for (Elt e2 : b) {
                if (this.couples.contient(e2, e1) && !e2.equals(e1) || !this.comparables(e1, e2))
                    found = true;
            }
            if (!found)
                min.ajouter(e1);
        }

        return min;
    }

    // Renvoie l'ensemble des majorants de b
    //lance une IllegalArgumentException en cas de paramètre invalide
    public EnsembleAbstrait major(EnsembleAbstrait b) {

        if (b == null) throw new IllegalArgumentException();
        if (!b.inclusDans(this.couples.depart())) throw new IllegalArgumentException();

        EnsembleAbstrait max = new Ensemble();

        for (Elt e1 : this.couples.depart()) {
            boolean found = false;
            for (Elt e2 : b) {
                if (this.couples.contient(e1, e2) && !e2.equals(e1) || !this.comparables(e1, e2))
                    found = true;
            }
            if (!found)
                max.ajouter(e1);
        }

        return max;
    }

    // Renvoie l'infimum de b s'il existe; renvoie null sinon
    //lance une IllegalArgumentException en cas de paramètre invalide
    public Elt infimum(EnsembleAbstrait b) {
        if (b == null) throw new IllegalArgumentException();
        if (!b.inclusDans(this.couples.depart())) throw new IllegalArgumentException();

        EnsembleAbstrait inf = this.minor(b);

        return this.maximum(inf);
    }

    // Renvoie le supremum de b s'il existe; renvoie null sinon
    //lance une IllegalArgumentException en cas de paramètre invalide
    public Elt supremum(EnsembleAbstrait b) {
        if (b == null) throw new IllegalArgumentException();
        if (!b.inclusDans(this.couples.depart())) throw new IllegalArgumentException();

        EnsembleAbstrait sup = this.major(b);

        return this.minimum(sup);
    }


    /**
     * Vérifie si la relation d'ordre est bornée.
     * Une relation d'ordre est bornée si elle possède un minimum et un maximum
     *
     * @return true si la relation d'ordre est bornée, false sinon
     */

    public boolean estBornee() {
        // TODO
        EnsembleAbstrait ensembleSousJacent = this.couples.depart();
        Elt min = this.minimum(ensembleSousJacent);
        Elt max = this.maximum(ensembleSousJacent);

        return (min != null && max != null);
    }

    /**
     * Recherche l’ensemble des compléments de elem dans la relation d'ordre.
     * Le complément d'un élément est un élément x tel que le supremum
     * de elem et x est le maximum de la relation d'ordre et que l'infimum
     * de elem et x est le minimum de la relation d'ordre.
     *
     * @param elem l'élément à trouver le complément de
     * @return l’ensemble des compléments de l'élément s'ils existent,
     * null sinon
     * @throws IllegalArgumentException si l'élément est nul ou ne fait
     *                                  pas partie de la relation d'ordre
     * @throws MathException            si la relation d'ordre n'est pas bornée
     */

    public EnsembleAbstrait tousLesComplements(Elt elem) {
        //TODO

        // Ligne 1 : Vérifier si elem est null
        if (elem == null) throw new IllegalArgumentException();

        // Ligne 2 : Vérifier si elem appartient à l'ensemble sous-jacent
        if (!this.couples.depart().contient(elem)) throw new IllegalArgumentException();

        // Ligne 3 : Vérifier si la relation est bornée
        if (!this.estBornee()) throw new MathException();

        // Ligne 4 : Récupérer l'ensemble sous-jacent
        EnsembleAbstrait ensembleSousJacent = this.couples.depart();

        // Ligne 5 : Récupérer le minimum et maximum de l'ensemble sous-jacent
        Elt minGlobal = this.minimum(ensembleSousJacent);
        Elt maxGlobal = this.maximum(ensembleSousJacent);

        // Ligne 6 : Créer l'ensemble qui contiendra les compléments
        EnsembleAbstrait complements = new Ensemble();

        // Ligne 7 : Parcourir tous les éléments de l'ensemble sous-jacent
        for (Elt x : ensembleSousJacent) {

            // Ligne 8 : Créer un ensemble temporaire contenant {elem, x}
            EnsembleAbstrait paire = new Ensemble();
            paire.ajouter(elem);
            paire.ajouter(x);

            // Ligne 9 : Calculer le supremum de {elem, x}
            Elt sup = this.supremum(paire);

            // Ligne 10 : Calculer l'infimum de {elem, x}
            Elt inf = this.infimum(paire);

            // Ligne 11 : Vérifier si x est complément de elem
            if (sup != null && inf != null && sup.equals(maxGlobal) && inf.equals(minGlobal)) {
                complements.ajouter(x);
            }
        }

        // Ligne 12 : Retourner null si aucun complément trouvé, sinon retourner l'ensemble

        return complements;
    }

    public String toString() {
        return couples.toString();
    }


}

