package orange.tech.xpass.navigation;

import java.util.function.Supplier;

public interface CallBackController<T> {
	 void content(Supplier<T> sup);
}
