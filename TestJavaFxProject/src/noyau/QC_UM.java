package noyau;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class QC_UM extends Question implements Serializable {

    //attributs
	protected static final long serialVersionUID = 6979691458343068713L;
    protected Set<String> propositionsFausses= new HashSet< String >();

    //Constructeurs
    public QC_UM(String enonce, Set<String> propositionsCorrectes, Set<String> propositionsFausses) {
        super(enonce, propositionsCorrectes);
        this.propositionsFausses = propositionsFausses;
    }
    public QC_UM(String enonce)
    {
        super(enonce);
    }
    public QC_UM() {}


    //Mathodes
    public abstract void evaluer (Reponse rep);

    public Set<String> getPropositionsFausses() {
        return propositionsFausses;
    }
    public void setPropositionsFausses(Set<String> propositionsFausses) {
        this.propositionsFausses = propositionsFausses;
    }

    public boolean setPropositionFausse(String prop) {
    	
    	ArrayList<String> allPropositions= new ArrayList<String>();
    	allPropositions.addAll(this.propositionsCorrectes);
    	allPropositions.addAll(this.propositionsFausses);
    	
    	if (allPropositions.contains(prop)) {return false;}
    	else {return this.propositionsFausses.add(prop);}
        
    }
    public void RemovePropositionFausse(String prop) {
        this.propositionsFausses.remove(prop);
    }
    public void afficherListPropositionsFausses() {
        Iterator<String> it = this.propositionsFausses.iterator();
        int i = 1;
        System.out.println();
        while (it.hasNext()) {
            System.out.println(i + "/ " + it.next());
            i++;
        }
    }
    public void afficher_quest () {
        System.out.println(" Question :  " + this.getEnonce());

        System.out.println("***** Ensemble des propositions fausses *****");
        this.afficherListPropositionsFausses();
        System.out.println("----------------------------------------------");
        System.out.println("***** Ensemble des propositions correctes *****");
        this.afficherListPropositionsCorrectes();
        this.afficherListPropositionsCorrectes();
    }

    public List<String> getPropositionsAleatoires(){
    	ArrayList<String> result = new ArrayList<String>();
    	result.addAll(this.propositionsFausses);
    	result.addAll(super.propositionsCorrectes);
    	Collections.shuffle(result,new Random());	
    	//System.out.println(result.toString());
    	//randomize(result,result.size());
    	return result;
    }
    
    // A Function to generate a random permutation of a list 
    static void randomize(ArrayList<String> arr, int n) 
    { 
        // Creating a object for Random class 
        Random r = new Random(); 
           
        // Start from the last element and swap one by one. We don't 
        // need to run for the first element that's why i > 0 
        for (int i = n-1; i > 0; i--) { 
               
            // Pick a random index from 0 to i 
            int j = r.nextInt(i); 
               
            // Swap arr[i] with the element at random index 
            String temp = arr.get(i); 
            arr.set(i, arr.get(j)); 
            arr.set(j,temp); 
        } 
  
        // Prints the random list
        System.out.println(arr.toString()); 
    } 
       
}


