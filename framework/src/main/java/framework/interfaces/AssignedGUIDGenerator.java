package framework.interfaces;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.UUIDHexGenerator;
import org.hibernate.type.Type;

public class AssignedGUIDGenerator extends UUIDHexGenerator{

	private String entityName;  
	
	public void configure(Type type, Properties params, Dialect dialect) throws MappingException {  
		  entityName = params.getProperty(ENTITY_NAME);  
		  if (entityName==null) {  
		      throw new MappingException("no entity name");  
		  }  
		  super.configure(type, params, dialect);    
	}  
	
	public Serializable generate(SessionImplementor session, Object obj)   throws HibernateException {  
		Serializable id = session.getEntityPersister(entityName,obj).getIdentifier( obj, session.getEntityMode() );  
		if (id==null || id.toString().equals("")) {
			id = super.generate(session, obj);  
		}  
		return id;  
	 } 
}
