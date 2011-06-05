package fr.mch.mdo.restaurant.beans;

public class MdoDtoBean implements IMdoDtoBean 
{
	private static final long serialVersionUID = 1L;

	protected Long id;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		MdoDtoBean other = (MdoDtoBean) obj;
		if (id == null) {
			// Whenever the value of other.id(maybe null)
			return false;
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "MdoDtoBean [id=" + id + "]";
	}
}
