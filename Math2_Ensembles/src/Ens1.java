import javax.lang.model.element.Element;
import java.util.NoSuchElementException;

public class Ens1 extends EnsembleAbstrait {

	private boolean[] tabB; // e appartient ? l'ensemble courant ssi tabE[e.val()] est ? true.
	private int cardinal;

	public Ens1() {
		//TODO
		tabB = new boolean[MAX +1];
		cardinal = 0;
	}
	public Ens1(Elt e){
		this();
		if (e == null) throw new IllegalArgumentException("l'element n'est peux pas etre null");
		tabB[e.val()] = true;
		cardinal = 1;
	}
	public Ens1(EnsembleInterface a){
		this();
		if (a == null)throw new IllegalArgumentException();
		for (int i = 1; i <= MAX; i++) {
			Elt e = new Elt(i);
			if (a.contient(e)) {
				tabB[e.val()] = true;
				cardinal++;
			}

		}

	}
	public boolean estVide() {
		//TODO
		return cardinal == 0;
	}
	
	public Elt unElement() {
		//TODO
		if (estVide())throw new MathException("l'ensemble est vide ");
		int i =0 ;
		while (!tabB[i])
			i++;
		return new Elt(i);
	}

	public boolean contient(Elt e) {
		//TODO
		if (e == null)throw new IllegalArgumentException();
		return tabB[e.val()];
	}

	public void ajouter(Elt e) {
		//TODO
		if (e == null ) throw new IllegalArgumentException();
		if (contient(e))
			return;
		tabB[e.val()] = true;
		cardinal++;
	}

	public void enlever(Elt e) {
		//TODO
		if (e == null)throw new IllegalArgumentException();
		if (!contient(e))
			return;
		tabB[e.val()] = false;
		cardinal--;
	}

	public int cardinal() {
		//TODO
		return cardinal ;
	}

	public void complementer() {
		//TODO
		//inverse
		for (int i = 1; i < tabB.length ; i++) {
			tabB[i] =!tabB[i];
		}
		cardinal = MAX- cardinal;
	}

	public String toString() {
		// TODO
		StringBuilder text = new StringBuilder("}");
		if (estVide())
			return text.append("}").toString();
		int j =0;
		for (int i = 1; i < tabB.length ; i++) {
			if (tabB[i])
				if (j>0){
					text.append(",");
				}
			text.append(i);
			j++;
		}
		return text.toString();
	}
	
}
