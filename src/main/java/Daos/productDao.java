package Daos;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface productDao {
	public int insertProduct(Map<String, Object>map);
	public List<Map<String, Object>>findAll(Map<String, Object>map);
	public List<Map<String, Object>>findAllByKey(Map<String, Object>map);
	public Map<String, Object>findByMnum(int mnum);
	public int updateProduct(Map<String, Object>map);
	public int deleteProduct(int mnum);
	public List<Map<String, Object>>findByCreatedInVbank(Map<String, Object>map);
	public int findBySizeAndEdgeAndNameInMenu(Map<String, Object>map);
	public int updateCount(Map<String, Object>map);
	public int updateVbankToCheckDone(Map<String, Object>map);
}
