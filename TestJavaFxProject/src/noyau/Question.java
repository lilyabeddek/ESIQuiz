package noyau;

import java.io.Serializable;
import java.util.*;

public abstract class Question implements Serializable{
	
    //attributs
	protected static final long serialVersionUID = 6979691458343068713L;
    protected String enonce;
    protected Set<String> propositionsCorrectes= new HashSet<String>();

    // constructeurs
    public Question(String enonce, Set<String> propositionsCorrectes) {
        this.enonce = enonce;
        this.propositionsCorrectes = propositionsCorrectes;

    }

    public Question(String enonce) {
        this.enonce = enonce;
    }
    public Question() {}


    //methodes
  
    @Override
    public String toString() {return this.enonce;}

    public Set<String> getPropositionsCorrectes() {
        return propositionsCorrectes;
    }

    public String getEnonce() {
		return enonce;
	}

	public void setEnonce(String enonce) {
		this.enonce = enonce;
	}

	public String getPropositionCorrecte() {
        return propositionsCorrectes.iterator().next();
    }

    // ajout de propositions correctes par le formateur
    public void setPropositionsCorrectes(HashSet<String> propositionsCorrectes) {
        this.propositionsCorrectes = propositionsCorrectes;
    }

    public boolean setPropositionCorrecte(String prop) {
        return propositionsCorrectes.add(prop);
    }



    public void RemovePropositionCorrecte (String prop) {
        propositionsCorrectes.remove(prop);
    }
    public void RemovePropositionCorrecte () {//unique proposition pour QCU
        propositionsCorrectes.remove(propositionsCorrectes.iterator().next());
    }

    public void afficherListPropositionsCorrectes(){
        Iterator<String> it =this.propositionsCorrectes.iterator();
        int i=1;
        System.out.println();
        while (it.hasNext()){
            System.out.println(i+"/ "+it.next());
            i++;
        }
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (enonce != null ? enonce.toUpperCase().hashCode() : 0);
        return hash;
    }
    
   
    
    public abstract void evaluer (Reponse rep);
    public abstract void afficher_quest();

}

