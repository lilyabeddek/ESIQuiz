package noyau;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;


public class Qapprenant extends Quiz implements Serializable{
	
	protected static final long serialVersionUID = 6979691458343068713L;
    private float p_accomplissement;// indique le pourcentage d'accomplissement du quiz
    private float p_reussite=-1;
    private Map<Question,Reponse> reponses = new HashMap<Question,Reponse>();

    //constructeur
    public Qapprenant(String nom,LocalDate dateOuverture,LocalDate dateExpiration) {
		super(nom,dateOuverture,dateExpiration);
	}
    //------------------------ getters and setters ---------------------//
    
    public Map<Question,Reponse> getReponses(){return this.reponses;}
   
    public boolean estAccimpli() {
    	return (float) Math.round(p_accomplissement * 100)/100 >=100;
    }
    public boolean estSoumi() {
    	return p_reussite!=-1;
    }
	public float getP_accomplissement() {
		return (float) Math.round(p_accomplissement * 100)/100;
	}
	
	public float getValeurCompleteAccomplssement() {
		return p_accomplissement;
	}

	public void setP_accomplissement(float p_accomplissement) {
		this.p_accomplissement = p_accomplissement;
	}

	public float getP_reussite() {
		if(p_reussite==-1) {return 0;}
		else {return ((float) Math.round(p_reussite * 100)/100);}
		
	}

	public void setP_reussite(float p_reussite) {
		this.p_reussite = p_reussite;
	}
	
	public void add_entry(Question key,Reponse value){ reponses.put(key,value); nbQuestions++;}
	
    //---------------------------- methodes -----------------------//
    public Reponse repondre_quest (Question q)
    {   Reponse r = reponses.get(q);
        Scanner sc = new Scanner(System.in);
        if(r==null){
            r=new Reponse(q.getEnonce());
        }
        System.out.println(" Veuillez introduire la proposition :");
        sc.nextLine();
        r.setReponseChoisie(sc.nextLine());
        sc.close();
        return r;
    }
    public void Sauv_rep (Reponse rep,Question e)
    {
        reponses.put(e, rep);
    }

    //evaluer le quiz
    public void evaluerQuiz() {
        float resultat=0;
        float nbQuestions=this.getNbQuestions();
        float unitePoint = 100/nbQuestions;
        
         Iterator<Map.Entry<Question,Reponse>> it= reponses.entrySet().iterator();
         Map.Entry<Question,Reponse> e;
         while (it.hasNext()){
             e=it.next();
             e.getKey().evaluer(e.getValue());
             resultat+=(e.getValue().getpObtenu()*unitePoint)/100;
         }
        this.p_reussite=resultat;
    }
}

