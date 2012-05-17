package ar.com.oxen.nibiru.crud.utils;

import java.util.ArrayList;
import java.util.List;

import ar.com.oxen.commons.eventbus.api.EventBus;
import ar.com.oxen.nibiru.crud.manager.api.CrudAction;
import ar.com.oxen.nibiru.crud.manager.api.CrudActionExtension;
import ar.com.oxen.nibiru.crud.manager.api.CrudEntity;
import ar.com.oxen.nibiru.crud.manager.api.ManageChildCrudEntitiesEvent;

public class ManageChildrenCrudActionExtension<T> implements
		CrudActionExtension<T> {
	private List<CrudAction> actions;

	private String actionName;
	private String parentField;
	private String topic;
	private EventBus eventBus;
	private String[] allowedRoles;

	public ManageChildrenCrudActionExtension(String actionName,
			String parentField, String topic, EventBus eventBus) {
		this(actionName, parentField, topic, eventBus, null);
	}
	public ManageChildrenCrudActionExtension(String actionName,
			String parentField, String topic, EventBus eventBus, String[] allowedRoles) {
		super();
		this.actionName = actionName;
		this.parentField = parentField;
		this.eventBus = eventBus;
		this.topic = topic;
		this.allowedRoles = allowedRoles;

		this.actions = new ArrayList<CrudAction>(1);
		this.actions.add(new SimpleCrudAction(this.actionName, true, false,
				true, false, null));
	}

	@Override
	public List<CrudAction> getActions() {
		return this.actions;
	}

	@Override
	public CrudEntity<T> performAction(CrudAction action, CrudEntity<T> entity) {
		this.eventBus.fireEvent(new ManageChildCrudEntitiesEvent(
				this.parentField, entity.getEntity()), topic);
		return null;
	}

	@Override
	public String[] getAllowedRoles() {
		return allowedRoles;
	}
}
