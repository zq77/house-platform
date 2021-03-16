package com.platform.house.constant;

public enum RoleType {

	ADMIN("管理员", SysConstants.ROLE_ADMIN),
	WECHATUSER("微信用户", SysConstants.ROLE_WECHAT_USER),
	STORE_ADMIN("门店管理员", SysConstants.ROLE_STORE_ADMIN),
	AGENT("经纪人", SysConstants.ROLE_AGENT),
	NORMAL_USER("普通用户", SysConstants.ROLE_NORMAL_USER);

	RoleType(String roleName, String roleValue) {
		this.roleName = roleName;
		this.roleValue = roleValue;
	}
	
	private String roleName;
	
	private String roleValue;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleValue() {
		return roleValue;
	}

	public void setRoleValue(String roleValue) {
		this.roleValue = roleValue;
	}


}
