package orange.tech.xpass.builder;

import java.util.Random;
import java.util.stream.Stream;


public class PasswordBuilder implements Builder {
	
	private static PasswordBuilder instance = new PasswordBuilder();
	
	private Random random = new Random();
	
	private StringBuilder sb = new StringBuilder();		

	private Integer length;

	private boolean allowSymbol;

	private boolean allowNumber;

	private boolean allowUpper;

	private boolean allowLower;
	
	private PasswordBuilder() {}

	public static PasswordBuilder getInstance() {return instance;}
	
	@Override
	public Builder length(Integer value) {
		this.length = value;
		return this;
	}
	
	
	@Override
	public Builder lower(boolean value) {
		 this.allowLower = value;		 	
		 return this;
	}

	@Override
	public Builder upper(boolean value) {
		this.allowUpper = value;		
		return this;
	}

	@Override
	public Builder number(boolean value) {
		this.allowNumber = value;		
		return this;
	}

	@Override
	public Builder symbol(boolean value) {
		this.allowSymbol = value;		
		return this;	
	}

	@Override
	public String build() {
		
		sb.delete(0, sb.length());
		
		boolean canProceed = Stream.of(allowUpper,allowLower,allowNumber,allowSymbol)
								   .anyMatch(value -> value == true);
		
		if(canProceed) {
			while(true) {			
				if(allowLower) {
					sb.append(getLower());
					if(stop()) {
						break;
					}
				}			
				if(allowUpper) {
					sb.append(getUpper());
					if(stop()) {
						break;
					}
				}			
				if(allowNumber) {
					sb.append(getNumbers());
					if(stop()) {
						break;
					}
				}			
				if(allowSymbol) {
					sb.append(getSymbol());
					if(stop()) {
						break;
					}
				}			
			}		
		}
		return sb.toString();
	}


	private boolean stop() {
		return sb.length() == length;
	}


	private String getLower() {
		return allowLower ? Character.toString(Double.valueOf(Math.floor(Math.random()*26) + 97).intValue()) : "";
	}
	
	private String getUpper() {
		return allowUpper ? Character.toString(Double.valueOf(Math.floor(Math.random()*26) + 65).intValue()) : "";
	}
	
	private String getNumbers() {
		return allowNumber ? Character.toString(Double.valueOf(Math.floor(Math.random()*10) + 48).intValue()) : "";
	}
	
	
	private String getSymbol() {
		String symbols = "!@#$%^&*=<>,.";
		return this.allowSymbol ? String.valueOf(symbols.toCharArray()[random.nextInt(0,symbols.length())]) : "";
	}
	
	
	

	

}
