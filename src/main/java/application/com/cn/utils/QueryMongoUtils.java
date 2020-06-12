package application.com.cn.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.regex.Pattern;

public class QueryMongoUtils {
    public static final Integer MAX_PAGE_SIZE = 5000;
    public static final Integer PAGE_NUMBER = 1;

    public Criteria getRequestRestriction(Map<String, Object> query) {
        Criteria allCriteria = new Criteria();
        List<Criteria> criterias = new ArrayList<Criteria>();
        if (null != query) {
            for (String key : query.keySet()) {
                if ("$or".equals(key)) {
                    Map<String, Object> orValues = (Map<String, Object>) query
                            .get(key);
                    criterias.add(_parseRequestRestrictionOr(orValues));
                } else {
                    Object value = query.get(key);
                    criterias.addAll(_parseCriteria(key, value));
                }
            }
        }
        if (!criterias.isEmpty()) {
            allCriteria.andOperator((Criteria[]) criterias
                    .toArray(new Criteria[criterias.size()]));
        }

        return allCriteria;
    }

    private Criteria _parseRequestRestrictionOr(Map<String, Object> query) {
        Criteria allOrCriteria = new Criteria();
        List<Criteria> criterias = new ArrayList<>();
        if (null != query) {
            for (String key : query.keySet()) {
                Object value = query.get(key);
                if (StringUtils.startsWith(key, "$and"))
                    criterias
                            .add(getRequestRestriction((Map<String, Object>) value));
                else {
                    criterias.addAll(_parseCriteria(key, value));
                }
            }
        }
        if (!criterias.isEmpty()) {
            allOrCriteria.orOperator((Criteria[]) criterias
                    .toArray(new Criteria[criterias.size()]));
        }

        return allOrCriteria;
    }

    private List<Criteria> _parseCriteria(String key, Object value) {
        if ("id".equals(key)) {
            key = "_id";
        }
        List<Criteria> criterias = new ArrayList<>();
        Map<String, Object> compareValue;
        if ((value instanceof Map)) {
            compareValue = (Map<String, Object>) value;
            for (String compare : compareValue.keySet()) {
                Object _compareValue = compareValue.get(compare);
                if ("$ge".equals(compare))
                    criterias.add(Criteria.where(key).gte(_compareValue));
                else if ("$le".equals(compare))
                    criterias.add(Criteria.where(key).lte(_compareValue));
                else if ("$gt".equals(compare))
                    criterias.add(Criteria.where(key).gt(_compareValue));
                else if ("$lt".equals(compare))
                    criterias.add(Criteria.where(key).lt(_compareValue));
                else if ("$in".equals(compare))
                    criterias.add(Criteria.where(key).in(
                            (Collection<Object>) _compareValue));
                else if ("$like".equals(compare))
                    criterias.add(Criteria.where(key).regex(
                            Pattern.compile(
                                    Pattern.quote((String) _compareValue), 2)));
                else if ("$left_like".equals(compare))
                    criterias
                            .add(Criteria
                                    .where(key)
                                    .regex(Pattern.compile(
                                            Pattern.quote((String) _compareValue) + "$", 2)));
                else if ("$right_like".equals(compare))
                    criterias
                            .add(Criteria.where(key).regex(
                                    Pattern.compile("^" +
                                                    Pattern.quote(
                                                            (String) _compareValue),
                                            2)));
                else if ("$not_like".equals(compare))
                    criterias.add(Criteria
                            .where(key)
                            .not()
                            .regex(Pattern.compile(
                                    Pattern.quote((String) _compareValue), 2)));
                else if ("$not_left_like".equals(compare))
                    criterias
                            .add(Criteria
                                    .where(key)
                                    .not()
                                    .regex(Pattern.compile(
                                            Pattern.quote((String) _compareValue
                                            ) + "$", 2)));
                else if ("$not_right_like".equals(compare))
                    criterias
                            .add(Criteria
                                    .where(key)
                                    .not()
                                    .regex(Pattern.compile("^" +
                                                    Pattern.quote(
                                                            (String) _compareValue),
                                            2)));
                else if ("$ne".equals(compare))
                    criterias.add(Criteria.where(key).ne(_compareValue));
                else if ("$null".equals(compare))
                    criterias.add(Criteria.where(key).is(null));
                else if ("$not_null".equals(compare))
                    criterias.add(Criteria.where(key).not().is(null));
                else if ("$not_in".equals(compare))
                    criterias.add(Criteria.where(key).not()
                            .in((Collection<Object>) _compareValue));
                else if ("$where".equals(compare))
                    criterias.add(Criteria.where("$where").is(_compareValue));
            }
        } else {
            criterias.add(Criteria.where(key).is(value));
        }

        return criterias;
    }

    public Sort getSortRequest(Map<String, Object> sort) {
        List<Sort.Order> orders = new ArrayList<>();
        if (null != sort) {
            Iterator<String> iterator = sort.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String direction = sort.get(key).toString();
                if ("-1".equals(direction) || "desc".equalsIgnoreCase(direction)) {
                    orders.add(Sort.Order.desc(key));
                } else {
                    orders.add(Sort.Order.asc(key));
                }
            }
        }
        if (CollectionUtils.isEmpty(orders)) {
            orders.add(Sort.Order.asc("id"));
        }
        return Sort.by(orders);
    }

    public Pageable getPageRequest(Map<String, Object> pagination) {
        Integer pageSize = MAX_PAGE_SIZE;
        Integer pageNumber = PAGE_NUMBER;
        if (null != pagination && null != pagination.get("pageNumber") && StringUtils.isNumeric(pagination.get("pageNumber").toString())) {
            pageNumber = Integer.parseInt(pagination.get("pageNumber").toString()) - 1;
        }
        if (null != pagination && null != pagination.get("pageSize") && StringUtils.isNumeric(pagination.get("pageSize").toString())) {
            pageSize = Integer.parseInt(pagination.get("pageSize").toString());
        }
        return PageRequest.of(pageNumber, pageSize);
    }
}
