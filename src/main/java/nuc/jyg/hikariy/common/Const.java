package nuc.jyg.hikariy.common;

/**
 * @author Ji YongGuang.
 * @date 20:46 2019-04-27.
 * @description
 */
public final class Const {

    private Const() {

    }

    public static final String CURRENT_USER = "currentUser";

    public interface Role {
        int ROLE_CUSTOMER = 0; // 普通用户
        int ROLE_ADMIN = 1; // 管理员
    }
}
