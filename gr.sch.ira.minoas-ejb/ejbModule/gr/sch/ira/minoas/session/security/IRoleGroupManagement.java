package gr.sch.ira.minoas.session.security;

public interface IRoleGroupManagement {

	public void constructNewRoleGroup();

	public void removeRoleGroup();

	public abstract void saveRoleGroup();

	public void search();

	public void selectRoleGroup();

	public void setSearchString(String searchString);

	public void updateRoleGroup();

}