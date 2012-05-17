package ar.com.oxen.nibiru.application.generic.presenter;

import java.util.HashMap;
import java.util.Map;

import ar.com.oxen.nibiru.application.api.main.MainView;
import ar.com.oxen.nibiru.extensionpoint.api.ExtensionPointManager;
import ar.com.oxen.nibiru.extensionpoint.api.ExtensionTracker;
import ar.com.oxen.nibiru.ui.api.extension.MenuItemExtension;
import ar.com.oxen.nibiru.ui.api.extension.SubMenuExtension;
import ar.com.oxen.nibiru.ui.api.mvp.ClickHandler;
import ar.com.oxen.nibiru.ui.api.view.HasMenuItems;
import ar.com.oxen.nibiru.ui.api.view.MenuItem;
import ar.com.oxen.nibiru.ui.utils.mvp.AbstractPresenter;
import ar.com.oxen.nibiru.security.api.AuthenticationService;
import ar.com.oxen.nibiru.security.api.AuthorizationService;

public class GenericMainPresenter extends AbstractPresenter<MainView> {
	private ExtensionPointManager extensionPointManager;
	private AuthenticationService authenticationService;
	private AuthorizationService authorizationService;

	public GenericMainPresenter(ExtensionPointManager extensionPointManager,
			AuthenticationService authenticationService, AuthorizationService authorizationService) {
		super(null);
		this.extensionPointManager = extensionPointManager;
		this.authenticationService = authenticationService;
		this.authorizationService = authorizationService;
	}

	@Override
	public void go() {
		this.getView().setUserName(
				this.authenticationService.getLoggedUserName());

		this.poulateMenuItem(this.getView().getMainMenu(),
				"ar.com.oxen.nibiru.menu");
	}

	private boolean isAllowedRole(String[] roles) {
		if (roles == null || roles.length == 0) {
			return true;
		}
		for (String role : roles){
			if (authorizationService.isCallerInRole(role)) {
				return true;
			}
		}
		return false;
	}
	private void poulateMenuItem(final HasMenuItems barMenuItem,
			String extensionName) {

		this.extensionPointManager.registerTracker(
				new ExtensionTracker<SubMenuExtension>() {
					private Map<SubMenuExtension, MenuItem> items = new HashMap<SubMenuExtension, MenuItem>();

					@Override
					public void onRegister(SubMenuExtension subMenuExtension) {
						String[] roles = subMenuExtension.getAllowedRoles();
						if (!isAllowedRole(roles)) {
							return;
						}
						MenuItem menuItem = getView().addMenuItem(
								subMenuExtension.getName(), barMenuItem,
								subMenuExtension.getPosition());
						this.items.put(subMenuExtension, menuItem);
						poulateMenuItem(menuItem, subMenuExtension
								.getExtensionPoint());
					}

					@Override
					public void onUnregister(SubMenuExtension subMenuExtension) {
						barMenuItem.removeMenuItem(this.items
								.get(subMenuExtension));
						this.items.remove(subMenuExtension);
					}
				}, extensionName, SubMenuExtension.class);

		this.extensionPointManager.registerTracker(
				new ExtensionTracker<MenuItemExtension>() {
					private Map<MenuItemExtension, MenuItem> items = new HashMap<MenuItemExtension, MenuItem>();

					@Override
					public void onRegister(
							final MenuItemExtension menuItemExtension) {
						String[] roles = menuItemExtension.getAllowedRoles();
						if (!isAllowedRole(roles)) {
							return;
						}

						MenuItem menuItem = getView().addMenuItem(
								menuItemExtension.getName(), barMenuItem,
								menuItemExtension.getPosition());
						menuItem.setClickHandler(new ClickHandler() {
							@Override
							public void onClick() {
								menuItemExtension.onClick();
							}
						});
						this.items.put(menuItemExtension, menuItem);
					}

					@Override
					public void onUnregister(MenuItemExtension menuItemExtension) {
						barMenuItem.removeMenuItem(this.items
								.get(menuItemExtension));
						this.items.remove(menuItemExtension);
					}
				}, extensionName, MenuItemExtension.class);
	}
}
