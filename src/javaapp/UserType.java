
package javaapp;

public class UserType {
    
    protected int hierarchie;
    private String username;
    
    protected  UserType (int niveau){
    this.hierarchie = niveau;
    }

    UserType(int niveau, String usernameValidated) {
      this.hierarchie = niveau;
      this.username = usernameValidated;
    }
   
  public int getHierarchie() {
        return hierarchie;
    }

    public void setHierarchie(int hierarchie) {
        this.hierarchie = hierarchie;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

 
    
}
