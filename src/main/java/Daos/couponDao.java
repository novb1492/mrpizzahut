package Daos;

import java.util.List;
import java.util.Map;

public interface couponDao {
 public Map<String, Object>findByCouponName(String cname);
 public int updateDone(Map<String, Object>map);
 public int insertCoupon(Map<String, Object>map);
 public List<Map<String, Object>>findAll(Map<String, Object>map);
 public List<Map<String, Object>>findAllByKey(Map<String, Object>map);
 public Map<String, Object>findByConum(int conum);
 public int updateCoupon(Map<String, Object>map);
}
