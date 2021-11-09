package noyau;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Apprenant extends Utilisateur implements Comparable<Apprenant>{

	protected static final long serialVersionUID = 6979691458343068713L;
    protected String nom;
    protected String prenom;
    protected LocalDate date_naissance;
    protected String adresse;
    protected float moyenne;
    protected List<Qapprenant> quizs = new ArrayList<Qapprenant>();


    //constructeur
    public Apprenant(String login,String pswrd,String nom, String prenom, LocalDate date_naissance, String adresse) {
        super(login,pswrd);
        this.nom = nom;
        this.prenom = prenom;
        this.date_naissance = date_naissance;
        this.adresse = adresse;
    }


    // getters / setters
    public String getNom() {
        return nom;
    }



    public void setNom(String nom) {
        this.nom = nom;
    }



    public String getPrenom() {
        return prenom;
    }



    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }



    public LocalDate getDate_naissance() {
        return date_naissance;
    }



    public void setDate_naissance(LocalDate date_naissance) {
        this.date_naissance = date_naissance;
    }



    public String getAdresse() {
        return adresse;
    }



    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }


    public List<Qapprenant> getQuizs() {
		return quizs;
	}



	public void setQuizs(List<Qapprenant> quizs) {
		this.quizs = quizs;
	}



    public void setMoyenne(int moyenne) {
        this.moyenne = moyenne;
    }

    // retourne la moyenne des quizs expirés de l'apprenant
    public float getMoyenne() {
    	
    	 Iterator<Qapprenant> t= quizs.iterator();
         Qapprenant item;
         int somme=0;
         int cpt=0;
         while(t.hasNext()) {
             item = t.next();
             if (item.getP_accomplissement()==100 && item.getDateFin().compareTo(LocalDate.now())<0) {
                 somme += item.getP_reussite();
                 cpt +=1 ;
             }
         }
         if(cpt!=0) {moyenne= somme/cpt;return moyenne;}
         else{moyenne=0;return 0;}
         
    }
    
    //ajoute un quiz à l'apprenant
    public void addQuiz(Qapprenant quiz) {
        this.quizs.add(quiz);
    }

    //calcule la moyenne de l'apprenant
    public float Calcul_moyenne () {
        Iterator<Qapprenant> t= quizs.iterator();
        Qapprenant item;
        int somme=0;
        int cpt=0;
        while(t.hasNext()) {
            item = t.next();
            if (item.getP_accomplissement()==100 && item.getDateFin().compareTo(LocalDate.now())<0) {
                somme += item.getP_reussite();
                cpt +=1 ;
            }
        }
        return somme/cpt;
    }

    //affiche le quiz (efficace pour une éxecution mode console)
    public void afficherApp(int num) {
        Iterator<Qapprenant> it =quizs.iterator();

        System.out.println("--------------Apprenant num° "+num+"----------------");
        System.out.println("Nom : "+this.nom);
        System.out.println("Prenom : "+this.prenom);
        System.out.println("Adresse : "+this.adresse);
        System.out.println("Date de naissance : "+this.date_naissance.toString());
        System.out.println("Moyenne : "+this.moyenne);
        System.out.println("------------------------------------------------");
        while (it.hasNext()){
            Qapprenant q =it.next();
            System.out.println("Quiz  "+q.getNom()+" pourcentage d'accomplissement : "+q.getP_accomplissement()+
                    " pourcentage de réussite :" +q.getP_reussite());
            System.out.println("---------------------------------------------------------------------------------------------------------------------");
                    }
    }

  
   
	@Override
	public int compareTo(Apprenant app) {
		if (app.username.compareTo(this.username)==0 && this.passwd.compareTo(this.passwd)==0 ) {return 0;}
    	else {
    		if(this.nom.compareTo(app.nom)>=0) {return 1;}
    		else {return -1;}
    	}
	}

	//Recherche  d'un quiz parmis les quiz de l'apprenant
	public int rech_quiz(Qapprenant q){
        boolean trouv=false;
        int index=-1,resultat=-1;
        Iterator<Qapprenant> it=quizs.iterator();
        while (it.hasNext() && !trouv){
            index++;
            if(it.next().getNom().equals(q.getNom())){
                trouv=true;
                resultat=index;
            }
        }
        return resultat;
    }
	
	//remplacer le quiz de la position index de la liste des quizs
    public void remplacer_quiz(int index,Qapprenant newQuiz){
        quizs.set(index,newQuiz);
    }

}

