package report.dao.imps;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.property.ChainedPropertyAccessor;
import org.hibernate.property.PropertyAccessor;
import org.hibernate.property.PropertyAccessorFactory;
import org.hibernate.property.Setter;
import org.hibernate.transform.ResultTransformer;

@SuppressWarnings({"serial","rawtypes"})
public class ResultTransformerByWC implements ResultTransformer {
		// IMPL NOTE : due to the delayed population of setters (setters cached
		// 		for performance), we really cannot pro0perly define equality for
		// 		this transformer

		private final Class resultClass;
		private final PropertyAccessor propertyAccessor;
		private Setter[] setters;

		public ResultTransformerByWC(Class resultClass) {
			if ( resultClass == null ) {
				throw new IllegalArgumentException( "resultClass cannot be null" );
			}
			this.resultClass = resultClass;
			propertyAccessor = new ChainedPropertyAccessor(
					new PropertyAccessor[] {
							PropertyAccessorFactory.getPropertyAccessor( resultClass, null ),
							PropertyAccessorFactory.getPropertyAccessor( "field" )
					}
			);
		}

		public Object transformTuple(Object[] tuple, String[] aliases) {
			Object result;

			try {
				if ( setters == null ) {
					setters = new Setter[aliases.length];
					for ( int i = 0; i < aliases.length; i++ ) {
						String alias = aliases[i];
						if ( alias != null ) {
							setters[i] = propertyAccessor.getSetter( resultClass, alias );
						}
					}
				}
				result = resultClass.newInstance();

				for ( int i = 0; i < aliases.length; i++ ) {
					if ( setters[i] != null && tuple[i] != null) {
						setters[i].set( result, tuple[i], null );
					}
				}
			}
			catch ( InstantiationException e ) {
				throw new HibernateException( "Could not instantiate resultclass: " + resultClass.getName() );
			}
			catch ( IllegalAccessException e ) {
				throw new HibernateException( "Could not instantiate resultclass: " + resultClass.getName() );
			}

			return result;
		}

		public List transformList(List collection) {
			return collection;
		}

		public int hashCode() {
			int result;
			result = resultClass.hashCode();
			result = 31 * result + propertyAccessor.hashCode();
			return result;
		}
	}