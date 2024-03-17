package orange.tech.xpass.navigation;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import orange.tech.xpass.controller.BaseController;
import orange.tech.xpass.controller.ConfigController;
import orange.tech.xpass.controller.HomeController;
import orange.tech.xpass.controller.KeyController;
import orange.tech.xpass.controller.LoginController;
import orange.tech.xpass.controller.MainController;
import orange.tech.xpass.controller.ProceedModalController;
import orange.tech.xpass.controller.DeleteModalController;
import orange.tech.xpass.controller.RegisterController;
import orange.tech.xpass.modal.DeleteModal;
import orange.tech.xpass.navigation.FxLoader.Url;

@Service
public class NavigationServiceImpl implements NavigationService {
	
	private FxLoader fxLoader;
	
	@Autowired
	public NavigationServiceImpl(FxLoader fxLoader) {
		this.fxLoader = fxLoader;
	}
	

	@Override
	public <T> Navigator getNavigator(Class<? extends BaseController> controller,Supplier<T> obj) {
		
		if(controller.equals(LoginController.class)) {		
			return fxLoader.load(Url.LOGIN,obj);			
		}else if(controller.equals(HomeController.class)){
			return fxLoader.load(Url.HOME, obj);
		}else if(controller.equals(KeyController.class)) {
			return fxLoader.load(Url.KEY, obj);
		}else if(controller.equals(ConfigController.class)) {
			return fxLoader.load(Url.CONFIG, obj);
		}else if(controller.equals(RegisterController.class)) {
			return fxLoader.load(Url.REGISTER, obj);
		}else if(controller.equals(MainController.class)) {
			return fxLoader.load(Url.MAIN, obj);
		}else if(controller.equals(DeleteModalController.class)) {
			return fxLoader.load(Url.RM, obj);
		}else if(controller.equals(ProceedModalController.class)) {
			return fxLoader.load(Url.PM, obj);
		}
		
		throw new RuntimeException(String.format("No such controller %s found!",controller.getName()));
	}

	@Override
	public <T> Navigator getNavigator(Class<? extends BaseController> controller) {
		return getNavigator(controller, null);
	}

}
