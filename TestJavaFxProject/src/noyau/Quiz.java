package noyau;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public abstract class Quiz implements Serializable{

	protected static final long serialVersionUID = 6979691458343068713L;
    protected String nom;
    protected LocalDate dateDebut;
    protected LocalDate dateFin;
    protected int nbQuestions;
    protected Set<Notion> notions = new HashSet<Notion>();

    public Quiz(String nom,LocalDate dateOuverture,LocalDate dateExpiration) {
		this.nom=nom;
		this.dateDebut=dateOuverture;
		this.dateFin=dateExpiration;
	}
    
    public String getNom() {
        return this.nom;
    }

    public LocalDate getDateDebut() {
        return this.dateDebut;
    }

    public LocalDate getDateFin() {
        return this.dateFin;
    }
    
    
    public Notion getNotion(String titre){
            Notion n =new Notion(titre);
            Notion resultat = null;
            boolean stop=false;
            Iterator<Notion> it = notions.iterator();
            while (it.hasNext()&& !stop)
            {  resultat=it.next();
               if(resultat.equals(n)){
                stop=true;}}
           if (!stop){return null;} else { return resultat;}
     }
    
    
    public int getNbQuestions() {
        return this.nbQuestions;
    }

    public void afficherQuiz() {
        System.out.println(" Quiz : "+this.getNom()+" Nombre de Questions : "+this.getNbQuestions()+" Date début :"+this.getDateDebut()+" Date Expiration :"+this.getDateFin());
        System.out.println("---------------------------------------------------------------------------------------------------");
        for(Notion n :notions) {
            n.afficher_notion();
        }
    }
    
    
    public void afficher_titresNotions() {
        Iterator<Notion> it = this.notions.iterator();
        int i=1;
        System.out.println();
        while (it.hasNext()) {
            System.out.println("Notion numéro "+i+" : "+it.next().getTitre());
            i++;
        }
    }
    
    public List<Question> getQuestions(){
    	List<Question> listQuests =new ArrayList<Question>();
    	for (Notion n: this.notions) {
    		listQuests.addAll(n.getQuest());
    	}
    	return listQuests;
    }

    public boolean estOuvert() {
    	return this.dateDebut.compareTo(LocalDate.now())<=0;
    }
    
    public boolean estExpire() {
    	return this.dateFin.compareTo(LocalDate.now())<0;
    }
}
