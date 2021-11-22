package Daos;

import java.util.List;
import java.util.Map;

public interface buketDao {
 public List<Map<String, Object>>findByEmail(String email);
 public List<Map<String, Object>>findAll();
 public Map<String, Object> findByBid(int  bid);
 public int updateCount(Map<String, Object>map);
 public int deleteById(int cnum);
 public Map<String, Object> findUser(String email);
 public Map<String, Object>findByPizzaName(Map<String, Object>map);
 public int deleteByEmail(String email);
 public Map<String, Object> findPriceByProduct(Map<String, Object>map);
}
