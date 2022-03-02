package vas.mq.util;

import java.util.ArrayList;
import java.util.HashMap;

public class ListedHashMap extends HashMap {
	private static final long serialVersionUID = 1L;
	private ArrayList order = new ArrayList();

	public Object put(Object key, Object value) {
		if (!order.contains(key))
			order.add(key);

		return super.put(key, value);
	}

	public Object remove(Object key) {
		order.remove(order.indexOf(key));
		return super.remove(key);
	}

	public Object getValueAt(int offset) {
		return super.get(order.get(offset));
	}

	public void setValueAt(int offset, Object value) {
		super.put(order.get(offset), value);
	}

	public Object getKeyAt(int offset) {
		return order.get(offset);
	}

	public int getIndexOfKey(Object key) {
		return order.indexOf(key);
	}
}
//لا اله الا الله 