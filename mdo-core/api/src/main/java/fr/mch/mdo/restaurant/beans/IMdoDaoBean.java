package fr.mch.mdo.restaurant.beans;

public interface IMdoDaoBean extends IMdoBeanIdentifiable
{
    boolean isDeleted();
    
    void setDeleted(boolean deleted);
}
