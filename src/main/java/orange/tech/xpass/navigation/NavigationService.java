package orange.tech.xpass.navigation;

import java.util.function.Supplier;

import orange.tech.xpass.controller.BaseController;


public interface NavigationService {
	public <T> Navigator getNavigator(Class<? extends BaseController> controller,Supplier<T> obj);
	public <T> Navigator getNavigator(Class<? extends BaseController> controller);
}
