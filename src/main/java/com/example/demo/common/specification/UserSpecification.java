package com.example.demo.common.specification;

import com.example.demo.src.user.entity.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    public static Specification<User> searchUser(Map<String, Object> searchKey){
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for(String key : searchKey.keySet()){
                if (key == "createdAt") {
                    LocalDateTime createdAt = LocalDateTime.parse(searchKey.get(key).toString());
                    predicates.add(criteriaBuilder.between(root.get(key), createdAt, createdAt.plusDays(1)));
                    continue;
                }
                predicates.add(criteriaBuilder.equal(root.get(key), searchKey.get(key)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

}
