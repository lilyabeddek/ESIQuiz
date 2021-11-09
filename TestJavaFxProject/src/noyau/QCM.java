package noyau;

import java.util.ArrayList;

public class QCM extends QC_UM {

	protected static final long serialVersionUID = 6979691458343068713L;
	public QCM() {}
	public QCM(String enonce) {
        super(enonce);
    }
	
	
	
	public boolean setPropositionCorrecte(String prop) {

    	ArrayList<String> allPropositions= new ArrayList<String>();
    	allPropositions.addAll(this.propositionsCorrectes);
    	allPropositions.addAll(this.propositionsFausses);
    	
    	if (allPropositions.contains(prop)) {return false;}
    	else {return this.propositionsCorrectes.add(prop);}
        
    }
	
	
    public void evaluer (Reponse rep) {

        double pourcentage =0;
        double nbPropositions= super.propositionsCorrectes.size()+super.propositionsFausses.size();
        
        for (String propCorrecte : super.propositionsCorrectes) {
           

            if( rep.getReponsesSChoisies().contains(propCorrecte)){
            	
                pourcentage += 1/nbPropositions;
            }
            else {
                pourcentage -= 1/nbPropositions;
            }
        }

        for (String propIncorrecte : super.propositionsFausses) {

            if( rep.getReponsesSChoisies().contains(propIncorrecte)){
                pourcentage -= 1/nbPropositions;
            }
            else {
                pourcentage += 1/nbPropositions;
            }

        }
        if (pourcentage >0) {

            rep.setpObtenu((double) Math.round(pourcentage * 100));
        }
        else {
            rep.setpObtenu(0);
        }


    }
    
 
}
