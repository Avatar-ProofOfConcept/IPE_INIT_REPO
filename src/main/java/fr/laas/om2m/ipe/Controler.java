package fr.laas.om2m.ipe;



public class Controler {
	public static void main(String[] args) {
		 
		IPE ipe = new IPE();
		
		 ipe.createRepository("Repository04");
		 ipe.ExportMetaData("OntologyFiles/Avatar1.owl","http://localhost:"+3001+"/","Repository04");
		 ipe.ExportMetaData("OntologyFiles/Avatar2.owl","http://localhost:"+3002+"/","Repository04");
		 ipe.ExportMetaData("OntologyFiles/Avatar3.owl","http://localhost:"+3003+"/","Repository04");


		
	}
}
