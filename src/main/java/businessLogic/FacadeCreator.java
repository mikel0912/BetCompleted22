package businessLogic;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import configuration.ConfigXML;
import dataAccess.DataAccess;

public class FacadeCreator {
	public BLFacade createFacade(Integer isLocal) throws MalformedURLException {
		ConfigXML c=ConfigXML.getInstance();
		switch(isLocal) {
			case 1:
				DataAccess da= new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
				return new BLFacadeImplementation(da);
			case 2:
				String serviceName= "http://"+c.getBusinessLogicNode() +":"+ c.getBusinessLogicPort()+"/ws/"+c.getBusinessLogicName()+"?wsdl";
				
				URL url = new URL(serviceName);

		        QName qname = new QName("http://businessLogic/", "BLFacadeImplementationService");
		 
		        Service service = Service.create(url, qname);
		        return service.getPort(BLFacade.class);
			default:
				break;
		}
		return null;
	}

}
