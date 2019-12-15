package fr.laas.om2m.ipe;


 
/**
 * This is the interface of the sensor technology you want to connect to OM2M in this activity
 */
public interface ICreate {
	/**
	 * This function creates an AE in OM2M associated to a repository in OM2M
	 */
	public void createRepository(String name);
	
	/**
	 * Export an Avatar data to OM2M
	  
	 */
	public void ExportMetaData(String avatarOntology, String urlAvatar,String rep);
	
	
	
}
