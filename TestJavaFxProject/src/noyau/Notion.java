package noyau;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class Notion implements Serializable{
   // attributs
	protected static final long serialVersionUID = 6979691458343068713L;
    private String titre;
    private Set<Question> quests = new HashSet<Question>();

    // redefinitions
    @Override
    public String toString() {
		return titre;  	
    }
   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (titre != null ? titre.toUpperCase().hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
    	    String otherTitreNotion = "";
    	    if (object instanceof Notion) {
    	    	otherTitreNotion = ((Notion)object).titre;
    	    } else if(object instanceof String){
    	    	otherTitreNotion = (String)object;
    	    } else {
    	        return false;
    	    }   

    	    if ((this.titre == null && otherTitreNotion != null) || (this.titre != null && !this.titre.equalsIgnoreCase(otherTitreNotion))) {
    	    	return false;
    	    }
    	    return true;
          
       // return this.getTitre().equalsIgnoreCase(((Notion)object).getTitre());
    }

    //constructeurs getters et setters
    public Notion(Notion n) {
        this.titre = n.getTitre();
    }
    
    public Notion(String nom) {
        this.titre = nom;
    }

    public Notion(String nom,Set<Question> quests) {
        this.titre = nom;
        this.quests=quests;
    }

    public Set<Question> getQuest() {
        return quests;
    }

    public void setQuest(Set<Question> quest) {
        this.quests = quest;
    }

    
    public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	
	public int getNbQuestions() {
		return quests.size();
	}

	
	//ajouter ue question
    public boolean ajouterQuestion(Question question) {
    	return quests.add(question);
    }

    //recherche d'une question � partir de son enonc�
    public Question rechercheQuestion(String enonce) {
       
        Question q=null;
   
        Iterator<Question> it= quests.iterator() ;
        while (it.hasNext()){
            q=it.next();
            if (q.enonce.equalsIgnoreCase(enonce)){
            	
                return q;        
            }
        }
        return null;
    }

    
    //tester l'existance d'une question dans la notion � partir de son enonc�
    public boolean rechEnonceQuestion(String enonce) {
        Question q=null;
        Iterator<Question> it= quests.iterator() ;
        while (it.hasNext()){
            q=it.next();
            if (q.enonce.equalsIgnoreCase(enonce)){      	
                return true;        
            }
        }
        return false;
    }

    
    // equivalent de contains : test si une notion contient une question q
    public boolean contientQuestion(Question q) {
    	return this.quests.contains(q);
    }
    
    //supprimer une question
    public boolean supprimerQuestion(String enonce) {

        Question resultat = rechercheQuestion(enonce);
        if( resultat==null) {
            System.out.println("Cette question n'existe pas");
            return false;
        }
        else {
            quests.remove(resultat);
            System.out.println("la question a été supprimée avec succee");
            return true;
        }
    }
    
  //supprimer une question
    public boolean supprimerQuestion(Question q) {
        return quests.remove(q);
    }
    
    
    //modifier une question en mode console
    public void modifierQuestion(String enonce) {
        Question resultat = rechercheQuestion(enonce);
        if ( resultat==null) {System.out.println("cette notion ne comporte pas de questions avec cet Enonce");}
        else {

            Question q = resultat;
            Scanner sc = new 	Scanner(System.in);

            System.out.println("Vous les vous :");
            System.out.println("1/- Changer l'Enonce de la question");

            switch (q.getClass().getName()){

                case "QCM":
                    System.out.println("2/- ajouter une proposition correcte");
                    System.out.println("3/- supprimer une proposition correcte");
                    System.out.println("4/- ajouter une proposition incorrecte");
                    System.out.println("5/- supprimer une proposition incorrecte");
                    break;
                case "QCU":
                    System.out.println("2/- modifier la proposition correcte");
                    System.out.println("3/- ajouter une proposition incorrecte");
                    System.out.println("4/- supprimer une proposition incorrecte");
                    break;
                case "QO" :
                    System.out.println("2/- ajouter une proposition correcte");
                    System.out.println("3/- supprimer une proposition correcte");
                    break;

            }
            System.out.print("Entrer le numero de votre choix : ");
            switch(sc.nextInt()) {

                case 1:
                    System.out.print("Introduizer le nouvel Enonce : ");
                    sc.nextLine();
                    String newEnonce=sc.nextLine();
                    Question Resultat2 =rechercheQuestion(newEnonce);
                    if (Resultat2 !=null) {System.out.println("Nous somme désolés mais une autre question comporte le meme énoncé dans cette notion");}
                    else {
                        q.setEnonce(newEnonce);
                        System.out.println(q.enonce);
                    }

                    break;

                case 2 :
                    if (q.getClass().getName()=="QCM" || q.getClass().getName()=="QO" ) {

                        System.out.println("veuillez introduire la nouvelle proposition:");
                        sc.nextLine();
                        String prop = sc.nextLine();
                        if (q.getPropositionsCorrectes().contains(prop)) {
                            System.out.println("cette propositin existe deja");
                        }
                        else {
                            q.setPropositionCorrecte(prop);
                            System.out.println("la proposition a ete ajouté avec succee");
                        }
                    }
                    else {
                        System.out.println("veuillez introduire la nouvelle proposition correcte:");
                        sc.nextLine();
                        String prop = sc.nextLine();
                        q.RemovePropositionCorrecte();
                        q.setPropositionCorrecte(prop);

                    }
                    break;
                case 3:
                    if (q.getClass().getName()=="QCM" || q.getClass().getName()=="QO" ) {


                        if ((q.getPropositionsCorrectes().size()>0)) {
                            q.afficherListPropositionsCorrectes();
                            System.out.println("Choisissez le numero de la proposition que vous voulez supprimer :");
                            sc.nextLine();
                            int num = Integer.parseInt(sc.nextLine());

                            if (num>0) {
                                if (num<= q.getPropositionsCorrectes().size()) {
                                    //q.RemovePropositionCorrecte(num-1);f graphique  elle devient remove(String selecteditem)
                                    System.out.println("la proposition a ete supprimée avec succée");
                                }
                                else {System.out.println("le nombre que vous avez introduit est incorrecte");}
                            }
                            else {System.out.println("le nombre que vous avez introduit est incorrecte");}

                        }
                    }
                    else {
                        System.out.println("veuillez introduire la nouvelle proposition:");
                        sc.nextLine();
                        String prop = sc.nextLine();
                        if (q.getPropositionsCorrectes().contains(prop)) {
                            System.out.println("cette propositin existe deja");
                        }
                        else {
                            q.setPropositionCorrecte(prop);
                            System.out.println("la proposition a ete ajouté avec succee");
                        }
                    }
                    break;
                case 4:
                    if (q.getClass().getName()=="QCM") {
                        QCM p=(QCM)q;
                        System.out.println("veuillez introduire la nouvelle proposition:");
                        sc.nextLine();
                        String prop = sc.nextLine();
                        if (p.getPropositionsFausses().contains(prop)) {
                            System.out.println("cette propositin existe deja");
                        }
                        else {
                            p.setPropositionFausse(prop);
                            System.out.println("la proposition a ete ajouté avec succee");
                        }
                    }
                    else {
                        QCU p=(QCU)q;
                        if ((p.getPropositionsFausses().size()>0)) {
                            q.afficherListPropositionsCorrectes();
                            System.out.println("Choisissez le numero de la proposition que vous voulez supprimer :");
                            sc.nextLine();
                            int num = Integer.parseInt(sc.nextLine());
                            if (num>0) {
                                if (num<=p.getPropositionsFausses().size()) {

                                    //p.RemovePropositionFausse(num-1); devient remove selected item string
                                    System.out.println("la proposition a ete supprimée avec succée");
                                }
                                else {System.out.println("le nombre que vous avez introduit est incorrecte");}
                            }
                            else {System.out.println("le nombre que vous avez introduit est incorrecte");}

                        }
                    }
                    break;
                case 5:
                    QCM p=(QCM)q;
                    if ((p.getPropositionsFausses().size()>0)) {
                        q.afficherListPropositionsCorrectes();
                        System.out.println("Choisissez le numero de la proposition que vous voulez supprimer :");
                        sc.nextLine();
                        int num = Integer.parseInt(sc.nextLine());
                        if (num>0) {
                            if (num<=p.getPropositionsFausses().size()) {

                               // p.RemovePropositionFausse(num-1); devient remove selected item string
                                System.out.println("la proposition a ete supprimée avec succée");
                            }
                            else {System.out.println("le nombre que vous avez introduit est incorrecte");}
                        }
                        else {System.out.println("le nombre que vous avez introduit est incorrecte");}

                    }
                    break;
            }
         
            sc.close();

        }
    }
    //-------------- Modifier Notion mode console-------------------------------
    public void modifier_Notion(){
        int choix=0;
        Scanner sc=new Scanner(System.in);
        while (choix != 3){
            System.out.println(" Notion  :  "+titre);
            System.out.println(" 1/ Modifier le titre de la Notion.");
            System.out.println(" 2/ Modifier une question.");
            System.out.println(" 3/ Quitter ");
            System.out.println(" Veuillez introduire le numéro de votre choix :");
            choix=sc.nextInt();
            switch (choix){
                case 1:
                    System.out.println(" veuillez introduire le nouveau titre de la Notion : ");
                    sc.nextLine();
                    titre=sc.nextLine();
                    this.setTitre(titre);
                    break;
                case 2 :
                    this.afficher_notion();
                    System.out.println();
                    System.out.println(" veuillez introduire l'énoncé de la question que vous voulez modifier :");
                    sc.nextLine();
                    titre=sc.nextLine();
                    this.modifierQuestion(titre);
                    break;
            }
        }
        sc.close();
    }
    //------------------- afficher notion
    public void afficher_notion() {

        System.out.println("        Titre de la notion  :" + this.getTitre());
        System.out.println("------------------ Ensembles des questions ------------");
        Set<Question> n = new HashSet<Question>();
        n = this.getQuest();
        Iterator<Question> it = n.iterator();
        while (it.hasNext()) {
            it.next().afficher_quest();
        }
    }
    
    
    // retourne vrai si la notion donn� en parametres contient toutes les questions de la notion en cours d'utilisation
    public boolean toutesLesQuestionsSonDans(Notion notion) {
   
    	Iterator<Question> it=this.quests.iterator();
    	Question q;
    	
    	
    	System.out.println("toutes les questionq du quiz");
    	this.afficher_notion();
    	System.out.println("*************************************");
    	System.out.println("les questions de la notion de la formation");
    	
    	while(it.hasNext()) {
    		q=it.next();
    		System.out.println(q.enonce);
    		if(!notion.contientQuestion(q)) {
    			return false;
    		}
    	}
    	return true;		
    }

}

