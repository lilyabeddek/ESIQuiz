package noyau;

import java.util.ArrayList;

public class QCU extends QC_UM {

	protected static final long serialVersionUID = 6979691458343068713L;
	public QCU() {}
	public QCU(String enonce) {
        super(enonce);
    }
	
	public boolean setPropositionCorrecte(String prop) {
	
    	ArrayList<String> allPropositions= new ArrayList<String>();
    	allPropositions.addAll(this.propositionsCorrectes);
    	allPropositions.addAll(this.propositionsFausses);
    	
    	if (allPropositions.contains(prop)) {return false;}
    	else {return this.propositionsCorrectes.add(prop);}
        
    }
	
	public boolean RemplacerPropositionCorrecte(String prop) {
		
    	if (this.propositionsCorrectes.contains(prop)) {return true;}
    	else if(this.propositionsFausses.contains(prop)) {
    		return false;
    	}
    	else {
    		this.propositionsCorrectes.remove(this.getPropositionCorrecte());
    		this.propositionsCorrectes.add(prop);
    		return true;		
    	}
        
    }
	
    public void evaluer (Reponse rep) {
    	
        if (rep.getResponseChoisie().equalsIgnoreCase(super.propositionsCorrectes.iterator().next()) )
        {
            rep.setpObtenu(100);
        }
        else {
            rep.setpObtenu(0);
        }


    }
    
    
   /* @Override
    public boolean equals(Object arg0) {
    	return this.enonce.equalsIgnoreCase(((QCU)arg0).getEnonce());
    }*/

}

