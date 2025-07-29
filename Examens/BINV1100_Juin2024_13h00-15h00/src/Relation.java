/**
 * Classe Relation héritant de RelationDeBase
 * Fournit des outils de manipulation des relations entre sous-ensembles de l'Univers
 */

import java.util.*;

public class Relation extends RelationDeBase {

    /**
     * Valeur numérique de MAXELT
     */
    private static final int MAX = Elt.MAXELT.val();

    /**
     * Construit la Relation vide sur l'ensemble vide
     */
    public Relation() {
        super();
    }

    /**
     * Construit la Relation vide de d vers a
     */
    public Relation(EnsembleAbstrait d, EnsembleAbstrait a) {
        super(d, a);
    }

    /**
     * Clone
     */
    public Relation clone() {
        return (Relation) super.clone();
    }

    //Ex1
    //renvoie le domaine de la relation courante
    public EnsembleAbstrait domaine() {

        EnsembleAbstrait dom = new Ensemble();

        //SOLUTION AVEC COUPLE

        for (Couple c : this) {
            dom.ajouter(c.getX());
        }

        return dom;
    }

    //renvoie l'image de la relation courante
    public EnsembleAbstrait image() {

        EnsembleAbstrait im = new Ensemble();

        for (Couple c : this) {
            im.ajouter(c.getY());
        }
        return im;
    }

    // EX 2
    // renvoie la complémentaire de la relation courante
    public Relation complementaire() {

        Relation compl = new Relation(this.depart(), this.arrivee());

        for (Elt el : this.depart()) {
            for (Elt el2 : this.arrivee()) {
                Couple c = new Couple(el, el2);
                if (!this.contient(c)) {
                    compl.ajouter(c);
                }
            }
        }
        return compl;
    }

    // renvoie la réciproque de la relation courante
    public Relation reciproque() {

        Relation rec = new Relation(this.arrivee(), this.depart());

        for (Elt el : this.depart()) {
            for (Elt el2 : this.arrivee()) {
                Couple c = new Couple(el, el2);
                if (this.contient(c)) {
                    rec.ajouter(c.reciproque());
                }
            }
        }

        return rec;

    }

    // si possible, remplace la relation courante par son union avec r
    //sinon, lance une IllegalArgumentException
    public void ajouter(RelationInterface r) {
        if (r == null)
            throw new IllegalArgumentException("La relation r est Null");

        if (!this.depart().equals(r.depart()) || !this.arrivee().equals(r.arrivee())) {
            throw new IllegalArgumentException();
        }
        for (Couple c : r) {
            this.ajouter(c);
        }
    }

    // si possible, remplace this par sa différence avec r
    //sinon, lance une IllegalArgumentException
    public void enlever(RelationInterface r) {

        if (r == null)
            throw new IllegalArgumentException();


        if (!this.depart().equals(r.depart()) || !this.arrivee().equals(r.arrivee())) {
            throw new IllegalArgumentException();
        }

        Relation r2 = this.clone();
        Iterator<Couple> it = r2.iterator();

        while (it.hasNext()) {
            Couple c = it.next();
            if (r.contient(c)) {
                this.enlever(c);
            }
        }
    }

    // si possible, remplace this par son intersection avec r
    //sinon, lance une IllegalArgumentException
    public void intersecter(RelationInterface r) {

        if (r == null)
            throw new IllegalArgumentException();

        if (!this.depart().equals(r.depart()) || !this.arrivee().equals(r.arrivee())) {
            throw new IllegalArgumentException();
        }

        Relation r2 = this.clone();
        Iterator<Couple> it = r2.iterator();

        while (it.hasNext()) {
            Couple c = it.next();
            if (!r.contient(c)) {
                this.enlever(c);
            }
        }
    }

    // si possible, renvoie la composée : this après r
    //sinon, lance une IllegalArgumentException
    public Relation apres(RelationInterface r) {


        if (r == null)
            throw new IllegalArgumentException();

        if (!r.arrivee().equals(this.depart())) {
            throw new IllegalArgumentException();
        }

        Relation compose = new Relation(r.depart(), this.arrivee());

        for (Couple c : r) {
            for (Elt e : this.arrivee()) {
                if (this.contient(c.getY(), e))
                    compose.ajouter(c.getX(), e);
            }
        }

        return compose;
    }



    /*Les exercices 4 et 5 ne concernent que les relations sur un ensemble.
     * Les méthodes demandées génèreront donc une MathException lorsque l'ensemble de départ
     * ne coïncide pas avec l'ensemble d'arrivée.
     */

    /* Ex 4 */

    // Clôture la Relation courante pour la réflexivité
    public void cloReflex() {

        EnsembleAbstrait dep = this.depart();
        EnsembleAbstrait arr = this.arrivee();

        if (dep.cardinal() != arr.cardinal())
            throw new MathException();

        for (Elt e : dep) {
            if (!arr.contient(e))
                throw new MathException();
            this.ajouter(e, e);
        }
    }

    // Clôture la Relation courante pour la symétrie
    public void cloSym() {

        EnsembleAbstrait dep = this.depart();
        EnsembleAbstrait arr = this.arrivee();

        if (dep.cardinal() != arr.cardinal())
            throw new MathException();

        for (Elt e : dep) {
            if (!arr.contient(e))
                throw new MathException();
            for (Elt e2 : arr) {
                if (!e.equals(e2) && this.contient(e, e2) && !this.contient(e2, e))
                    this.ajouter(e2, e);
            }
        }
    }

    // Clôture la Relation courante pour la transitivité (Warshall)
    public void cloTrans() {

        EnsembleAbstrait dep = this.depart();
        EnsembleAbstrait arr = this.arrivee();

        if (dep.cardinal() != arr.cardinal())
            throw new MathException();

        for (Elt e : dep) {

            if (!arr.contient(e))
                throw new MathException();

            for (Elt e2 : dep)
                if (this.contient(e2, e))
                    for (Elt e3 : dep)
                        if (this.contient(e, e3))
                            this.ajouter(e2, e3);
        }
    }


    //Ex 5
    /*Les questions qui suivent ne concernent que les relations sur un ensemble.
     * Les méthodes demandées génèreront donc une MathException lorsque l'ensemble de départ
     * ne coïncide pas avec l'ensemble d'arrivée.
     */
    // renvoie true ssi this est réflexive
    public boolean reflexive() {
        EnsembleAbstrait dep = this.depart();
        EnsembleAbstrait arr = this.arrivee();

        if (dep.cardinal() != arr.cardinal())
            throw new MathException();

        for (Elt e : dep) {

            if (!arr.contient(e))
                throw new MathException();

            if (!this.contient(e, e))
                return false;
        }
        return true;
    }

    // renvoie true ssi this est antiréflexive
    public boolean antireflexive() {

        EnsembleAbstrait dep = this.depart();
        EnsembleAbstrait arr = this.arrivee();

        if (dep.cardinal() != arr.cardinal())
            throw new MathException();

        for (Elt e : dep) {

            if (!arr.contient(e))
                throw new MathException();

            if (this.contient(e, e))
                return false;
        }
        return true;
    }

    // renvoie true ssi this est symétrique
    public boolean symetrique() {
        EnsembleAbstrait dep = this.depart();
        EnsembleAbstrait arr = this.arrivee();

        if (dep.cardinal() != arr.cardinal())
            throw new MathException();

        for (Elt e : dep) {

            if (!arr.contient(e))
                throw new MathException();

            for (Elt e2 : dep) {
                if (this.contient(e, e2) && !this.contient(e2, e))
                    return false;
            }

        }
        return true;
    }

    // renvoie true ssi this est antisymétrique
    public boolean antisymetrique() {
        EnsembleAbstrait dep = this.depart();
        EnsembleAbstrait arr = this.arrivee();

        if (dep.cardinal() != arr.cardinal())
            throw new MathException();

        for (Elt e : dep) {

            if (!arr.contient(e))
                throw new MathException();

            for (Elt e2 : dep) {
                if (this.contient(e, e2) && this.contient(e2, e) && !e.equals(e2))
                    return false;
            }

        }
        return true;
    }

    // renvoie true ssi  this est transitive
    public boolean transitive() {
        EnsembleAbstrait dep = this.depart();
        EnsembleAbstrait arr = this.arrivee();

        if (dep.cardinal() != arr.cardinal())
            throw new MathException();

        for (Elt e : dep) {

            if (!arr.contient(e))
                throw new MathException();

            for (Elt e2 : dep) {
                if (this.contient(e, e2))
                    for (Elt e3 : arr)
                        if (this.contient(e, e2) && this.contient(e2, e3) && !(this.contient(e, e3)))
                            return false;
            }

        }
        return true;
    }

    // Ex 6
    //Construit une copie de la relation en paramètre
    //lance une IllegalArgumentException en cas de paramètre invalide
    public Relation(RelationInterface r) {
        if (r == null) throw new IllegalArgumentException();
        for (Elt x : r.depart()) {
            for (Elt y : r.arrivee()) {
                this.ajouterDepart(x);
                this.ajouterArrivee(y);
                Couple c = new Couple(x, y);
                if (r.contient(c))
                    this.ajouter(c);
            }
        }

    }

    //renvoie l'identité sur e
    //lance une IllegalArgumentException en cas de paramètre invalide
    public static Relation identite(EnsembleAbstrait e) {
        if (e == null) throw new IllegalArgumentException();

        Relation id = new Relation(e, e);
        for (Elt elem : e) {
            id.ajouter(elem, elem);
        }
        return id;
    }

    //renvoie le produit cartésien de a et b
    //lance une IllegalArgumentException en cas de paramètre invalide
    public static Relation produitCartesien(EnsembleAbstrait a, EnsembleAbstrait b) {
        if (a == null || b == null) throw new IllegalArgumentException();
        Relation prodCart = new Relation(a, b);
        for (Elt x : a)
            for (Elt y : b)
                prodCart.ajouter(new Couple(x, y));
        return prodCart;
    }

} // class Relation
