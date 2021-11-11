package Daos;

import java.util.Map;

public interface couponDao {
 public Map<String, Object>findByCouponName(String cname);
 public int updateDone(Map<String, Object>map);
}
