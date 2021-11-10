package Daos;

import java.util.Map;

public interface payDao {
	public Map<String, Object>findByPizzaName(Map<String, Object>map);
	public int insert(Map<String, Object>map);
}
