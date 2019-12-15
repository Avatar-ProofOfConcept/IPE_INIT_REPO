package fr.laas.om2m.ipe;

 import java.util.ArrayList;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.ContentInstance;
  
import fr.laas.mooc.helper.http.HTTPPost;
import fr.laas.mooc.helper.om2m.ResourceCreator;
import fr.laas.mooc.helper.om2m.Serializer;
 import fr.laas.mooc.helper.virtual.SensorManager;
 

public class IPE implements ICreate {
 
	 
 
public void ExportMetaData(String avatarOntology, String urlAvatar,String rep){
		
		//The model containing the data/ontology
		Model modelData = ModelFactory.createDefaultModel();
        modelData.read(avatarOntology);
		String url=urlAvatar;
		//Extract the necesseries Meta Data of the Avatar from the URL
		String name = ExtractData.ExtractName(modelData);
		String owner = ExtractData.ExtractOwner(modelData);
		String location = ExtractData.ExtractLocation(modelData);
		Double latitude = Double.parseDouble(location.split("&")[0]);
		Double longitude = Double.parseDouble(location.split("&")[1]);
		ArrayList <Interest> interestsList = ExtractData.ExtractInterests(modelData);
		//System.out.println("		[EXPORT META DATA OF AVATAR]: "+name+"		"+latitude+"  "+longitude);

		
		// Push a description into a content instance
		HTTPPost request = new HTTPPost();
 		request.setDestinator("http://localhost:8080/~/mn-cse/mn-name/"+rep+"/"+rep+"_DATA/");
		request.addHeader("X-M2M-Origin", "admin:admin");
		request.addHeader("Content-Type", "application/xml;ty=4");
		ContentInstance descriptor = new ContentInstance();
 		String agentName=name.split("@")[0];
 		descriptor.setContent("Content");
 		descriptor.setName(agentName);
		descriptor.setContentInfo("application/obix:0");
		//Labels
		//descriptor.getLabels().add("<TEST>"+"TEST"+"</TEST>");
		descriptor.getLabels().add("<name>"+agentName+"</name>");
		descriptor.getLabels().add("<owner>"+owner+"</owner>");
		descriptor.getLabels().add("<latitude>"+latitude+"</latitude>");
		descriptor.getLabels().add("<longitude>"+longitude+"</longitude>");
		descriptor.getLabels().add("<url>"+url+"</url>");
		
		
		//Interests
		descriptor.getLabels().add("<nb_interest>"+interestsList.size()+"</nb_interest>");
		for (int i=0; i<interestsList.size();i++){
			if (interestsList.get(i).getLevel()!=0.0){
				descriptor.getLabels().add("<interest>"+interestsList.get(i).getName()+"/"+interestsList.get(i).getLevel()+"</interest>");
			}
		}
 		request.setBody(Serializer.toXML(descriptor));
		request.send();


		
	}
	@Override
	public void createRepository(String name) {
		
		/*Repo creation*/
		HTTPPost request = new HTTPPost();
		request.setDestinator("http://localhost:8080/~/mn-cse/mn-name/");
		request.addHeader("X-M2M-Origin", "admin:admin");
		request.addHeader("Content-Type", "application/xml;ty=2");
		AE ae = ResourceCreator.createAE(name, "Repository");
		ae.getLabels().add("Type/Repository");
		ae.getLabels().add("Location/P1");
		ae.getLabels().add("Owner/O1");
		request.setBody(Serializer.toXML(ae));
		request.send();	
		/*DATA creation*/
		request = new HTTPPost();
		request.setDestinator("http://localhost:8080/~/mn-cse/mn-name/"+name+"/");
		request.addHeader("X-M2M-Origin", "admin:admin");
		request.addHeader("Content-Type", "application/xml;ty=3");
		Container cnt = ResourceCreator.createContainer(name+"_DATA");
		request.setBody(Serializer.toXML(cnt));
		request.send();
	}
	
}
