package noyau;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;


public class QuizFormateur extends Quiz {

	private static final long serialVersionUID = 6979691458343068713L;
	private boolean distribue;
	
	public QuizFormateur(String nom,LocalDate dateOuverture,LocalDate dateExpiration) {
		super(nom,dateOuverture,dateExpiration);
	}
    public void setNom(String nom) {
        super.nom = nom;
    }
    public void setDateDebut(LocalDate dateDebut) {
        super.dateDebut = dateDebut;
    }
    public void setDateFin(LocalDate dateFin) {
        super.dateFin = dateFin;
    }
    public void setNbQuestions(int nbQuestions) {
        super.nbQuestions = nbQuestions;
    }
    
    public void incrementerNbQuest(int delta) {
    	super.nbQuestions += delta;
    }
    
    public boolean isDistribue() {
		return distribue;
	}
	public void setDistribue(boolean distribue) {
		this.distribue = distribue;
	}
	
	public boolean addNotion(Notion n){ return super.notions.add(n);}
    
    public boolean supprimerQuestion(Question q) {
    	
    	Notion n;
    	Iterator<Notion> it=super.notions.iterator();
    	
    	while(it.hasNext()) {
    		n=it.next();
    		if(n.contientQuestion(q)){
    		
    			n.supprimerQuestion(q);
    			this.nbQuestions--;
    			return true;
    		}
    	}
    	return false;
    }

    public boolean  AjouterQuestQuiz(Notion notionChoisi) {
     	Notion notion=	recherche_notion(notionChoisi.getTitre());
    	
    	if (notion!= null) {
   
    		if(notionChoisi.toutesLesQuestionsSonDans(notion)) {
    			System.out.println("toutes les questions de cette notion sont dej adans le quiz");
    			return false;
    		}
    		else {
    			Random random=new Random();
        		int numQuest;
        		numQuest=random.nextInt(notionChoisi.getQuest().size());
        	  
        		while(!notion.ajouterQuestion((Question)(notionChoisi.getQuest().toArray()[numQuest]))) {
        			System.out.println(((Question)(notionChoisi.getQuest().toArray()[numQuest])).getEnonce());
        	    	System.out.println(numQuest);
        			numQuest=random.nextInt(notionChoisi.getQuest().size());		
        		}
        		this.incrementerNbQuest(1);	
        		return true;
    		}
    	
    		
    	}
    	else {
    		
    		//la notion n'existe pas dans le quiz 
    		notion=new Notion(notionChoisi);
    		Random random=new Random();
    		int numQuest=random.nextInt(notionChoisi.getQuest().size());
			notion.ajouterQuestion((Question)(notionChoisi.getQuest().toArray()[numQuest]));
			this.addNotion(notion); 		
    		this.incrementerNbQuest(1);
    		return true;
    	}
    	
    	
    }
    
    
    
    public boolean  ajouQuestionMemeNotion(Question questionChoisi,Set<Notion> listNotions) {
    	
    	//recherche pour savoir de quelle notion parmis toutes les notions de la formation provient la question 
    	Notion notion=rechercheQuestion(questionChoisi,listNotions); 
    	
    	//recherche pour avoir la notion du quiz qui contient la question
    	Notion notionQuiz = rechercheQuestion(questionChoisi,this.notions);
    	
    	if (notion != null) {
    			
    		if (notion.getQuest().size()>1) {
    			
    			if (notion.toutesLesQuestionsSonDans(notionQuiz)) {
    				System.out.println("toutes les questions de cette notion sont dej adans le quiz");
    				return false;
    			}
    			else {
    				Random random=new Random();
            		int numQuest;
            		numQuest=random.nextInt(notion.getQuest().size());
            		while(!notionQuiz.ajouterQuestion((Question)(notion.getQuest().toArray()[numQuest]))) {
            			numQuest=random.nextInt(notion.getQuest().size());		
            		}
            		this.incrementerNbQuest(1);
            		return true;
            		
    			}
    			
    			
        		
    		}
    		else {
    			System.out.println("Cette notion ne contient pas assez de questions ");
    			return false;
    		}
    		   		
    	}
    	else {
    		System.out.println("Cette notion a ete supprimé vous ne pouvez plus remplacer de questions");	
    		return false;
    	}
    	
    	
    }
    
    public Notion recherche_notion(String titre) {
        Notion n1;
        Set<Notion> ns= this.notions;
        Iterator<Notion> it= ns.iterator();

        while(it.hasNext()) {
            n1=it.next();
            if(n1.getTitre().equals(titre)) {
            	return n1;           
            }
        }
        return null;  
   }

    public Notion rechercheQuestion(Question q,Collection<Notion> listNotions) {
        Notion n;
        Iterator<Notion> it= listNotions.iterator();

        while(it.hasNext()) {
            n=it.next();
            if(n.contientQuestion(q)) {
                return n;       
            }
        }
        return null;
   }

    @Override
    public boolean equals(Object o) {
    	return(this.nom.equals(((QuizFormateur)o).nom));
    }
    
    @Override
    public int hashCode() {
    	System.out.println("Salut");
        int hash = 0;
        hash += (nom != null ? nom.toUpperCase().hashCode() : 0);
        return hash;
    }

}

