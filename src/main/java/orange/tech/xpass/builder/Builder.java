package orange.tech.xpass.builder;

public interface Builder {

	
	Builder lower(boolean value);
	Builder upper(boolean value);
	Builder number(boolean value);
	Builder symbol(boolean value);
	Builder length(Integer value);
	String build();
	
}
