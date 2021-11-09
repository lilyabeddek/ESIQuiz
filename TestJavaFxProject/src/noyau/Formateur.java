package noyau;

import java.time.LocalDate;
import java.util.*;

public class Formateur extends Utilisateur{

	//attributs
	protected static final long serialVersionUID = 6979691458343068713L;
    protected Formation formation;
    protected TreeSet<Apprenant> apprenants=new TreeSet<Apprenant>();

    //constructeur
    public Formateur(String login, String passwd) {
        super(login,passwd);
    }

    public TreeSet<Apprenant> getApprenants() {
 		return apprenants;
 	}

 	public void setApprenants(TreeSet<Apprenant> apprenants) {
 		this.apprenants = apprenants;
 	}
    
 	//creer une formation
    public void set_formation (String nom,String description, LocalDate datedeb,LocalDate datefin) {          
        this.formation=new Formation(nom,description,datedeb,datefin);
        afficherFormation();
    }
    
    public Formation get_formation () {          
        return this.formation;
    }
     //retourne l'apprenant de la position i (mode console)
     public Apprenant getApprenant(int i) {
    	 return (Apprenant)(apprenants.toArray())[i];
     }
     
     //teste si le formateur a dÈj‡ crÈer une formation ou pas 
     public boolean hasFormation() {
    	 return formation!=null;
     }
     
     //ajout d'un apprenant ‡ la liste des apprenants
     public boolean ajouter_apprenant (String nom,String prenom,LocalDate date_naissance, String adresse) {

         String login = prenom.substring(0, 1)+nom;
         String pswrd =nom+date_naissance.toString();
         Apprenant app = new Apprenant(login,pswrd,nom,prenom,date_naissance,adresse);
         if (this.hasFormation()) {copieQuizsNouvelApprenant(app);  }
             
         System.out.println("------------------------------------------------");
         System.out.println("--Le nouvel apprenant a √©t√© ajout√© avec succee--");
         System.out.println("Login : "+login);
         System.out.println("Mot de passe : "+pswrd);
         System.out.println("Nom : "+nom);
         System.out.println("Prenom : "+prenom);
         System.out.println("Adresse : "+adresse);
         System.out.println("Date de naissance : "+date_naissance.toString());   
         System.out.println("Nb de quiz CopiÈs :"+app.getQuizs().size());
         System.out.println("------------------------------------------------");
         return apprenants.add(app);
        

     }

     
     //creation d'une formation en mode console
    public void set_formation () {
        Scanner sc =new Scanner(System.in);
        String nom;String description;String dt,dt1; LocalDate datedeb; LocalDate datefin;
        System.out.println();

        System.out.println("------------------------------------------------------");
        System.out.println("  Veuillez saisir le titre de votre formation :");
        nom=sc.nextLine();
        System.out.println("------------------------------------------------------");
        System.out.println("  Veuillez saisir une petite description de cette formation  :");
        //sc.nextLine();
        description=sc.nextLine();
        System.out.println("------------------------------------------------------");
        System.out.println("  Veuillez saisir la date de debut de la formation : format jj/mm/aaaa");
        //sc.nextLine();
        dt=sc.nextLine();
        datedeb=LocalDate.of(Integer.parseInt(dt.substring(6,10)),Integer.parseInt(dt.substring(3, 5))-1,Integer.parseInt(dt.substring(0, 2)));
        //datedeb.set(Integer.parseInt(date.substring(0, 1)),Integer.parseInt(date.substring(3, 4))-1,Integer.parseInt(date.substring(6,9)));
        System.out.println("------------------------------------------------------");
        System.out.println("  Veuillez saisir la date de fin de la formation :");
        //sc.nextLine();
        dt1=sc.nextLine();
        datefin=LocalDate.of(Integer.parseInt(dt1.substring(6,10)),Integer.parseInt(dt1.substring(3,5))-1,Integer.parseInt(dt1.substring(0, 2)));
        //datefin.set(Integer.parseInt(date.substring(0, 1)),Integer.parseInt(date.substring(3, 4))-1,Integer.parseInt(date.substring(6,9)));
        this.formation=new Formation(nom,description,datedeb,datefin);
        System.out.println("------------------------------------------------------");

        int choix=1;
        do {
            System.out.println("-------------------------Menu-------------------------");
            System.out.println("1/ Ajouter une notion ");
            System.out.println("2/ Quitter");
            System.out.println("------------------------------------------------------");
            System.out.println("Entez le numero de votre choix :");
            //sc.nextLine();
            choix=sc.nextInt();
            switch(choix) {
                case 1:
                    formation.add_notion();
                    break;
                case 2:
                    System.out.println("La formation a √©t√© cr√©e a √©t√© ajout√© avec succ√©e");
                    break;
                default :
                    System.out.println("les numeros de choix possibles sont 1 et 2");
                    break;
            }

        }while(choix!=2);

        sc.close();

    }

    
    //afficher les informations de formation (test de la creation en mode console)
    public void afficherFormation() {
        System.out.println("");
        System.out.println("------------------------------------------------------");
        System.out.println("Formation :");
        System.out.println("Titre : "+formation.get_nom());
        System.out.println("Description : "+formation.get_description());
        System.out.println("Date debut : "+formation.get_datedeb().toString());
        System.out.println("Date Fin : "+formation.get_datefin().toString());
        System.out.println("");
        System.out.println("Liste des notions :");
        formation.afficher_notions();
        System.out.println("------------------------------------------------------");
    }
    
    //ajout d'un apprenant en mode console
    public void ajouter_apprenant () {

        String login;
        String pswrd;
        String nom;
        String prenom;
        String adresse;
        String date;
        LocalDate date_naissance;

        Scanner sc = new Scanner(System.in);
        int choix =1;
        int cpt=0;
        while(choix!=2){
            System.out.println();
            System.out.println("------------------------------");
            System.out.println("Entez le nom du nouvel apprenant : ");
            if (cpt>0)
            {sc.nextLine();}
            nom=sc.nextLine();
            System.out.println("Entez le pr√©nom du nouvel apprenant : ");
            //sc.nextLine();
            prenom=sc.nextLine();
            System.out.println("Entez l'adresse du nouvel apprenant : ");
            //sc.nextLine();
            adresse=sc.nextLine();
            System.out.println("Entez la date de naissance du nouvel apprenant : format jj/mm/aaaa");
            date=sc.nextLine();
            date_naissance=LocalDate.of(Integer.parseInt(date.substring(6,10)),Integer.parseInt(date.substring(3, 5)),Integer.parseInt(date.substring(0, 2)));
            //date_naissance=new Date(Integer.parseInt(date.substring(6, 9)),Integer.parseInt(date.substring(3, 4)),Integer.parseInt(date.substring(0, 1)));
            login= prenom.substring(0, 1)+nom;
            pswrd=nom+date_naissance.toString();
            apprenants.add(new Apprenant(login,pswrd,nom,prenom,date_naissance,adresse));
            System.out.println("------------------------------------------------");
            System.out.println("--Le nouvel apprenant a √©t√© ajout√© avec succee--");
            System.out.println("Login : "+login);
            System.out.println("Mot de passe : "+pswrd);
            System.out.println("Nom : "+nom);
            System.out.println("Prenom : "+prenom);
            System.out.println("Adresse : "+adresse);
            System.out.println("Date de naissance : "+date_naissance.toString());
            System.out.println("------------------------------------------------");



            System.out.println("----------------------Menu----------------------");
            System.out.println("1/-Ajouter un autre apprenant ");
            System.out.println("2/-Quitter");
            System.out.println("------------------------------------------------");
            cpt ++;
            //sc.nextLine();
            choix=sc.nextInt();
            if (choix==2) {System.out.println("le dernier apprenant a √©t√© ajout√© avec succ√©e");}


        }
        sc.close();
    }

    //autentification de l'apprenant
    public int rechApprenant(Utilisateur user) {
   	 int i=0;
        while(i<apprenants.size()) {
       	 if (user.compareTo(((Apprenant)((apprenants.toArray())[i])))==1) {	 
                return i;
       	 }
            else if (user.compareTo(((Apprenant)((apprenants.toArray())[i])))==0){return -1;}
            else {i++;}
       	 
        }
        return -2;
   }
  
    //autentification de l'apprenant 2
	public int rechApprenant(String userName,String password) {
		 int i=0;
        while(i<apprenants.size()) {
       	 if (((Apprenant)((apprenants.toArray())[i])).compareTo(userName,password)==1) {	 
                return i;
       	 }
            else if (((Apprenant)((apprenants.toArray())[i])).compareTo(userName,password)==0){return -1;}
            else {i++;}
       	 
        }
        return -2;
   }

	//suppression d'un apprenant en mode console 
    public void supprimer_apprenant () {
        afficherListeApprenants();
        Scanner sc= new Scanner(System.in);
        int num;
        do {
            System.out.println("");
            System.out.println("Entrez la le numero de l'etudiant que vous voulez supprimer :");
            num= sc.nextInt();
            if((num-1)<=0 || (num-1)>= apprenants.size()) {System.out.println("le nombre que vous avez introduit est invalide ! Reessayez");}
        }while((num-1)<=0 || (num-1)>= apprenants.size());
        apprenants.remove((Apprenant)(apprenants.toArray()[num]));
        System.out.println("L'Apprenant a ete supprimÈ avec succËs !");
        sc.close();
    }

    
    //suppression d'un apprenant
    public void supprimer_apprenant (Apprenant p) {
    	apprenants.remove(p);
    }
    
    //affichage de la liste des apprenants mode console
    public void afficherListeApprenants() {
        int i=1;
        for (Apprenant app:apprenants) {
            app.afficherApp(i);
            i++;
        }
    }

    //consultation des activitÈs de l'aprenant mode console
    public void consulter_apprenant (String Nom, String prenom) {
        Iterator<Apprenant>it =apprenants.iterator();
        int i=1;
        Apprenant p;
        boolean trouv=false;
        while (it.hasNext()&& !trouv){
            p=it.next();
            if(p.getNom()==Nom && p.getPrenom()==prenom ){
                p.afficherApp(i);
                trouv=true;
            }
            i++;
        }
        if (!trouv){
            System.out.println(" Aprennant inexistant ");}

    }

    //afficher le classement des apprenants selon leurs moyenne
      public TreeSet<Apprenant> afficher_classement () {
      
        TreeSet<Apprenant> app = new TreeSet<Apprenant>(new Comparator<Apprenant>() {
            @Override
            public int compare(Apprenant o1, Apprenant o2) {
            	 if(Float.compare(o2.getMoyenne(),o1.getMoyenne())==0) {
            		 if (o1.getUsername().compareTo(o2.getUsername())==0 && o1.getPasswd().compareTo(o2.getPasswd())==0 ) {return 0;}
            	    	else {
            	    		if(o1.getNom().compareTo(o2.getNom())>=0) {return 1;}
            	    		else {return -1;}
            	    	}
            	 }
            	 else {
            		 return Float.compare(o2.getMoyenne(),o1.getMoyenne());
            	 }
            	            
            }
        });
        app.addAll(apprenants);
        return app;
       
    }


    // procedure de copie de tous les quizs ouverts non distribuÈ(copiÈ) vers les comptes des apprenants     
    public void copieQuizsTousApprenants(){
        QuizFormateur q;
        
        Iterator<QuizFormateur> it =formation.getQuizs().iterator();
        Iterator<Apprenant> it1= apprenants.iterator();
        while (it.hasNext()){
        	
            q=it.next();
            if (q.estOuvert() && !q.estExpire() && !q.isDistribue()){
                while (it1.hasNext()){
                    it1.next().addQuiz(formation.Quiz_to_Qapprenant(q));
                }
                q.setDistribue(true);          
            }
        }
    }
    
    //procedure de copie de tous les quizs ouverts verts le compte d'un nouvel apprenant
    private void copieQuizsNouvelApprenant(Apprenant apprenant){
        QuizFormateur q;
        Iterator<QuizFormateur> it =formation.getQuizs().iterator();
      
        while (it.hasNext()){
            q=it.next();
            if (q.estOuvert() && !q.estExpire()){      
                apprenant.addQuiz(formation.Quiz_to_Qapprenant(q));     
            }
        }
    }
    
    //copie d'un nouveau quiz ouvert vers les comptes tous les apprenants
    public  void copieQuizTousApprenants(QuizFormateur q) {
    	 Apprenant app;
    	 Iterator<Apprenant> it =apprenants.iterator();
    	 while (it.hasNext()){
             app=it.next();
             app.addQuiz(formation.Quiz_to_Qapprenant(q)); 
         }
    	 q.setDistribue(true);
    	 
    }
    
    
    
   
}

