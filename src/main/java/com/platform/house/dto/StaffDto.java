package com.platform.house.dto;

import com.platform.house.constant.Education;
import com.platform.house.constant.Gender;
import com.platform.house.constant.MaritalStatus;
import com.platform.house.constant.PoliticalStatus;
import com.platform.house.constant.RoleType;
import com.platform.house.constant.SysConstants;
import com.platform.house.domain.Staff;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;

import java.util.Date;

public class StaffDto {
    private Long id;
    private Long userId;
    private String name;
    private Gender gender;
    private String phone;
    private String idCard;
    private RoleType role;
    private Long storeId;
    private String storeName;
    private String avatar;
    private String jobNumber;
    private String entryDate;
    private String birthday;
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
    private Boolean enabled;

    public StaffDto(Staff staff) {
        BeanUtils.copyProperties(staff, this);
        if (staff.getUser() != null) {
            this.userId = staff.getUser().getId();
            this.name = staff.getUser().getRealname();
            this.gender = staff.getUser().getGender();
            this.phone = staff.getUser().getPhone();
            this.idCard = staff.getUser().getIdCard();
            this.avatar = staff.getUser().getAvatar();
            this.role = RoleType.valueOf(staff.getRole().getValue().toUpperCase());
        }
        if (staff.getStore() != null) {
            this.storeId = staff.getStore().getId();
            this.storeName = staff.getStore().getName();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public void setGender(Gender gender) {
        this.gender = gender;
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

    public void setRole(RoleType role) {
        this.role = role;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
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

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        if (entryDate != null) {
            this.entryDate = DateFormatUtils.format(entryDate, SysConstants.DATE_PARSE_PATTERNS[0]);
        }
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        if (birthday != null) {
            this.birthday = DateFormatUtils.format(birthday, SysConstants.DATE_PARSE_PATTERNS[0]);
        }
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

    public void setPolitical(PoliticalStatus political) {
        this.political = political;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
