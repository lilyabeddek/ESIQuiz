package noyau;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Reponse implements Serializable{
    //attributs
	protected static final long serialVersionUID = 6979691458343068713L;
    private List<String> reponsesChoisies= new ArrayList < String >();
    private double pObtenu;


    //Constructor
    public Reponse(List<String> reponsesChoisies) {
        this.reponsesChoisies = reponsesChoisies;
    }
    public Reponse(String responseChoisie) {
        this.reponsesChoisies.add(responseChoisie);
    }
    public Reponse() {}

    //retourne la liste des reponses dans le cas QCM

    public List<String> getReponsesSChoisies() {
        return reponsesChoisies;
    }
    //retourne la proposition choisi ou la reponse introduite dans le cas Qo Qcu
    public String getResponseChoisie() {
        return reponsesChoisies.get(0);
    }

    public void setReponsesChoisies(List<String> reponsesChoisies) {
    	this.reponsesChoisies.clear();
        this.reponsesChoisies.addAll(reponsesChoisies);
    }
    public void setReponseChoisie(String reponseChoisie) {
    	this.reponsesChoisies.clear();
        this.reponsesChoisies.add(reponseChoisie);
    }
    public double getpObtenu() {
        return pObtenu;
    }
    public void setpObtenu(double pObtenu) {
        this.pObtenu = pObtenu;
    }
    public void supprimerResponse(String proposition) {
        this.reponsesChoisies.remove(proposition);
    }


}
