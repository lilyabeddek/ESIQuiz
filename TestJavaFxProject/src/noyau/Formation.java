package noyau;


import java.util.*;
import java.io.Serializable;
import java.time.LocalDate;

public class Formation implements Serializable{
	
	//attributs
	protected static final long serialVersionUID = 6979691458343068713L;
    private String nom;
    private String description;
    private LocalDate date_deb;
    private LocalDate date_fin;
    private Set<Notion> notions = new HashSet<Notion>();
    private HashSet<QuizFormateur> quizs = new HashSet<QuizFormateur>();

//-------------------------- constructeurs ------------------------------------//

    public Formation(String nom, String description, LocalDate date_deb, LocalDate date_fin) {

        this.nom = nom;
        this.description = description;
        this.date_deb = date_deb;
        this.date_fin = date_fin;
    }

    public Formation(String nom, String description, LocalDate date_deb, LocalDate date_fin, Set<Notion> notions) {

        this.nom = nom;
        this.description = description;
        this.date_deb = date_deb;
        this.date_fin = date_fin;
        this.notions = notions;
    }

    //-------------------------- getters et setters -----------------------------//
    public void set_nom(String nom) {
        this.nom = nom;
    }

    public String get_nom() {
        return this.nom;
    }

    public void set_description(String description) {
        this.description = description;
    }

    public String get_description() {
        return this.description;
    }

    public void set_datedeb(LocalDate date_deb) {
        this.date_deb = date_deb;
    }

    public LocalDate get_datedeb() {
        return this.date_deb;
    }

    public void set_datefin(LocalDate date_fin) {
        this.date_fin = date_fin;
    }

    public LocalDate get_datefin() {
        return this.date_fin;
    }

    public Set<QuizFormateur> getQuizs() {
        return quizs;
    }

    public void setQuizs(HashSet<QuizFormateur> quizs) {
        this.quizs = quizs;
    }
    

    public Set<Notion> getNotions() {
		return notions;
	}

	public void setNotions(Set<Notion> notions) {
		this.notions = notions;
	}

	//-------------------- methodes -------------------------
	
	//retourne vrai si la formation est finie <=> sa date fin est arriv�e
	public boolean terminee() {
		return this.date_fin.compareTo(LocalDate.now())<0;
	}
	
	// ajouter une notion
	 public boolean add_notion(Notion n) {
		 return notions.add(n);
	 }
	
	// ajouter une notion mode console
    public void add_notion() {
        String nom, enonce, prop;
        int c1 = 0, c2 = 0, c3 = 0, c4 = 0;

        Notion notion;
        Scanner sc = new Scanner(System.in);

        System.out.println("veuillez introduire le titre de la notion");
        //sc.nextLine();
        nom = sc.nextLine();
        notion = new Notion(nom);
        System.out.println("veuillez introduire l'ensemble des questions");
        while (c1 != 2) {
            System.out.println("1/ ajouter une question");
            System.out.println("2/ quitter");
            switch (c1 = sc.nextInt()) {
                case 1:
                    System.out.println(" veuillez introduire le type de la question");
                    System.out.println("1/ QCU");
                    System.out.println("2/ QCM");
                    System.out.println("3/ QO");

                    switch (sc.nextInt()) {
                        case 1:
                            System.out.println(" veuillez introduire l'Enonce de la question");
                            QCU q = new QCU();
                            sc.nextLine();
                            enonce = sc.nextLine();
                            q.setEnonce(enonce);
                            while (c2 != 3) {
                                System.out.println("1/ ajouter une proposition correcte");
                                System.out.println("2/ ajouter une proposition fausse");
                                System.out.println("3/ quitter");
                                c2 = sc.nextInt();
                                switch (c2) {
                                    case 1:
                                        System.out.println("veuillez introduire la proposition correcte");
                                        sc.nextLine();
                                        prop = sc.nextLine();
                                        q.setPropositionCorrecte(prop);
                                        break;
                                    case 2:
                                        System.out.println("veuillez introduire la proposition fausse");
                                        sc.nextLine();
                                        prop = sc.nextLine();
                                        q.setPropositionFausse(prop);
                                        break;
                                }
                            }
                            c2 = 0;
                            notion.ajouterQuestion(q);
                            break;
                        case 2:
                            System.out.println(" veuillez introduire l'Enonce de la question");
                            QCM q1 = new QCM();
                            sc.nextLine();
                            enonce = sc.nextLine();
                            q1.setEnonce(enonce);
                            while (c3 != 3) {
                                System.out.println("1/ ajouter une proposition correcte");
                                System.out.println("2/ ajouter une proposition fausse");
                                System.out.println("3/ quitter");
                                //sc.nextInt();
                                switch (c3 = sc.nextInt()) {
                                    case 1:
                                        System.out.println("veuillez introduire la proposition correcte");
                                        sc.nextLine();
                                        prop = sc.nextLine();
                                        q1.setPropositionCorrecte(prop);
                                        break;
                                    case 2:
                                        System.out.println("veuillez introduire la proposition fausse");
                                        sc.nextLine();
                                        prop = sc.nextLine();
                                        q1.setPropositionFausse(prop);
                                        break;
                                }
                            }
                            c3 = 0;
                            notion.ajouterQuestion(q1);
                            break;
                        case 3:
                            System.out.println("veuillez introduire l'Enonce de la question");
                            QO q2 = new QO();
                            sc.nextLine();
                            enonce = sc.nextLine();
                            q2.setEnonce(enonce);
                            System.out.println();
                            while (c4 != 2) {
                                System.out.println("1/ ajouter une proposition correcte");
                                System.out.println("2/ quitter");
                                //sc.nextInt();
                                switch (c4 = sc.nextInt()) {
                                    case 1:
                                        System.out.println("veuillez introduire la proposition correcte");
                                        sc.nextLine();
                                        prop = sc.nextLine();
                                        q2.setPropositionCorrecte(prop);
                                        break;
                                }
                            }
                            c4 = 0;
                            notion.ajouterQuestion(q2);
                            break;
                    }
                    break;
            }
        }

        sc.close();
        notions.add(notion);
        System.out.println("Notion ajoutée avec succée !");
    }

    //--------------- afficher toutes les notions de la formation
    public void afficher_notions() {
        Iterator<Notion> it = this.notions.iterator();
        while (it.hasNext()) {
            it.next().afficher_notion();
        }
    }
    //-------------- afficher les titres de toutes les notions
    public void afficher_titresNotions() {
        Iterator<Notion> it = this.notions.iterator();
        int i=1;
        System.out.println();
        while (it.hasNext()) {
            System.out.println("Notion numéro "+i+" : "+it.next().getTitre());
            i++;
        }
    }
    //------------------ recherche la position d'une notion avec comme titre le string donn� en parametres----------------------------
    public int  recherche_positionNotion(String titre,Notion n) {
        Notion n1;n=new Notion("");
        int i =-1;
        boolean trouv=false;
        Set<Notion> ns= this.notions;
        Iterator<Notion> it= ns.iterator();

        while(it.hasNext() && !trouv) {
            i++;
            n1=it.next();
            if(n1.getTitre()==titre) {
                trouv=true;
                n=n1;
            }
        }
        return i;

    }
    
  //------------------ recherche une notion avec comme titre le string donn� en parametres et la re----------------------------
    public void recherche_notion(String titre,Notion n) {
        Notion n1;n=new Notion("");
        boolean trouv=false;
        Set<Notion> ns= this.notions;
        Iterator<Notion> it= ns.iterator();

        while(it.hasNext() && !trouv) {
            n1=it.next();
            if(n1.getTitre().equalsIgnoreCase(titre)) {
                trouv=true;
                n=n1;
            }
        }
   }
    
    // retourne vrai si une notion avec le titre donn� en parametre existe dans la formation
    public boolean recherche_notion(String titre) {
        Notion n1;
        boolean trouv=false;
        Set<Notion> ns= this.notions;
        Iterator<Notion> it= ns.iterator();

        while(it.hasNext() && !trouv) {
            n1=it.next();
            if(n1.getTitre().equalsIgnoreCase(titre)) {
                return true;
            }
        }
        return false;
   }
    //------- Supprimer une notion mode console------------------
    public void supprimer_notion() {
        String titre;
        Notion n=new Notion("");
        Scanner sc=new Scanner(System.in);
        System.out.println("Veuillez introduire le titre de la notion :");
        titre=sc.nextLine();
        recherche_positionNotion(titre,n);
        if (n.getTitre()!="") {
            this.notions.remove(n);
        }else {
            System.out.println("La notion que vous venez d'introduire n'existe pas");
        }
        sc.close();
    }
    
    //------- Supprimer une notion ------------------
    public void supprimer_notion(Notion n) {
    	notions.remove(n);
    }
    
//------------ modifier le titre d'une notion-----------------------
    public boolean modifierTitreNotion(Notion notion,String nouveauTitre){  
       if (!recherche_notion(nouveauTitre))
       { 
    	   notion.setTitre(nouveauTitre);
    	   return true;
       }
       else {
           return false;
       }
    }
    //----------- Creation du quiz ( Qformateur) mode console ----------------------
    public void creerQuiz(){
        QuizFormateur q=new QuizFormateur("",null,null);
        Scanner sc =new Scanner(System.in);
        String s;
        LocalDate date;
        int choix,nbquestions=0;
        System.out.println(" veuillez donner un titre au quiz : ");
        s=sc.nextLine();
        q.setNom(s);
        System.out.println(" veuillez introduire la date début du quiz : Format (jj/mm/aaaa) ");
        s=sc.nextLine();
        date=LocalDate.of(Integer.parseInt(s.substring(6,10)),Integer.parseInt(s.substring(3, 5)),Integer.parseInt(s.substring(0, 2)));
        q.setDateDebut(date);
        System.out.println(" veuillez introduire la date d'éxpration du quiz : Format (jj/mm/aaaa) ");
        s=sc.nextLine();
        date=LocalDate.of(Integer.parseInt(s.substring(6,10)),Integer.parseInt(s.substring(3, 5)),Integer.parseInt(s.substring(0, 2)));
        q.setDateFin(date);
        this.afficher_titresNotions();
        System.out.println(" veuillez introduire le numéro de la notion choisie : ");
        choix=sc.nextInt();
        if (choix>notions.size()){
            System.out.println(" Nombre incorrect veuillez réessayer á nouveau ");
        }else{
      // selected item on utilise recherche notion ( titre, Notion)
       // q.addNotion(notions.get(choix-1));
             }
        choix=-1;
        System.out.println(" Voulez vous :");
        System.out.println(" 1 / ajouter une nouvelle notion");
        System.out.println(" 2 / Quitter ");
        choix=sc.nextInt();
        while (choix != 2){
        switch (choix){
            case 1:
                this.afficher_titresNotions();
                System.out.println(" veuillez introduire le numéro de la notion choisie : ");
                choix=sc.nextInt();
                if (choix>notions.size()){
                    System.out.println(" Nombre incorrect veuillez réessayer á nouveau ");
                }else{
                    // hna on fait selected item String le titre de la notion
                   // q.addNotion(notions.get(choix-1));
                }
                break;
        }}
      Iterator<Notion> it= q.notions.iterator();
        while (it.hasNext()){
            nbquestions+= it.next().getQuest().size();
        }
     q.setNbQuestions(nbquestions);
      this.add_quiz(q);
     q.afficherQuiz();

     sc.close();
    }
    
    
    // creation d'un nouveau quiz � partir d'une map notion /nb de questions choisis par l'utilisateur -------------------------
    public QuizFormateur creerQuiz(String nom,LocalDate dateOuverture,LocalDate dateExpiration,Map<Notion,Integer> listeNotions)
    {
    	QuizFormateur quiz = new QuizFormateur(nom,dateOuverture,dateExpiration);
    	Notion notionChoisi,newNotion;
    	int nbQuestions,numQuest;

    	
    	Random random= new Random();
    	for(Map.Entry<Notion,Integer> coupleNotionInt: listeNotions.entrySet()) {
    		notionChoisi=coupleNotionInt.getKey();		
    		newNotion=new Notion(notionChoisi);		
    		nbQuestions=coupleNotionInt.getValue();  	
    		int nbQuestsAjoutees=0;
    		while(nbQuestsAjoutees!=nbQuestions) {

    			numQuest=random.nextInt(notionChoisi.getQuest().size());
    			if (newNotion.ajouterQuestion((Question)(notionChoisi.getQuest().toArray()[numQuest]))) {
    				nbQuestsAjoutees++;
    			}
        		
    		}
    		quiz.addNotion(newNotion); 		
    		quiz.incrementerNbQuest(nbQuestions);
    	}
    	return quiz;
    }
    
   
    
    
    
    
    //------- Transformer un quiz formateur en un quiz Apprenant
    public Qapprenant Quiz_to_Qapprenant(QuizFormateur q){
    	
        Qapprenant p=new Qapprenant(q.getNom(),q.getDateDebut(),q.getDateFin()); 
        Notion n;
        Iterator<Notion> it = q.notions.iterator();
        while (it.hasNext()){
            n=it.next();
            Iterator<Question> it1 =n.getQuest().iterator();
            while (it1.hasNext()){
                p.add_entry(it1.next(),new Reponse());
            }}
        return p;
    }

    
    
    // ajouter un quiz 
    public boolean add_quiz(QuizFormateur quiz) {
        return quizs.add(quiz);
    }
    
    // supprimer un quiz mode console 
    public void supprimer_quiz(){
        int choix=0;
        Scanner sc=new Scanner(System.in);
        this.afficherListQuiz();
        System.out.println(" Veuillez introduire le numéro du Quiz que vous voulez supprimer :");
        choix=sc.nextInt();
        if (choix<=this.quizs.size() && choix>0 ){ this.quizs.remove((QuizFormateur)quizs.toArray()[choix - 1]);}
        else{ System.out.println(" Numéro introduit est incorrecte veuillez réessayer á nouveau"); }
        sc.close();
    }
    
 // modifier un quiz mode console 
    public void modifier_quiz(){
        int choix=0,choix1,numquiz,numNotion;
        LocalDate date;
        String s;
        QuizFormateur q;
        Scanner sc = new Scanner(System.in);
        this.afficherListQuiz();
        System.out.println(" Entrez le numéro du Quiz que vous voulez modifier :");
        numquiz=sc.nextInt();
        if (numquiz<=quizs.size()&& numquiz>0) {
            while (choix != 5) {
                q = (QuizFormateur)quizs.toArray()[numquiz - 1];
                System.out.println(" Voulez vous :");
                System.out.println(" 1/ Modifier le titre du Quiz ");
                System.out.println(" 2/ Modifier la date Début du Quiz");
                System.out.println(" 3/ Modifier la date Fin du Quiz");
                System.out.println(" 4/ Modifier les Notions du Quiz");
                System.out.println(" 5/ Quitter");
                System.out.println(" Veuillez Introduire le numéro de votre choix :");
                choix = sc.nextInt();
                switch (choix) {
                    case 1:
                        System.out.println(" Veuillez Introduire le nouveau Titre :");
                        sc.nextLine();
                        s = sc.nextLine();
                        q.setNom(s);
                     
                        break;
                    case 2:
                        System.out.println(" Veuillez Introduire la nouvelle date de début : format jj/mm/aaaa");
                        sc.nextLine();
                        s = sc.nextLine();
                        date = LocalDate.of(Integer.parseInt(s.substring(6, 10)), Integer.parseInt(s.substring(3, 5)), Integer.parseInt(s.substring(0, 2)));
                        q.setDateDebut(date);
                       
                
                        break;
                    case 3:
                        System.out.println(" Veuillez Introduire la nouvelle date de fin : format jj/mm/aaaa");
                        sc.nextLine();
                        s = sc.nextLine();
                        date = LocalDate.of(Integer.parseInt(s.substring(6, 10)), Integer.parseInt(s.substring(3, 5)), Integer.parseInt(s.substring(0, 2)));
                        q.setDateFin(date);
                   
                        break;
                    case 4:
                        System.out.println(" Voulez vous : ");
                        System.out.println(" 1/ Modifier la Notion ");
                        System.out.println(" 2/ Supprimer La Notion ");
                        System.out.println(" 3/ Ajouter une Notion ");
                        System.out.println(" Veuillez Introduire le numéro de votre choix :");
                        choix1 = sc.nextInt();
                        switch (choix1) {
                            case 1:
                            	((QuizFormateur)(quizs.toArray()[numquiz-1])).afficher_titresNotions();
                                System.out.println(" Veuillez introduire le numéro de la notion");
                                numNotion = sc.nextInt();
                                if (numNotion <= ((QuizFormateur)(quizs.toArray()[numquiz-1])).notions.size() && numNotion > 0) {
                                    // slected item
                                  //  quizs.get(numquiz - 1).getNotion(numNotion - 1).modifier_Notion();
                                } else {
                                    System.out.println(" Veuillez introduire un numéro valide");
                                }
                                break;
                            case 2:
                            	((QuizFormateur)(quizs.toArray()[numquiz-1])).afficher_titresNotions();
                                System.out.println(" Veuillez introduire le numéro de la notion");
                                numNotion = sc.nextInt();
                                if (numNotion <= ((QuizFormateur)(quizs.toArray()[numquiz-1])).notions.size() && numNotion > 0) {
                                    //ywelou selected item
                                   // quizs.get(numquiz - 1).setNbQuestions( quizs.get(numquiz - 1).getNbQuestions()-quizs.get(numquiz - 1).notions.get(numNotion - 1).getQuest().size());
                                    //quizs.get(numquiz - 1).notions.remove(numNotion - 1);

                                } else {
                                    System.out.println(" Veuillez introduire un numéro valide");
                                }
                                 break;
                            case 3:
                                this.afficher_titresNotions();
                                System.out.println(" Veuillez introduire le numéro de la notion");
                                numNotion = sc.nextInt();
                                if (numNotion <= this.notions.size() && numNotion > 0) {
                                    // hedou yweliw selected item
                                    //quizs.get(numquiz - 1).setNbQuestions( quizs.get(numquiz - 1).getNbQuestions()+this.notions.get(numNotion - 1).getQuest().size());
                                  //  quizs.get(numquiz - 1).addNotion(this.notions.get(numNotion - 1));
                                } else {
                                    System.out.println(" Veuillez introduire un numéro valide");
                                }
                                break;

                        }
                        break;
                }
            }
        }
        ((QuizFormateur)(quizs.toArray()[numquiz-1])).afficherQuiz();
        sc.close();
    }
    
    //fficher la liste des quizs mode console
    public void afficherListQuiz() {
        int i=1;
        for (Quiz q:quizs) {
            System.out.println("---------------------------------------------------------------------------------------------------");
            System.out.println("Quiz°"+i+"   Titre : "+q.getNom() +"  Date debut : "+q.getDateDebut().toString()+"  Date d'éxpiration : "+q.getDateFin().toString());
            System.out.println("---------------------------------------------------------------------------------------------------");
        }
    }
}

