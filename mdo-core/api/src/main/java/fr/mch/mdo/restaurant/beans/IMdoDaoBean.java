package fr.mch.mdo.restaurant.beans;

public interface IMdoDaoBean extends IMdoBean
{
    Long getId();

    void setId(Long id);

    boolean isDeleted();
    
    void setDeleted(boolean deleted);
}
