package nuc.jyg.hikariy.common;

import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author Ji YongGuang.
 * @date 12:39 2019-05-25.
 * @description
 */
@Component
public class HostHolder {

    private static final Queue<Object> enterpriseValues = new ArrayDeque<>();
    private static final Queue<Object> allEnterpriseValues = new ArrayDeque<>();

    public Object getValues() {
        return enterpriseValues.poll();
    }

    public void setValues(Object value) {
        enterpriseValues.add(value);
    }

    public Object getAllValues() {
        return allEnterpriseValues.poll();
    }

    public void setAllValues(Object value) {
        allEnterpriseValues.add(value);
    }
}
