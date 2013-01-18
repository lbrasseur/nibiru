package ar.com.oxen.nibiru.crud.manager.jpa;

import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import ar.com.oxen.commons.bean.api.WrapperFactory;
import ar.com.oxen.nibiru.crud.manager.api.CrudActionExtension;
import ar.com.oxen.nibiru.crud.manager.api.CrudFactory;
import ar.com.oxen.nibiru.crud.manager.api.CrudManager;
import ar.com.oxen.nibiru.security.api.AuthorizationService;

public class JpaCrudFactory implements CrudFactory {
	private EntityManager entityManager;
	private WrapperFactory wrapperFactory;
	private AuthorizationService authorizationService;
	private UserTransaction userTransaction;

	@Override
	public <T> CrudManager<T> createCrudManager(Class<T> persistentClass) {
		return new JpaCrudManager<T>(this.entityManager, persistentClass,
				this.wrapperFactory, this.authorizationService);
	}

	@Override
	public <T> CrudActionExtension<T> createDefaultCrudActionExtension(
			Class<T> persistentClass) {
		return new JpaCrudActionExtension<T>(entityManager, persistentClass,
				wrapperFactory, this.userTransaction);
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void setWrapperFactory(WrapperFactory wrapperFactory) {
		this.wrapperFactory = wrapperFactory;
	}

	public void setAuthorizationService(
			AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}

	public void setUserTransaction(UserTransaction userTransaction) {
		this.userTransaction = userTransaction;
	}
}
