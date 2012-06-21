package ar.com.oxen.nibiru.module.utils;

import java.util.Collection;
import java.util.LinkedList;

import ar.com.oxen.commons.eventbus.api.EventBus;
import ar.com.oxen.nibiru.extensionpoint.api.ExtensionPointManager;
import ar.com.oxen.nibiru.ui.api.mvp.Presenter;
import ar.com.oxen.nibiru.ui.api.mvp.View;

/**
 * Base class for module configurators.
 * 
 * @param <VF>
 *            The view factory class
 * @param <PF>
 *            The presenter factory class
 */
public abstract class AbstractModuleConfigurator<VF, PF> {
	private ExtensionPointManager extensionPointManager;
	private Collection<Object> registeredExtensions = new LinkedList<Object>();
	private EventBus eventBus;
	private VF viewFactory;
	private PF presenterFactory;

	/**
	 * Starts the module. This method must be externally called (for example,
	 * with init-method attribute on Spring context XML).
	 */
	public void startup() {
		this.eventBus.subscribeAnnotatedObject(this);
		this.configure();
	}

	/**
	 * Same as startup, but for shutdown.
	 */
	public void shutdown() {
		/* Remove all the extensions */
		for (Object extension : this.registeredExtensions) {
			this.extensionPointManager.unregisterExtension(extension);
		}
		this.registeredExtensions.clear();

		this.unconfigure();
	}

	/**
	 * Abstract method to be override in order to customize module
	 * configuration.
	 */
	protected void configure() {
	}

	/**
	 * Abstract method to be override in order to customize module
	 * un-configuration.
	 */
	protected void unconfigure() {
	}

	/**
	 * Activates a view/presenter. Typically this method will be called from
	 * subclasses upon the receiving of an event from the bus in order to
	 * navigate to a given window.
	 * 
	 * @param <V>
	 *            The view type
	 * @param view
	 *            The view
	 * @param presenter
	 *            The presenter
	 */
	protected <V extends View> void activate(V view, Presenter<V> presenter) {
		presenter.setView(view);
		presenter.go();
		view.show();
	}

	/**
	 * Registers an extension under a name and an interface. The extension will
	 * be automatically un-published when the module will be unloaded.
	 * 
	 * @param <K>
	 *            The extension point interface
	 * @param extension
	 *            The extension
	 * @param extensionPointName
	 *            The extension point name
	 * @param extensionPointInterface
	 *            The extension point interface
	 */
	protected <K> void registerExtension(K extension,
			String extensionPointName, Class<K> extensionPointInterface) {
		this.extensionPointManager.registerExtension(extension,
				extensionPointName, extensionPointInterface);
		this.registeredExtensions.add(extension);
	}

	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	protected EventBus getEventBus() {
		return eventBus;
	}

	protected VF getViewFactory() {
		return viewFactory;
	}

	public void setViewFactory(VF viewFactory) {
		this.viewFactory = viewFactory;
	}

	protected PF getPresenterFactory() {
		return presenterFactory;
	}

	public void setPresenterFactory(PF presenterFactory) {
		this.presenterFactory = presenterFactory;
	}

	protected ExtensionPointManager getExtensionPointManager() {
		return extensionPointManager;
	}

	public void setExtensionPointManager(
			ExtensionPointManager extensionPointManager) {
		this.extensionPointManager = extensionPointManager;
	}
}
