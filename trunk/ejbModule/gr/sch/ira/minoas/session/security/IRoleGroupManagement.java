package gr.sch.ira.minoas.session.security;

public interface IRoleGroupManagement {

	public void search();

	public void setSearchString(String searchString);

	public void updateRoleGroup();

	public void removeRoleGroup();

	public void selectRoleGroup();

	public void constructNewRoleGroup();

	public abstract void saveRoleGroup();

}