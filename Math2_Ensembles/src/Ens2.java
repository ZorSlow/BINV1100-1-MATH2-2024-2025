public class Ens2 extends EnsembleAbstrait {

	private Elt[] elements; // contient les elements de l'ensemble. Il ne peut pas y avoir de doublon.
	private int cardinal;

	public Ens2() {
		//TODO
		elements = new Elt[MAX];
		cardinal = 0;
		
	}
	public Ens2(Elt e){
		this();
		if (e == null)throw new IllegalStateException();
		elements[0] = e;
		cardinal = 1;
	}
	public Ens2(EnsembleInterface a){
		this();
		if (a == null) throw new IllegalArgumentException();
		for (int i = 1; i < MAX ; i++) {
			Elt e = new Elt(i);
			if (a.contient(e)){
				this.ajouter(e);
			}
		}
	}

	public boolean estVide() {
		//TODO

		return  cardinal == 0;
	}
	
	public Elt unElement() {
		//TODO
		if (estVide())throw new MathException();
		return elements[0] ;
	}

	public boolean contient(Elt e) {
		//TODO
		if (e == null) throw new IllegalArgumentException("e ne peut pas être null");

		for (int i = 0; i < cardinal; i++) {
			if (elements[i].equals(e))
				return true;
		}
		return false;
	}

	public void ajouter(Elt e) {
		//TODO
		if (e == null) throw new IllegalArgumentException("e ne peut pas être null");
		if (cardinal >= elements.length)
			throw new IllegalStateException("L'ensemble est plein");
		if (!contient(e)){
			elements[cardinal] = e;
			cardinal++;
		}
	}

	public void enlever(Elt e) {
		//TODO
		if (e == null) throw new IllegalArgumentException("e ne peut pas être null");
		if (cardinal >= elements.length)
			throw new IllegalStateException("L'ensemble est plein");
		if (!contient(e)) return;
		int indiceE = 0;
		if (contient(e)) {
			for (int i = 0; i < cardinal; i++) {
				if (elements[i].equals(e))
					indiceE = i;
			}
			for (int i = indiceE; i < cardinal - 1; i++) {
				elements[i] = elements[i + 1];
			}
			cardinal--;
			elements[cardinal] = null;
		}
	}

	public int cardinal() {
		//TODO
		return cardinal;
	}

	public void complementer() {
		//TODO;
		Elt[] complementaire = new Elt[MAX];
		int indice =0;
		for (int i = 1; i <= MAX ; i++) {
			Elt e = new Elt(i);
			if (!contient(e)) {
				complementaire[indice] = e;
				indice++;
			}
		}
		elements = complementaire;
		cardinal = indice;
	}

	public String toString() {
		//TODO
		StringBuilder text = new StringBuilder("{");
		if (estVide())
			return text.append("}").toString();
		for (int i = 0; i < cardinal; i++) {
			if (i >0) {
				text.append(", ");
			}
			text.append(elements[i]);
		}
		text.append("}");
		return text.toString();
	}

}
