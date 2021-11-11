package Daos;

import java.util.Map;

public interface payDao {
	public Map<String, Object>findByPizzaName(Map<String, Object>map);
	public int insert(Map<String, Object>map);
	public int insertCard(Map<String, Object>map);
	public int insertVbank(Map<String, Object>map);
	public int insertKpay(Map<String, Object>map);
}
