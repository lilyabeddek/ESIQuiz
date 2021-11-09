package noyau;

import java.util.Iterator;

public class QO extends Question {

	protected static final long serialVersionUID = 6979691458343068713L;
	public QO() {}
	public QO(String enonce) {
        super(enonce);
    }
	

    public void evaluer (Reponse rep) {

    	Iterator<String> it= super.getPropositionsCorrectes().iterator();
    	boolean stop=false;
    	String s="";
    	
    	while(it.hasNext() && !stop) {
    		s=it.next();

    		 if (rep.getResponseChoisie().equalsIgnoreCase(s) )
    	        {
    	            rep.setpObtenu(100);
    	            stop=true;
    	        }
    	        		
    	}
    	if(!stop) {
 	         rep.setpObtenu(0);        
    	}
       
    }
    public void afficher_quest() {
        System.out.println(" Question :  " + this.getEnonce());
        System.out.println("***** la proposition correcte *****");
        this.afficherListPropositionsCorrectes();
    }
    
    
}

