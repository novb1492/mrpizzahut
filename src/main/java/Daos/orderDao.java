package Daos;

import java.util.List;
import java.util.Map;

public interface orderDao {
	public List<Map<String, Object>>findAll(Map<String, Object>map);
	public List<Map<String, Object>>findAllByKey(Map<String, Object>map);
	public List<Map<String, Object>>findByDate(Map<String, Object>map);
	public List<String>getAllStitle();
	public List<Integer>finByDateCancel(Map<String, Object>map);
	public Map<String, Object>findByOnum(int onum);
}
