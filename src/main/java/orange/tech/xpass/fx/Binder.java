package orange.tech.xpass.fx;

import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;

public class Binder {
	private Object o1;
    private String p1;
    public Object getO1() { return o1; }
    public void setO1(Object o1) { this.o1 = o1; bind(); }
    public String getP1() { return p1; }
    public void setP1(String p1) { this.p1 = p1; bind(); }

    private Object o2;
    private String p2;
    public Object getO2() { return o2; }
    public void setO2(Object o2) { this.o2 = o2; bind(); }
    public String getP2() { return p2; }
    public void setP2(String p2) { this.p2 = p2; bind(); }

    private <T> void bind() {
        if (o1 == null || p1 == null || o2 == null || p2 == null) return;
        try {
            @SuppressWarnings("unchecked")
            Property<T> property1 = (Property<T>)o1.getClass().getMethod(p1 + "Property").invoke(o1);
            @SuppressWarnings("unchecked")
            Property<T> property2 = (Property<T>)o2.getClass().getMethod(p2 + "Property").invoke(o2);
            Bindings.bindBidirectional(property1, property2);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        o1 = o2 = null;
        p1 = p2 = null;
    }
}
