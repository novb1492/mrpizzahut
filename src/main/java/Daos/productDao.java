package Daos;

import java.util.List;
import java.util.Map;

public interface productDao {
	public int insertProduct(Map<String, Object>map);
	public List<Map<String, Object>>findAll(Map<String, Object>map);
	public List<Map<String, Object>>findAllByKey(Map<String, Object>map);
}
