package com.platform.house.form;

import com.platform.house.constant.Education;
import com.platform.house.constant.Gender;
import com.platform.house.constant.MaritalStatus;
import com.platform.house.constant.PoliticalStatus;
import com.platform.house.constant.RoleType;
import com.platform.house.domain.Staff;
import com.platform.house.domain.User;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

public class StaffForm {
    @NotBlank(message = "姓名不能为空")
    private String name;
    @NotNull(message = "性别不能为空")
    private Gender gender;
    @NotBlank(message = "电话号码不能为空")
    @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$", message = "请输入正确的手机号")
    private String phone;
    private String idCard;
    @NotNull(message = "角色不能为空")
    private RoleType role;
    private Long storeId;
    private String avatar;
    private String jobNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date entryDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String joinWay;
    private String referrer;
    private String birthplace;
    private String nationality;
    private PoliticalStatus political;
    private MaritalStatus maritalStatus;
    private Education education;
    private String graduatedYear;
    private String graduatedSchool;
    private String specialty;
    private String emergencyContact;
    private String emergencyPhone;

    public Staff toStaff() {
        Staff staff = new Staff();
        return merge(staff);
    }
    public Staff merge(Staff staff) {
        staff.setBirthday(this.getBirthday());
        staff.setJobNumber(this.getJobNumber());
        staff.setJoinWay(this.getJoinWay());
        staff.setEntryDate(this.getEntryDate());
        staff.setReferrer(this.getReferrer());
        staff.setBirthplace(this.getBirthplace());
        staff.setNationality(this.getNationality());
        staff.setPolitical(this.getPolitical());
        staff.setMaritalStatus(this.getMaritalStatus());
        staff.setEducation(this.getEducation());
        staff.setGraduatedYear(this.getGraduatedYear());
        staff.setGraduatedSchool(this.getGraduatedSchool());
        staff.setSpecialty(this.getSpecialty());
        staff.setEmergencyContact(this.getEmergencyContact());
        staff.setEmergencyPhone(this.getEmergencyPhone());
        return staff;
    }

    public User toUser() {
        User user = new User();
        mergeUser(user);
        // 电话号码后6位是密码
        if (this.phone.length() > 6) {
            user.setPassword(user.generagePassword(this.phone.substring(this.phone.length() - 6)));
        } else {
            user.setPassword(user.generagePassword("123456"));
        }
        return user;
    }

    public User mergeUser(User user) {
        user.setRealname(this.name);
        user.setNickname(this.name);
        user.setUsername(this.phone);
        user.setPhone(this.phone);
        user.setGender(this.gender);
        user.setIdCard(this.idCard);
        user.setAvatar(this.avatar);
        user.setUpdateAt(new Date());
        return user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (gender != null) {
            this.gender = Gender.valueOf(gender);
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(String role) {
        if (role != null) {
            this.role = RoleType.valueOf(role.toUpperCase());
        }
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getJoinWay() {
        return joinWay;
    }

    public void setJoinWay(String joinWay) {
        this.joinWay = joinWay;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public PoliticalStatus getPolitical() {
        return political;
    }

    public void setPolitical(String political) {
        if (political != null) {
            this.political = PoliticalStatus.valueOf(political);
        }
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        if (maritalStatus != null) {
            this.maritalStatus = MaritalStatus.valueOf(maritalStatus);
        }
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(String education) {
        if (education != null) {
            this.education = Education.valueOf(education);
        }
    }

    public String getGraduatedYear() {
        return graduatedYear;
    }

    public void setGraduatedYear(String graduatedYear) {
        this.graduatedYear = graduatedYear;
    }

    public String getGraduatedSchool() {
        return graduatedSchool;
    }

    public void setGraduatedSchool(String graduatedSchool) {
        this.graduatedSchool = graduatedSchool;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyPhone() {
        return emergencyPhone;
    }

    public void setEmergencyPhone(String emergencyPhone) {
        this.emergencyPhone = emergencyPhone;
    }

}
