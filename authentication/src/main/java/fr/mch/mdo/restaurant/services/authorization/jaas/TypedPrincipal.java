package fr.mch.mdo.restaurant.services.authorization.jaas;

import java.io.Serializable;
import java.security.Principal;

/**
 * TypedPrincipals are derived from, and can be treated like Principals but they
 * also contain extra information about the type of the Principal which can be
 * USER, GROUP or DOMAIN. I'm not 100% certain that this is a good way of doing
 * things. Suggestions welcome.
 * 
 * @author Andy Armstrong
 * @version 1.0.3
 */
public class TypedPrincipal implements Principal, Serializable
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     */
    protected String name;

    /**
     * Create a TypedPrincipal with a name.
     * 
     * <p>
     * 
     * @param name
     *                the name for this Principal.
     * @exception NullPointerException
     *                    if the <code>name</code> is <code>null</code>.
     */
    public TypedPrincipal(String name) {
	this.name = name;
    }

    public void setName(String name) {
	this.name = name;
    }

    /**
     * Return the name for this <code>TypedPrincipal</code>.
     * 
     * <p>
     * 
     * @return the name for this <code>TypedPrincipal</code>
     */
    public String getName() {
	return name;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	TypedPrincipal other = (TypedPrincipal) obj;
	if (name == null) {
	    if (other.name != null) {
		return false;
	    }
	} else if (!name.equals(other.name)) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "TypedPrincipal [name=" + name + "]";
    }
}