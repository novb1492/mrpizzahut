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
	public Map<String, Object>findByMchttrdnoAndOnumJoin(Map<String, Object>map);
	public int updateOrderCancleFlag(Map<String, Object>map);
	public int findByProductName(Map<String, Object>map);
	public int updateProductCount(Map<String, Object>map);
	public int updateCouponToDone(Map<String, Object>map);
}
