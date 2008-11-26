package gr.sch.ira.minoas.seam.components;



import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.security.Identity;

@Name("authenticator")

public class Authenticator extends BaseSeamComponent {
	@In
	protected Identity identity;

	
	
	public boolean authenticate() {
		info("authenticating #0", identity.getUsername());
		
		// write your authentication logic here,
		// return true if the authentication was
		// successful, false otherwise
		if(identity.getUsername().equals("slavikos"))
			identity.addRole("admin");
		return true;
	}
}
