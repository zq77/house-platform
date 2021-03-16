package com.platform.house.security;

import com.platform.house.domain.Permission;
import com.platform.house.domain.Role;
import com.platform.house.domain.Staff;
import com.platform.house.domain.User;
import com.platform.house.repo.RolePermissionRepo;
import com.platform.house.repo.StaffRepo;
import com.platform.house.repo.UserRepo;
import com.platform.house.repo.UserRoleRepo;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class CustomRealm extends AuthorizingRealm {

    public static final String SALT = "MD5";
    public static final int HASH_ITERATION_COUNT = 1024;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserRoleRepo userRoleRepo;
    @Autowired
    private StaffRepo staffRepo;
    @Autowired
    private RolePermissionRepo rolePermissionRepo;

    /**
     * 告诉shiro如何根据获取到的用户信息中的密码和盐值来校验密码
     */
    {
        //设置用于匹配密码的CredentialsMatcher
        HashedCredentialsMatcher hashMatcher = new HashedCredentialsMatcher();
        hashMatcher.setHashAlgorithmName(SALT);
        hashMatcher.setStoredCredentialsHexEncoded(true);
        //加密的次数
        hashMatcher.setHashIterations(HASH_ITERATION_COUNT);
        this.setCredentialsMatcher(hashMatcher);
    }


    /**
     *  定义如何获取用户的角色和权限的逻辑，给shiro做权限判断
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        ShiroUser shiroUser = (ShiroUser) getAvailablePrincipal(principals);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(shiroUser.getRoles());
        info.setStringPermissions(shiroUser.getPerms());
        return info;
    }

    /**
     * 定义如何获取用户信息的业务逻辑，给shiro做登录
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        if (username == null) {
            throw new AccountException("Null username are not allowed by this realm.");
        }


        User user = userRepo.getUserByname(username);
        if (user == null) {
            throw new UnknownAccountException("No account found for [" + username + "]");
        }
        ShiroUser shiroUser = new ShiroUser(user);
        //查询用户的角色和权限存到SimpleAuthenticationInfo中，这样在其它地方
        //SecurityUtils.getSubject().getPrincipal()就能拿出用户的所有信息，包括角色和权限
        List<Role> roles = userRoleRepo.getRolesByUserId(user.getId());
        if (roles.isEmpty()) {
            throw new AuthenticationException("No role found for [" + username + "]");
        }
        shiroUser.setRoles(roles.stream().map(Role::getValue).collect(Collectors.toSet()));
        List<Staff> staffs = staffRepo.findWithRoleByUserId(user.getId());
        shiroUser.getRoles().addAll(staffs.stream().map(staff -> staff.getRole().getValue()).collect(Collectors.toSet()));
        shiroUser.setPerms(rolePermissionRepo.getPermissionsByRoleIds(
                roles.stream().map(Role::getId).collect(Collectors.toSet()))
                .stream().map(Permission::getValue).collect(Collectors.toSet()));

        return new SimpleAuthenticationInfo(shiroUser, shiroUser.getPassword(),
                ByteSource.Util.bytes(shiroUser.getCredentialsSalt()), getName());

    }
}
