package com.rabbiter.hrm.util;

import com.rabbiter.hrm.entity.Admin;
import com.rabbiter.hrm.entity.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 安全工具类
 * 用于获取当前登录用户信息
 */
public class SecurityUtils {

    /**
     * 获取当前请求
     */
    public static HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            return attributes.getRequest();
        }
        return null;
    }

    /**
     * 获取当前会话
     */
    public static HttpSession getCurrentSession() {
        HttpServletRequest request = getCurrentRequest();
        if (request != null) {
            return request.getSession();
        }
        return null;
    }

    // ==================== 管理员相关 ====================

    /**
     * 获取当前登录管理员ID
     */
    public static Long getCurrentAdminId() {
        HttpSession session = getCurrentSession();
        if (session != null) {
            return (Long) session.getAttribute("adminId");
        }
        return null;
    }

    /**
     * 获取当前登录管理员信息
     */
    public static Admin getCurrentAdmin() {
        HttpSession session = getCurrentSession();
        if (session != null) {
            return (Admin) session.getAttribute("admin");
        }
        return null;
    }

    /**
     * 设置当前登录管理员
     */
    public static void setCurrentAdmin(Admin admin) {
        HttpSession session = getCurrentSession();
        if (session != null) {
            session.setAttribute("adminId", admin.getId());
            session.setAttribute("admin", admin);
            session.setAttribute("userType", "admin");
        }
    }

    /**
     * 判断当前是否管理员登录
     */
    public static boolean isAdminLoggedIn() {
        HttpSession session = getCurrentSession();
        return session != null && session.getAttribute("admin") != null;
    }

    // ==================== 普通用户相关 ====================

    /**
     * 获取当前登录用户ID
     */
    public static Long getCurrentUserId() {
        HttpSession session = getCurrentSession();
        if (session != null) {
            // 先检查是否是管理员
            Long adminId = (Long) session.getAttribute("adminId");
            if (adminId != null) {
                return adminId;
            }
            // 再检查是否是普通用户
            return (Long) session.getAttribute("userId");
        }
        return null;
    }

    /**
     * 获取当前登录用户信息
     */
    public static User getCurrentUser() {
        HttpSession session = getCurrentSession();
        if (session != null) {
            return (User) session.getAttribute("user");
        }
        return null;
    }

    /**
     * 设置当前登录用户
     */
    public static void setCurrentUser(User user) {
        HttpSession session = getCurrentSession();
        if (session != null) {
            session.setAttribute("userId", user.getId());
            session.setAttribute("user", user);
            session.setAttribute("userType", "user");
        }
    }

    /**
     * 判断当前是否用户登录
     */
    public static boolean isUserLoggedIn() {
        HttpSession session = getCurrentSession();
        return session != null && session.getAttribute("user") != null;
    }

    /**
     * 判断是否已登录（管理员或用户）
     */
    public static boolean isLoggedIn() {
        return isAdminLoggedIn() || isUserLoggedIn();
    }

    // ==================== 退出登录 ====================

    /**
     * 退出登录
     */
    public static void logout() {
        HttpSession session = getCurrentSession();
        if (session != null) {
            session.removeAttribute("adminId");
            session.removeAttribute("admin");
            session.removeAttribute("userId");
            session.removeAttribute("user");
            session.removeAttribute("userType");
            session.invalidate();
        }
    }

    /**
     * 获取当前用户类型
     */
    public static String getUserType() {
        HttpSession session = getCurrentSession();
        if (session != null) {
            return (String) session.getAttribute("userType");
        }
        return null;
    }

    // ==================== 权限检查 ====================

    /**
     * 检查当前用户是否有管理员权限
     */
    public static boolean hasAdminRole() {
        return isAdminLoggedIn();
    }

    /**
     * 检查当前用户是否有超级管理员权限
     */
    public static boolean hasSuperAdminRole() {
        Admin admin = getCurrentAdmin();
        return admin != null && admin.getRole() == 1;
    }

    /**
     * 检查当前用户是否是指定用户
     */
    public static boolean isCurrentUser(Long userId) {
        Long currentUserId = getCurrentUserId();
        return currentUserId != null && currentUserId.equals(userId);
    }

    /**
     * 获取客户端IP
     */
    public static String getClientIp() {
        HttpServletRequest request = getCurrentRequest();
        if (request == null) {
            return "unknown";
        }

        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // 多个代理的情况，第一个IP为客户端真实IP
        if (ip != null && ip.length() > 15 && ip.indexOf(",") > 0) {
            ip = ip.substring(0, ip.indexOf(","));
        }

        return ip;
    }

    /**
     * 获取当前请求的User-Agent
     */
    public static String getUserAgent() {
        HttpServletRequest request = getCurrentRequest();
        if (request != null) {
            return request.getHeader("User-Agent");
        }
        return null;
    }
}